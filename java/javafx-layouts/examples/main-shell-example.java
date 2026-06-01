import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * Main application shell using BorderPane.
 * Left sidebar + center content area. This is the top-level layout.
 */
public class MainShellExample extends BorderPane {

    public MainShellExample() {
        // ✅ BorderPane for top-level shell (Rule 1)
        setPadding(new Insets(0));
        setLeft(buildSidebar());
        setCenter(buildWelcomeContent());
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(16));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #2d2d2d;");

        Button productsBtn = new Button("Products");
        Button salesBtn = new Button("Sales");
        Button reportsBtn = new Button("Reports");

        // Stretch buttons to fill sidebar width
        productsBtn.setMaxWidth(Double.MAX_VALUE);
        salesBtn.setMaxWidth(Double.MAX_VALUE);
        reportsBtn.setMaxWidth(Double.MAX_VALUE);

        sidebar.getChildren().addAll(
            new Label("Inventory App") {{
               .setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            }},
            new Label("") {{ setMinHeight(8); }},  // spacer
            productsBtn, salesBtn, reportsBtn
        );
        return sidebar;
    }

    private VBox buildWelcomeContent() {
        VBox content = new VBox(16);
        content.setPadding(new Insets(24));
        content.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Welcome");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitle = new Label("Select an option from the sidebar.");

        content.getChildren().addAll(title, subtitle);
        return content;
    }
}
