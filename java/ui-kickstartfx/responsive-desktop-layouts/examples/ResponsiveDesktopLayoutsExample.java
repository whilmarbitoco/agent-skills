/**
 * Responsive Desktop Layouts — breakpoint detection, binding-based sizing,
 * and adaptive sidebar behavior.
 * Java 21 — real compilable code.
 */
public class ResponsiveDesktopLayoutsExample {

    // ── Breakpoint enum ──
    enum Breakpoint {
        COMPACT(0, 1024),    // laptop, small window
        MEDIUM(1024, 1440),  // standard desktop
        EXPANDED(1440, Integer.MAX_VALUE); // dual monitor, ultrawide

        final int minWidth;
        final int maxWidth;

        Breakpoint(int min, int max) { this.minWidth = min; this.maxWidth = max; }

        static Breakpoint fromWidth(double width) {
            for (var bp : values()) {
                if (width >= bp.minWidth && width < bp.maxWidth) return bp;
            }
            return COMPACT;
        }
    }

    // ── Layout configuration derived from breakpoint ──
    record LayoutConfig(
        Breakpoint breakpoint,
        int gridColumns,
        boolean sidebarDocked,
        double sidebarWidth,
        double contentPadding
    ) {
        static LayoutConfig forWidth(double screenWidth) {
            var bp = Breakpoint.fromWidth(screenWidth);
            return switch (bp) {
                case COMPACT -> new LayoutConfig(bp, 2, false, 0, 8);
                case MEDIUM  -> new LayoutConfig(bp, 3, true, 200, 16);
                case EXPANDED -> new LayoutConfig(bp, 4, true, 260, 24);
            };
        }
    }

    // ── Simulated responsive shell ──
    static class ResponsiveShell {
        private double screenWidth;
        private LayoutConfig config;

        ResponsiveShell(double initialWidth) {
            resize(initialWidth);
        }

        void resize(double newWidth) {
            this.screenWidth = newWidth;
            this.config = LayoutConfig.forWidth(newWidth);
        }

        void printLayout() {
            System.out.printf(
                "  Width: %6.0fpx | BP: %-8s | Columns: %d | Sidebar: %-6s | Padding: %.0fpx%n",
                screenWidth,
                config.breakpoint(),
                config.gridColumns(),
                config.sidebarDocked() ? "docked" : "overlay",
                config.contentPadding()
            );
        }

        LayoutConfig config() { return config; }
    }

    public static void main(String[] args) {
        var shell = new ResponsiveShell(1920);

        System.out.println("=== Responsive Layout at Different Screen Widths ===");
        double[] widths = {960, 1024, 1280, 1440, 1920, 2560};
        for (double w : widths) {
            shell.resize(w);
            shell.printLayout();
        }

        System.out.println("\n=== Breakpoint Detection ===");
        for (double w : widths) {
            System.out.printf("  %6.0fpx → %s%n", w, Breakpoint.fromWidth(w));
        }
    }
}
