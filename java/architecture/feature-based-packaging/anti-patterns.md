# Feature-Based Packaging — Anti-Patterns

## Pattern 1: Technical-layer packaging instead of feature

```java
// WRONG: All controllers together, all services together, all models together
com.shop.app.controller.InvoiceController   // knows nothing about Invoice internals
com.shop.app.controller.CustomerController
com.shop.app.service.InvoiceService          // reformats Invoice, talks to Customer
com.shop.app.service.CustomerService
com.shop.app.model.Invoice                  // model knows about both
com.shop.app.model.Customer
// Result: Changing invoice logic means touching 3 packages + shared model

// FIX: Cohesive feature packages
com.shop.invoice.InvoiceController
com.shop.invoice.InvoiceService
com.shop.invoice.Invoice
com.shop.customer.CustomerController
com.shop.customer.CustomerService
com.shop.customer.Customer
```

## Pattern 2: Shared "common" package becomes a dumping ground

```java
// WRONG: Everything ends up in common
com.shop.common.Utils          // 500 methods, no cohesion
com.shop.common.Exceptions     // 47 exception classes
com.shop.common.Config         // reads every config file
com.shop.common.Helpers        // ???

// FIX: Shared is small and well-defined
com.shop.shared.persistence.TimestampProvider
com.shop.shared.validation.AmountValidator
com.shop.shared.error.BusinessException
```

## Pattern 3: Cross-feature imports creating cycles

```java
// WRONG: Invoice package imports Customer package, Customer imports Invoice
com.shop.invoice.InvoiceService → import com.shop.customer.CustomerRepository
com.shop.customer.CustomerService → import com.shop.invoice.InvoiceRepository
// → Circular dependency

// FIX: Introduce an intermediary or event-driven communication
com.shop.shared.event.CustomerDeletedEvent;  // both sides import from shared
com.shop.invoice.InvoiceService handles CustomerDeletedEvent → cascade void invoices
```

## Pattern 4: Feature package with no internal layering

```java
// WRONG: All classes public, no layering within feature
public class Invoice extends Model { }         // public
public class InvoiceRepository { }            // public
public class InvoiceService { }               // public
public class InvoiceController { }            // public
public class InvoiceValidator { }             // public
public class InvoiceLineCalculator { }        // public
// 6 public classes; external code reaches past Controller

// FIX: Package-private internals, public API surface only
public class InvoiceController { }             // public entry point
public record InvoiceDto(Long id, BigDecimal amount) {} // public DTO
// Service, Repository, helpers are package-private
class InvoiceService { }
class InvoiceRepository { }
```

## Pattern 5: "God feature package" with 30+ classes

```java
// WRONG: One mega-package for "order" with entities, payments, shipping, notifications
com.shop.order.OrderService         // 2000 lines
com.shop.order.PaymentProcessor     // not really an "order" concern
com.shop.order.ShippingLabelGen     // not really an "order" concern
com.shop.order.EmailNotification    // not really an "order" concern

// FIX: Split into sub-features or sibling features
com.shop.order.core.OrderService        // order lifecycle
com.shop.payment.PaymentProcessor       // payment feature, subscribes to OrderPlaced
com.shop.shipping.ShippingLabelGen      // shipping feature, subscribes to OrderConfirmed
```
