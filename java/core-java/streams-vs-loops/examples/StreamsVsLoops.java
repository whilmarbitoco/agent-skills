package com.pos.core.streams;

import java.util.*;
import java.util.stream.*;

/**
 * When to use streams vs loops. Streams for declarative data
 * pipelines, loops for performance-critical or side-effecting code.
 */
public class StreamsVsLoops {

    List<Product> products = List.of();

    // GOOD: stream for filtering + mapping + collecting
    List<String> expensiveNames = products.stream()
        .filter(p -> p.price().compareTo(new java.math.BigDecimal("1000")) > 0)
        .map(Product::name)
        .sorted()
        .toList(); // Java 16+ — returns unmodifiable list

    // GOOD: loop for early exit
    Product findFirstExpensive() {
        for (Product p : products) {
            if (p.price().compareTo(new java.math.BigDecimal("1000")) > 0) {
                return p; // early exit — stream can't do this cleanly
            }
        }
        return null;
    }

    // GOOD: loop for side effects
    void printAll() {
        for (Product p : products) {
            System.out.println(p.name()); // side effect — loop is clearer
        }
    }

    // GOOD: IntStream for numeric ranges
    int sum = IntStream.rangeClosed(1, 100).sum();

    // GOOD: grouping
    Map<String, List<Product>> byCategory = products.stream()
        .collect(Collectors.groupingBy(Product::category));

    // GOOD: teeing (Java 12+) — two collectors at once
    record Stats(long count, java.math.BigDecimal total) {}
    var stats = products.stream().collect(
        Collectors.teeing(
            Collectors.counting(),
            Collectors.reducing(java.math.BigDecimal.ZERO, Product::price, java.math.BigDecimal::add),
            Stats::new
        )
    );

    record Product(String name, java.math.BigDecimal price, String category) {}
}
