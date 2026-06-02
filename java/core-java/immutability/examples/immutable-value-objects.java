package com.simplepos.domain;

import java.util.Currency;
import java.util.Objects;

/**
 * Immutability patterns in Java 21.
 * Records for value objects. Defensive copies. With-er methods.
 */

// Money — validated at construction, no setters
public record Money(java.math.BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
        if (amount.compareTo(java.math.BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("amount must be non-negative");
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException("Currency mismatch");
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public static Money of(double amount) {
        return new Money(java.math.BigDecimal.valueOf(amount), Currency.getInstance("PHP"));
    }
}

// SaleLine — copy-on-modify via compact constructor
public record SaleLine(
    long productId,
    String productName,
    Money unitPrice,
    int quantity,
    Money lineTotal
) {
    public SaleLine {
        Objects.requireNonNull(unitPrice);
        Objects.requireNonNull(lineTotal);
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
    }

    // With-er method for copy-on-modify
    public SaleLine withQuantity(int newQty) {
        Money newTotal = Money.of(newQty).multiply(unitPrice.amount());
        return new SaleLine(productId, productName, unitPrice, newQty, newTotal);
    }
}

// ProductView — immutable view for UI (never expose Ebean entity directly)
public record ProductView(
    long id,
    String name,
    Money price,
    int stock,
    String category
) {
    public static ProductView fromEntity(Product entity) {
        return new ProductView(
            entity.getId(),
            entity.getName(),
            entity.getPrice(),
            entity.getStock(),
            entity.getCategory()
        );
    }
}
