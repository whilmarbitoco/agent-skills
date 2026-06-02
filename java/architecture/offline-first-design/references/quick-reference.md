# Offline-First — Quick Reference

## Sync entity columns
| Column | Type | Purpose |
|--------|------|---------|
| `localId` | Long/UUID | Local PK |
| `remoteId` | String/null | Server PK (null until first sync) |
| `updatedAt` | Instant | Last local modification |
| `syncStatus` | String | PENDING / SYNCED / CONFLICT |

## Sync queue (outbox) columns
| Column | Type | Purpose |
|--------|------|---------|
| `id` | Long | Auto-increment PK |
| `entityType` | String | "INVOICE" |
| `entityId` | Long | localId |
| `operation` | String | "CREATE" / "UPDATE" / "DELETE" |
| `payload` | String | JSON |
| `createdAt` | Instant | Enqueue time |

## State machine: PENDING → SYNCED, PENDING → CONFLICT
Conflict resolution: compare `updatedAt` timestamps.
