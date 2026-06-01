import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Theme;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

/**
 * KickStartFX application entry point.
 * - AtlantAFX theme set via UserAgent
 * - Ikonli icons via FontIcon
 * - No logback.xml needed (JDK Platform Logging)
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set AtlantAFX theme (Primer Light/Dark, Nord, Cupertino)
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        // Ikonli icon — no image files needed
        FontIcon icon = new FontIcon(Material2AL.HOME);
        icon.setIconSize(24);

        Label title = new Label("Simple POS");
        title.getStyleClass().add(Styles.TITLE_1);

        VBox root = new VBox(16, icon, title);
        root.getStyleClass().add(Styles.PADDING_16);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
