package com.pos.patterns.solid;

/**
 * SOLID principles in Java — quick reference with POS examples.
 */
public class SolidPrinciples {

    // S: Single Responsibility — one reason to change
    // BAD: class handles DB + UI + validation
    // GOOD: separate into Repository, Controller, Validator

    // O: Open/Closed — extend without modifying
    interface Discount {
        java.math.BigDecimal apply(java.math.BigDecimal amount);
    }
    static class SeniorDiscount implements Discount {
        public java.math.BigDecimal apply(java.math.BigDecimal a) {
            return a.multiply(new java.math.BigDecimal("0.80"));
        }
    }
    // Add new discount types without changing existing code

    // L: Liskov Substitution — subtypes must be substitutable
    // BAD: Square extends Rectangle but breaks setWidth/setHeight contract
    // GOOD: use composition or sealed interfaces

    // I: Interface Segregation — small, focused interfaces
    interface Printable { void print(); }
    interface Scannable { void scan(); }
    // NOT: interface Machine { void print(); void scan(); void fax(); }

    // D: Dependency Inversion — depend on abstractions
    // BAD: class directly creates new Database()
    // GOOD: constructor accepts Database interface
}
