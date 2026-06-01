import org.int4.fx.values.ObservableValue;
import org.int4.fx.values.Var;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * KickStartFX reactive bindings with fx-values.
 * Var is mutable, Val is derived/read-only.
 * Much cleaner than raw Property API.
 */
public class InventoryViewModel {

    // Mutable state — use Var
    private final Var<String> searchQuery = Var.newVar("");
    private final Var<Boolean> isLoading = Var.newVar(false);

    // Derived/read-only state — use Val
    // Automatically re-evaluated when searchQuery changes
    public ObservableValue<Boolean> hasResults = isLoading.not()
        .and(searchQuery.map(s -> !s.isBlank()));

    // Filtered list — declarative, no manual listeners
    private final Var<ObservableList<Product>> allProducts =
        Var.newVar(FXCollections.observableArrayList());

    public ObservableValue<ObservableList<Product>> filteredProducts =
        Var.map(searchQuery, query -> {
            if (query.isBlank()) return allProducts.get();
            return allProducts.get().stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(java.util.stream.Collectors.toCollection(
                    FXCollections::observableArrayList));
        });

    // Actions
    public void setSearch(String query) {
        searchQuery.set(query);
    }

    public void loadProducts() {
        isLoading.set(true);
        // Background load — same VT pattern as javafx-threading
        Thread.startVirtualThread(() -> {
            List<Product> results = repository.findAll();
            javafx.application.Platform.runLater(() -> {
                allProducts.set(FXCollections.observableArrayList(results));
                isLoading.set(false);
            });
        });
    }

    // Constructor injection
    private final ProductRepository repository;

    public InventoryViewModel(ProductRepository repository) {
        this.repository = repository;
    }
}
