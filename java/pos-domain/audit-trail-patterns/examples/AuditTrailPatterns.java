package com.pos.domain.audit;

import java.time.Instant;
import java.util.*;

/**
 * Audit trail: record every state change for compliance.
 * Every create/update/delete must produce an audit entry.
 */
public class AuditTrailPatterns {

    record AuditEntry(
        String entityType,
        String entityId,
        String action,      // CREATE, UPDATE, DELETE
        String fieldName,
        String oldValue,
        String newValue,
        String userId,
        Instant timestamp
    ) {}

    class AuditService {
        private final List<AuditEntry> entries = new ArrayList<>();

        public void log(String entityType, String entityId, String action,
                        String field, String oldVal, String newVal, String userId) {
            entries.add(new AuditEntry(
                entityType, entityId, action, field, oldVal, newVal,
                userId, Instant.now()
            ));
        }

        // Generic field-level diff
        public void logChanges(String entityType, String entityId,
                               Map<String, String> oldVals,
                               Map<String, String> newVals,
                               String userId) {
            Set<String> allKeys = new HashSet<>();
            allKeys.addAll(oldVals.keySet());
            allKeys.addAll(newVals.keySet());
            for (String key : allKeys) {
                String old = oldVals.get(key);
                String neu = newVals.get(key);
                if (!Objects.equals(old, neu)) {
                    log(entityType, entityId, "UPDATE", key, old, neu, userId);
                }
            }
        }
    }
}
