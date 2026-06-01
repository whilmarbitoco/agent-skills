# Records & Sealed Types Anti-Patterns

## Using classes with manual equals/hashCode where records belong

```java
// WRONG — 30 lines of boilerplate for what a record does in 1
public class Money {
    private final BigDecimal amount;
    public BigDecimal getAmount() { return amount; }
    @Override public boolean equals(Object o) { ... }
    @Override public int hashCode() { ... }
}
```

**Use record: `public record Money(BigDecimal amount) { public Money { Objects.requireNonNull(amount); } }`**

## Mutable wrapper around immutable record

```java
// WRONG — defeats purpose of immutability
public class Product {
    private Money price; // setter can mutate via reference
    public void setPrice(Money price) { this.price = price; }
}
```

**Recreate the record on update: `this.price = new Money(newAmount)` not mutable setters.**

## instanceof + cast chains instead of pattern matching

```java
// WRONG — verbose, compiler doesn't check exhaustiveness
if (payment instanceof PaymentType.Card) {
    PaymentType.Card card = (PaymentType.Card) payment;
    return Money.of(2.50);
}
if (payment instanceof PaymentType.GCash) { ... }
```

**Use exhaustive switch: `return switch (payment) { case PaymentType.Card c -> Money.of(2.50); case PaymentType.GCash g -> Money.of(1.00); case PaymentType.Cash c -> Money.of(0) };`**

## Non-sealed open interface for domain closure

```java
// WRONG — anyone can implement, compiler can't check exhaustiveness
public interface PaymentType {}
```

**Seal it: `public sealed interface PaymentType permits Cash, Card, GCash {}`.**

## Records for entities that need lazy loading

```java
// WRONG — Ebean needs mutable classes for lazy loading/proxies
public record Product(Long id, String name, BigDecimal price) {}
```

**Use records only for value objects (Money, SaleLine, views). Ebean entities stay as classes.**
