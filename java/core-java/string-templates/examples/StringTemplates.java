package com.pos.core.strings;

/**
 * Java 21 string templates (preview) — safer string interpolation
 * than concatenation or String.format.
 */
public class StringTemplates {

    // OLD: concatenation — error-prone
    String oldWay(Product p) {
        return "Product: " + p.name() + " costs " + p.price() + " (qty: " + p.qty() + ")";
    }

    // OLD: String.format — no compile-time checking
    String formatWay(Product p) {
        return String.format("Product: %s costs %.2f (qty: %d)", p.name(), p.price(), p.qty());
    }

    // NEW: String templates (Java 21 preview)
    // String s = STR."Product: \{p.name()} costs \{p.price()} (qty: \{p.qty()})";

    // NEW: JSON template (Java 21 preview)
    // String json = JSON."""
    //     {
    //         "name": "\{p.name()}",
    //         "price": \{p.price()},
    //         "qty": \{p.qty()}
    //     }
    //     """;

    // CURRENT BEST PRACTICE (pre-preview): text blocks + formatted
    String textBlock(Product p) {
        return """
            Product: %s
            Price: %.2f
            Qty: %d
            """.formatted(p.name(), p.price(), p.qty());
    }

    record Product(String name, double price, int qty) {}
}
