# Service Repository Pattern Anti-Patterns

## Concrete class dependency — can't test in isolation

```java
// WRONG — stuck with EbeanProductRepository
public InventoryService() {
    this.repository = new EbeanProductRepository(database);
}
```

**Fix: Constructor injection. Pass `ProductRepository` interface — swap fakes in tests.**

## Raw SQL in service — bypasses repository

```java
// WRONG — service does DB work
public List<Product> findExpensive() {
    return DB.sqlQuery("SELECT * FROM product WHERE price > 1000").findList();
}
```

**Fix: Add `findByPriceGreaterThan(Money min)` to repository interface.**

## Direct instantiation in controller

```java
// WRONG — controller creates its own service
@FXML
private void init() {
    this.service = new InventoryService(new EbeanProductRepository(db));
}
```

**Fix: Inject via constructor or factory. Never `new` a service in a controller.**
