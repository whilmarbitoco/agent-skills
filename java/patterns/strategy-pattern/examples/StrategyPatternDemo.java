package com.pos.patterns.strategy;

/**
 * Strategy pattern: interchangeable algorithms at runtime.
 * Perfect for payment methods, discount rules, export formats.
 */
public class StrategyPatternDemo {

    interface PaymentStrategy {
        boolean pay(java.math.BigDecimal amount);
        String getName();
    }

    static class CashPayment implements PaymentStrategy {
        public boolean pay(java.math.BigDecimal amount) {
            System.out.println("Cash payment: " + amount);
            return true;
        }
        public String getName() { return "Cash"; }
    }

    static class CardPayment implements PaymentStrategy {
        public boolean pay(java.math.BigDecimal amount) {
            System.out.println("Card payment: " + amount);
            return true;
        }
        public String getName() { return "Card"; }
    }

    static class GcashPayment implements PaymentStrategy {
        public boolean pay(java.math.BigDecimal amount) {
            System.out.println("GCash payment: " + amount);
            return true;
        }
        public String getName() { return "GCash"; }
    }

    // Context: uses a strategy
    static class PaymentContext {
        private PaymentStrategy strategy;
        void setStrategy(PaymentStrategy s) { this.strategy = s; }
        boolean executePayment(java.math.BigDecimal amount) {
            return strategy.pay(amount);
        }
    }

    // Usage
    void demo() {
        PaymentContext ctx = new PaymentContext();
        ctx.setStrategy(new GcashPayment());
        ctx.executePayment(new java.math.BigDecimal("500"));
    }
}
