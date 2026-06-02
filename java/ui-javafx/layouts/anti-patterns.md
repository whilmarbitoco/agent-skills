# Anti-Patterns: Layouts in JavaFX

## Pattern 1 — Hardcoded Sizes Prevent Resizing

```java
// WRONG: Fixed size never adapts
var textField = new TextField();
textField.setPrefWidth(200);
textField.setMinWidth(200);
textField.setMaxWidth(200);
// Always 200 px regardless of window size.
```

```java
// FIX: Let the parent decide
var textField = new TextField();
GridPane.setHgrow(textField, Priority.ALWAYS);
// Column constraints absorb extra space, field stretches.
```

## Pattern 2 — Nested GridPanes for Simple Rows

```java
// WRONG: Over-nested GridPanes
GridPane outer = new GridPane();
GridPane inner = new GridPane();
inner.add(label1, 0, 0);
inner.add(field1, 1, 0);
outer.add(inner, 0, 0); // Grid inside grid — unnecessary.
```

```java
// FIX: Single GridPane with row constraints
GridPane grid = new GridPane();
grid.add(label1, 0, 0);
grid.add(field1, 1, 0);
grid.add(label2, 0, 1);
grid.add(field2, 1, 1);
grid.setHgap(8);
grid.setVgap(8);
```

## Pattern 3 — Absolute Layout with AnchorPane

```java
// WRONG: AnchorPane for a responsive form
AnchorPane pane = new AnchorPane();
AnchorPane.setTopAnchor(label, 10.0);
AnchorPane.setLeftAnchor(label, 10.0);
// Pixel-perfect on one resolution, broken on all others.
```

```java
// FIX: GridPane with percentage columns
GridPane grid = new GridPane();
ColumnConstraints labelCol = new ColumnConstraints();
labelCol.setPercentWidth(30);
ColumnConstraints fieldCol = new ColumnConstraints();
fieldCol.setPercentWidth(70);
fieldCol.setHgrow(Priority.ALWAYS);
grid.getColumnConstraints().addAll(labelCol, fieldCol);
```

## Pattern 4 — No Grow Policy on Main Content

```java
// WRONG: Center region won't stretch
BorderPane root = new BorderPane();
root.setCenter(tableView);
// tableView hugs its preferred size; extra space goes to margins.
```

```java
// FIX: Set Vgrow/Hgrow on children
BorderPane.setMargin(tableView, new Insets(8));
tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
```

## Pattern 5 — Ignoring Insets and Gaps

```java
// WRONG: Controls piled on top of each other
VBox box = new VBox(
    new Label("Name"), nameField,
    new Label("Email"), emailField
);
// No spacing between elements.
```

```java
// FIX: Explicit spacing and padding
VBox box = new VBox(8); // 8 px gap between children
box.setPadding(new Insets(16)); // 16 px border padding
box.getChildren().addAll(
    new Label("Name"), nameField,
    new Label("Email"), emailField
);
```
