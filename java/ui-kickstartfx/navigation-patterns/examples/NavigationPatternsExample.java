import java.util.*;

/**
 * Navigation Patterns — Route registry, navigation service, history stack.
 * Demonstrates sealed Route interface, lazy loading, and back navigation.
 * Java 21 — real compilable code.
 */
public class NavigationPatternsExample {

    // ── Sealed interface for type-safe routes ──
    sealed interface Route permits Dashboard, ProductDetail, SalesList, Reports {
        record Dashboard() implements Route {}
        record ProductDetail(String productId) implements Route {}
        record SalesList(LocalDate date) implements Route {}
        record Reports(String reportType) implements Route {}
    }

    // ── Navigation context for passing data between screens ──
    record NavigationContext(Map<String, Object> params) {
        NavigationContext() { this(new HashMap<>()); }

        @SuppressWarnings("unchecked")
        <T> T get(String key) { return (T) params.get(key); }

        NavigationContext with(String key, Object value) {
            var copy = new HashMap<>(params);
            copy.put(key, value);
            return new NavigationContext(copy);
        }
    }

    // ── Navigation state ──
    record NavState(Route currentRoute, NavigationContext context) {}

    // ── Navigation service port (interface for testability) ──
    interface NavigationService {
        void goTo(Route route, NavigationContext ctx);
        void goBack();
        NavState currentState();
    }

    // ── In-memory implementation with history stack ──
    static class InMemoryNavigationService implements NavigationService {
        private static final int MAX_HISTORY = 20;

        private final Map<Route, String> routeViewMap = new HashMap<>();
        private final Deque<NavState> history = new ArrayDeque<>(MAX_HISTORY);
        private NavState current;

        InMemoryNavigationService(Route initialRoute) {
            this.current = new NavState(initialRoute, new NavigationContext());
        }

        @Override
        public void goTo(Route route, NavigationContext ctx) {
            if (history.size() >= MAX_HISTORY) {
                history.pollFirst(); // remove oldest
            }
            history.push(current); // save current to history
            current = new NavState(route, ctx);
            var view = routeViewMap.getOrDefault(route, "default-view");
            System.out.printf("Loading view %-20s | context=%s%n", view, ctx.params());
        }

        @Override
        public void goBack() {
            if (!history.isEmpty()) {
                current = history.poll();
                System.out.printf("Back to: %s%n", current.currentRoute());
            } else {
                System.out.println("No history — already at root");
            }
        }

        @Override
        public NavState currentState() { return current; }

        // Register a route with its view
        void register(Route route, String fxmlPath) {
            routeViewMap.put(route, fxmlPath);
        }
    }

    public static void main(String[] args) {
        var nav = new InMemoryNavigationService(
            new Route.Dashboard()
        );
        nav.register(new Route.Dashboard(), "dashboard.fxml");
        nav.register(new Route.ProductDetail(""), "product-detail.fxml");
        nav.register(new Route.SalesList(null), "sales-list.fxml");

        System.out.println("=== Navigation Demo ===");
        nav.goTo(new Route.ProductDetail("SKU-123"),
            new NavigationContext().with("productId", "SKU-123"));

        nav.goTo(new Route.SalesList(java.time.LocalDate.of(2026, 6, 1)),
            new NavigationContext().with("date", "2026-06-01"));

        System.out.println("\n=== Back Navigation ===");
        nav.goBack();
        nav.goBack();
        nav.goBack(); // already at root
    }
}
