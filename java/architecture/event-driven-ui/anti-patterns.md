# Event-Driven UI — Anti-Patterns

## Pattern 1: Using events for parent-child communication (over-engineering)

```java
// WRONG: Parent and child views communicate via event bus
// ChildView fires AmountChangedEvent → ParentView subscribes
// They're already in the same layout tree!

// FIX: Use direct binding or method call — they have a natural relationship
public class InvoiceSummaryView {
    private final InvoiceListViewModel listVm;

    public InvoiceSummaryView(InvoiceListViewModel listVm) {
        this.listVm = listVm;
        // Direct binding — no event bus needed
        totalLabel.textProperty().bind(
            Bindings.createStringBinding(
                () -> computeTotal(listVm.selectedInvoice().get()),
                listVm.selectedInvoice()
            )
        );
    }
}
```

## Pattern 2: Mutable event objects

```java
// WRONG: Event has setters — handlers can mutate the payload
public class InvoicePaidEvent {
    private Long invoiceId;
    private BigDecimal amount; // mutable!

    public void setAmount(BigDecimal amount) { this.amount = amount; } // dangerous
}

// Handler A: event.setAmount(event.getAmount().multiply(new BigDecimal("0.9")));
// Handler B: reads 0.9x the original amount — data race!

// FIX: Events are records — immutable
public record InvoicePaidEvent(Long invoiceId, BigDecimal amount, Instant paidAt) {}
```

## Pattern 3: No unregistration — memory leak

```java
// WRONG: Controller registers handler but never cleans up
public class DashboardController {
    public DashboardController(EventBus bus) {
        bus.subscribe(InvoiceCreatedEvent.class, e -> refreshDashboard());
        // When this view is closed → handler stays in bus → entire controller stays in memory
    }
}

// FIX: Store subscription, unregister on dispose
public class DashboardController implements AutoCloseable {
    private final EventBus.Subscription subscription;

    public DashboardController(EventBus bus) {
        this.subscription = bus.subscribe(InvoiceCreatedEvent.class, e -> refreshDashboard());
    }

    @Override
    public void close() {
        subscription.unsubscribe(); // prevent memory leak
    }
}
```

## Pattern 4: Event carries command semantics ("Do this!")

```java
// WRONG: Event is a command — says "do this" instead of "this happened"
public record CloseInvoiceEvent(Long invoiceId) { }
public record PrintInvoiceEvent(Long invoiceId, String printerName) { }
// These are commands in disguise — coupling sender to receiver's behavior

// FIX: Events describe facts; commands are separate
// Event: something happened
public record InvoicePaidEvent(Long invoiceId, Money amount, Instant paidAt) { }

// Command: intent to do something (use direct call, not bus)
public class InvoiceService {
    public void printInvoice(Long invoiceId, String printerName) { // direct, testable
        var invoice = repo.findById(invoiceId).orElseThrow();
        printer.print(invoice);
    }
}
```

## Pattern 5: Global god-bus — everything subscribes to everything

```java
// WRONG: Single global bus, 50+ handlers across the entire app
public class GlobalEventBus {
    public static final EventBus INSTANCE = new EventBus(); // god-bus
}
// Module A: GlobalEventBus.INSTANCE.subscribe(AnyEvent, ...)
// Module B: GlobalEventBus.INSTANCE.subscribe(AnyEvent, ...)
// Module C-Z: same
// → Impossible to reason about. Handler runs? Who knows. Order? Undefined.

// FIX: Scoped buses per module; one bus per feature
public class InvoiceModule {
    private final EventBus bus = new EventBus(); // scoped to invoice module

    public InvoiceModule(InvoiceService service) {
        var controller = new InvoiceController(bus);
        var summary = new InvoiceSummaryWidget(bus);
        bus.subscribe(InvoiceCreatedEvent.class, e -> service.onCreated(e));
        // 3 handlers in one module — reason about easily
    }
}
```
