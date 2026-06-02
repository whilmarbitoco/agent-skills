# Offline-First Design — Checklist

## Implementation
- [ ] All reads go to local SQLite — no blocking on network
- [ ] Every syncable entity has `remoteId`, `updatedAt`, `syncStatus` columns
- [ ] Outbox table stores pending sync operations in the same transaction as mutations
- [ ] Sync runs on virtual threads, never on FX Application Thread
- [ ] Conflict resolution strategy is explicit (timestamp-based with logging)
- [ ] Exponential backoff on sync failures (initial 200ms, max 5 retries)

## Review
- [ ] App functions fully with zero network connectivity
- [ ] No silent data loss — failed syncs are logged and queued for retry
- [ ] Sync metadata is present on every entity that needs it
- [ ] UI shows sync state (SYNCED, PENDING, CONFLICT) to the user
