# Domain-Driven Structure (Lite) — Anti-Patterns

## Pattern 1: Anemic domain entities — all getters/setters, no behavior

```java
// WRONG: Entity is a data bag with no methods
@Entity
public class Order extends Model {
    @Id Long id;
    String status;          // public mutable field — anyone can set to anything
    BigDecimal totalAmount;
    List<OrderLine> lines;
    // No methods. Service does ALL logic.
}

// Service becomes a god:
order.totalAmount = lines.stream().map(...).reduce(...);
order.status = "CONFIRMED";
order.save();

// FIX: Entity enforces its own invariants
@Entity
public class Order extends Model {
    @Id Long id;
    String status = "DRAFT";
    BigDecimal totalAmount;
    List<OrderLine> lines = new ArrayList<>();

    public void confirm() {
        if (!"DRAFT".equals(status)) throw new IllegalStateException("Only DRAFT orders confirm");
        if (lines.isEmpty()) throw new IllegalStateException("Cannot confirm empty order");
        this.status = "CONFIRMED";
        recalculateTotal();
    }

    public void addLine(Product product, int quantity) {
        if (!"DRAFT".equals(status)) throw new IllegalStateException("Can only add lines to DRAFT orders");
        lines.add(new OrderLine(product, quantity));
        recalculateTotal();
    }

    private void recalculateTotal() {
        this.totalAmount = lines.stream()
            .map(l -> l.price().multiply(BigDecimal.valueOf(l.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
```

## Pattern 2: Value object as mutable class

```java
// WRONG: "Value object" with setters — not truly a value
public class Money {
    private BigDecimal amount;
    private Currency currency;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal a) { this.amount = a; } // mutable!
    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency c) { this.currency = c; }
}

// Two Money instances with same values have different equals/hashCode — breaks collections

// FIX: record is immutable by default
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        if (amount.scale() > 2) throw new IllegalArgumentException("Max 2 decimal places");
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Amount cannot be negative");
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) throw new IllegalArgumentException("Currency mismatch");
        return new Money(this.amount.add(other.amount), this.currency);
    }
}
```

## Pattern 3: Partial aggregate persistence

```java
// WRONG: Persisting aggregate pieces separately — breaks consistency
public class OrderService {
    public void saveOrder(Order order) {
        Ebean.save(order);                 // saves order header
        for (OrderLine line : order.getLines()) {
            Ebean.save(line);              // saves lines one by one — partial failure possible
        }
    }
}

// FIX: Repository persists the whole aggregate in one transaction
public class OrderRepository {
    public void save(Order order) {
        var tx = Ebean.beginTransaction();
        try {
            Ebean.save(order);
            Ebean.saveAll(order.getLines()); // all lines in one call
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new OrderSaveException("Failed to save order " + order.getId(), e);
        }
    }

    public Optional<Order> findById(Long id) {
        // Load aggregate roots with their children in one query
        return Ebean.find(Order.class)
            .fetch("lines")
            .where().idEq(id)
            .findOneOrEmpty();
    }
}
```

## Pattern 4: Domain events not collected — side effects inside entity

```java
// WRONG: Entity calls publisher directly — couples domain to infrastructure
@Entity
public class Order extends Model {
    @Transient EventPublisher publisher; // infrastructure concern!

    public void confirm() {
        this.status = "CONFIRMED";
        publisher.publish(new OrderConfirmed(this.id)); // domain depends on publisher!
    }
}

// FIX: Entity returns domain events; application layer publishes them
@Entity
public class Order extends Model {
    private transient final List<Object> domainEvents = new ArrayList<>();

    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void confirm() {
        if (!"DRAFT".equals(status)) throw new IllegalStateException("Only DRAFT orders confirm");
        this.status = "CONFIRMED";
        domainEvents.add(new OrderConfirmed(id, Instant.now()));
    }
}

// Application service publishes after save
public class OrderService {
    public void confirmOrder(Long orderId) {
        var order = orders.findById(orderId).orElseThrow();
        order.confirm();
        orderRepo.save(order);
        order.getDomainEvents().forEach(eventBus::publish);
        order.clearDomainEvents();
    }
}
```

## Pattern 5: Domain service holding state between calls

```java
// WRONG: Stateful domain service — behaves like a cache, not pure logic
public class PricingService {
    private Map<Long, BigDecimal> priceCache = new HashMap<>(); // hidden state!

    public BigDecimal getPrice(Long productId) {
        return priceCache.computeIfAbsent(productId, repo::findPriceById);
    }
}

// FIX: Stateless — receive what you need as arguments, delegate caching to caller/repo
public class PricingService {
    private final ProductRepository products;

    public PricingService(ProductRepository products) {
        this.products = products;
    }

    public Money calculateLineTotal(Long productId, int quantity) {
        var price = products.findPriceById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        return new Money(price.multiply(BigDecimal.valueOf(quantity)), Currency.getInstance("PHP"));
    }
}
```
