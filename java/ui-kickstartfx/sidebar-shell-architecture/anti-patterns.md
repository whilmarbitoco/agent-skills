# Sidebar Shell Architecture — Anti-Patterns

## 1. Reloading the shell on every navigation

```java
// WRONG — entire shell FXML reloads when switching views → visible flash
public void navigate(Route route) {
    Parent shell = fxmlLoader.load(getClass().getResource("/shell.fxml"));
    ((StackPane) shell.lookup("#content")).getChildren().setAll(loadView(route));
    stage.setScene(new Scene(shell));  // entire scene rebuilt
}
```

```java
// FIX: shell loads once; only center content changes
public class ShellController {
    @FXML private StackPane contentArea;

    public void setContent(Node view) {
        contentArea.getChildren().setAll(view);
    }
}
```

## 2. Controllers directly accessing shell elements

```java
// WRONG — content controller manipulates sidebar
public class ReportController {
    public void showReport(String title) {
        var sidebar = (VBox) Scene.lookup("#sidebar");
        sidebar.getChildren().add(new Label(title));  // content knows about shell
    }
}
```

```java
// FIX: shell exposes a shell state model, content controller updates that
public class ShellController {
    private final StringProperty pageTitle = new SimpleStringProperty("");
    // Bind title Label to pageTitle
}

// Navigation controller sets shell state
shellController.setPageTitle("Sales Report");
```

## 3. Hard-coded sidebar pixel widths

```java
// WRONG — magic numbers in code
sidebar.setPrefWidth(240);
sidebar.setMinWidth(240);
sidebar.setMaxWidth(240);
// CSS: .sidebar { -fx-pref-width: 240px; }
```

```java
// FIX: drive widths from CSS custom properties
// CSS: .sidebar { -fx-pref-width: var(--sidebar-width, 240px); }
// Collapse: --sidebar-collapsed-width: 56px;

public void setCollapsed(boolean collapsed) {
    sidebar.pseudoClassStateChanged(COLLAPSED, collapsed);
}
```

## 4. No animation on collapse/expand

```java
// WRONG — instant width jump, jarring UX
public void toggleSidebar() {
    sidebar.setPrefWidth(isCollapsed ? 56 : 240);
    sidebar.setMinWidth(isCollapsed ? 56 : 240);
}
```

```java
// FIX: smooth animated transition
public void setCollapsed(boolean collapsed) {
    double target = collapsed ? 56 : 240;
    double current = sidebar.getWidth();
    var timeline = new Timeline(
        new KeyFrame(Duration.millis(200),
            new KeyValue(sidebar.prefWidthProperty(), target, Interpolator.EASE_BOTH)
        )
    );
    timeline.play();
}
```

## 5. Sidebar knows about every controller

```java
// WRONG — shell imports all 15 controllers
import SalesController;
import ProductController;
import ReportController;
// Shell construct and holds references to all
```

```java
// FIX: shell only knows about Route enum and NavigationService
// Route enum lists all destinations; shell binds sidebar buttons to routes
public sealed interface Route permits
    Dashboard, Products, Sales, Reports, Settings {}
```
