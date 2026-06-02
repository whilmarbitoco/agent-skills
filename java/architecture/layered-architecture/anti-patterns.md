# Layered Architecture — Anti-Patterns

## Pattern 1: Presentation skips Service layer

```java
// WRONG: Controller directly uses Repository — business logic bypassed
public class InvoiceController {
    private final InvoiceRepository repo; // no service!

    public void createInvoice(BigDecimal amount) {
        var invoice = new Invoice(null, amount, LocalDate.now());
        repo.save(invoice); // no validation, no tax calc, no tx boundary
    }
}

// FIX: Controller → Service → Repository
public class InvoiceController {
    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    public void createInvoice(BigDecimal amount) {
        var dto = new CreateInvoiceRequest(amount);
        service.createInvoice(dto); // validation + tx inside
    }
}
```

## Pattern 2: Domain model leaking framework annotations

```java
// WRONG: Record value object polluted with JPA annotations
@Embeddable
public record Money(
    @Column(name = "amount") BigDecimal amount,
    @Column(name = "currency") String currency
) {}

// FIX: Keep domain pure; Ebean entity class has annotations
public record Money(BigDecimal amount, String currency) {
    {
        if (amount.scale() > 2) throw new IllegalArgumentException("Max 2 decimal places");
    }
}

@Entity
public class Invoice extends Model {
    @Id Long id;
    @DbDefault("0.00") BigDecimal amount;
    @DbDefault("'PHP'") String currency;
}
```

## Pattern 3: Service doing SQL directly

```java
// WRONG: Service bypasses repository, runs raw SQL
public class InvoiceService {
    private final Database db;

    public List<Invoice> findOverdue() {
        return db.findDto("select * from invoice where due_date < now()", Invoice.class);
    }
}

// FIX: Service calls Repository interface
public class InvoiceService {
    private final InvoiceRepository invoices;

    public List<Invoice> findOverdue() {
        return invoices.findOverdueBefore(LocalDate.now());
    }
}
```

## Pattern 4: Upward dependency (Repository knows about Service)

```java
// WRONG: Repository imports Service
public class InvoiceRepository {
    private final PricingService pricingService; // upward dep!

    public void save(Invoice inv) {
        inv.total = pricingService.calculateTotal(inv.lines); // wrong direction
        inv.save();
    }
}

// FIX: Repository only persists. Service orchestrates.
public class InvoiceService {
    private final InvoiceRepository invoices;
    private final PricingService pricing;

    public Invoice saveInvoice(Invoice inv) {
        inv.total = pricing.calculateTotal(inv.lines);
        invoices.save(inv);
        return inv;
    }
}
```

## Pattern 5: Business logic in filters / interceptors

```java
// WRONG: Filter applies discount — business logic in web layer
public class DiscountFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        Invoice inv = (Invoice) req.getAttribute("invoice");
        inv.amount = inv.amount.multiply(new BigDecimal("0.9")); // 10% off — why here?!
    }
}

// FIX: Discount logic stays in Service
public class InvoiceService {
    public Invoice applyDiscount(Invoice inv, DiscountCode code) {
        var pct = code.resolvePercentage(); // domain rule
        var discounted = inv.amount.multiply(BigDecimal.ONE.subtract(pct));
        return new Invoice(inv.id, discounted, inv.currency, inv.dueDate);
    }
}
```
