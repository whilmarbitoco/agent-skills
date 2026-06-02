import java.util.*;

/**
 * Component Composition — custom control trio with FXML loading,
 * bindable properties, and pseudo-class state toggling.
 * Java 21 — real compilable code.
 */
public class ComponentCompositionExample {

    // ── Simulates PseudoClass for state-driven CSS ──
    record PseudoClass(String name) {
        static final PseudoClass ERROR = new PseudoClass("error");
        static final PseudoClass SELECTED = new PseudoClass("selected");
        static final PseudoClass COLLAPSED = new PseudoClass("collapsed");
    }

    // ── Simulates custom control base ──
    abstract static class CustomControl {
        protected final Map<PseudoClass, Boolean> pseudoStates = new LinkedHashMap<>();
        protected final Map<String, Object> properties = new LinkedHashMap<>();

        void pseudoClassStateChanged(PseudoClass pc, boolean active) {
            pseudoStates.put(pc, active);
        }

        boolean isActive(PseudoClass pc) {
            return pseudoStates.getOrDefault(pc, false);
        }

        void setProperty(String key, Object value) {
            properties.put(key, value);
        }

        @SuppressWarnings("unchecked")
        <T> T getProperty(String key) {
            return (T) properties.get(key);
        }

        String renderState() {
            var activeClasses = pseudoStates.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(e -> ":" + e.getKey().name())
                .reduce("", (a, b) -> a + b);
            return "%s%s".formatted(getClass().getSimpleName(), activeClasses);
        }
    }

    // ── ProductCard component with bindable product and selected state ──
    static class ProductCard extends CustomControl {
        private final StringProperty productName = new StringProperty("");
        private final StringProperty price = new StringProperty("");

        void bindProduct(String name, String price) {
            this.productName.set(name);
            this.price.set(price);
        }

        String productName() { return productName.get(); }
        String price() { return price.get(); }
    }

    record StringProperty(String value) {
        void set(String v) {}
        String get() { return value; }
    }

    // ── SearchBar component with onQuery callback ──
    static class SearchBar extends CustomControl {
        private String query = "";

        void setQuery(String q) {
            this.query = q;
            // In real FX: onQueryProperty().get().accept(q);
        }

        String query() { return query; }
    }

    // ── PaginationBar component ──
    static class PaginationBar extends CustomControl {
        private int currentPage = 0;
        private int totalPages = 0;

        void setPage(int page, int total) {
            this.currentPage = page;
            this.totalPages = total;
        }

        int currentPage() { return currentPage; }
        int totalPages() { return totalPages; }
        boolean hasNext() { return currentPage < totalPages - 1; }
        boolean hasPrevious() { return currentPage > 0; }
    }

    public static void main(String[] args) {
        System.out.println("=== ProductCard Component ===");
        var card = new ProductCard();
        card.bindProduct("Coffee Latte", "120.00");
        System.out.printf("  Name: %s | Price: ₱%s%n", card.productName(), card.price());
        System.out.println("  State: " + card.renderState());

        card.pseudoClassStateChanged(PseudoClass.SELECTED, true);
        System.out.println("  After select → " + card.renderState());

        card.pseudoClassStateChanged(PseudoClass.ERROR, true);
        System.out.println("  After error → " + card.renderState());

        System.out.println("\n=== PaginationBar Component ===");
        var pagination = new PaginationBar();
        pagination.setPage(2, 10);
        System.out.printf("  Page %d of %d | hasNext=%s | hasPrevious=%s%n",
            pagination.currentPage() + 1, pagination.totalPages(),
            pagination.hasNext(), pagination.hasPrevious());
    }
}
