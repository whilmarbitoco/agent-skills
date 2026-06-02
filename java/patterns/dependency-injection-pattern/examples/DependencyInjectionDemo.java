package com.pos.patterns.di;

/**
 * Manual dependency injection for JavaFX POS apps.
 * No framework needed — constructor injection + composition root.
 */
public class DependencyInjectionDemo {

    // Composition root: wire everything together
    static class AppModule {
        private final Database db = new Database();
        private final ProductRepository productRepo = new ProductRepository(db);
        private final InventoryService inventoryService = new InventoryService(productRepo);
        private final ProductController productController = new ProductController(inventoryService);
    }

    // Constructor injection — dependencies are explicit
    static class ProductController {
        private final InventoryService service;
        ProductController(InventoryService service) { this.service = service; }
        void loadProducts() { service.findAll(); }
    }

    static class InventoryService {
        private final ProductRepository repo;
        InventoryService(ProductRepository repo) { this.repo = repo; }
        void findAll() { repo.findAll(); }
    }

    static class ProductRepository {
        private final Database db;
        ProductRepository(Database db) { this.db = db; }
        void findAll() { db.query("SELECT * FROM products"); }
    }

    static class Database {
        void query(String sql) { /* ... */ }
    }
}
