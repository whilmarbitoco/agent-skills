package com.pos.domain.sync;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

/**
 * Offline sync readiness: design every entity for eventual sync.
 * Add sync metadata to all tables, queue mutations, resolve conflicts.
 */
public class OfflineSyncReadiness {

    // Every entity needs sync metadata
    interface Syncable {
        String getSyncId();        // UUID, not auto-increment
        Instant getModifiedAt();
        void setModifiedAt(Instant t);
        long getVersion();         // optimistic locking
        void incrementVersion();
        boolean isDeleted();       // soft delete for sync
    }

    // Mutation queue for offline changes
    record PendingMutation(
        String syncId,
        String entityType,
        String operation,    // INSERT, UPDATE, DELETE
        String payload,      // JSON
        Instant createdAt,
        int retryCount
    ) {}

    class MutationQueue {
        private final ConcurrentLinkedQueue<PendingMutation> queue = new ConcurrentLinkedQueue<>();

        public void enqueue(PendingMutation m) { queue.add(m); }

        public List<PendingMutation> drain() {
            List<PendingMutation> batch = new ArrayList<>();
            PendingMutation m;
            while ((m = queue.poll()) != null) batch.add(m);
            return batch;
        }
    }

    // Conflict resolution: last-write-wins with version check
    class SyncResolver {
        public <T extends Syncable> T resolve(T local, T remote) {
            if (local.getVersion() >= remote.getVersion()) return local;
            return remote;
        }
    }
}
