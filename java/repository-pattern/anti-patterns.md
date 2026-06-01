# Repository Pattern Anti-Patterns

## Returning null instead of Optional

```java
// WRONG — NPE waiting to happen downstream
public Product findById(Long id) {
    return database.find(Product.class, id); // null if not found
}
```

**Return `Optional<Product>`: `return Optional.ofNullable(database.find(Product.class, id))`.**

## Raw queries in service — bypasses repository

```java
// WRONG — service does database work, defeats repository purpose
public List<Product> findExpensive() {
    return database.sqlQuery("SELECT * FROM product WHERE price > 1000").findList();
}
```

**Add query method to repository: `List<Product> findByPriceGreaterThan(Money min)`.**

## Concrete class dependency — can't test in isolation

```java
// WRONG — service is stuck with EbeanProductRepository, can't swap for tests
public InventoryService() {
    this.repository = new EbeanProductRepository(database);
}
```

**Constructor injection: `public InventoryService(ProductRepository repository)`.**

## Doing work in getter methods

```java
// WRONG — getter triggers lazy loading, causes unexpected queries
public List<SaleLine> getLines() {
    return sale.getLines(); // might trigger DB query if lazy
}
```

**Don't use entity getters for cross-entity traversal after retrieval. Use fetch joins or explicit loading.**

## Delete method lacks null check

```java
// WRONG — NPE if id not found
public void delete(Long id) {
    Product p = database.find(Product.class, id);
    database.delete(p); // NPE if p is null
}
```

**Check first: `if (p != null) database.delete(p);` or use `Optional` chaining.**
