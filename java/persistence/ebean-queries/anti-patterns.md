# Ebean Queries — Anti-Patterns

## 1. Using JPQL string concatenation for dynamic queries

```java
// WRONG — string-based JPQL, no compile-time safety, SQL injection risk
String jpql = "SELECT c FROM Customer c WHERE c.name = '" + name + "'";
List<Customer> customers = DB.find(Customer.class)
    .jpql(jpql) // ebean doesn't have .jpql(), this is illustrative
    .findList();
```

```java
// FIX: use type-safe ExpressionList
import io.ebean.DB;

List<Customer> customers = DB.find(Customer.class)
    .where()
    .eq("name", name)
    .findList();
```

## 2. Not fetching relationships — causing N+1

```java
// WRONG — loads orders one-by-one, N+1 problem
List<Order> orders = DB.find(Order.class)
    .where().eq("status", OrderStatus.PENDING)
    .findList();
for (Order o : orders) {
    System.out.println(o.getCustomer().getName()); // N extra queries!
}
```

```java
// FIX: eager fetch the relationship in one query
List<Order> orders = DB.find(Order.class)
    .fetch("customer") // single JOIN query
    .where().eq("status", OrderStatus.PENDING)
    .findList();
for (Order o : orders) {
    System.out.println(o.getCustomer().Name()); // no extra query
}
```

## 3. Loading entire table into memory

```java
// WRONG — loads ALL 100K rows into a List
List<Customer> all = DB.find(Customer.class).findList();
```

```java
// FIX: paginate or use findEach for streaming
// Pagination:
var page = DB.find(Customer.class)
    .setFirstRow(0)
    .setMaxRows(50)
    .orderBy("name")
    .findPagedList();

// Streaming:
try (var cursor = DB.find(Customer.class).findIterate()) {
    while (cursor.hasNext()) {
        Customer c = cursor.next();
        log.debug("Processing {}", c.getId());
    }
}
```

## 4. Using `findList()` for batch update operations

```java
// WRONG — loads entities into memory just to update a field
List<Order> pending = DB.find(Order.class)
    .where().eq("status", OrderStatus.PENDING)
    .findList();
pending.forEach(o -> o.setStatus(OrderStatus.CANCELLED));
DB.saveAll(pending); // memory-heavy for large sets
```

```java
// FIX: execute bulk update via UpdateQuery
int updated = DB.update(Order.class)
    .set("status", OrderStatus.CANCELLED)
    .where().eq("status", OrderStatus.PENDING)
    .update();
log.info("Cancelled {} orders", updated);
```

## 5. Not closing iterate cursors / leaking resources

```java
// WRONG — iterate without try-with-resources may leak DB cursor
var it = DB.find(Customer.class).findIterate();
for (Customer c : it) { // auto-close? not guaranteed
    process(c);
}
```

```java
// FIX: always use try-with-resources with findEach / findIterate
try (var each = DB.find(Customer.class).findEach(c -> {
    process(c);
})) {
    // cursor auto-closed
}

// Or explicit try-with-resources for iterate:
try (var cursor = DB.find(Customer.class).findIterate()) {
    while (cursor.hasNext()) {
        process(cursor.next());
    }
}
```
