import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Currency;

/**
 * Sale entity with @OneToMany → SaleLine and @ManyToOne → Customer.
 * Demonstrates proper relationship mapping.
 */
@Entity
@Table(name = "sales", indexes = {
    @Index(name = "idx_sale_customer", columnList = "customer_id"),
    @Index(name = "idx_sale_created", columnList = "createdAt")
})
public class Sale {

    static final Currency PHP = Currency.getInstance("PHP");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @WhenCreated
    private Instant createdAt;

    @WhenModified
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // ✅ LAZY fetch for collections (Rule 5)
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleLine> lines = new ArrayList<>();

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, length = 20)
    private String paymentType; // CASH, CARD, GCASH

    public Sale(Customer customer, List<SaleLine> lines) {
        this.customer = customer;
        this.lines = lines;
        lines.forEach(line -> line.setSale(this));
        this.totalAmount = computeTotal();
    }

    private BigDecimal computeTotal() {
        return lines.stream()
            .map(l -> l.getUnitPrice().multiply(BigDecimal.valueOf(l.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ── Getters / setters ──
    public Long getId() { return id; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public List<SaleLine> getLines() { return lines; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
}

/**
 * SaleLine — child of Sale, owns the relationship.
 */
@Entity
@Table(name = "sale_lines", indexes = {
    @Index(name = "idx_sale_line_sale", columnList = "sale_id"),
    @Index(name = "idx_sale_line_product", columnList = "product_id")
})
public class SaleLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    public SaleLine(Product product, int quantity, BigDecimal unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() { return id; }
    public Long getVersion() { return version; }
    public Sale getSale() { return sale; }
    public void setSale(Sale sale) { this.sale = sale; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
}
