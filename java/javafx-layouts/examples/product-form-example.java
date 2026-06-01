import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.geometry.Pos;

/**
 * ProductFormExample uses GridPane for a labeled input form.
 * Labels in column 0, fields in column 1, errors in column 2.
 * ColumnConstraints control label/field width ratio.
 */
public class ProductFormExample extends GridPane {

    private final TextField nameField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField stockField = new TextField();
    private final Button saveButton = new Button("Save");

    public ProductFormExample() {
        // ✅ GridPane for forms (Rule 2)
        setHgap(12);
        setVgap(10);
        setPadding(new Insets(24));

        // Two columns: labels auto-width, fields grow
        javafx.scene.layout.ColumnConstraints col1 = new javafx.scene.layout.ColumnConstraints();
        col1.setHalignment(HPos.RIGHT);
        col1.setPrefWidth(100);

        javafx.scene.layout.ColumnConstraints col2 = new javafx.scene.layout.ColumnConstraints();
        col2.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        col2.setFillWidth(true);

        getColumnConstraints().addAll(col1, col2);

        int row = 0;
        addFormRow(row++, "Name *", nameField);
        addFormRow(row++, "Price (₱) *", priceField);
        addFormRow(row++, "Stock *", stockField);

        // Save button spans field column
        add(saveButton, 1, row);
        setMargin(saveButton, new Insets(12, 0, 0, 0));
    }

    private void addFormRow(int row, String label, TextField field) {
        Label labelNode = new Label(label);
        add(labelNode, 0, row);
        add(field, 1, row);
    }
}
