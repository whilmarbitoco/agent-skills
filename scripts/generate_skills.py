#!/usr/bin/env python3
"""Generate all 66 remaining Java 21 skill files."""
import yaml
from pathlib import Path

base = Path(__file__).parent.parent / "java"

skills = yaml.safe_load(r"""
- domain: core-java
  skills:
  - id: switch-pattern-matching
    title: "Pattern Matching for switch & instanceof"
    level: intermediate
    desc: "Use when using pattern matching in switch, instanceof, or record pattern destructuring."
    concepts: ["instanceof pattern matching", "switch with patterns", "record patterns", "guarded patterns", "null handling"]
    rules: ["Always use pattern matching not instanceof+cast", "Combine sealed+switch for exhaustiveness", "Use guarded patterns for conditions"]
    pos: "Refactor instanceof chains to pattern matching with sealed PaymentType."

  - id: sequenced-collections
    title: "Sequenced Collections API"
    level: beginner
    desc: "Use when working with SequencedCollection, SequencedSet, SequencedMap (JEP 431)."
    concepts: ["SequencedCollection", "reversed()", "getFirst()", "getLast()"]
    rules: ["Use reversed() for backward iteration", "Prefer getFirst/getLast over get(0)/get(size-1)"]
    pos: "Replace get(0)/get(size-1) in POS list views."

  - id: collections-best-practices
    title: "Collections & Immutable Data"
    level: intermediate
    desc: "Use when choosing collection types or creating immutable collections in Java 21."
    concepts: ["Immutable collections", "List.of()", "Map.of()", "copyOf()"]
    rules: ["Use List.of for static data", "Use List.copyOf for defensive copies", "Never return mutable internal collections"]
    pos: "Immutable collections for reference data; copyOf in service getters."

  - id: immutability
    title: "Immutability Patterns"
    level: intermediate
    desc: "Use when designing immutable value objects with records and defensive copying."
    concepts: ["final fields", "records", "with-er methods", "defensive copying"]
    rules: ["Use records for all value objects", "Never expose mutable internal state", "Use with-er methods for copy-on-modify"]
    pos: "All POS value objects as records. ProductView/SaleView for UI."

  - id: exception-strategy
    title: "Exception Handling Strategy"
    level: intermediate
    desc: "Use when designing exception hierarchies for domain errors."
    concepts: ["Domain exceptions", "Exception hierarchy", "Optional vs exceptions", "Error messages"]
    rules: ["Use domain-specific unchecked exceptions", "Include entity context in messages", "Use Optional for nullable query results"]
    pos: "Create InsufficientStockException, InvalidReceiptException, CashSessionClosedException."

  - id: streams-vs-loops
    title: "Streams API vs Imperative Loops"
    level: intermediate
    desc: "Use when deciding between Stream API and imperative loops."
    concepts: ["Stream pipeline", "filter-map-reduce", "collectors", "parallel streams caution"]
    rules: ["Use streams for multi-step transformations", "Use loops for side effects", "Never use parallel streams on small collections"]
    pos: "Streams for report data transformation. Loops for I/O."

  - id: concurrency-fundamentals
    title: "Concurrency Fundamentals"
    level: advanced
    desc: "Use when working with ExecutorService, CompletableFuture, locks, concurrent collections."
    concepts: ["ExecutorService", "CompletableFuture", "ReentrantLock", "ConcurrentHashMap"]
    rules: ["Always shutdown ExecutorService", "Prefer CompletableFuture over raw Future", "Use ConcurrentHashMap for shared maps"]
    pos: "ConcurrentHashMap for product cache. CompletableFuture for async composition."

  - id: string-templates
    title: "String Templates (Preview)"
    level: intermediate
    desc: "Use when working with STR. string interpolation (JEP 430 preview)."
    concepts: ["STR. processor", "String interpolation", "FMT. processor"]
    rules: ["Use STR. for safe interpolation", "Use FMT. for formatted output"]
    pos: "STR. for receipt line formatting and log messages."

- domain: architecture
  skills:
  - id: layered-architecture
    title: "Layered Architecture for Desktop Apps"
    level: intermediate
    desc: "Use when structuring a JavaFX desktop app with clear separation of concerns."
    concepts: ["Layer boundaries", "Dependency direction", "Package structure"]
    rules: ["Domain has zero dependencies on other layers", "UI depends on service interfaces only", "No cyclic dependencies"]
    pos: "Simple POS: domain -> persistence -> services -> ui."

  - id: feature-based-packaging
    title: "Feature-Based Package Organization"
    level: intermediate
    desc: "Use when deciding between package-by-layer vs package-by-feature."
    concepts: ["Feature modules", "Package cohesion", "Navigation boundaries"]
    rules: ["Group by feature first", "Each feature has own UI+service+repository"]
    pos: "Features: inventory, sales, reports, sessions."

  - id: mvvm-javafx
    title: "MVVM Pattern with JavaFX"
    level: advanced
    desc: "Use when implementing Model-View-ViewModel in JavaFX."
    concepts: ["ViewModel", "Observable state", "Data binding"]
    rules: ["ViewModel exposes ObservableList/Property for binding", "ViewModel has no reference to View"]
    pos: "Each POS screen has a ViewModel."

  - id: service-repository-pattern
    title: "Service-Repository Pattern"
    level: intermediate
    desc: "Use when implementing service-repository pattern for data access."
    concepts: ["Repository interface", "Ebean implementation", "Transaction boundary"]
    rules: ["Repository interfaces in persistence package", "Services orchestrate repositories"]
    pos: "InventoryRepository, SaleRepository with Ebean implementations."

  - id: offline-first-design
    title: "Offline-First Design"
    level: advanced
    desc: "Use when designing the POS to work without network connectivity."
    concepts: ["Local database", "Transaction queue", "Sync strategies"]
    rules: ["All data stored locally in SQLite", "Never block UI waiting for network"]
    pos: "Simple POS works fully offline."

  - id: domain-driven-structure-lite
    title: "Domain-Driven Structure (Lite)"
    level: advanced
    desc: "Use when applying DDD concepts to JavaFX desktop apps."
    concepts: ["Entities", "Value objects", "Aggregates", "Domain events"]
    rules: ["Value objects are records", "Repositories only for aggregates"]
    pos: "Sale and Product are aggregate roots."

  - id: event-driven-ui
    title: "Event-Driven UI Architecture"
    level: intermediate
    desc: "Use when implementing event-based communication between UI and services."
    concepts: ["Event bus", "Publish-subscribe", "JavaFX events"]
    rules: ["Use JavaFX events for UI events", "Lightweight event bus for service-to-service"]
    pos: "Cart updated -> total recalculated. Sale completed -> stock updated."

- domain: ui-javafx
  skills:
  - id: observable-properties
    title: "Observable Properties & Bindings"
    level: intermediate
    desc: "Use when working with JavaFX Property types and observable collections."
    concepts: ["SimpleObjectProperty", "ObservableList", "ChangeListener", "InvalidationListener"]
    rules: ["Use appropriate Property type", "Prefer InvalidationListener for derived values", "Never hold strong listener references"]
    pos: "Product table uses ObservableList. Money fields use ObjectProperty."

  - id: bindings
    title: "Bindings Deep Dive"
    level: intermediate
    desc: "Use when creating computed properties and binding UI state."
    concepts: ["Bindings.create*", "bindBidirectional()", "Low-level binding"]
    rules: ["Use high-level bindings API", "Always unbind before rebinding"]
    pos: "Total label bound to cart sum. Discount bound bidirectionally."

  - id: threading-and-platform-runlater
    title: "Threading & Platform.runLater"
    level: intermediate
    desc: "Use when working with JavaFX threading and background tasks."
    concepts: ["FX Application Thread", "Platform.runLater()", "Task", "Service"]
    rules: ["Never block FX thread with I/O", "Use Task for cancellable background work", "Use Service for restartable work"]
    pos: "All DB queries on virtual thread. Results via Platform.runLater."

  - id: layout-system
    title: "JavaFX Layout System"
    level: beginner
    desc: "Use when working with Pane, VBox, HBox, GridPane, BorderPane."
    concepts: ["BorderPane", "VBox-HBox", "GridPane", "StackPane"]
    rules: ["Use BorderPane for top-level shell", "Use GridPane for forms", "Avoid AnchorPane"]
    pos: "Main shell: BorderPane. Forms: GridPane. Cards: VBox."

  - id: css-theming
    title: "JavaFX CSS Theming"
    level: intermediate
    desc: "Use when styling JavaFX applications with CSS."
    concepts: ["CSS selectors", "Looked-up colors", "Dark mode", "Modena theme"]
    rules: ["Use looked-up colors for theme variables", "Separate CSS files per theme"]
    pos: "Custom CSS with looked-up colors. Dark mode toggles CSS class."

  - id: reusable-components
    title: "Reusable UI Components"
    level: intermediate
    desc: "Use when creating custom JavaFX controls or composite components."
    concepts: ["Custom control", "fxml:include", "ControlsFX"]
    rules: ["Prefer FXML-included components", "Use ControlsFX for advanced controls"]
    pos: "ProductCard, SaleLineItem, MoneyField as reusable components."

  - id: tableview-best-practices
    title: "TableView Best Practices"
    level: intermediate
    desc: "Use when working with JavaFX TableView and TableColumn."
    concepts: ["TableColumn", "CellValueFactory", "CellFactory", "SortedList", "FilteredList"]
    rules: ["Use PropertyValueFactory for simple cases", "Custom CellFactory for formatted cells", "Use FilteredList for search"]
    pos: "Product table with Money cell. Sale history with date formatting."

  - id: form-validation
    title: "Form Validation Patterns"
    level: intermediate
    desc: "Use when implementing form input validation in JavaFX."
    concepts: ["Validation", "Visual feedback", "Error messages"]
    rules: ["Validate on focus lost AND on submit", "Use CSS :invalid pseudo-class", "Disable submit until valid"]
    pos: "Product form: name required, price > 0, stock >= 0."

  - id: async-ui-patterns
    title: "Async UI Patterns"
    level: advanced
    desc: "Use when implementing background tasks with progress reporting."
    concepts: ["Task", "Service", "ProgressBar", "Cancellation"]
    rules: ["Use Task for one-shot background work", "Use Service for restartable work", "Always handle cancellation"]
    pos: "Report generation as Task with progress bar."

  - id: fxml-patterns
    title: "FXML & Controller Patterns"
    level: intermediate
    desc: "Use when working with FXML files and controller injection."
    concepts: ["fx:controller", "fx:id", "fx:include", "Controller factory"]
    rules: ["One FXML per screen", "Controller receives dependencies via factory", "Keep controllers thin"]
    pos: "Each POS screen is FXML with dedicated controller via factory."

- domain: ui-kickstartfx
  skills:
  - id: workspace-layout
    title: "KickStartFX Workspace Layout"
    level: intermediate
    desc: "Use when setting up KickStartFX workspace structure."
    concepts: ["Workspace", "Window configuration"]
    rules: ["Extend KickStartFX Application class", "Configure workspace in start method"]
    pos: "Simple POS uses single workspace with sidebar navigation."

  - id: navigation-patterns
    title: "Navigation Patterns"
    level: intermediate
    desc: "Use when implementing KickStartFX navigation between screens."
    concepts: ["View registration", "View switching", "Sidebar nav"]
    rules: ["Register all views in workspace config", "Use workspace.navigateTo()"]
    pos: "Sidebar: Dashboard, Inventory, Sales, Reports, Sessions."

  - id: theme-customization
    title: "Theme Customization"
    level: intermediate
    desc: "Use when customizing KickStartFX appearance."
    concepts: ["CSS variables", "Color scheme", "Font configuration"]
    rules: ["Use KickStartFX theme API", "Override CSS variables for branding"]
    pos: "Custom blue accent, custom font, dark sidebar."

  - id: component-composition
    title: "Component Composition"
    level: intermediate
    desc: "Use when composing KickStartFX views and components."
    concepts: ["Component reuse", "Parameter passing", "Event communication"]
    rules: ["Components receive data via constructor", "Events for component-to-parent"]
    pos: "ProductCard component receives ProductView record."

  - id: sidebar-shell-architecture
    title: "Sidebar Shell Architecture"
    level: advanced
    desc: "Use when implementing the main shell with sidebar navigation."
    concepts: ["Sidebar layout", "Content area", "Responsive behavior"]
    rules: ["Sidebar fixed width, content fills remaining space", "Collapse on small screens"]
    pos: "240px sidebar with icons+labels. Content area views."

  - id: responsive-desktop-layouts
    title: "Responsive Desktop Layouts"
    level: intermediate
    desc: "Use when making JavaFX desktop layouts responsive."
    concepts: ["Binding to scene width", "Min-max constraints", "Breakpoints"]
    rules: ["Use binding not listeners", "Set min/max sizes on regions"]
    pos: "Min 1024x768. Sidebar collapses below 900px."

  - id: dark-mode-strategy
    title: "Dark Mode Strategy"
    level: intermediate
    desc: "Use when implementing dark mode in KickStartFX."
    concepts: ["CSS theme switching", "Dark mode toggle", "System preference detection"]
    rules: ["Two CSS files: light + dark", "Toggle via root style class"]
    pos: "User preference saved to config. Toggle in settings."

- domain: persistence
  skills:
  - id: ebean-setup
    title: "Ebean ORM Setup & Configuration"
    level: intermediate
    desc: "Use when configuring Ebean ORM for Java 21 + Maven + SQLite."
    concepts: ["Ebean configuration", "SQLite platform", "DDL generation", "Code enhancement"]
    rules: ["Configure Ebean programmatically", "Use SQLitePlatform", "Code enhancement via annotation processor"]
    pos: "Ebean in AppBootstrap. DDL from annotations."

  - id: ebean-entities
    title: "Entity Modeling with Ebean"
    level: intermediate
    desc: "Use when creating Ebean entities and defining relationships."
    concepts: ["Entity annotations", "Relationships", "Version optimistic locking", "Index"]
    rules: ["Always include Version for optimistic locking", "Add WhenCreated/WhenModified", "Index frequently queried columns"]
    pos: "Product, Sale, SaleLine, StockMovement entities."

  - id: ebean-queries
    title: "Type-Safe Queries with Ebean"
    level: intermediate
    desc: "Use when writing Ebean queries with the type-safe DSL."
    concepts: ["QBean", "ExpressionList", "fetch joins", "pagination"]
    rules: ["Use generated Q-beans", "Use fetch() for eager loading", "Paginate with setMaxRows()"]
    pos: "findByName, findLowStock, findToday queries."

  - id: sqlite-best-practices
    title: "SQLite Best Practices for Desktop Apps"
    level: intermediate
    desc: "Use when working with SQLite in a desktop application."
    concepts: ["WAL mode", "File location", "Backup", "Thread safety"]
    rules: ["Enable WAL mode", "Store DB in user home directory", "Implement periodic backup"]
    pos: "DB in app data dir. WAL mode. Daily backup."

  - id: database-migrations
    title: "Database Migrations"
    level: intermediate
    desc: "Use when managing database schema changes across app versions."
    concepts: ["Ebean migration", "DDL diff", "Migration scripts"]
    rules: ["Use Ebean DDL diff for generating migrations", "Number scripts sequentially"]
    pos: "Migrations in resources/db/migration/."

- domain: maven
  skills:
  - id: multi-module-projects
    title: "Multi-Module Maven Projects"
    level: intermediate
    desc: "Use when structuring a Maven project with multiple modules."
    concepts: ["Parent POM", "Module BOM", "Dependency management"]
    rules: ["Parent POM defines versions in dependencyManagement", "Each module has own pom.xml"]
    pos: "Modules: core, persistence, ui, app."

  - id: dependency-management
    title: "Dependency Management & BOMs"
    level: intermediate
    desc: "Use when managing Maven dependencies and version conflicts."
    concepts: ["dependencyManagement", "BOM", "Version conflicts", "Exclusions"]
    rules: ["Define all versions in parent POM", "Import BOMs for JavaFX and Ebean", "Never use LATEST or RELEASE"]
    pos: "Parent POM imports JavaFX and Ebean BOMs."

  - id: shading-and-packaging
    title: "Shading, Packaging & jpackage"
    level: advanced
    desc: "Use when building a distributable JAR or native installer."
    concepts: ["maven-shade-plugin", "maven-jpackage-plugin", "uber-jar"]
    rules: ["Use jpackage for native installers", "Use shade plugin only for library JARs"]
    pos: "jpackage for deb, msi, dmg."

  - id: javafx-maven-plugin
    title: "JavaFX Maven Plugin (Gluon)"
    level: intermediate
    desc: "Use when configuring the Gluon JavaFX Maven plugin."
    concepts: ["javafx:run", "javafx:jlink", "javafx:native"]
    rules: ["Configure mainClass in plugin", "Set release=21 in compiler plugin"]
    pos: "javafx:run in dev. Module path in pom.xml."

  - id: profiles-environments
    title: "Maven Profiles for Environments"
    level: intermediate
    desc: "Use when managing different build configurations for dev/test/prod."
    concepts: ["Maven profiles", "Profile activation", "Resource filtering"]
    rules: ["dev: DDL gen on, debug logging", "prod: DDL off, warn logging"]
    pos: "dev, test (H2), prod profiles."

  - id: reproducible-builds
    title: "Reproducible Builds"
    level: beginner
    desc: "Use when ensuring Maven builds are reproducible."
    concepts: ["Maven wrapper", "Plugin versions", "Dependency locking"]
    rules: ["Maven Wrapper required", "Pin all plugin versions", "mvnw in repo"]
    pos: "mvnw in repo. All plugin versions pinned."

- domain: testing
  skills:
  - id: junit5-fundamentals
    title: "JUnit 5 Fundamentals"
    level: beginner
    desc: "Use when writing unit tests with JUnit 5."
    concepts: ["Test annotation", "BeforeEach", "DisplayName", "Assertions", "Parameterized tests"]
    rules: ["Use DisplayName for readable names", "One assertion per test", "Use assertAll for multiple"]
    pos: "All service logic tested with JUnit 5."

  - id: testfx-ui-testing
    title: "TestFX for JavaFX UI Testing"
    level: advanced
    desc: "Use when testing JavaFX UI with TestFX."
    concepts: ["TestFX", "FXRobot", "headless testing", "Monocle"]
    rules: ["Run UI tests headless with Monocle", "Use robot to simulate user actions"]
    pos: "UI tests: product form, sale flow, search filtering."

  - id: integration-testing
    title: "Integration Testing with SQLite"
    level: intermediate
    desc: "Use when writing integration tests with a real database."
    concepts: ["In-memory SQLite", "Schema setup/teardown", "Test data seeding"]
    rules: ["Use sqlite::memory: for test DB", "Run schema from Ebean config in setup"]
    pos: "Integration tests: full sale flow, stock adjustment."

- domain: packaging
  skills:
  - id: jpackage-basics
    title: "jpackage Basics"
    level: intermediate
    desc: "Use when creating native application packages with jpackage."
    concepts: ["jpackage CLI", "Module handling", "Resource packaging"]
    rules: ["Use jpackage module mode for JavaFX", "Include all resources in input dir"]
    pos: "jpackage config in pom.xml plugin section."

  - id: native-installers
    title: "Native Installers"
    level: advanced
    desc: "Use when building platform-specific native installers."
    concepts: ["deb", "msi", "dmg", "WiX"]
    rules: ["deb on Linux, msi on Windows (WiX), dmg on macOS"]
    pos: "deb for Linux, msi for Windows, dmg for macOS."

- domain: patterns
  skills:
  - id: solid-principles
    title: "SOLID Principles in Java"
    level: intermediate
    desc: "Use when evaluating code against SOLID design principles."
    concepts: ["SRP", "OCP", "LSP", "ISP", "DIP"]
    rules: ["One reason to change per class", "Extend not modify", "Small focused interfaces"]
    pos: "Each POS service has one responsibility."

  - id: factory-pattern
    title: "Factory & Abstract Factory"
    level: intermediate
    desc: "Use when creating objects without specifying exact class."
    concepts: ["Factory method", "Abstract factory", "Static factory"]
    rules: ["Factory for Report generators", "Factory for PaymentType handlers"]
    pos: "ReportFactory creates different report types."

  - id: observer-pattern
    title: "Observer Pattern (JavaFX Properties)"
    level: intermediate
    desc: "Use when implementing observer pattern with JavaFX properties."
    concepts: ["Observable", "ChangeListener", "ObservableList", "WeakListener"]
    rules: ["JavaFX properties ARE the observer pattern", "Use weak listeners for memory safety"]
    pos: "Cart observable -> total auto-updates."

  - id: strategy-pattern
    title: "Strategy Pattern"
    level: intermediate
    desc: "Use when defining interchangeable algorithm families."
    concepts: ["Strategy interface", "Concrete strategies", "Context class"]
    rules: ["Strategy for report generation", "Strategy for payment processing"]
    pos: "ReceiptFormatterStrategy: Thermal, Html, Pdf."

  - id: decorator-pattern
    title: "Decorator Pattern"
    level: intermediate
    desc: "Use when adding behavior to objects dynamically."
    concepts: ["Decorator interface", "Concrete decorator", "Component composition"]
    rules: ["Use decorators for cross-cutting concerns", "Preserve original interface"]
    pos: "DiscountDecorator, TaxDecorator on Sale."

  - id: dependency-injection-pattern
    title: "Dependency Injection Pattern"
    level: intermediate
    desc: "Use when implementing constructor-based DI."
    concepts: ["Constructor injection", "Composition root", "Manual wiring"]
    rules: ["All deps via constructor", "Composition root in AppBootstrap", "No ServiceLocator"]
    pos: "AppBootstrap wires repositories -> services -> controllers."

- domain: performance
  skills:
  - id: jvm-tuning
    title: "JVM Tuning for Desktop Apps"
    level: advanced
    desc: "Use when optimizing JVM flags for desktop JavaFX apps."
    concepts: ["G1GC", "heap sizing", "JIT compilation"]
    rules: ["Use G1GC", "Set Xms to 50% of Xmx", "Use Xshare:on for CDS"]
    pos: "Flags: Xms256m Xmx512m G1GC Xshare:on."

  - id: memory-profiling
    title: "Memory Profiling & Leak Detection"
    level: advanced
    desc: "Use when diagnosing memory leaks in JavaFX apps."
    concepts: ["Heap dump", "VisualVM", "JavaFX listener leaks"]
    rules: ["Check for listener leaks", "Use WeakListener", "Monitor with VisualVM"]
    pos: "Common leak: binding not removed on screen close."

  - id: startup-optimization
    title: "Application Startup Optimization"
    level: intermediate
    desc: "Use when reducing cold-start time of desktop JavaFX apps."
    concepts: ["AppCDS", "Lazy loading", "Splash screen"]
    rules: ["Use AppCDS", "Lazy-load non-critical services", "Splash screen during startup"]
    pos: "Splash while Ebean initializes. Lazy service loading."

- domain: security
  skills:
  - id: secrets-management
    title: "Secrets Management"
    level: intermediate
    desc: "Use when handling API keys or credentials in a desktop app."
    concepts: ["dotenv", "Environment variables", "Keyring"]
    rules: ["Use .env for dev", "Use OS keyring for production", "Never commit .env"]
    pos: ".env for dev. OS keyring for production."

  - id: config-encryption
    title: "Configuration Encryption"
    level: intermediate
    desc: "Use when storing sensitive configuration on disk."
    concepts: ["AES encryption", "Key derivation"]
    rules: ["Encrypt sensitive config at rest", "Use AES-256-GCM"]
    pos: "DB password stored encrypted."

  - id: auth-patterns
    title: "Authentication Patterns (Local)"
    level: intermediate
    desc: "Use when implementing local user authentication."
    concepts: ["Password hashing", "Session management", "RBAC", "BCrypt"]
    rules: ["Use BCrypt for hashing", "Session token with expiry", "RBAC for cashier/admin"]
    pos: "Cashier (sales), admin (full). BCrypt hashing."

- domain: pos-domain
  skills:
  - id: inventory-transaction-modeling
    title: "Inventory Transaction Modeling"
    level: advanced
    desc: "Use when modeling inventory transactions and stock movements."
    concepts: ["Transaction pattern", "Stock ledger", "Double-entry"]
    rules: ["Every stock change is a transaction", "Immutable transaction records"]
    pos: "StockMovement records every qty change."

  - id: receipt-generation
    title: "Receipt Generation"
    level: intermediate
    desc: "Use when generating receipts with JasperReports."
    concepts: ["JasperReports", "Thermal printer", "Receipt template"]
    rules: ["Use jrxml templates", "Support raw ESC/POS printing"]
    pos: "Receipt: header, body, footer with barcode."

  - id: barcode-scanning-flow
    title: "Barcode Scanning Flow"
    level: intermediate
    desc: "Use when implementing barcode scanning integration."
    concepts: ["ZXing", "Camera capture", "External scanner", "Keyboard wedge"]
    rules: ["Support camera and USB scanner", "Debounce rapid scan events"]
    pos: "USB scanner in keyboard wedge mode."

  - id: stock-movement-architecture
    title: "Stock Movement Architecture"
    level: advanced
    desc: "Use when designing the stock movement system."
    concepts: ["Movement types", "Approval workflow", "Thresholds", "Reorder alerts"]
    rules: ["5 movement types", "Adjustments require reason", "Low-stock threshold"]
    pos: "Purchase, Sale, Adjustment, Return, Loss."

  - id: cash-session-management
    title: "Cash Session Management"
    level: intermediate
    desc: "Use when implementing cash register session lifecycle."
    concepts: ["Session lifecycle", "Cash reconciliation"]
    rules: ["Daily session per cashier", "Reconcile at close", "Audit trail"]
    pos: "Open with float. Sales recorded. Close by counting cash."

  - id: audit-trail-patterns
    title: "Audit Trail Patterns"
    level: intermediate
    desc: "Use when implementing audit logging for compliance."
    concepts: ["Who-What-When", "Immutable log entries"]
    rules: ["Log user, action, entity, old/new values, timestamp", "Append-only"]
    pos: "All CRUD logged with user and timestamp."

  - id: offline-sync-readiness
    title: "Offline Sync Readiness"
    level: advanced
    desc: "Use when preparing the POS for online synchronization."
    concepts: ["Local-first architecture", "Conflict resolution", "Sync queue"]
    rules: ["All operations work offline", "Queue sync when offline"]
    pos: "Currently offline-first. Future: upload sales."

- domain: reporting
  skills:
  - id: jasperreports-basics
    title: "JasperReports Basics"
    level: intermediate
    desc: "Use when creating JasperReports templates and generating reports."
    concepts: ["JRXML", "JasperCompileManager", "JRBeanCollectionDataSource", "Subreports"]
    rules: ["Design jrxml templates", "Compile on first load, cache compiled"]
    pos: "Reports: daily sales, inventory, cash session."

  - id: barcode-generation
    title: "Barcode Generation with ZXing"
    level: intermediate
    desc: "Use when generating barcodes or QR codes."
    concepts: ["QR Code", "Code128", "EAN-13", "BufferedImage"]
    rules: ["Use ZXing library", "QR for receipt lookup", "Code128 for product IDs"]
    pos: "QR on receipt. Code128 on product labels."
""")

