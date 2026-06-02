// Feature-based package structure for Simple POS
//
// com.simplepos/
// ├── inventory/              ← feature module
// │   ├── model/              ← Product entity, Category enum
// │   ├── repository/         ← ProductRepository interface + Ebean impl
// │   ├── service/            ← InventoryService (business logic)
// │   └── ui/                 ← screens/, components/, dialogs/
// ├── sales/
// │   ├── model/              ← Sale, SaleLine entities
// │   ├── repository/         ← SaleRepository
// │   ├── service/            ← SaleService, ReceiptService
// │   └── ui/                 ← SaleScreen, CartView
// ├── reports/
// │   ├── generator/          ← ReportService
// │   └── templates/          ← .jrxml templates
// └── shared/                 ← cross-cutting
//     ├── config/             ← AppConfig, DatabaseConfig
//     └── util/               ← Money, DateUtils, FxUtils

// Key rules:
// 1. Dependencies flow downward: ui → service → repository → model
// 2. No upward references: model never imports service or ui
// 3. Shared types (Money, config) go in shared/ root
// 4. Each feature can be developed and tested independently
// 5. Cross-feature communication via events (UiEventBus) or service calls
