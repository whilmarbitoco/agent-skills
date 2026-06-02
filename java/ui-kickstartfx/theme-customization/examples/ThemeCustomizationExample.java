import java.util.*;

/**
 * Theme Customization — CSS token layering, palette management.
 * Simulates the layered CSS approach: brand.css + palette-{light,dark}.css.
 * Java 21 — real compilable code.
 */
public class ThemeCustomizationExample {

    // ── Represents a CSS looked-up color token ──
    record CssColor(String name, String value) {}

    // ── Theme palette — corresponds to a palette-{mode}.css file ──
    record ThemePalette(String name, Map<String, String> colors) {
        String toCss() {
            var sb = new StringBuilder("/* palette-").append(name).append(".css */\n:root {\n");
            colors.forEach((token, value) ->
                sb.append("    %-20s %s;%n".formatted(token, value))
            );
            sb.append("}\n");
            return sb.toString();
        }

        Optional<CssColor> color(String token) {
            return Optional.ofNullable(colors.get(token))
                .map(value -> new CssColor(token, value));
        }
    }

    // ── Theme layering engine — simulates Scene stylesheets ──
    static class ThemeManager {
        private final ThemePalette brand;
        private ThemePalette activePalette;

        ThemeManager(ThemePalette brand, ThemePalette initial) {
            this.brand = brand;
            this.activePalette = initial;
        }

        void setPalette(ThemePalette palette) {
            this.activePalette = palette;
            System.out.printf("Palette switched to: %s%n", palette.name());
        }

        ThemePalette active() { return activePalette; }
        ThemePalette brand() { return brand; }

        /** Simulates CSS cascade: palette overrides, brand provides non-color tokens */
        Map<String, String> effectiveColors() {
            var merged = new LinkedHashMap<String, String>();
            brand.colors().forEach(merged::putIfAbsent); // brand first
            activePalette.colors().forEach(merged::put);   // palette overrides
            return merged;
        }
    }

    static ThemePalette lightPalette() {
        return new ThemePalette("light", Map.of(
            "-fx-surface",         "#FFFFFF",
            "-fx-surface-variant", "#F5F5F5",
            "-fx-on-surface",      "#212121",
            "-fx-on-surface-variant", "#616161",
            "-fx-primary-container", "#E8F5E9",
            "-fx-brand-primary",   "#2E7D32",
            "-fx-brand-danger",    "#C62828"
        ));
    }

    static ThemePalette darkPalette() {
        return new ThemePalette("dark", Map.of(
            "-fx-surface",         "#1E1E1E",
            "-fx-surface-variant", "#2C2C2C",
            "-fx-on-surface",      "#E0E0E0",
            "-fx-on-surface-variant", "#9E9E9E",
            "-fx-primary-container", "#1B3A1B",
            "-fx-brand-primary",   "#BB86FC",
            "-fx-brand-danger",    "#EF5350"
        ));
    }

    static ThemePalette brandTokens() {
        return new ThemePalette("brand", Map.of(
            "--spacing-xs", "4px",
            "--spacing-sm", "8px",
            "--spacing-md", "16px",
            "--radius-sm",  "4px",
            "--radius-md",  "8px",
            "-fx-font-family", "\"Inter\""
        ));
    }

    public static void main(String[] args) {
        var manager = new ThemeManager(brandTokens(), lightPalette());

        System.out.println("=== Effective Colors (Light) ===");
        manager.effectiveColors().forEach((k, v) ->
            System.out.printf("  %-25s → %s%n", k, v)
        );

        System.out.println("\n=== Switch to Dark ===");
        manager.setPalette(darkPalette());
        manager.effectiveColors().forEach((k, v) ->
            System.out.printf("  %-25s → %s%n", k, v)
        );

        System.out.println("\n=== Palette CSS File ===");
        System.out.println(darkPalette().toCss());
    }
}
