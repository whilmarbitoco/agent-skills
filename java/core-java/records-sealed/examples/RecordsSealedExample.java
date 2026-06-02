import java.util.List;

/**
 * Records, sealed interfaces, exhaustive switch, and record patterns.
 * Java 21 — real compilable code.
 */
public class RecordsSealedExample {

    // --- Sealed interface: closed type hierarchy ---
    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle(double radius) implements Shape {}
    record Rectangle(double width, double height) implements Shape {}
    record Triangle(double base, double height) implements Shape {}

    // --- Exhaustive switch expression — no default needed ---
    static double area(Shape shape) {
        return switch (shape) {
            case Circle c    -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
            case Triangle t  -> 0.5 * t.base() * t.height();
        };
    }

    // --- Record pattern with destructuring (JEP 440) ---
    sealed interface Expr permits Const, Add, Mul {}
    record Const(double value) implements Expr {}
    record Add(Expr left, Expr right) implements Expr {}
    record Mul(Expr left, Expr right) implements Expr {}

    static double eval(Expr expr) {
        return switch (expr) {
            case Const(var v)       -> v;
            case Add(var l, var r)  -> eval(l) + eval(r);
            case Mul(var l, var r)  -> eval(l) * eval(r);
        };
    }

    // --- Record with defensive copy ---
    record Team(String name, List<String> members) {
        // Compact canonical constructor — defensive copy
        Team {
            members = List.copyOf(members);
        }
    }

    // --- Nested record + pattern matching ---
    sealed interface Json permits JsonStr, JsonNum, JsonArr {}
    record JsonStr(String value) implements Json {}
    record JsonNum(double value) implements Json {}
    record JsonArr(List<Json> elements) implements Json {}

    static String toIndented(Json json, int indent) {
        return switch (json) {
            case JsonStr(var s)                -> " ".repeat(indent) + "\"" + s + "\"";
            case JsonNum(var n)                -> " ".repeat(indent) + n;
            case JsonArr(var els) when els.isEmpty() -> " ".repeat(indent) + "[]";
            case JsonArr(var els) -> {
                var sb = new StringBuilder(" ".repeat(indent) + "[\n");
                for (int i = 0; i < els.size(); i++) {
                    sb.append(toIndented(els.get(i), indent + 2));
                    if (i < els.size() - 1) sb.append(",");
                    sb.append("\n");
                }
                sb.append(" ".repeat(indent) + "]");
                yield sb.toString();
            }
        };
    }

    public static void main(String[] args) {
        // Shape demo
        var shapes = List.of(new Circle(3), new Rectangle(4, 5), new Triangle(6, 7));
        shapes.forEach(s -> System.out.printf("%s area = %.2f%n", s, area(s)));

        // Expression evaluator
        Expr expr = new Add(new Mul(new Const(3), new Const(4)), new Const(5));
        System.out.println("eval(3*4+5) = " + eval(expr));

        // Team record with defensive copy
        var members = new java.util.ArrayList<>(List.of("Alice", "Bob"));
        var team = new Team("Core", members);
        members.add("Eve"); // does NOT affect team
        System.out.println("Team members: " + team.members());

        // JSON pretty-print
        var json = new JsonArr(List.of(
                new JsonStr("name"),
                new JsonNum(42),
                new JsonArr(List.of(new JsonStr("nested")))
        ));
        System.out.println(toIndented(json, 0));
    }
}
