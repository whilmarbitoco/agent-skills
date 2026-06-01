import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

/**
 * InventoryViewModel holds observable state for the inventory view.
 * Has NO reference to any JavaFX View types (no ListView, Label, etc.)
 * — only Properties and ObservableLists.
 * The FXML View binds to these properties.
 */
public class InventoryViewModel {

    static final Currency PHP = Currency.getInstance("PHP");

    private final ObservableList<ProductView> products =
        FXCollections.observableArrayList();
    private final StringProperty searchQuery = new SimpleStringProperty("");
    private final SimpleStringProperty statusMessage = new SimpleStringProperty("Ready");
    private final SimpleBooleanProperty loading = new SimpleBooleanProperty(false);
    private final SimpleObjectProperty<ProductView> selectedProduct =
        new SimpleObjectProperty<>();

    private final ProductService productService;

    /** Constructor injection — no static mutable state. */
    public InventoryViewModel(ProductService productService) {
        this.productService = productService;
    }

    /** Load products from service, update observable list. */
    public void loadProducts() {
        loading.set(true);
        statusMessage.set("Loading...");

        productService.findAll().thenAccept(productList -> {
            javafx.application.Platform.runLater(() -> {
                products.setAll(productList.stream()
                    .map(ProductView::fromEntity)
                    .toList());
                loading.set(false);
                statusMessage.set("Loaded " + products.size() + " products");
            });
        }).exceptionally(ex -> {
            javafx.application.Platform.runLater(() -> {
                loading.set(false);
                statusMessage.set("Error: " + ex.getMessage());
            });
            return null;
        });
    }

    /** Expose read-only access — View binds these. */
    public ObservableList<ProductView> getProducts() { return products; }
    public StringProperty searchQueryProperty() { return searchQuery; }
    public StringProperty statusMessageProperty() { return statusMessage; }
    public SimpleBooleanProperty loadingProperty() { return loading; }
    public SimpleObjectProperty<ProductView> selectedProductProperty() { return selectedProduct; }

    /** View-layer DTO — flat, bindable. */
    public record ProductView(long id, String name, BigDecimal price, int stock) {
        public static ProductView fromEntity(Product entity) {
            return new ProductView(entity.getId(), entity.getName(),
                entity.getPrice(), entity.getStockQuantity());
        }

        public String getPriceFormatted() {
            return PHP.getSymbol() + " " + price.setScale(2).toPlainString();
        }
    }
}
