import jakarta.persistence.*;
import io.ebean.annotation.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Full Ebean entity example: Customer + Order + OrderLine.
 * Demonstrates @Entity, @ManyToOne, @OneToMany, @SoftDelete,
 * @Version, @WhenCreated, @DbEnumType, and equals/hashCode.
 * Java 21, Ebean 15.
 */
public final class EbeanEntitiesExample {

    private EbeanEntitiesExample() {}

    // --- Enum ---

    enum OrderStatus { PENDING, SHIPPED, DELIVERED, CANCELLED }

    // --- Customer entity ---

    @Entity
    @Table(name = "customers")
    static class Customer {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false, unique = true)
        private String email;

        @Version
        private Long version;

        @WhenCreated
        private Instant createdAt;

        @WhenModified
        private Instant updatedAt;

        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
        private List<Order> orders = new ArrayList<>();

        protected Customer() {}

        public Customer(String name, String email) {
            this.name = Objects.requireNonNull(name);
            this.email = Objects.requireNonNull(email);
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public Long getVersion() { return version; }
        public Instant getCreatedAt() { return createdAt; }
        public Instant getUpdatedAt() { return updatedAt; }
        public List<Order> getOrders() { return List.copyOf(orders); }

        public void addOrder(Order order) {
            orders.add(order);
            order.setCustomer(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Customer that)) return false;
            return id != null && id.equals(that.id);
        }

        @Override
        public int hashCode() { return id != null ? id.hashCode() : 0; }
    }

    // --- Order entity ---

    @Entity
    @Table(name = "orders")
    static class Order {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(optional = false)
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @DbEnumType(ENUM)
        @Column(nullable = false)
        private OrderStatus status = OrderStatus.PENDING;

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OrderLine> lines = new ArrayList<>();

        @Version
        private Long version;

        @WhenCreated
        private Instant createdAt;

        @SoftDelete
        private boolean deleted;

        protected Order() {}

        public void setCustomer(Customer customer) { this.customer = customer; }
        public void addLine(OrderLine line) {
            lines.add(line);
            line.setOrder(this);
        }

        public Long getId() { return id; }
        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { this.status = status; }
        public List<OrderLine> getLines() { return List.copyOf(lines); }
    }

    // --- OrderLine entity ---

    @Entity
    @Table(name = "order_lines")
    static class OrderLine {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(optional = false)
        @JoinColumn(name = "order_id", nullable = false)
        private Order order;

        @Column(nullable = false)
        private String productName;

        @Column(nullable = false)
        private int quantity;

        @Column(nullable = false)
        private BigDecimal unitPrice;

        @Version
        private Long version;

        protected OrderLine() {}

        public OrderLine(String productName, int quantity, BigDecimal unitPrice) {
            this.productName = Objects.requireNonNull(productName);
            this.quantity = quantity;
            this.unitPrice = Objects.requireNonNull(unitPrice);
        }

        public void setOrder(Order order) { this.order = order; }
        public Long getId() { return id; }
        public String getProductName() { return productName; }
        public int getQuantity() { return quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
    }

    public static void main(String[] args) {
        System.out.println("Ebean Entity classes loaded. See Customer, Order, OrderLine above.");
        System.out.println("Key annotations: @Entity, @Version, @SoftDelete, @DbEnumType(ENUM)");
        System.out.println("Relationships: @ManyToOne (LAZY), @OneToMany (LAZY, orphanRemoval)");
    }
}
