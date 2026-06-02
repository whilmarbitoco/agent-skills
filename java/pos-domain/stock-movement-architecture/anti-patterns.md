# Stock Movement Architecture — Anti-Patterns

## Problem 1: Direct stock update without audit trail

```java
// WRONG — no record of who changed what
public void updateStock(String productId, int newQty) {
    db.execute("UPDATE products SET qty = ? WHERE id = ?", newQty, productId);
}
```

```java
// FIX — record every movement
public void adjustStock(String productId, int delta, String reason, String userId) {
    StockMovement movement = new StockMovement(productId, delta, reason, userId);
    movementRepo.save(movement);
    productRepo.updateQuantity(productId, delta);
}
```

## Problem 2: No transaction wrapping

```java
// WRONG — movement saved but stock update fails
movementRepo.save(movement); // succeeds
productRepo.updateQuantity(id, delta); // fails — inconsistent state
```

```java
// FIX — wrap in transaction
@Transactional
public void adjustStock(String productId, int delta, String reason, String userId) {
    movementRepo.save(new StockMovement(productId, delta, reason, userId));
    productRepo.updateQuantity(productId, delta);
    // Both succeed or both rollback
}
```

## Problem 3: Negative stock allowed

```java
// WRONG — no validation
product.setQuantity(product.getQuantity() - soldQty);
```

```java
// FIX — validate before update
if (product.getQuantity() + delta < 0) {
    throw new InsufficientStockException(product.getId(), delta);
}
product.setQuantity(product.getQuantity() + delta);
```
