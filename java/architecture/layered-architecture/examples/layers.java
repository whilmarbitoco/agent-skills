package com.simplepos;

/**
 * Layered architecture for Simple POS Desktop.
 * Strict dependency direction: UI → Service → Repository → Domain
 *                    (UI never touches Repository or Domain directly)
 */

// === DOMAIN (innermost layer — zero dependencies) ===
// Entities, value objects, enums, domain events
// Records for immutability
public record Money(java.math.BigDecimal amount) { /* ... */ }
public record SaleLine(long productId, String name, Money price, int qty, Money total) {}

// === REPOSITORY (data access abstraction) ===
public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findByCategory(String category);
    List<Product> findAll();
}

// === SERVICE (business logic — orchestrates repositories) ===
public class InventoryService {
    private final ProductRepository repository;

    public InventoryService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product adjustStock(long productId, int delta) {
        Product product = repository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        product.adjustStock(delta);
        return repository.save(product);
    }
}

// === UI (outermost layer — depends on service, never repository) ===
public class InventoryController {
    private final InventoryService service; // NOT ProductRepository

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // Controller is thin: delegate to service, update UI
    private void onSave() {
        service.adjustStock(productId, delta);
        refreshTable();
    }
}
