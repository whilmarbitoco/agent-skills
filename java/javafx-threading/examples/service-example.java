import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Restartable service that refreshes inventory data.
 * Can be called multiple times — each call runs independently.
 */
public class InventoryService extends Service<ObservableList<Product>> {

    private final ProductRepository repository;
    private String category = "all";

    public InventoryService(ProductRepository repository) {
        this.repository = repository;

        // React to state changes — these run on FX thread
        setOnRunning(event -> {
            progressIndicator.setVisible(true);
            refreshButton.setDisable(true);
        });
        setOnSucceeded(event -> {
            progressIndicator.setVisible(false);
            refreshButton.setDisable(false);
            productTable.setItems(getValue());
        });
        setOnFailed(event -> {
            progressIndicator.setVisible(false);
            refreshButton.setDisable(false);
            showErrorAlert("Refresh failed: " + getException().getMessage());
        });
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    protected Task<ObservableList<Product>> createTask() {
        String cat = this.category;
        return new Task<>() {
            @Override
            protected ObservableList<Product> call() throws Exception {
                List<Product> results;
                if ("all".equals(cat)) {
                    results = repository.findAll();
                } else {
                    results = repository.findByCategory(cat);
                }
                return FXCollections.observableArrayList(results);
            }
        };
    }
}

// Usage in controller:
InventoryService service = new InventoryService(repository);
refreshButton.setOnAction(event -> {
    service.setCategory(categoryCombo.getValue());
    service.restart();  // restart() safely cancels previous run
});
