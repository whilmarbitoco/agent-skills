# Service + Repository Pattern — Anti-Patterns

## Pattern 1: Repository returning null

```java
// WRONG: Repository returns null when not found
public class InvoiceRepository {
    public Invoice findById(Long id) {
        return Ebean.find(Invoice.class, id); // returns null if missing!
    }
}

// FIX: Return Optional — caller must handle absence
public class InvoiceRepository {
    public Optional<Invoice> findById(Long id) {
        return Optional.ofNullable(Ebean.find(Invoice.class, id));
    }
}

// Caller is now forced to handle both cases:
var invoice = repo.findById(id)
    .orElseThrow(() -> new InvoiceNotFoundException(id));
```

## Pattern 2: Service with no transaction boundary

```java
// WRONG: Multiple repo calls with no transaction — partial update possible
public class TransferService {
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        var from = fromRepo.findById(fromId).orElseThrow();
        var to = toRepo.findById(toId).orElseThrow();
        from.debit(amount);  // saved? maybe.
        fromRepo.save(from); // if this fails...
        to.credit(amount);   // ...this never runs. Money lost.
        toRepo.save(to);
    }
}

// FIX: Wrap in a single transaction
public class TransferService {
    private final AccountRepository accounts;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        var tx = Ebean.beginTransaction();
        try {
            var from = accounts.findById(fromId, tx).orElseThrow();
            var to = accounts.findById(toId, tx).orElseThrow();
            from.debit(amount);
            to.credit(amount);
            accounts.save(from, tx);
            accounts.save(to, tx);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            tx.end();
        }
    }
}

// Or with @Transactional:
@Transactional
public void transfer(Long fromId, Long toId, BigDecimal amount) {
    var from = accounts.findById(fromId).orElseThrow();
    var to = accounts.findById(toId).orElseThrow();
    from.debit(amount);
    to.credit(amount);
    accounts.save(from);
    accounts.save(to);
}
```

## Pattern 3: Repository with business logic

```java
// WRONG: Pricing rules in the repository
public class InvoiceRepository {
    public void save(Invoice invoice) {
        if (invoice.getAmount().compareTo(new BigDecimal("10000")) > 0) {
            invoice.setDiscount(invoice.getAmount().multiply(new BigDecimal("0.05"))); // WRONG layer
        }
        invoice.save();
    }
}

// FIX: Repository only persists. Pricing in domain or service.
public class InvoiceRepository {
    public void save(Invoice invoice) {
        Ebean.save(invoice);
    }
}

public class InvoiceService {
    public Invoice applyVolumeDiscount(Invoice invoice) {
        var discounted = PricingRules.applyVolumeDiscount(invoice);
        repo.save(discounted);
        return discounted;
    }
}
```

## Pattern 4: Service method with too many parameters (no DTO)

```java
// WRONG: 7 parameters, caller must know order
public void createInvoice(Long customerId, BigDecimal amount, LocalDate issuedOn,
                          LocalDate dueDate, String notes, String category, boolean isRounding) {
    // ...
}

// FIX: Request record — self-documenting, immutable
public record CreateInvoiceRequest(
    Long customerId,
    BigDecimal amount,
    LocalDate issuedOn,
    LocalDate dueDate,
    String notes,
    String category,
    boolean isRounding
) {}

public Invoice createInvoice(CreateInvoiceRequest req) {
    // ...
}
```

## Pattern 5: String concatenation in logging

```java
// WRONG: String concatenation even when log level is off
log.info("Processing invoice " + invoice.getId() + " for customer " + customer.getId()
    + " amount " + amount + " date " + date + " status " + status);

// FIX: SLF4J parameterized logging — args only evaluated if INFO is enabled
log.info("Processing invoice {} for customer {} amount {} date {} status {}",
    invoice.getId(), customer.getId(), amount, date, status);
```
