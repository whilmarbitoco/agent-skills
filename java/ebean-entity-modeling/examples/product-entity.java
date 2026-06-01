import io.ebean.Database;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_product_name", columnList = "name"),
    @Index(name = "idx_product_category", columnList = "category")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    @Column(length = 100)
    private String category;

    @Column(nullable = false)
    private boolean active = true;

    @Version
    private Long version;

    @WhenCreated
    private Instant createdAt;

    @WhenModified
    private Instant updatedAt;

    // Required for Ebean
    public Product() {}

    public Product(String name, BigDecimal price, int stock, String category) {
        this.name = Objects.requireNonNull(name);
        this.price = Objects.requireNonNull(price);
        this.stock = stock;
        this.category = category;
    }

    // Domain behavior — not anemic
    public void adjustStock(int delta) {
        int newStock = this.stock + delta;
        if (newStock < 0) {
            throw new IllegalArgumentException(
                "Stock cannot be negative: " + this.stock + " + " + delta);
        }
        this.stock = newStock;
    }

    public void deactivate() {
        this.active = false;
    }

    // Getters only — no setters for domain fields
    public Long getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public int getStock() { return stock; }
    public String getCategory() { return category; }
    public boolean isActive() { return active; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
