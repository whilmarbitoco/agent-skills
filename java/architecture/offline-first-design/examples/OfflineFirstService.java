package com.pos.architecture.offline;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Offline-first pattern: queue mutations locally, sync when online.
 * Every write operation succeeds locally first.
 */
public class OfflineFirstService {

    private final ConcurrentLinkedQueue<Mutation> pending = new ConcurrentLinkedQueue<>();
    private final LocalStore localStore;
    private final SyncClient syncClient;

    public OfflineFirstService(LocalStore localStore, SyncClient syncClient) {
        this.localStore = localStore;
        this.syncClient = syncClient;
    }

    public void save(Sale sale) {
        // 1. Always save locally first — never fail
        localStore.save(sale);
        // 2. Queue for sync
        pending.add(new Mutation("SALE", sale.getId(), sale));
        // 3. Attempt sync if online
        if (syncClient.isOnline()) {
            flush();
        }
    }

    public void flush() {
        Mutation m;
        while ((m = pending.poll()) != null) {
            try {
                syncClient.push(m);
            } catch (Exception e) {
                pending.add(m); // retry later
                break;
            }
        }
    }

    record Mutation(String type, String id, Object data) {}
}
