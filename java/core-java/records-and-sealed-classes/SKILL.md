---
name: records-and-sealed-classes
description: "Use when modeling domain data with records, sealed interfaces, and pattern matching. Covers JEP 395 (records), JEP 409 (sealed), JEP 440 (record patterns), and POS domain modeling."
category: java
tags:
  - java-21
  - core-java
  - records
  - sealed-classes
  - pattern-matching
---

# Records, Sealed Classes & Pattern Matching

**Skill ID:** `records-and-sealed-classes`  
**Domain:** `core-java` → `language-features`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**Requires:** none  
**Related:** `switch-pattern-matching, immutability, domain-driven-structure-lite`

**Version Compatibility:** Java `>= 21`

---

## Purpose

Java 21 stabilizes records (JEP 395), sealed classes (JEP 409), and record patterns (JEP 440). Together, they enable algebraic data modeling — immutable data carriers with closed type hierarchies. This skill covers when to use each, how to combine them, and their role in domain modeling for the POS system.

---

## Concepts Covered

- **Records** — immutable data carriers with compact syntax
- **Compact constructors** — validation in record constructors
- **Sealed classes/interfaces** — closed type hierarchies
- **Record patterns** — destructuring records in pattern matching
- **Exhaustive switch** — sealed types enable compiler-checked switches
- **Value objects** — records as Money, Address, DateRange

---

## Rules / Best Practices

1. **Records for all immutable value objects** — Money, Quantity, DateRange, Price
2. **Sealed hierarchies for domain type closures** — PaymentType, StockMovementType, ReportType
3. **Never extend a record** — records are final by design
4. **Use compact constructors for validation** — prevents invalid states at construction
5. **Combine sealed + switch for exhaustive handling** — compiler catches missing cases
6. **Records implement interfaces freely** — use for polymorphic behavior

---

## Code Conventions

| Convention | Rule |
|------------|------|
| Record naming | Noun — `Money`, `Address`, `SaleLine` |
| Value objects | Always a record, never a class with getters |
| Sealed interface | Name the interface, `permits` the records |
| Compact constructor | Throws IllegalArgumentException for invalid input |

```java
// ✅ CORRECT — Record for value object
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount must be non-negative");
        }
    }
    
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                "Cannot add %s to %s".formatted(other.currency, this.currency));
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
    
    public static Money of(double amount) {
        return new Money(BigDecimal.valueOf(amount), Currency.getInstance("PHP"));
    }
}

// ✅ CORRECT — Sealed hierarchy for domain types
public sealed interface PaymentType 
    permits PaymentType.Cash, PaymentType.Card, PaymentType.GCash {
    
    record Cash(BigDecimal tendered, BigDecimal change) implements PaymentType {}
    record Card(String reference, String provider) implements PaymentType {}
    record GCash(String reference, String mobile) implements PaymentType {}
}

// ✅ CORRECT — Exhaustive switch with sealed types
public Money calculateFee(PaymentType payment) {
    return switch (payment) {
        case PaymentType.Cash c  -> Money.of(0);
        case PaymentType.Card c   -> Money.of(2.50);
        case PaymentType.GCash g  -> Money.of(1.00);
    }; // Compiler verifies all cases covered — no default needed!
}

// ❌ WRONG — Using a class where a record is appropriate
public class ProductName { // Should be a record
    private final String value;
    public String getValue() { return value; } // Verbose getter
    @Override public boolean equals(Object o) { /* boilerplate */ }
    @Override public int hashCode() { /* boilerplate */ }
}
```

---

## Examples

### Domain Modeling — POS Value Objects

```java
// Money — always non-negative, currency-safe
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Money cannot be negative: " + amount);
    }
    public Money add(Money other) { /* ... */ }
    public Money subtract(Money other) { /* ... */ }
    public static Money zero() { return new Money(BigDecimal.ZERO, Currency.getInstance("PHP")); }
    public static Money of(double amount) { return new Money(BigDecimal.valueOf(amount), Currency.getInstance("PHP")); }
}

// SaleLine — item sold in a transaction
public record SaleLine(
    long productId,
    String productName,
    Money unitPrice,
    int quantity,
    Money lineTotal // unitPrice * quantity
) {
    public SaleLine {
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
    }
}
```

