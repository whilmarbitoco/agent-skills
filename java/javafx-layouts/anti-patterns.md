# JavaFX Layouts Anti-Patterns

## Using AnchorPane for everything

```java
// WRONG — absolute positioning breaks on resize
AnchorPane pane = new AnchorPane();
AnchorPane.setTopAnchor(label, 10.0);
AnchorPane.setLeftAnchor(label, 20.0);
```

**Use BorderPane/GridPane/VBox instead. AnchorPane doesn't adapt to window size.**

## Manually tracking scene size for responsiveness

```java
// WRONG — manual resize listener, every node positioned individually
scene.widthProperty().addListener((obs, was, now) -> {
    button.setLayoutX(now.doubleValue() - 100); // fragile
});
```

**Use layout constraints (Hgrow, Vgrow) and BorderPane regions — they resize automatically.**

## GridPane without column constraints — uneven columns

```java
// WRONG — columns sized by widest content, looks uneven
gridPane.add(label, 0, 0);
gridPane.add(field, 1, 0);
gridPane.add(label2, 0, 1);
gridPane.add(field2, 1, 1); // different sizes = ugly
```

**Set ColumnConstraints: `new ColumnConstraints(120)` for labels, `new ColumnConstraints(USE_COMPUTED_SIZE, 300, 600)` for fields.**

## Scrolling content without ScrollPane

```java
// WRONG — content clipped, no scroll
VBox content = new VBox(10);
for (int i = 0; i < 100; i++) content.getChildren().add(new Label("Item " + i));
root.setCenter(content); // overflows, invisible items
```

**Wrap in ScrollPane: `root.setCenter(new ScrollPane(content))`.**

## Putting BorderPane directly on Scene — no root container

```java
// WRONG — BorderPane has no padding, content touches edges
Scene scene = new Scene(borderPane, 900, 600);
```

**Wrap BorderPane in a StackPane or add padding: `borderPane.setPadding(new Insets(16))`.**
