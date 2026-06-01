import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * ViewModel showing command binding pattern.
 * Exposes Runnable commands that FXML buttons bind to via Controller.
 */
public class OrderViewModel {

    private final ObservableList<OrderLineView> orderLines =
        FXCollections.observableArrayList();
    private final StringProperty customerName = new SimpleStringProperty("");
    private final StringProperty errorMessage = new SimpleStringProperty("");
    private final SimpleStringProperty totalAmount = new SimpleStringProperty("₱ 0.00");

    private final OrderService orderService;

    /** Constructor injection. */
    public OrderViewModel(OrderService orderService) {
        this.orderService = orderService;
    }

    /** Command: bound to "Add Item" button in FXML. */
    public void addOrderLine(long productId, int quantity) {
        // Fetch product, add line
    }

    /** Command: bound to "Submit Order" button in FXML. */
    public void submitOrder() {
        if (customerName.get().isBlank()) {
            errorMessage.set("Customer name is required");
            return;
        }
        orderService.createOrder(customerName.get(), orderLines)
            .thenAccept(result ->
                javafx.application.Platform.runLater(() -> {
                    orderLines.clear();
                    customerName.set("");
                    errorMessage.set("");
                })
            );
    }

    public ObservableList<OrderLineView> getOrderLines() { return orderLines; }
    public StringProperty customerNameProperty() { return customerName; }
    public StringProperty errorMessageProperty() { return errorMessage; }
    public StringProperty totalAmountProperty() { return totalAmount; }

    public record OrderLineView(String productName, int quantity, BigDecimal unitPrice) {}
}
