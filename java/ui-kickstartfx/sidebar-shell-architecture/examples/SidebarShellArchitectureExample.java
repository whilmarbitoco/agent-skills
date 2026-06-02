/**
 * Sidebar Shell Architecture — main application frame with persistent shell,
 * navigation sidebar, and swappable content area.
 * Java 21 — real compilable code.
 */
public class SidebarShellArchitectureExample {

    // ── Sealed interface for navigation routes ──
    sealed interface Route permits Dashboard, Products, Sales, Reports, Settings {
        record Dashboard() implements Route {}
        record Products() implements Route {}
        record Sales() implements Route {}
        record Reports(String type) implements Route {}
        record Settings() implements Route {}
    }

    // ── Shell state — what the shell needs to know ──
    record ShellState(
        Route activeRoute,
        boolean sidebarCollapsed,
        String pageTitle,
        String username
    ) {
        ShellState withRoute(Route route) {
            return new ShellState(route, sidebarCollapsed, resolveTitle(route), username);
        }
        ShellState withCollapsed(boolean collapsed) {
            return new ShellState(activeRoute, collapsed, pageTitle, username);
        }
        private static String resolveTitle(Route route) {
            return switch (route) {
                case Route.Dashboard d -> "Dashboard";
                case Route.Products p -> "Products";
                case Route.Sales s -> "Sales";
                case Route.Reports r -> "Reports: " + r.type();
                case Route.Settings s -> "Settings";
            };
        }
    }

    // ── Sidebar item ──
    record SidebarEntry(String icon, String label, Route route, boolean isAdminOnly) {
        String render(boolean collapsed) {
            return collapsed ? icon : icon + "  " + label;
        }
    }

    // ── Shell controller — owns sidebar and content area state ──
    static class ShellController {
        private ShellState state;
        private final java.util.List<SidebarEntry> entries;

        ShellController(ShellState initial, java.util.List<SidebarEntry> entries) {
            this.state = initial;
            this.entries = entries;
        }

        void navigateTo(Route route) {
            state = state.withRoute(route);
            System.out.printf("  [Shell] Navigate to: %s | Title: %s%n",
                route.getClass().getSimpleName(), state.pageTitle());
        }

        void toggleSidebar() {
            state = state.withCollapsed(!state.sidebarCollapsed());
            System.out.printf("  [Shell] Sidebar %s%n",
                state.sidebarCollapsed() ? "collapsed" : "expanded");
        }

        void renderSidebar() {
            System.out.println("  ┌─── Sidebar ──────────────────────");
            for (var entry : entries) {
                String marker = entry.route().equals(state.activeRoute()) ? " ●" : "  ";
                System.out.printf("  │%s %s%n", marker, entry.render(state.sidebarCollapsed()));
            }
            System.out.println("  └──────────────────────────────────");
        }

        void renderTopbar() {
            System.out.printf("  [Topbar] %s | User: %s%n",
                state.pageTitle(), state.username());
        }

        ShellState state() { return state; }
    }

    public static void main(String[] args) {
        var entries = java.util.List.of(
            new SidebarEntry("🏠", "Dashboard", new Route.Dashboard(), false),
            new SidebarEntry("📦", "Products", new Route.Products(), false),
            new SidebarEntry("💰", "Sales", new Route.Sales(), false),
            new SidebarEntry("📊", "Reports", new Route.Reports("daily"), false),
            new SidebarEntry("⚙", "Settings", new Route.Settings(), true)
        );

        var shell = new ShellController(
            new ShellState(new Route.Dashboard(), false, "Dashboard", "admin"),
            entries
        );

        System.out.println("=== Initial State ===");
        shell.renderTopbar();
        shell.renderSidebar();

        System.out.println("\n=== Navigate to Products ===");
        shell.navigateTo(new Route.Products());
        shell.renderTopbar();
        shell.renderSidebar();

        System.out.println("\n=== Collapse Sidebar ===");
        shell.toggleSidebar();
        shell.renderSidebar();

        System.out.println("\n=== Navigate to Reports ===");
        shell.navigateTo(new Route.Reports("monthly"));
        shell.renderTopbar();
    }
}
