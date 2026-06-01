import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Value objects as records. Sealed hierarchy for domain types.
 * Exhaustive pattern matching — compiler verifies all cases.
 */

// Money — immutable, validated at construction
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Money cannot be negative: " + amount);
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

// SaleLine — transaction line item
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

// PaymentType — sealed hierarchy, exhaustive switch
public sealed interface PaymentType
    permits PaymentType.Cash, PaymentType.Card, PaymentType.GCash {

    record Cash(BigDecimal tendered, BigDecimal change) implements PaymentType {}
    record Card(String reference, String provider) implements PaymentType {}
    record GCash(String reference, String mobile) implements PaymentType {}
}

// Exhaustive switch — compiler verifies all cases, no default needed
class PaymentProcessor {

    public Money calculateFee(PaymentType payment) {
        return switch (payment) {
            case PaymentType.Cash c  -> Money.of(0);
            case PaymentType.Card c   -> Money.of(2.50);
            case PaymentType.GCash g  -> Money.of(1.00);
        };
    }

    // Pattern matching with instanceof
    public boolean isDigital(PaymentType payment) {
        return switch (payment) {
            case PaymentType.Cash _  -> false;
            case PaymentType.Card _  -> true;
            case PaymentType.GCash _ -> true;
        };
    }
}

// ProductView — record for UI consumption (never expose Ebean entity directly)
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
