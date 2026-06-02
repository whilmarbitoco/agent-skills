import java.nio.file.*;
import java.util.List;

/**
 * Workspace Layout — Maven multi-module JavaFX project structure.
 * Shows correct module organization, module-info.java, and pom layout.
 * Java 21, compilable reference project.
 */
public class WorkspaceLayoutExample {

    /** Simplified project layout mirror — shows correct directory structure */
    record Module(String name, List<String> packages, List<String> exports) {}

    static final Module ROOT = new Module("pos-app", List.of(), List.of());

    static final Module CORE = new Module("pos-core", List.of(
        "com.pos.core.product",
        "com.pos.core.sales",
        "com.pos.core.inventory"
    ), List.of(
        "com.pos.core.product",
        "com.pos.core.sales"
    ));

    static final Module UI = new Module("pos-ui", List.of(
        "com.pos.ui.shell",
        "com.pos.ui.product",
        "com.pos.ui.sales"
    ), List.of(
        "com.pos.ui.shell"
    ));

    static List<Module> workspaceModules() {
        return List.of(ROOT, CORE, UI);
    }

    /** Shows what a correct module-info.java looks like */
    static String moduleInfo(String moduleName, List<String> requires, List<String> exports) {
        var sb = new StringBuilder();
        sb.append("module ").append(moduleName).append(" {\n");
        for (String req : requires) {
            sb.append("    requires ").append(req).append(";\n");
        }
        sb.append("\n");
        for (String exp : exports) {
            sb.append("    exports ").append(exp).append(";\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    static boolean isValidModule(Module module) {
        return !module.name().isBlank()
            && !module.packages().isEmpty()
            && module.exports().stream().allMatch(e -> module.packages().contains(e));
    }

    public static void main(String[] args) {
        System.out.println("=== Workspace Module Layout ===");
        workspaceModules().forEach(m ->
            System.out.printf("Module %-15s exports: %s%n", m.name(), m.exports())
        );

        System.out.println("\n=== pos-core module-info ===");
        System.out.println(moduleInfo("com.pos.core",
            List.of("java.base", "java.sql"),
            CORE.exports()
        ));

        System.out.println("=== Validation ===");
        workspaceModules().stream()
            .filter(m -> !m.name().equals("pos-app")) // root has no source
            .forEach(m -> System.out.printf("  %-15s valid=%s%n",
                m.name(), isValidModule(m)));
    }
}
