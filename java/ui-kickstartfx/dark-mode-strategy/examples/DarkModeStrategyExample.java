import java.util.*;

/**
 * Dark Mode Strategy — runtime theme switching with layered CSS,
 * preference persistence, and pre-show palette application.
 * Java 21 — real compilable code.
 */
public class DarkModeStrategyExample {

    // ── Simulates java.util.prefs.Preferences ──
    record Preferences(Map<String, String> store) {
        static Preferences userNodeForPackage(String pkg) {
            // In real code: Preferences.userNodeForPackage(App.class)
            return new Preferences(new HashMap<>());
        }
        String get(String key, String def) {
            return store.getOrDefault(key, def);
        }
        void put(String key, String value) {
            store.put(key, value);
        }
    }

    // ── Theme mode ──
    enum ThemeMode { LIGHT, DARK }

    // ── Palette definition ──
    record Palette(String name, Map<String, String> tokens) {
        String toCss() {
            var sb = new StringBuilder("/* palette-").append(name).append(".css */\n:root {\n");
            tokens.forEach((k, v) -> sb.append("    %-25s %s;%n".formatted(k, v)));
            sb.append("}\n");
            return sb.toString();
        }
    }

    // ── Theme manager with persistence ──
    static class ThemeManager {
        private final Preferences prefs;
        private final Palette lightPalette;
        private final Palette darkPalette;
        private ThemeMode activeMode;

        ThemeManager(Preferences prefs, Palette light, Palette dark) {
            this.prefs = prefs;
            this.lightPalette = light;
            this.darkPalette = dark;
            // Load persisted preference
            String saved = prefs.get("ui.theme", "light");
            this.activeMode = "dark".equals(saved) ? ThemeMode.DARK : ThemeMode.LIGHT;
        }

        void setTheme(ThemeMode mode) {
            this.activeMode = mode;
            prefs.put("ui.theme", mode.name().toLowerCase());
            System.out.printf("Theme set to: %s (persisted)%n", mode);
        }

        void toggle() {
            setTheme(activeMode == ThemeMode.LIGHT ? ThemeMode.DARK : ThemeMode.LIGHT);
        }

        ThemeMode activeMode() { return activeMode; }
        Palette activePalette() {
            return activeMode == ThemeMode.LIGHT ? lightPalette : darkPalette;
        }

        /** Simulates applying theme to Scene before show() */
        List<String> stylesheets() {
            return List.of("brand.css", "palette-" + activeMode.name().toLowerCase() + ".css");
        }
    }

    static Palette lightPalette() {
        return new Palette("light", Map.of(
            "-fx-surface",       "#FFFFFF",
            "-fx-on-surface",    "#212121",
            "-fx-brand-primary", "#2E7D32",
            "-fx-brand-danger",  "#C62828"
        ));
    }

    static Palette darkPalette() {
        return new Palette("dark", Map.of(
            "-fx-surface",       "#1E1E1E",
            "-fx-on-surface",    "#E0E0E0",
            "-fx-brand-primary", "#BB86FC",
            "-fx-brand-danger",  "#EF5350"
        ));
    }

    public static void main(String[] args) {
        var prefs = Preferences.userNodeForPackage("com.pos.app");
        var manager = new ThemeManager(prefs, lightPalette(), darkPalette());

        System.out.println("=== Initial State (from persisted prefs) ===");
        System.out.printf("  Active: %s | Stylesheets: %s%n",
            manager.activeMode(), manager.stylesheets());

        System.out.println("\n=== Toggle to Dark ===");
        manager.toggle();
        System.out.printf("  Active: %s | Stylesheets: %s%n",
            manager.activeMode(), manager.stylesheets());

        System.out.println("\n=== Dark Palette CSS ===");
        System.out.println(darkPalette().toCss());

        System.out.println("=== Simulate App Restart (load persisted) ===");
        var restarted = new ThemeManager(prefs, lightPalette(), darkPalette());
        System.out.printf("  Restored theme: %s%n", restarted.activeMode());
    }
}
