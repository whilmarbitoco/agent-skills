import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.math.BigDecimal;
import java.util.List;

/**
 * CardListExample renders product cards in a VBox inside a ScrollPane.
 * Each card is an HBox with name/price. VBox grows to fill ScrollPane.
 */
public class CardListExample extends ScrollPane {

    private final VBox cardContainer = new VBox(8);

    public CardListExample() {
        setContent(cardContainer);
        setFitToWidth(true);  // cards stretch to full width
        setFitToHeight(false);
        setPannable(true);
        setStyle("-fx-background: #f5f5f5;");

        cardContainer.setPadding(new Insets(12));
        cardContainer.setStyle("-fx-background-color: #f5f5f5;");
    }

    public void setProducts(List<Product> products) {
        cardContainer.getChildren().clear();
        for (Product product : products) {
            cardContainer.getChildren().add(createCard(product));
        }
    }

    private HBox createCard(Product product) {
        HBox card = new HBox();
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: white; -fx-border-radius: 6; -fx-background-radius: 6;");
        card.setAlignment(Pos.CENTER_LEFT);
        card.setSpacing(16);
        HBox.setHgrow(card, Priority.ALWAYS);

        VBox left = new VBox(4);
        Label nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label stockLabel = new Label("Stock: " + product.getStockQuantity());
        stockLabel.setStyle("-fx-text-fill: #666;");
        left.getChildren().addAll(nameLabel, stockLabel);

        Label priceLabel = new Label("₱ " + product.getPrice().setScale(2).toPlainString());
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        card.getChildren().addAll(left, priceLabel);
        HBox.setHgrow(left, Priority.ALWAYS);

        return card;
    }

    /** Minimal product record for demo. */
    public record Product(String name, BigDecimal price, int stockQuantity) {
        public String getName() { return name; }
        public BigDecimal getPrice() { return price; }
        public int getStockQuantity() { return stockQuantity; }
    }
}
