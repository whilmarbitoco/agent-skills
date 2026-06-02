import java.time.LocalDate;
import java.util.List;

/**
 * Switch pattern matching examples: type patterns, guarded patterns, null handling.
 * Java 21 — real compilable code.
 */
public class SwitchPatternsExample {

    // --- Type patterns with null handling ---
    static int size(Object value) {
        return switch (value) {
            case null                 -> -1;
            case String s             -> s.length();
            case Integer i            -> (i == 0) ? 0 : (int) Math.log10(Math.abs(i)) + 1;
            case List<?> list         -> list.size();
            case Object[] arr         -> arr.length;
            default                   -> 0;
        };
    }

    // --- Guarded patterns (when clauses) ---
    static String classifyNumber(Number n) {
        return switch (n) {
            case Integer i when i < 0           -> "negative int";
            case Integer i when i == 0          -> "zero";
            case Integer i when i > 0 && i < 10 -> "small positive int";
            case Integer i                      -> "large positive int";
            case Double d when d.isNaN()        -> "NaN";
            case Double d when d.isInfinite()   -> "infinite";
            case Double d                       -> "double: " + d;
            case Long l when l > 1_000_000L     -> "big long";
            case Long l                         -> "long: " + l;
            case null                           -> "null number";
            default                             -> "unknown number type";
        };
    }

    // --- Pattern matching with records (sealed not required but common) ---
    sealed interface Status permits Pending, Active, Suspended, Closed {}
    record Pending(String queuedAt) implements Status {}
    record Active(String since) implements Status {}
    record Suspended(String reason, LocalDate until) implements Status {}
    record Closed(String closedAt) implements Status {}

    static String humanReadable(Status status) {
        return switch (status) {
            case Pending(var at)                  -> "Waiting since " + at;
            case Active(var since)                -> "Active since " + since;
            case Suspended(var reason, var until) -> "Suspended: " + reason + " until " + until;
            case Closed(var at)                   -> "Closed at " + at;
        };
    }

    // --- Pattern matching replaces visitor-like dispatch ---
    sealed interface Payment permits Cash, Card, Transfer {}
    record Cash(double amount) implements Payment {}
    record Card(double amount, String last4) implements Payment {}
    record Transfer(double amount, String reference) implements Payment {}

    static String receiptLine(Payment payment) {
        return switch (payment) {
            case Cash(var amt)                        -> String.format("Cash: $%.2f", amt);
            case Card(var amt, var last4)             -> String.format("Card ****%s: $%.2f", last4, amt);
            case Transfer(var amt, var ref)           -> String.format("Transfer %s: $%.2f", ref, amt);
        };
    }

    public static void main(String[] args) {
        // size() demo
        System.out.println("size(\"hello\") = " + size("hello"));
        System.out.println("size(12345) = " + size(12345));
        System.out.println("size(List.of(1,2,3)) = " + size(List.of(1, 2, 3)));
        System.out.println("size(null) = " + size(null));
        System.out.println("size(3.14) = " + size(3.14)); // default case

        // classifyNumber() demo
        System.out.println(classifyNumber(42));
        System.out.println(classifyNumber(-7));
        System.out.println(classifyNumber(0));
        System.out.println(classifyNumber(Double.NaN));
        System.out.println(classifyNumber(3.14));
        System.out.println(classifyNumber(2_000_000L));
        System.out.println(classifyNumber(null));

        // Status demo
        List<Status> statuses = List.of(
                new Pending("2024-01-15"),
                new Active("2024-02-01"),
                new Suspended("maintenance", LocalDate.of(2024, 6, 1)),
                new Closed("2024-05-01")
        );
        statuses.forEach(s -> System.out.println(humanReadable(s)));

        // Payment demo
        var payments = List.of(
                new Cash(25.50),
                new Card(100.00, "4242"),
                new Transfer(500.00, "RF-001")
        );
        payments.forEach(p -> System.out.println(receiptLine(p)));
    }
}
