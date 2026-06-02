# Event-Driven UI — Quick Reference

## Event bus API
```java
// Subscribe
Subscription bus.subscribe(Class<T> type, Consumer<T> handler);

// Publish (synchronous — handlers run in same thread)
void bus.publish(T event);

// Unregister
void subscription.unsubscribe();
```

## Event design rules
- `record` — always
- Past tense name — `InvoicePaidEvent`, not `PayInvoiceEvent`
- Data only — no callbacks, no handler references
- Flat payload — no nested graphs

## Disposal pattern
```java
public class MyController implements AutoCloseable {
    private final Subscription sub;
    public MyController(EventBus bus) {
        this.sub = bus.subscribe(MyEvent.class, e -> handle(e));
    }
    @Override public void close() { sub.unsubscribe(); }
}
```