def gen_skill(skill, domain):
    sid = skill["id"]
    title = skill["title"]
    level = skill["level"]
    desc = skill["desc"]
    concepts_md = "\n".join(f"- **{c}**" for c in skill["concepts"])
    rules_md = "\n".join(f"{i+1}. {r}" for i, r in enumerate(skill["rules"]))

    return f"""---
name: {sid}
description: "{desc}"
category: java
tags:
  - java-21
  - {domain}
---

# {title}

**Skill ID:** `{sid}`  
**Domain:** `{domain}`  
**Level:** {level}  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** {skill['pos']}

---

## Purpose

{desc}

---

## Concepts Covered

{concepts_md}

---

## Rules / Best Practices

{rules_md}

---

## Checklists

### Implementation
- [ ] Follow all rules above
- [ ] Java 21 features used where applicable
- [ ] POS domain guidance followed

### Code Review
- [ ] No layer boundary violations
- [ ] Constructor injection used

---

## Project-Specific Guidance (Simple POS)

{skill['pos']}

---

## Recommended Reading
- [Java 21 Docs](https://docs.oracle.com/en/java/javase/21/)  
- [OpenJDK JEPs](https://openjdk.org/projects/jdk/21/)

---

## AI/Agent Guide

### Strict Conventions
- Follow all rules above
- Java 21 features (records, sealed, virtual threads, pattern matching)
- Constructor injection only; no static mutable state

### Preferred Libraries
- See references/canonical-stack.yaml

### Example Prompts

```
Implement {sid} in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
"""

total = 0
for domain_block in skills:
    domain = domain_block["domain"]
    for skill in domain_block["skills"]:
        skill_dir = base / domain / skill["id"]
        skill_dir.mkdir(parents=True, exist_ok=True)
        md = gen_skill(skill, domain)
        f = skill_dir / "SKILL.md"
        f.write_text(md, encoding="utf-8")
        total += 1

# Count results
domain_counts = {}
for domain_dir in base.iterdir():
    if domain_dir.is_dir():
        c = sum(1 for d in domain_dir.iterdir() if d.is_dir() and (d / "SKILL.md").exists())
        if c > 0:
            domain_counts[domain_dir.name] = c

print(f"Generated {total} skills")
for d, c in sorted(domain_counts.items()):
    print(f"  {d}: {c}")
print(f"Total: {sum(domain_counts.values())} skills across {len(domain_counts)} domains")
