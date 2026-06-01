---
name: records-and-sealed-classes
description: >
  Extends agent's knowledge of Java 21 records (JEP 395), sealed classes (JEP 409),
  record patterns (JEP 440), and pattern matching for switch (JEP 441). Use when
  modeling domain value objects, defining closed type hierarchies, or refactoring
  instanceof chains.
compatibility: Java 21+ required
metadata:
  domain: core-java
  level: intermediate
  stack: [java-21]
  related: [switch-pattern-matching, immutability, domain-driven-structure-lite]
  version: "1.1.0"
---

# Records, Sealed Classes & Pattern Matching

Java 21 stabilizes records, sealed classes, and record patterns. Together they
enable algebraic data modeling — immutable data carriers with closed type
hierarchies that the compiler can verify exhaustively.

## When This Skill Applies

- Modeling value objects (Money, Address, DateRange, SaleLine)
- Defining closed domain type hierarchies (PaymentType, StockMovementType)
- Refactoring `instanceof` + cast chains to pattern matching
- Writing exhaustive `switch` expressions over sealed types

## Core Rules

1. **Records for all immutable value objects.** Never a class with manual equals/hashCode.
2. **Sealed interfaces for domain type closures.** Compiler verifies exhaustive handling.
3. **Compact constructors for validation.** Reject invalid states at construction time.
4. **Exhaustive switch over sealed types.** No `default` needed — compiler catches missing cases.
5. **Records implement interfaces freely.** Use for polymorphic behavior.
6. **Never extend a record.** Records are final by design.

## Examples

### Value objects as records

```java
// ✅ CORRECT — record with compact constructor validation
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("amount must be non-negative: " + amount);
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException("Currency mismatch");
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public static Money of(double amount) {
        return new Money(BigDecimal.valueOf(amount), Currency.getInstance("PHP"));
    }
}
```

```java
// ❌ WRONG — class with manual equals/hashCode where a record belongs
public class ProductName {
    private final String value;
    public String getValue() { return value; }
    @Override public boolean equals(Object o) { /* boilerplate */ }
    @Override public int hashCode() { /* boilerplate */ }
}
```

### Sealed hierarchy with exhaustive switch

```java
// ✅ CORRECT — sealed interface, exhaustive switch, no default
public sealed interface PaymentType
    permits PaymentType.Cash, PaymentType.Card, PaymentType.GCash {

    record Cash(BigDecimal tendered, BigDecimal change) implements PaymentType {}
    record Card(String reference, String provider) implements PaymentType {}
    record GCash(String reference, String mobile) implements PaymentType {}
}

public Money calculateFee(PaymentType payment) {
    return switch (payment) {
        case PaymentType.Cash c  -> Money.of(0);
        case PaymentType.Card c   -> Money.of(2.50);
        case PaymentType.GCash g  -> Money.of(1.00);
    }; // Compiler verifies all cases — no default needed
}
```

### Record patterns with instanceof

```java
// ✅ CORRECT — pattern matching with variable binding
if (payment instanceof PaymentType.Card card && card.provider().equals("VISA")) {
    return Money.of(2.00);
}

// ❌ WRONG — instanceof + manual cast
if (payment instanceof PaymentType.Card) {
    PaymentType.Card card = (PaymentType.Card) payment;
    if (card.provider().equals("VISA")) { /* ... */ }
}
```

### POS domain modeling

```java
// SaleLine — immutable transaction line item
public record SaleLine(
    long productId,
    String productName,
    Money unitPrice,
    int quantity,
    Money lineTotal
) {
    public SaleLine {
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
    }
}

// StockMovementType — sealed hierarchy for inventory
public sealed interface StockMovementType
    permits StockMovementType.Purchase, StockMovementType.Sale,
            StockMovementType.Adjustment, StockMovementType.Return {

    record Purchase(long poId, Money cost) implements StockMovementType {}
    record Sale(long saleId) implements StockMovementType {}
    record Adjustment(String reason, int delta) implements StockMovementType {}
    record Return(long originalSaleId) implements StockMovementType {}
}
```

## Conventions

| Aspect | Convention |
|--------|-----------|
| Record naming | Noun — `Money`, `SaleLine`, `CashSessionSummary` |
| Value objects | Always a record, never a class with getters |
| Sealed interface | Name the interface, `permits` the record variants |
| Compact constructor | Throws `IllegalArgumentException` for invalid input |

## Anti-Patterns

- **Mutable record fields** — impossible by design, but wrapping records in mutable containers defeats the purpose
- **Non-sealed open hierarchies** for domain closures — compiler can't verify exhaustiveness
- **`instanceof` + cast chains** — use pattern matching instead
- **Anemic records** — records should have domain behavior (add, subtract, validate), not just data

## Verification

### Implementation
- [ ] All value objects modeled as records
- [ ] All type closures modeled as sealed interfaces
- [ ] Compact constructors validate invariants
- [ ] Exhaustive switch for sealed type handling

### Code Review
- [ ] No mutable value objects
- [ ] No `instanceof` chains — use pattern matching
- [ ] Sealed interfaces exhaustively handled in switch

## Reference Material

- `references/records-sealed-patterns.md` — full JEP reference, advanced patterns
- `assets/pos-domain-model.md` — complete POS domain model using records + sealed types

## Recommended Reading

- [JEP 395: Records](https://openjdk.org/jeps/395)
- [JEP 409: Sealed Classes](https://openjdk.org/jeps/409)
- [JEP 440: Record Patterns](https://openjdk.org/jeps/440)
- [JEP 441: Pattern Matching for switch](https://openjdk.org/jeps/441)
