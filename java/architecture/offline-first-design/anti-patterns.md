# Offline-First Design — Anti-Patterns

## Pattern 1: Reading from remote, not local

```java
// WRONG: UI blocks on HTTP call for data that should be local
public class InvoiceService {
    public List<Invoice> getInvoices() {
        return httpClient.get("/api/invoices").body(List.of(Invoice.class)); // NETWORK!
    }
}
// User opens invoice list → 3-second loading spinner every time

// FIX: Read from local DB; sync happens in background
public class InvoiceService {
    private final InvoiceRepository localRepo;
    private final SyncService sync;

    public List<Invoice> getInvoices() {
        sync.ifOnline(() -> sync.pullInvoices()); // best-effort background sync
        return localRepo.findAll(); // always instant, from SQLite
    }
}
```

## Pattern 2: No outbox — mutations lost on crash

```java
// WRONG: Write to server, update local only on success
public void markPaid(Long invoiceId) {
    var response = httpClient.post("/api/invoices/" + invoiceId + "/pay", "");
    if (response.statusCode() == 200) {
        localRepo.markPaid(invoiceId); // if crash before this line → payment lost!
    }
}

// FIX: Local mutation first, outbox entry in same transaction
@Transactional
public void markPaid(Long invoiceId) {
    localRepo.markPaid(invoiceId);                           // local first
    outboxRepo.enqueue(new OutboxEntry("INVOICE_PAID",      // persisted in same tx
        json("invoiceId", invoiceId)));
    // SyncService picks up outbox entry and pushes to server later
}
```

## Pattern 3: Ignoring sync conflicts

```java
// WRONG: Last write wins without checking — silently drops server changes
public void syncInvoice(Invoice local, Invoice remote) {
    localRepo.save(local); // always overwrite — remote changes discarded
}

// FIX: Timestamp-based conflict resolution
public void syncInvoice(Invoice local, Invoice remote) {
    if (local.updatedAt().isAfter(remote.updatedAt())) {
        outboxRepo.enqueue(new OutboxEntry("INVOICE_UPDATED", local.toJson()));
        log.debug("Local newer, will push: {}", local.id());
    } else if (remote.updatedAt().isAfter(local.updatedAt())) {
        localRepo.save(remote); // accept server version
        log.debug("Remote newer, accepted: {}", remote.id());
    } else {
        log.info("No conflict, in sync: {}", local.id());
    }
}
```

## Pattern 4: Exponential backoff missing — server hammered on failure

```java
// WRONG: Infinite retry loop, no backoff
while (!sync.isComplete()) {
    try {
        sync.pushPending();
    } catch (Exception e) {
        // immediately retries — hammers the server
    }
}

// FIX: Exponential backoff with max retries
public class SyncService {
    private static final int MAX_RETRIES = 5;

    public void pushPending() {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                doPush();
                return;
            } catch (IOException e) {
                attempt++;
                long waitMs = (long) Math.pow(2, attempt) * 100; // 200ms, 400ms, 800ms...
                log.warn("Sync attempt {}/{} failed, waiting {}ms", attempt, MAX_RETRIES, waitMs);
                try { Thread.sleep(waitMs); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); return; }
            }
        }
        log.error("Sync failed after {} attempts", MAX_RETRIES);
    }
}
```

## Pattern 5: Entity missing sync metadata columns

```java
// WRONG: No way to tell if entity has been synced
@Entity
public class Invoice extends Model {
    @Id Long id;
    BigDecimal amount;
    // No updated_at, no sync_status, no local/remote id
}

// FIX: Every syncable entity carries sync metadata
@Entity
public class Invoice extends Model {
    @Id Long id;
    String remoteId;                            // null until first sync
    Instant updatedAt = Instant.now();
    String syncStatus = "PENDING";             // PENDING | SYNCED | CONFLICT
    BigDecimal amount;
}
```
