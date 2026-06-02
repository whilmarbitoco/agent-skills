package com.example.core.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Example value record used across core and desktop modules.
 * Lives in core-api (or core-model) — the module both layers depend on.
 */
public record Product(
    long id,
    String name,
    BigDecimal price,
    int quantity,
    Instant createdAt
) {
    public Product {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(price, "price must not be null");
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price must be >= 0");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must be >= 0");
        }
    }

    public Product withQuantity(int newQuantity) {
        return new Product(id, name, price, newQuantity, createdAt);
    }
}
