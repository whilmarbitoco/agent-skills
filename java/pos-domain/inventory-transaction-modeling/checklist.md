# Checklist: Inventory Transaction Modeling

## Domain Model
- [ ] `Money` record uses `BigDecimal` + `Currency` (PHP supported)
- [ ] `Money` compact constructor validates nulls and scale
- [ ] `StockMovement` is an immutable record (no setters, no mutation methods)
- [ ] `StockMovement` compact constructor validates all fields
- [ ] `Quantity` record wraps amount with unit of measure
- [ ] All timestamps use `Instant` (UTC), never `LocalDateTime`

## Double-Entry Ledger
- [ ] `JournalEntry` contains a list of `LedgerLine` records
- [ ] Each `JournalEntry` has at least one DEBIT and one CREDIT line
- [ ] `JournalEntry.isBalanced()` returns true when sum(debits) == sum(credits)
- [ ] `TransactionLedger` is append-only (no remove, no update)
- [ ] Corrections are done via reversal entries, not mutation
- [ ] Trial balance method sums all entries and verifies zero

## Money Safety
- [ ] No `double` or `float` used for monetary calculations anywhere
- [ ] `BigDecimal` constructed from `String`, never from `double`
- [ ] Currency mismatch checked before any arithmetic operation
- [ ] `MathContext` or explicit `RoundingMode` used for division
- [ ] PHP currency (`Currency.PHP`) supported with correct fraction digits

## Immutability
- [ ] All value objects are Java 21 records
- [ ] No `@Setter` or `setXxx()` methods on any domain object
- [ ] Collections returned as unmodifiable (`Collections.unmodifiableList`)
- [ ] Constructor injection used for all service dependencies

## Audit & Integrity
- [ ] Every `StockMovement` references a `journalEntryId`
- [ ] Ledger entries include a creation timestamp and operator ID
- [ ] Reversal entries reference the original entry ID and include a reason
