# Anti-Patterns: Receipt Generation

## AP-1: Compiling JasperReports Template on Every Request

```java
// ❌ WRONG — compiling .jrxml on every receipt generation (expensive XML parsing)
public byte[] generateReceipt(Receipt receipt) throws Exception {
    JasperReport report = JasperCompileManager.compileReport("receipt.jrxml"); // DON'T
    // ...
}

// ✅ CORRECT — compile once, cache the JasperReport
public class ReceiptTemplateService {
    private final JasperReport cachedReport;

    public ReceiptTemplateService(String templatePath) throws Exception {
        this.cachedReport = JasperCompileManager.compileReport(
            getClass().getResourceAsStream(templatePath)
        );
    }

    public byte[] generateReceipt(Receipt receipt) throws Exception {
        Map<String, Object> params = buildParameters(receipt);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(receipt.items());
        JasperPrint print = JasperFillManager.fillReport(cachedReport, params, dataSource);
        return JasperExportManager.exportReportToPdf(print);
    }
}
```

## AP-2: Using double for Receipt Totals

```java
// ❌ WRONG — floating-point arithmetic for receipt totals
double subtotal = 0;
for (ReceiptItem item : items) {
    subtotal += item.qty() * item.unitPrice(); // precision loss!
}
double tax = subtotal * 0.12; // 12% VAT — imprecise

// ✅ CORRECT — BigDecimal throughout
Money subtotal = items.stream()
    .map(ReceiptItem::lineTotal)
    .reduce(Money.zero(Currency.PHP), Money::add);

Money tax = subtotal.multiply(new BigDecimal("0.12")); // exact 12% VAT
```

## AP-3: Hardcoded Store Information in Template

```java
// ❌ WRONG — store name, address, TIN hardcoded in .jrxml
// Changing store details requires editing XML template

// ✅ CORRECT — pass store info as parameters
public Map<String, Object> buildParameters(Receipt receipt) {
    return Map.of(
        "storeName", storeConfig.name(),
        "storeAddress", storeConfig.address(),
        "storeTin", storeConfig.tin(),
        "accNo", storeConfig.accNumber(),
        "permitNo", storeConfig.permitNumber(),
        "transactionId", receipt.transactionId(),
        "dateTime", receipt.timestamp(),
        "cashier", receipt.cashierName()
    );
}
```

## AP-4: Generating QR Code on Every Request Without Caching

```java
// ❌ WRONG — generating QR code from scratch for identical receipt URLs
private BufferedImage generateQR(String url) {
    // ZXing encode is moderately expensive
    return QRCodeWriter.encode(url, BarcodeFormat.QR_CODE, 120, 120);
}

// ✅ CORRECT — cache QR codes for identical URLs (e.g., same verification URL)
private final Map<String, BufferedImage> qrCache = new ConcurrentHashMap<>();

private BufferedImage generateQR(String url) {
    return qrCache.computeIfAbsent(url, u -> {
        try {
            var bitMatrix = new QRCodeWriter().encode(u, BarcodeFormat.QR_CODE, 120, 120);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new ReceiptGenerationException("QR generation failed", e);
        }
    });
}
```

## AP-5: Not Handling Thermal Printer Character Encoding

```java
// ❌ WRONG — sending raw UTF-8 to thermal printer (expects code page)
outputStream.write(receiptText.getBytes(StandardCharsets.UTF_8)); // garbled output

// ✅ CORRECT — use printer's native code page
public byte[] toEscPos(Receipt receipt) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    // Initialize printer
    out.write(ESC);
    out.write('@');

    // Set code page (e.g., CP437 for most thermal printers)
    out.write(ESC);
    out.write('t');
    out.write(0); // code page 0

    // Print receipt content
    out.write(receiptText.getBytes("CP437"));

    // Cut paper
    out.write(GS);
    out.write('V');
    out.write(65);
    out.write(1);

    return out.toByteArray();
}
```

## AP-6: Mutable Receipt Records

```java
// ❌ WRONG — receipt with setters allows post-creation modification
public class Receipt {
    private List<ReceiptItem> items;
    public void setItems(List<ReceiptItem> items) { this.items = items; }
    public void addItem(ReceiptItem item) { this.items.add(item); } // mutable!
}

// ✅ CORRECT — immutable record
public record Receipt(
    String transactionId,
    List<ReceiptItem> items,
    ReceiptTotals totals,
    PaymentInfo payment,
    String cashierName,
    Instant timestamp
) {
    public Receipt {
        items = List.copyOf(items); // defensive immutable copy
    }
}
```

## AP-7: Missing Receipt Number Sequence

```java
// ❌ WRONG — using UUID as receipt number (too long, not sequential)
String receiptNumber = UUID.randomUUID().toString(); // "a1b2c3d4-..."

// ✅ CORRECT — sequential receipt number with register prefix
// Format: REG-YYYYMMDD-XXXXX (e.g., REG01-20260601-00001)
public record ReceiptNumber(String registerId, LocalDate date, long sequence) {
    @Override
    public String toString() {
        return "%s-%s-%05d".formatted(
            registerId,
            date.format(DateTimeFormatter.BASIC_ISO_DATE),
            sequence
        );
    }
}
```
