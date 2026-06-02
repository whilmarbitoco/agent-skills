# Workspace Layout — Anti-Patterns

## 1. Source code in root module

```java
// WRONG — root module has src/main/java with utility classes
// root/
// ├── pom.xml
// └── src/main/java/com/pos/DateUtils.java  ← violates separation
```

```java
// FIX: root pom.xml has only <modules>; source lives in core or ui modules
// root/
// ├── pom.xml          ← only <modules> and <dependencyManagement>
// ├── core/
// │   └── src/main/java/com/pos/core/DateUtils.java  ← belongs here
// └── ui/
```

## 2. Core module imports JavaFX

```java
// WRONG — core depends on javafx.scene.paint.Color
import javafx.scene.paint.Color;

public class ProductFormatter {
    public Color availabilityColor(Product p) {  // leaks toolkit into domain
        return p.stock() > 0 ? Color.GREEN : Color.RED;
    }
}
```

```java
// FIX: core uses domain enums, UI maps to colors
package com.pos.core.product;

public enum Availability { IN_STOCK, LOW, OUT_OF_STOCK }
// UI layer maps Availability → Color, not the reverse
```

## 3. Missing module-info.java

```java
// WRONG — no module-info.java, so all packages are implicitly exported
// Any module can access internal APIs and break encapsulation
```

```java
// FIX: explicit module-info.java in every module
// core/src/main/java/module-info.java
module com.pos.core {
    exports com.pos.core.product;
    exports com.pos.core.sales;
    // internal packages stay internal
}
```

## 4. God-package with mixed concerns

```java
// WRONG — com.pos.utils holds ProductParser, ReceiptPrinter, ThemeLoader, etc.
package com.pos.utils;
```

```java
// FIX: one package per concern
// com.pos.core.product
// com.pos.core.receipt
// com.pos.ui.theme
// com.pos.ui.receipt
```

## 5. FXML far from its controller

```java
// WRONG — FXML in resources root, controller deep in java package
// src/main/resources/dashboard.fxml
// src/main/java/com/pos/ui/controller/DashboardController.java
```

```java
// FIX: FXML mirrors controller location
// src/main/java/com/pos/ui/controller/DashboardController.java
// src/main/resources/com/pos/ui/controller/dashboard.fxml
```
