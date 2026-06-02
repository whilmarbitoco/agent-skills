# Ebean Queries — Checklist

## Implementation

- [ ] Use `ExpressionList` / `.where()` for all standard queries — no JPQL string concat
- [ ] Explicit `fetch("relationship")` when accessing related data in loops
- [ ] `setFirstRow()` + `setMaxRows()` for pagination on any list query
- [ ] `findEach()` or `findIterate()` for batch processing > 1000 rows
- [ ] `UpdateQuery` for bulk updates instead of load-modify-saveAll
- [ ] `RawSql` only for complex aggregations not expressible in ExpressionList

## Review

- [ ] No N+1: relationships accessed in loops are pre-fetched
- [ ] No unbounded `findList()` calls without pagination
- [ ] All `findIterate()` / `findEach()` use try-with-resources
- [ ] ORDER BY clauses present on paginated queries
- [ ] Logging uses parameterized format: `log.info("Found {} orders", count)`
