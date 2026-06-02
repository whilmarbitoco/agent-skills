package com.pos.feature.inventory;

/**
 * Feature-based packaging: each feature is a self-contained module
 * with its own domain, UI, and data access.
 *
 * Structure:
 *   com.pos.feature.inventory/
 *     ├── domain/       (entities, value objects)
 *     ├── service/      (business logic)
 *     ├── ui/           (JavaFX controllers, views)
 *     └── repository/   (data access)
 */
public class FeatureModule {

    // Feature entry point — only this class is public
    public static void register() {
        // Register routes, menu items, DI bindings
        RouteRegistry.register("/inventory", InventoryView::new);
        ServiceRegistry.register(InventoryService.class, InventoryServiceImpl::new);
    }
}
