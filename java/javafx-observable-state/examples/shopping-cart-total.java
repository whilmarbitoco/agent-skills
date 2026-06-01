import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * ShoppingCartTotal binds a Label to the sum of cart items.
 * Uses Bindings.createBigDecimalBinding — automatic recalculation
 * when items are added/removed or quantities change.
 */
public class ShoppingCartTotal {

    static final Currency PHP = Currency.getInstance("PHP");

    private final ObservableList<CartItem> items =
        FXCollections.observableArrayList(
            item -> new javafx.beans.Observable[]{
                item.quantityProperty(), item.priceProperty()
            }
        );

    private final Label totalLabel;

    public ShoppingCartTotal(Label totalLabel) {
        this.totalLabel = totalLabel;
        bindTotal();
    }

    private void bindTotal() {
        totalLabel.textProperty().bind(
            Bindings.createStringBinding(() -> {
                BigDecimal total = items.stream()
                    .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                return PHP.getSymbol() + " " + total.setScale(2).toPlainString();
            }, items)
        );
    }

    public void addItem(String name, BigDecimal price, int qty) {
        items.add(new CartItem(name, price, qty));
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    /** ViewModel item with JavaFX properties for binding. */
    static class CartItem {
        private final SimpleStringProperty name = new SimpleStringProperty();
        private final SimpleObjectProperty<BigDecimal> price = new SimpleObjectProperty<>();
        private final javafx.beans.property.SimpleIntegerProperty quantity =
            new javafx.beans.property.SimpleIntegerProperty();

        CartItem(String name, BigDecimal price, int quantity) {
            this.name.set(name);
            this.price.set(price);
            this.quantity.set(quantity);
        }

        String getName() { return name.get(); }
        BigDecimal getPrice() { return price.get(); }
        int getQuantity() { return quantity.get(); }
        javafx.beans.property.StringProperty nameProperty() { return name; }
        javafx.beans.property.ObjectProperty<BigDecimal> priceProperty() { return price; }
        javafx.beans.property.IntegerProperty quantityProperty() { return quantity; }
    }
}
