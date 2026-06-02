package com.pos.receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

// ─── Currency & Money (reusable value objects) ───────────────────────────────
enum Currency {
    PHP("PHP", 2), USD("USD", 2);
    private final String code;
    private final int fractionDigits;
    Currency(String code, int fractionDigits) { this.code = code; this.fractionDigits = fractionDigits; }
    public String getCode() { return code; }
    public int defaultFractionDigits() { return fractionDigits; }
}

record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);
    }
    public Money add(Money other) {
        assertSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }
    public Money multiply(int qty) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(qty)), this.currency);
    }
    public Money multiply(BigDecimal factor) {
        return new Money(this.amount.multiply(factor), this.currency);
    }
    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }
    private void assertSameCurrency(Money other) {
        if (this.currency != other.currency)
            throw new IllegalArgumentException("Currency mismatch");
    }
}

// ─── Receipt Data Records ────────────────────────────────────────────────────
record ReceiptItem(
    String sku,
    String description,
    int quantity,
    Money unitPrice,
    Money discount,
    Money lineTotal
) {
    public ReceiptItem {
        Objects.requireNonNull(sku);
        Objects.requireNonNull(description);
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be positive");
        Objects.requireNonNull(unitPrice);
        Objects.requireNonNull(discount);
        Objects.requireNonNull(lineTotal);
    }
}

record ReceiptTotals(
    Money subtotal,
    Money vatAmount,
    Money discountTotal,
    Money grandTotal,
    String vatBreakdown // e.g., "VATable: ₱100.00, VAT: ₱12.00, VAT-Exempt: ₱0.00"
) {
    public ReceiptTotals {
        Objects.requireNonNull(subtotal);
        Objects.requireNonNull(vatAmount);
        Objects.requireNonNull(discountTotal);
        Objects.requireNonNull(grandTotal);
    }
}

record PaymentInfo(
    Money amountTendered,
    Money changeDue,
    String paymentMethod // "CASH", "CARD", "GCASH", etc.
) {
    public PaymentInfo {
        Objects.requireNonNull(amountTendered);
        Objects.requireNonNull(changeDue);
        Objects.requireNonNull(paymentMethod);
    }
}

record ReceiptNumber(String registerId, LocalDate date, long sequence) {
    @Override
    public String toString() {
        return "%s-%s-%05d".formatted(
            registerId,
            date.format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE),
            sequence
        );
    }
}

record Receipt(
    ReceiptNumber receiptNumber,
    String transactionId,
    List<ReceiptItem> items,
    ReceiptTotals totals,
    PaymentInfo payment,
    String cashierName,
    String storeName,
    String storeAddress,
    String storeTin,
    Instant timestamp
) {
    public Receipt {
        Objects.requireNonNull(receiptNumber);
        Objects.requireNonNull(transactionId);
        items = List.copyOf(items); // defensive immutable copy
        Objects.requireNonNull(totals);
        Objects.requireNonNull(payment);
        Objects.requireNonNull(cashierName);
        Objects.requireNonNull(storeName);
        Objects.requireNonNull(timestamp);
    }
}

// ─── Receipt Number Generator ────────────────────────────────────────────────
class ReceiptNumberGenerator {
    private final String registerId;
    private long sequence = 0;
    private LocalDate lastDate = LocalDate.now();

    public ReceiptNumberGenerator(String registerId) {
        this.registerId = Objects.requireNonNull(registerId);
    }

    public synchronized ReceiptNumber next() {
        LocalDate today = LocalDate.now();
        if (!today.equals(lastDate)) {
            sequence = 0;
            lastDate = today;
        }
        sequence++;
        return new ReceiptNumber(registerId, today, sequence);
    }
}

// ─── Receipt Builder ─────────────────────────────────────────────────────────
class ReceiptBuilder {
    private ReceiptNumber receiptNumber;
    private String transactionId;
    private final List.Builder<ReceiptItem> items = List.builder();
    private PaymentInfo payment;
    private String cashierName;
    private String storeName = "MY STORE";
    private String storeAddress = "123 Main St";
    private String storeTin = "000-000-000-000";

    public ReceiptBuilder receiptNumber(ReceiptNumber rn) { this.receiptNumber = rn; return this; }
    public ReceiptBuilder transactionId(String id) { this.transactionId = id; return this; }
    public ReceiptBuilder addItem(ReceiptItem item) { items.add(item); return this; }
    public ReceiptBuilder payment(PaymentInfo p) { this.payment = p; return this; }
    public ReceiptBuilder cashier(String name) { this.cashierName = name; return this; }
    public ReceiptBuilder store(String name, String address, String tin) {
        this.storeName = name; this.storeAddress = address; this.storeTin = tin; return this;
    }

