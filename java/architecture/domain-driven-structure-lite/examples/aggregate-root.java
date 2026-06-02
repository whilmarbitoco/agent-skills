package com.simplepos.domain;

/**
 * Domain-driven structure for desktop apps.
 * Aggregate roots, value objects, repositories only for aggregates.
 */

@Entity
@Table(name = "sales")
public class Sale {

    @Id @GeneratedValue private Long id;
    private Instant createdAt;
    private Money total;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sale")
    private List<SaleLine> lines = new ArrayList<>();

    public void addLine(Product product, int qty, Money price) {
        this.lines.add(new SaleLine(this, product.getId(), product.getName(), price, qty));
        this.total = recalculateTotal();
    }

    private Money recalculateTotal() {
        return lines.stream()
            .map(SaleLine::lineTotal)
            .reduce(Money.zero(), Money::add);
    }

    public Long getId() { return id; }
    public Instant getCreatedAt() { return createdAt; }
    public Money getTotal() { return total; }
    public List<SaleLine> getLines() { return List.copyOf(lines); }
}

// Value object — immutable record with validation
public record Money(java.math.BigDecimal amount) {
    public Money { Objects.requireNonNull(amount); }
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }
    public static Money zero() {
        return new Money(java.math.BigDecimal.ZERO);
    }
    public static Money of(double amount) {
        return new Money(java.math.BigDecimal.valueOf(amount));
    }
}

// Repository only for aggregate roots
public interface SaleRepository {
    Sale save(Sale sale);
    Optional<Sale> findById(Long id);
    List<Sale> findByDateRange(Instant from, Instant to);
}
