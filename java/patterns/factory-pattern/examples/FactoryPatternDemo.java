package com.pos.patterns.factory;

/**
 * Factory pattern: create objects without specifying exact class.
 * Useful for creating different receipt types, report formats, etc.
 */
public class FactoryPatternDemo {

    interface Receipt {
        String format();
    }

    static class ThermalReceipt implements Receipt {
        public String format() { return "[THERMAL] 80mm receipt format"; }
    }

    static class A4Receipt implements Receipt {
        public String format() { return "[A4] Full page receipt format"; }
    }

    static class EmailReceipt implements Receipt {
        public String format() { return "[EMAIL] HTML email receipt format"; }
    }

    // Factory: create receipt by type
    static class ReceiptFactory {
        static Receipt create(String type) {
            return switch (type.toUpperCase()) {
                case "THERMAL" -> new ThermalReceipt();
                case "A4" -> new A4Receipt();
                case "EMAIL" -> new EmailReceipt();
                default -> throw new IllegalArgumentException("Unknown: " + type);
            };
        }
    }

    // Usage
    void demo() {
        Receipt r = ReceiptFactory.create("THERMAL");
        System.out.println(r.format());
    }
}