    public Receipt build() {
        List<ReceiptItem> itemList = items.build();
        Money subtotal = itemList.stream()
            .map(ReceiptItem::lineTotal)
            .reduce(Money.zero(Currency.PHP), Money::add);
        Money discounts = itemList.stream()
            .map(ReceiptItem::discount)
            .reduce(Money.zero(Currency.PHP), Money::add);
        Money vat = subtotal.multiply(new BigDecimal("0.12")); // 12% VAT
        Money grandTotal = subtotal.add(vat).subtract(discounts);

        ReceiptTotals totals = new ReceiptTotals(
            subtotal, vat, discounts, grandTotal,
            "VATable: " + subtotal + ", VAT: " + vat
        );

        return new Receipt(
            receiptNumber, transactionId, itemList, totals, payment,
            cashierName, storeName, storeAddress, storeTin, Instant.now()
        );
    }
}

// ─── Receipt Service (Constructor Injection) ─────────────────────────────────
class ReceiptService {
    private final ReceiptNumberGenerator numberGenerator;

    public ReceiptService(ReceiptNumberGenerator numberGenerator) {
        this.numberGenerator = Objects.requireNonNull(numberGenerator);
    }

    public Receipt createReceipt(List<ReceiptItem> items, PaymentInfo payment, String cashier) {
        var builder = new ReceiptBuilder()
            .receiptNumber(numberGenerator.next())
            .transactionId(java.util.UUID.randomUUID().toString())
            .payment(payment)
            .cashier(cashier);

        items.forEach(builder::addItem);
        return builder.build();
    }

    public String verificationUrl(Receipt receipt) {
        return "https://receipts.example.com/verify/" + receipt.transactionId();
    }
}

// ─── Demo ─────────────────────────────────────────────────────────────────────
class ReceiptGenerationDemo {
    public static void main(String[] args) {
        var generator = new ReceiptNumberGenerator("REG01");
        var service = new ReceiptService(generator);

        var item1 = new ReceiptItem("SKU-001", "Rice 5kg", 2,
            new Money(new BigDecimal("89.00"), Currency.PHP),
            Money.zero(Currency.PHP),
            new Money(new BigDecimal("178.00"), Currency.PHP));

        var item2 = new ReceiptItem("SKU-002", "Cooking Oil 1L", 1,
            new Money(new BigDecimal("75.50"), Currency.PHP),
            new Money(new BigDecimal("5.00"), Currency.PHP),
            new Money(new BigDecimal("70.50"), Currency.PHP));

        var payment = new PaymentInfo(
            new Money(new BigDecimal("300.00"), Currency.PHP),
            new Money(new BigDecimal("43.84"), Currency.PHP),
            "CASH"
        );

        Receipt receipt = service.createReceipt(List.of(item1, item2), payment, "Juan Dela Cruz");

        System.out.println("══════════════════════════════════");
        System.out.println("  " + receipt.storeName());
        System.out.println("  " + receipt.storeAddress());
        System.out.println("  TIN: " + receipt.storeTin());
        System.out.println("──────────────────────────────────");
        System.out.println("  Receipt: " + receipt.receiptNumber());
        System.out.println("  Cashier: " + receipt.cashierName());
        System.out.println("  Date: " + receipt.timestamp());
        System.out.println("──────────────────────────────────");
        receipt.items().forEach(item ->
            System.out.printf("  %s%n  %d x %s = %s%n", item.description(), item.quantity(), item.unitPrice(), item.lineTotal())
        );
        System.out.println("──────────────────────────────────");
        var t = receipt.totals();
        System.out.println("  Subtotal: " + t.subtotal());
        System.out.println("  VAT (12%): " + t.vatAmount());
        System.out.println("  Discount: -" + t.discountTotal());
        System.out.println("  TOTAL: " + t.grandTotal());
        System.out.println("──────────────────────────────────");
        var p = receipt.payment();
        System.out.println("  Tendered: " + p.amountTendered());
        System.out.println("  Change: " + p.changeDue());
        System.out.println("  Method: " + p.paymentMethod());
        System.out.println("══════════════════════════════════");
        System.out.println("  Verify: " + service.verificationUrl(receipt));
    }
}
