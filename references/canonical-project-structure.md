# Canonical Project Structure — Simple POS System
# Opinionated directory layout for Java 21 + JavaFX + Maven projects.

project:
  group_id: "com.simplepos"
  artifact_id: "simple-pos"
  base_package: "com.simplepos"

structure:
  root:
    files:
      - "pom.xml"                    # Parent POM (multi-module or single)
      - "mvnw / mvnw.cmd"            # Maven Wrapper (required)
      - ".env.example"               # Template for local config
      - "README.md"
      - "LICENSE"

  src_main_java:
    path: "src/main/java/com/simplepos"
    packages:
      app:
        name: "app"
        purpose: "Application entry point and lifecycle"
        files:
          - "SimplePosApplication.java"    # JavaFX Application subclass
          - "AppLauncher.java"              # Launcher (avoids module issues)

      bootstrap:
        name: "bootstrap"
        purpose: "DI wiring, config loading, startup orchestration"
        files:
          - "AppBootstrap.java"
          - "ServiceLocator.java"           # Lightweight service locator

      config:
        name: "config"
        purpose: "Configuration loading and environment management"
        files:
          - "AppConfig.java"
          - "DatabaseConfig.java"

      domain:
        name: "domain"
        purpose: "Domain entities, value objects, enums"
        subpackages:
          - "model/"                       # Ebean entities
          - "enums/"                       # Domain enumerations
          - "value/"                       # Value objects (records)

      infrastructure:
        name: "infrastructure"
        purpose: "External system adapters"
        subpackages:
          - "printer/"                     # Receipt printer abstraction
          - "barcode/"                     # Barcode scanner/hardware
          - "os/"                          # OS-specific utilities
          - "io/"                          # File I/O utilities

      persistence:
        name: "persistence"
        purpose: "Ebean models, repositories, migrations"
        files:
          - "EbeanServerConfig.java"
          - "DatabaseMigration.java"
        subpackages:
          - "repository/"                  # Repository interfaces + impls
          - "migration/"                   # Ebean migration scripts

      services:
        name: "services"
        purpose: "Business logic — UI-independent"
        subpackages:
          - "inventory/"
          - "sales/"
          - "report/"
          - "session/"

      ui:
        name: "ui"
        purpose: "All JavaFX UI code"
        subpackages:
          components:
            name: "components"
            purpose: "Reusable UI widgets (cards, badges, custom controls)"
          layouts:
            name: "layouts"
            purpose: "Shell layouts, sidebar, responsive grids"
          screens:
            name: "screens"
            purpose: "Feature screens (inventory, sales, reports, settings)"
          dialogs:
            name: "dialogs"
            purpose: "Modal dialogs and wizard flows"
          theme:
            name: "theme"
            purpose: "CSS files, font loading, dark mode support"

      util:
        name: "util"
        purpose: "Cross-cutting utilities"
        files:
          - "StringUtils.java"
          - "DateUtils.java"
          - "CurrencyUtils.java"
          - "FxUtils.java"                 # JavaFX helpers (alerts, dialogs)

      reports:
        name: "reports"
        purpose: "JasperReports templates and generation"
        files:
          - "ReportService.java"
        subpackages:
          - "templates/"                   # .jrxml files (in resources)
          - "generator/"                   # Report generation logic

  src_main_resources:
    path: "src/main/resources"
    files:
      - "logback.xml"
      - "application.properties"
      - "css/theme.css"
      - "css/dark-theme.css"
      - "fxml/"                          # FXML files alongside controllers
      - "images/"                        # Icons, logos
      - "reports/*.jrxml"               # JasperReports templates
      - "db/migration/"                 # SQL migration scripts

  src_test:
    path: "src/test/java/com/simplepos"
    mirrors: "src/main/java structure"
    conventions:
      - "*Test.java for unit tests"
      - "*IT.java for integration tests"
      - "TestFX for UI tests (headless where possible)"

  src_test_resources:
    path: "src/test/resources"
    files:
      - "test-data.sql"
      - "application-test.properties"

principles:
  - "Package by feature first, then layer if a feature grows large"
  - "No cyclic dependencies between packages"
  - "Domain package has zero dependencies on ui, infrastructure, or persistence"
  - "Services depend only on domain and persistence interfaces"
  - "UI depends on services (via interface), never on persistence directly"
  - "All Ebean entities in persistence, references in domain via interfaces"
