import javafx.concurrent.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Load products from database on background thread,
 * update TableView on FX thread.
 */
public class ProductLoader {

    private final ProductRepository repository;
    private final TableView<Product> table;
    private final TextField searchField;

    public ProductLoader(ProductRepository repository,
                         TableView<Product> table,
                         TextField searchField) {
        this.repository = repository;
        this.table = table;
        this.searchField = searchField;
    }

    public void load() {
        String query = searchField.getText();

        Task<ObservableList<Product>> task = new Task<>() {
            @Override
            protected ObservableList<Product> call() throws Exception {
                // This runs on a background thread — safe for DB access
                List<Product> results = repository.findByName(query);
                return FXCollections.observableArrayList(results);
            }
        };

        task.setOnSucceeded(event -> {
            // This runs on FX thread — safe to update UI
            table.setItems(task.getValue());
        });

        task.setOnFailed(event -> {
            // Always handle failure
            Throwable ex = task.getException();
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Load failed", ex);
            showErrorAlert("Failed to load products: " + ex.getMessage());
        });

        new Thread(task).start();
    }
}
