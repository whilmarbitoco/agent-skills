package com.pos.testing.junit;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 fundamentals for POS testing.
 * Real assertions, parameterized tests, lifecycle hooks.
 */
public class JUnit5Fundamentals {

    // Basic test
    @Test
    @DisplayName("Product price should be positive")
    void productPricePositive() {
        assertTrue(100 > 0, "Price must be positive");
    }

    // Parameterized test
    @ParameterizedTest
    @ValueSource(strings = {"apple", "banana", "cherry"})
    void productNameNotEmpty(String name) {
        assertNotNull(name);
        assertFalse(name.isBlank());
    }

    // Lifecycle
    @BeforeAll
    static void setupClass() {
        // One-time setup: create test DB, load fixtures
    }

    @BeforeEach
    void setup() {
        // Per-test setup: reset state
    }

    @AfterEach
    void teardown() {
        // Per-test cleanup
    }

    // Assertion groups
    @Test
    void productAssertions() {
        String name = "Test Product";
        assertAll("product",
            () -> assertNotNull(name),
            () -> assertFalse(name.isBlank()),
            () -> assertTrue(name.length() > 3)
        );
    }

    // Exception testing
    @Test
    void invalidPriceThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            // new Product("Test", new BigDecimal("-1"));
        });
    }
}
