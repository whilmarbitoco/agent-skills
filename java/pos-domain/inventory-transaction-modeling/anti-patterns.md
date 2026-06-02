# Anti-Patterns: Inventory Transaction Modeling

## AP-1: Mutable StockMovement Entity

```java
// ❌ WRONG — mutable entity with setters allows post-creation tampering
public class StockMovement {
    private UUID id;
    private String sku;
    private int quantity;
    private BigDecimal unitCost;
    private LocalDateTime timestamp;

    // Setters allow anyone to change the movement after creation
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

// ✅ CORRECT — immutable record with compact constructor validation
public record StockMovement(
    UUID id,
    String sku,
    int quantity,
    Money unitCost,
    MovementType type,
    UUID journalEntryId,
    Instant timestamp
) {
    public StockMovement {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(sku, "sku must not be null");
        if (sku.isBlank()) throw new IllegalArgumentException("sku must not be blank");
        if (quantity == 0) throw new IllegalArgumentException("quantity must not be zero");
        Objects.requireNonNull(unitCost, "unitCost must not be null");
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(journalEntryId, "journalEntryId must not be null");
        Objects.requireNonNull(timestamp, "timestamp must not be null");
    }
}
```

## AP-2: Using double/BigDecimal.valueOf(double) for Money

```java
// ❌ WRONG — floating-point precision loss
BigDecimal price = BigDecimal.valueOf(19.99); // imprecise — comes from double
double total = quantity * price.doubleValue(); // NEVER use double for money

// ✅ CORRECT — string-based BigDecimal, always
Money unitPrice = new Money(new BigDecimal("19.99"), Currency.PHP);
Money total = unitPrice.multiply(quantity); // stays in BigDecimal domain
```

## AP-3: Unbalanced Journal Entries

```java
// ❌ WRONG — single-sided entry, no balancing credit
public JournalEntry createStockIn(String sku, int qty, Money cost) {
    return new JournalEntry(
        UUID.randomUUID(),
        List.of(new LedgerLine("INVENTORY", cost.multiply(qty), Side.DEBIT)),
        Instant.now()
    );
}

// ✅ CORRECT — balanced debit+credit pair
public JournalEntry createStockIn(String sku, int qty, Money cost) {
    Money total = cost.multiply(qty);
    return new JournalEntry(
        UUID.randomUUID(),
        List.of(
            new LedgerLine("INVENTORY_ASSET", total, Side.DEBIT),
            new LedgerLine("ACCOUNTS_PAYABLE", total, Side.CREDIT)
        ),
        Instant.now()
    );
}
```

## AP-4: Mutable Ledger (Remove/Update Entries)

```java
// ❌ WRONG — ledger allows removing or updating entries
public class TransactionLedger {
    private final List<JournalEntry> entries = new ArrayList<>();

    public void addEntry(JournalEntry entry) { entries.add(entry); }
    public void removeEntry(UUID id) { entries.removeIf(e -> e.id().equals(id)); } // NEVER
    public void correctEntry(UUID id, JournalEntry corrected) { /* mutation */ }   // NEVER
}

// ✅ CORRECT — append-only ledger
public class TransactionLedger {
    private final List<JournalEntry> entries = new ArrayList<>();

    public void addEntry(JournalEntry entry) {
        Objects.requireNonNull(entry, "entry must not be null");
        if (!entry.isBalanced()) throw new UnbalancedJournalException(entry);
        entries.add(entry);
    }

    // To "correct" an error, post a reversing entry + a new correct entry
    public void reverseEntry(UUID originalEntryId, String reason) {
        JournalEntry original = entries.stream()
            .filter(e -> e.id().equals(originalEntryId))
            .findFirst()
            .orElseThrow(() -> new EntryNotFoundException(originalEntryId));
        entries.add(original.createReversal(reason));
    }

    public List<JournalEntry> entries() {
        return Collections.unmodifiableList(entries);
    }
}
```

## AP-5: Missing Currency in Money Arithmetic

```java
// ❌ WRONG — adding money without currency check
public Money add(Money a, Money b) {
    return new Money(a.amount().add(b.amount()), a.currency()); // no currency match check
}

// ✅ CORRECT — enforce currency compatibility
public Money add(Money a, Money b) {
    if (a.currency() != b.currency()) {
        throw new CurrencyMismatchException(a.currency(), b.currency());
    }
    return new Money(a.amount().add(b.amount(), MATH_CONTEXT), a.currency());
}
```

## AP-6: Using LocalDateTime Instead of Instant

```java
// ❌ WRONG — LocalDateTime has no timezone, ambiguous for audit
public record StockMovement(..., LocalDateTime timestamp) {}

// ✅ CORRECT — Instant is unambiguous UTC point-in-time
public record StockMovement(..., Instant timestamp) {}
```

## AP-7: No Validation in Compact Constructor

```java
// ❌ WRONG — record with no validation accepts garbage data
public record Money(BigDecimal amount, Currency currency) {}

// ✅ CORRECT — compact constructor validates invariants
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(currency, "currency must not be null");
        if (amount.scale() > currency.defaultFractionDigits()) {
            throw new IllegalArgumentException(
                "amount scale %d exceeds currency %s max fraction digits %d"
                    .formatted(amount.scale(), currency, currency.defaultFractionDigits())
            );
        }
    }
}
```