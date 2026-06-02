# Ebean Queries — Quick Reference

| Operation | API |
|---|---|
| Filter | `.where().eq("field", val).gt("amount", min)` |
| Like | `.like("name", "%value%")` |
| Fetch | `.fetch("customer").fetch("lines")` |
| Order | `.orderBy("createdAt desc")` |
| Pagination | `.setFirstRow(n).setMaxRows(size)` |
| Page object | `findPagedList()` → `getTotalCount()`, `getList()` |
| Bulk update | `DB.update(X.class).set(...).where(...).update()` |
| Iterate | `findIterate()` in try-with-resources |
| Raw SQL | `DB.sqlQuery("SELECT ...")` |

| Method | Returns |
|---|---|
| `findList()` | `List<T>` — all matching rows |
| `findOne()` | `Optional<T>` — first match |
| `findCount()` | `int` — count of matching rows |
| `findEach(Consumer)` | void — stream process |
| `findIterate()` | `QueryIterator<T>` — manual cursor |
