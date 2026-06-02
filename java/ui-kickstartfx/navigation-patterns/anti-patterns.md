# Navigation Patterns — Anti-Patterns

## 1. Controller-to-controller direct instantiation

```java
// WRONG — ProductListController directly creates ProductDetailController
public class ProductListController {
    public void onProductClick(Product p) {
        var detail = new ProductDetailController(p);  // tight coupling
        detail.show();
    }
}
```

```java
// FIX: navigate through the navigation service port
public class ProductListController {
    private final NavigationService nav;

    public ProductListController(NavigationService nav) {
        this.nav = nav;
    }

    public void onProductClick(Product p) {
        nav.goTo(Route.PRODUCT_DETAIL, Map.of("productId", p.id()));
    }
}
```

## 2. Eager loading every view at startup

```java
// WRONG — all FXML screens load when the app starts
Map<Route, Node> views = Map.of(
    Route.DASHBOARD, loadFxml("dashboard.fxml"),
    Route.PRODUCTS,  loadFxml("products.fxml"),
    Route.REPORTS,   loadFxml("reports.fxml"),  // 50+ MB of unused nodes
    // ...
);
```

```java
// FIX: lazy-load via Supplier; only construct when first navigated to
Map<Route, Supplier<Node>> views = Map.of(
    Route.DASHBOARD, () -> loadFxml("dashboard.fxml"),
    Route.PRODUCTS,  () -> loadFxml("products.fxml"),
    Route.REPORTS,   () -> loadFxml("reports.fxml"),
);
```

## 3. No route type safety — using strings

```java
// WRONG — magic strings, typo-prone
nav.goTo("product-detail");
nav.goTo("ProductDetail"); // silent bug
```

```java
// FIX: sealed interface for routes — compiler catches typos
sealed interface Route permits Dashboard, ProductDetail, SalesList {
    record Dashboard() implements Route {}
    record ProductDetail(String productId) implements Route {}
    record SalesList(LocalDate date) implements Route {}
}
```

## 4. Static singleton for navigation

```java
// WRONG — global mutable state, untestable
NavigationService.getInstance().goTo(Route.DASHBOARD);
```

```java
// FIX: constructor-injected service
public class ShellController {
    private final NavigationService nav;
    // Constructor injection — mockable in tests
    ShellController(NavigationService nav) {
        this.nav = nav;
    }
}
```

## 5. No history stack — no back navigation

```java
// WRONG — direct setRoot, no way to go back
public void navigateTo(Route route) {
    scene.setRoot(views.get(route));
}
```

```java
// FIX: maintain a history stack, expose goBack()
public class NavigationService {
    private final Deque<Route> history = new ArrayDeque<>(20);

    public void goTo(Route route) {
        if (history.size() >= 20) history.pollFirst();
        history.push(currentRoute);
        currentRoute = route;
        scene.setRoot(views.get(route).get());
    }

    public void goBack() {
        if (!history.isEmpty()) {
            currentRoute = history.poll();
            scene.setRoot(views.get(currentRoute).get());
        }
    }
}
```
