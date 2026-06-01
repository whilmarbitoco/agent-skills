import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * ProductTableExample shows an ObservableList backing a TableView
 * with a FilteredList for search. The filtered list auto-updates
 * when search text changes — no manual refresh needed.
 */
public class ProductTableExample {

    private final ProductRepository repository;
    private final ObservableList<Product> products =
        FXCollections.observableArrayList(
            product -> new javafx.beans.Observable[]{product.nameProperty()}
        );
    private final FilteredList<Product> filteredProducts = new FilteredList<>(products);
    private final javafx.scene.control.TextField searchField;
    private final javafx.scene.control.TableView<Product> table;

    public ProductTableExample(ProductRepository repository,
                               javafx.scene.control.TextField searchField,
                               javafx.scene.control.TableView<Product> table) {
        this.repository = repository;
        this.searchField = searchField;
        this.table = table;

        setupSearch();
        setupTable();
    }

    private void setupSearch() {
        // InvalidationListener is appropriate here — we only need to know
        // that the value changed, not the old value.
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String filter = newVal == null ? "" : newVal.toLowerCase().trim();
            filteredProducts.setPredicate(product ->
                filter.isEmpty() ||
                product.getName().toLowerCase().contains(filter)
            );
        });
    }

    private void setupTable() {
        table.setItems(filteredProducts);
        // columns set elsewhere in FXML or controller
    }

    /** Replace the backing list — filtered list auto-updates. */
    public void refresh(javafx.concurrent.Task<ObservableList<Product>> loadTask) {
        loadTask.setOnSucceeded(event -> {
            products.setAll(loadTask.getValue());
        });
        loadTask.setOnFailed(event -> {
            org.slf4j.LoggerFactory.getLogger(getClass())
                .error("Failed to load products", loadTask.getException());
        });
        new Thread(loadTask).start();
    }
}
