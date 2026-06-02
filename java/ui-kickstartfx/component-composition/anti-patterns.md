# Component Composition — Anti-Patterns

## 1. God-controller with all UI logic inlined

```java
// WRONG — 800-line controller builds search bar, table, pagination by hand
public class ProductListController {
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        // 200 lines of HBox, TextField, Button wiring for search
        // 300 lines of TableView<Product> column setup
        // 200 lines of pagination logic
    }
}
```

```java
// FIX: extract focused components
// SearchBar.java + SearchBar.fxml — reusable search input with clear button
// PaginationBar.java + PaginationBar.fxml — page controls
// ProductListController includes them via <fx:include>
public class ProductListController {
    @FXML private SearchBar searchBar;
    @FXML private PaginationBar paginationBar;

    @FXML
    public void initialize() {
        searchBar.onQuery(this::filterProducts);
        paginationBar.onPage(this::loadPage);
    }
}
```

## 2. Direct child node access from outside the component

```java
// WRONG — external code reaches into component internals
productCard.getChildren().get(0).setStyle("...");  // fragile!
((Label) productCard.lookup(".price")).setText("₱99");
```

```java
// FIX: expose bindable properties, not internal nodes
public class ProductCard extends VBox {
    private final ObjectProperty<Product> product = new SimpleObjectProperty<>();
    private final BooleanProperty selected = new SimpleBooleanProperty();

    // Bind Label.textProperty() to product inside the component
    // External code only sets product or selected
}
```

## 3. No triple-file structure — CSS shared across components

```java
// WRONG — all styles in one monolithic styles.css, 600 lines, hard to find rules
```

```java
// FIX: each component owns its CSS
// component/card/ProductCard.java
// component/card/ProductCard.fxml
// component/card/ProductCard.css
```

## 4. Using removed/added style classes instead of pseudo-classes

```java
// WRONG — imperative style toggling
card.getStyleClass().add("error");
card.getStyleClass().remove("error");
// CSS: .card.error { ... }  — class list becomes messy over time
```

```java
// FIX: pseudo-class for transient state
card.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), isError);
// CSS: .card:error { -fx-border-color: -fx-brand-danger; }
```

## 5. Controller holding cross-view state

```java
// WRONG — ProductSearchController stores lastQuery used by three other controllers
public class ProductSearchController {
    public static String lastQuery;  // shared mutable static!
}
```

```java
// FIX: state goes on a shared model, injected into each controller
public record SearchState(String query, int page, int size) {}
// ProductSearchController, ProductListController, ExportController
// all receive the same SearchState object via constructor
```
