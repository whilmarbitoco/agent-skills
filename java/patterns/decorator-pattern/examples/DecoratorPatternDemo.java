package com.pos.patterns.decorator;

/**
 * Decorator pattern: add behavior to objects dynamically.
 * Common in POS for pricing (base price + tax + discount).
 */
public class DecoratorPatternDemo {

    interface PricingStrategy {
        java.math.BigDecimal calculate(java.math.BigDecimal base);
    }

    // Base: no modification
    static class BasePricing implements PricingStrategy {
        public java.math.BigDecimal calculate(java.math.BigDecimal base) { return base; }
    }

    // Decorator: adds tax
    static class TaxDecorator implements PricingStrategy {
        private final PricingStrategy wrapped;
        private final java.math.BigDecimal rate;
        TaxDecorator(PricingStrategy wrapped, java.math.BigDecimal rate) {
            this.wrapped = wrapped; this.rate = rate;
        }
        public java.math.BigDecimal calculate(java.math.BigDecimal base) {
            return wrapped.calculate(base).multiply(java.math.BigDecimal.ONE.add(rate));
        }
    }

    // Decorator: adds discount
    static class DiscountDecorator implements PricingStrategy {
        private final PricingStrategy wrapped;
        private final java.math.BigDecimal percent;
        DiscountDecorator(PricingStrategy wrapped, java.math.BigDecimal percent) {
            this.wrapped = wrapped; this.percent = percent;
        }
        public java.math.BigDecimal calculate(java.math.BigDecimal base) {
            return wrapped.calculate(base).multiply(java.math.BigDecimal.ONE.subtract(percent));
        }
    }

    // Usage: compose decorators
    void demo() {
        PricingStrategy pricing = new DiscountDecorator(
            new TaxDecorator(new BasePricing(), new java.math.BigDecimal("0.12")),
            new java.math.BigDecimal("0.10")
        );
        // base 1000 → tax 12% = 1120 → discount 10% = 1008
        java.math.BigDecimal final_ = pricing.calculate(new java.math.BigDecimal("1000"));
    }
}
