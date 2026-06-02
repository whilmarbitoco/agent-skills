# Quick Reference: Inventory Transaction Modeling

## Core Records

| Record | Purpose | Key Fields |
|--------|---------|------------|
| `Money` | Monetary value object | `BigDecimal amount`, `Currency currency` |
| `StockMovement` | Immutable stock change event | `UUID id`, `String sku`, `int qty`, `Money unitCost`, `MovementType type`, `UUID journalEntryId`, `Instant timestamp` |
| `LedgerLine` | Single debit or credit line | `String accountCode`, `Money amount`, `Side side` |
| `JournalEntry` | Balanced group of ledger lines | `UUID id`, `List<LedgerLine>`, `Instant timestamp`, `String description` |
| `Quantity` | Amount + unit of measure | `BigDecimal amount`, `Unit unit` |

## Double-Entry Cheat Sheet

| Event | Debit | Credit |
|-------|-------|--------|
| Stock Purchase | INVENTORY_ASSET | ACCOUNTS_PAYABLE |
| Stock Sale (COGS) | COST_OF_GOODS_SOLD | INVENTORY_ASSET |
| Stock Adjustment (increase) | INVENTORY_ASSET | ADJUSTMENT_GAIN |
| Stock Adjustment (decrease) | ADJUSTMENT_LOSS | INVENTORY_ASPECT |
| Return to Supplier | ACCOUNTS_PAYABLE | INVENTORY_ASSET |

## BigDecimal Rules

```java
// Construction — always from String
new BigDecimal("19.99")          // ✅
BigDecimal.valueOf(19.99)        // ❌ comes from double
new BigDecimal(19.99)            // ❌ worst — floating-point artifact

// Arithmetic — always specify precision
a.add(b, MathContext.DECIMAL64)  // ✅
a.multiply(b, MathContext.DECIMAL64) // ✅
a.divide(b, RoundingMode.HALF_UP)    // ✅
a.divide(b)                           // ❌ throws ArithmeticException if non-terminating

// Comparison — use compareTo, never equals
a.compareTo(b) == 0              // ✅ 1.0 equals 1.00
a.equals(b)                      // ❌ 1.0 does NOT equal 1.00
```

## Common Pitfalls

| Pitfall | Fix |
|---------|-----|
| `LocalDateTime` for timestamps | Use `Instant` (UTC) |
| Mutable entities with setters | Use records with compact constructors |
| `equals()` for BigDecimal comparison | Use `compareTo() == 0` |
| Removing ledger entries | Append reversal entries instead |
| `double` math for money | `BigDecimal` from `String` only |
| Cross-currency arithmetic | Check currency match first |

## Java 21 Features Used

- **`record`** — all value objects are records (implicit `equals`, `hashCode`, `toString`)
- **Compact constructor** — validation without boilerplate parameter list
- **`switch` expressions** — movement type dispatch
- **`sealed` interfaces** — restrict `MovementType` hierarchy
- **`instanceof` pattern matching** — type checks with binding
