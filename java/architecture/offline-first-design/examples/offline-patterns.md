# Offline-First Design Anti-Patterns

## Network call on FX thread — freezes UI

```java
// WRONG — blocks UI waiting for network
button.setOnAction(e -> {
    List<Product> products = api.fetchProducts(); // network on FX thread!
    table.setItems(FXCollections.observableArrayList(products));
});
```

**Offload to VT: `Thread.startVirtualThread(() -> { var result = api.fetchProducts(); Platform.runLater(() -> updateUI(result)); });`**

## No local cache — app useless without network

```java
// WRONG — data only exists remotely
public List<Product> getProducts() {
    return api.fetchProducts(); // fails offline
}
```

**Cache locally first: load from SQLite immediately, sync in background. Data is always available.**

## No conflict resolution — last-write-wins silently loses data

```java
// WRONG — local changes overwritten by server
localRepo.save(product); // user's local edit
syncService.push(); // server has different version, local lost
```

**Use version vectors or timestamps. On conflict, keep both versions and let user resolve.**