---

## Anti-Patterns

### ❌ Mutable record fields Never happens (records are immutable by design), but a related anti-pattern:

```java
// WRONG — wrapping a record in mutable state
public class Product {
    private Money price; // Should be final, and Money itself should be used directly
    public void setPrice(Money price) { this.price = price; }
}
```
**Why:** defeats the purpose of immutable value objects  
**Fix:** Use `Money` as a field in the entity. Recreate the record on update, don't mutate.

### ❌ Non-sealed open hierarchies for domain closures

```java
// WRONG — open hierarchy when all types are known
public interface PaymentType {} // Anyone can implement this!
```
**Why:** compiler can't verify exhaustive switch handling  
**Fix:** `sealed interface PaymentType permits Cash, Card, GCash {}`

---

## Checklists

### Setup
- [ ] Java 21+ verified
- [ ] IDE configured for Java 21 features

### Implementation
- [ ] All value objects modeled as records
- [ ] All type closures modeled as sealed interfaces
- [ ] Compact constructors validate invariants
- [ ] Exhaustive switch for sealed type handling

### Code Review
- [ ] No mutable value objects
- [ ] No `instanceof` chains — use pattern matching instead
- [ ] Sealed interfaces exhaustively handled in switch statements

---

## Project-Specific Guidance (Simple POS)

Domain value objects to model as records:
- `Money` (amount + currency)
- `SaleLine` (product + qty + price + total)
- `StockAdjustment` (product + qty + reason)
- `CashSessionSummary` (opened + closed + totalSales + difference)

Domain type closures to model as sealed:
- `PaymentType` → Cash, Card, GCash, Check
- `StockMovementType` → Purchase, Sale, Adjustment, Return
- `ReportType` → DailySales, Inventory, Revenue

---

## Recommended Reading

### Official (Tier 1)
- [JEP 395: Records](https://openjdk.org/jeps/395)
- [JEP 409: Sealed Classes](https://openjdk.org/jeps/409)
- [JEP 440: Record Patterns](https://openjdk.org/jeps/440)

---

## Exercises

### Exercise 1 — Model POS Value Objects (easy)

**Task:** Create records for `Money`, `SaleLine`, and `CashSessionSummary`. Write a compact constructor in `Money` that rejects negative amounts. Create a test that verifies the validation works.  
**Verification:** Negative amount throws IllegalArgumentException; `Money.of(100).add(Money.of(50))` equals `Money.of(150)`.

---

## AI/Agent Guide

### Strict Conventions
- Always create value objects as records, never as classes with manual equals/hashCode
- Always validate in compact constructors
- Always use sealed interfaces for closed domain type hierarchies

### Forbidden Patterns
- Classes with only getters + equals + hashCode (should be records)
- `instanceof` chains instead of pattern matching
- Open interfaces for domain types where all variants are known

### Preferred Libraries
- `java.util.Currency` — currency-safe money handling
- `java.math.BigDecimal` — never double for money

### Example Prompts

```
Create a Java 21 record for a POS domain value object called SaleLine with fields:
productId (long), productName (String), unitPrice (Money), quantity (int), lineTotal (Money).
Include a compact constructor that validates quantity > 0.
```

### Architecture Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Value objects | Records | Immutable, correct equals/hashCode, pattern-matchable |
| Type closures | Sealed interfaces | Compiler-enforced exhaustiveness |
| Money precision | BigDecimal | Avoids floating-point rounding errors |

### Code Templates

```java
// Template: Value Object Record
public record ValueName(Type field1, Type field2) {
    public ValueName {
        Objects.requireNonNull(field1, "field1");
        // validation rules
    }
}
```
