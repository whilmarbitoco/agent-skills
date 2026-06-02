# Responsive Desktop Layouts — Anti-Patterns

## 1. Hard-coded pixel sizes everywhere

```java
// WRONG — works on one monitor, breaks on another
table.setPrefWidth(800);
sidebar.setPrefWidth(240);
detailPane.setPrefWidth(400);
// On a 1920px screen this wastes space; on 1366px it overflows.
```

```java
// FIX: bind to fractions of available space
table.prefWidthProperty().bind(
    BorderPane.getCenter().widthProperty().multiply(0.5)
);
sidebar.prefWidthProperty().bind(
    scene.widthProperty().multiply(0.18)
);
```

## 2. Manual Platform.runListener for layout changes

```java
// WRONG — imperative recalculation on every pulse
scene.widthProperty().addListener((obs, old, val) -> {
    double w = val.doubleValue();
    if (w > 1440) {
        grid.setColumns(4);
    } else if (w > 1024) {
        grid.setColumns(3);
    } else {
        grid.setColumns(2);
    }
});
```

```java
// FIX: use FlowPane which auto-wraps; or bind column count with a custom binding
IntegerBinding columns = Bindings.createIntegerBinding(() -> {
    double w = scene.getWidth();
    if (w >= 1440) return 4;
    if (w >= 1024) return 3;
    return 2;
}, scene.widthProperty());
```

## 3. Ignoring DPI scaling

```java
// WRONG — assumes 1px = 1 logical pixel at 96 DPI
label.setFont(Font.font(14));
imageView.setFitWidth(200);
// Looks tiny on a HiDPI 4K display, huge on an external low-DPI monitor
```

```java
// FIX: use em-based sizes, let JavaFX handle DPI
/* CSS: .label { -fx-font-size: 0.875rem; } */
/* JavaFX already scales rem against the display DPI — use Region.USE_COMPUTED_SIZE */
card.setMinWidth(Region.USE_COMPUTED_SIZE);
```

## 4. Fixed scene size prevents resizing

```java
// WRONG — locks the window
stage.setScene(new Scene(root, 1280, 720));
stage.setResizable(false);
```

```java
// FIX: set reasonable minimums, let user resize freely
stage.setMinWidth(960);
stage.setMinHeight(600);
stage.setScene(new Scene(root));
stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8);
```

## 5. Always-visible sidebar on small screens

```java
// WRONG — sidebar docked at all widths, pushing content off-screen
BorderPane.setAlignment(sidebar, Pos.CENTER_LEFT);
// On 1024px screen, 240px sidebar + 800px table = overflow
```

```java
// FIX: toggle sidebar between docked and overlay based on breakpoint
scene.widthProperty().addListener((obs, old, val) -> {
    boolean docked = val.doubleValue() >= 1024;
    sidebar.setManaged(docked);
    sidebar.setVisible(docked);
});
// Below breakpoint: sidebar becomes overlay toggled by hamburger button
```
