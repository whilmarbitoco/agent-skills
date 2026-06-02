# Service + Repository — Quick Reference

## Method signatures
```java
// Repository — finder returns Optional
Optional<Invoice> findById(Long id);
List<Invoice> findByCustomerId(Long customerId);
List<Invoice> findOverdueBefore(LocalDate date);
void save(Invoice invoice);
void delete(Long id);

// Service — one method per use case
Invoice createInvoice(CreateInvoiceRequest req);
Invoice markPaid(Long id, PaymentRequest req);
void cancel(Long id, String reason);
List<InvoiceSummary> listByCustomer(Long customerId);
```

## Transaction rule
One = one transaction. If Service calls 3 repos, all 3 share one tx.
Rollback on any exception before returning to caller.
