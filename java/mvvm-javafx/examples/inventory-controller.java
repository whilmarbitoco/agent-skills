import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.binding.Bindings;

/**
 * Thin Controller: instantiates ViewModel, binds it to FXML elements.
 * NO business logic here — only binding wiring.
 */
public class InventoryController implements Initializable {

    @FXML private TableView<ProductView> productTable;
    @FXML private TableColumn<ProductView, String> nameColumn;
    @FXML private TableColumn<ProductView, String> priceColumn;
    @FXML private TableColumn<ProductView, Number> stockColumn;
    @FXML private TextField searchField;
    @FXML private Label statusLabel;

    private InventoryViewModel viewModel;

    /** Called after FXML injection. Sets up ViewModel and bindings. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductService productService = new ProductService(new EbeanProductRepository());
        viewModel = new InventoryViewModel(productService);

        // ✅ Bind ViewModel properties to View elements
        productTable.setItems(viewModel.getProducts());
        statusLabel.textProperty().bind(viewModel.statusMessageProperty());
        searchField.textProperty().bindBidirectional(viewModel.searchQueryProperty());

        // Column cell value factories
        nameColumn.setCellValueFactory(
            cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().name()));
        priceColumn.setCellValueFactory(
            cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getPriceFormatted()));
        stockColumn.setCellValueFactory(
            cellData -> new javafx.beans.property.SimpleIntegerProperty(
                cellData.getValue().stock()).asObject());

        viewModel.loadProducts();
    }

    /** Minimal View record matching ViewModel.ProductView. */
    public record ProductView(long id, String name, BigDecimal price, int stock) {
        public String getName() { return name; }
        public String getPriceFormatted() {
            return Currency.getInstance("PHP").getSymbol() + " "
                + price.setScale(2).toPlainString();
        }
    }
}
