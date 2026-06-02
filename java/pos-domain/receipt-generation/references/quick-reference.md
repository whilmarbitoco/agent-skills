# Quick Reference: Receipt Generation

## Receipt Data Model

| Record | Key Fields |
|--------|------------|
| `Receipt` | `ReceiptNumber`, `transactionId`, `List<ReceiptItem>`, `ReceiptTotals`, `PaymentInfo`, `cashierName`, `storeName`, `storeTin`, `Instant timestamp` |
| `ReceiptItem` | `String sku`, `String description`, `int quantity`, `Money unitPrice`, `Money discount`, `Money lineTotal` |
| `ReceiptTotals` | `Money subtotal`, `Money vatAmount`, `Money discountTotal`, `Money grandTotal` |
| `PaymentInfo` | `Money amountTendered`, `Money changeDue`, `String paymentMethod` |
| `ReceiptNumber` | `String registerId`, `LocalDate date`, `long sequence` → `REG01-20260601-00001` |

## JasperReports Workflow

```
.jrxml template → JasperCompileManager.compileReport() → JasperReport (cache this)
                                                        ↓
Receipt data → JasperFillManager.fillReport() → JasperPrint
                                                        ↓
JasperExportManager.exportReportToPdf() → byte[] (PDF output)
```

## ESC/POS Command Reference

| Command | Hex | Purpose |
|---------|-----|---------|
| Initialize | `ESC @` | Reset printer |
| Bold on | `ESC E 1` | Enable bold |
| Bold off | `ESC E 0` | Disable bold |
| Double height | `ESC ! 0x10` | Double-height text |
| Align center | `ESC a 1` | Center alignment |
| Align left | `ESC a 0` | Left alignment |
| Cut paper | `GS V 65 1` | Partial cut |
| Print QR | `GS ( k` | QR code command sequence |

## QR Code URL Format

```
https://receipts.example.com/verify/{transactionId}
```

## Receipt Number Format

```
{registerId}-{YYYYMMDD}-{sequence}
Example: REG01-20260601-00001
```

## PHP VAT Calculation

```
VAT Rate: 12%
VATable Amount = Subtotal / 1.12
VAT Amount = Subtotal - VATable Amount
Grand Total = Subtotal - Discounts
```

## Common Pitfalls

| Pitfall | Fix |
|---------|-----|
| Compiling .jrxml per request | Cache `JasperReport` instance |
| Hardcoded store info | Pass as template parameters |
| `double` for totals | `BigDecimal` from `String` |
| Mutable receipt records | Use Java 21 records |
| Raw UTF-8 to thermal printer | Use CP437 code page |
| UUID as receipt number | Sequential with register prefix |
