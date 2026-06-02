package com.pos.ui.css;

/**
 * JavaFX CSS theming: AtlantAFX + custom overrides.
 * Dark/light mode, component styling, responsive design.
 */
public class CssTheming {

    // CSS file structure:
    // src/main/resources/com/pos/ui/
    //   ├── theme.css          (main theme)
    //   ├── components.css     (reusable components)
    //   └── screens.css        (screen-specific)

    // theme.css example:
    /*
    .root {
        -fx-font-family: "Inter", "Segoe UI", sans-serif;
        -fx-font-size: 14px;
        -fx-background: #f5f5f5;
    }

    .button-primary {
        -fx-background-color: #2563eb;
        -fx-text-fill: white;
        -fx-padding: 8 16;
        -fx-cursor: hand;
    }

    .button-primary:hover {
        -fx-background-color: #1d4ed8;
    }

    .card {
        -fx-background-color: white;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 2);
        -fx-padding: 16;
        -fx-background-radius: 8;
    }
    */

    // Loading CSS in JavaFX
    void applyTheme(Scene scene) {
        scene.getStylesheets().add(
            getClass().getResource("/com/pos/ui/theme.css").toExternalForm()
        );
    }

    // Dark mode toggle
    void toggleDarkMode(Scene scene, boolean dark) {
        scene.getStylesheets().clear();
        String css = dark ? "/com/pos/ui/dark.css" : "/com/pos/ui/light.css";
        scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
    }
}
