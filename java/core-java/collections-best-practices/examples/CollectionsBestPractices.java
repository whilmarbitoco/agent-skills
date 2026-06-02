package com.pos.core.collections;

import java.util.*;

/**
 * Java 21 collections best practices: choose the right collection,
 * use factory methods, prefer immutable views.
 */
public class CollectionsBestPractices {

    // WRONG: raw ArrayList, mutable
    // List<String> items = new ArrayList<>();

    // RIGHT: immutable list with factory method
    List<String> items = List.of("apple", "banana", "cherry");

    // RIGHT: mutable when needed, but typed
    List<String> mutable = new ArrayList<>(items);

    // RIGHT: Map factory methods
    Map<String, Integer> prices = Map.of(
        "apple", 50,
        "banana", 30,
        "cherry", 80
    );

    // RIGHT: Map.ofEntries for >10 entries
    Map<String, Integer> more = Map.ofEntries(
        Map.entry("apple", 50),
        Map.entry("banana", 30)
    );

    // RIGHT: SequencedCollection (Java 21)
    SequencedCollection<String> seq = new ArrayList<>(items);
    String first = seq.getFirst();   // Java 21
    String last = seq.getLast();     // Java 21
    seq.addFirst("avocado");         // Java 21
    seq.addLast("zucchini");         // Java 21

    // RIGHT: SequencedMap (Java 21)
    SequencedMap<String, Integer> sortedPrices = new TreeMap<>();
    sortedPrices.put("apple", 50);
    sortedPrices.put("banana", 30);
    Map.Entry<String, Integer> lowest = sortedPrices.firstEntry();
    Map.Entry<String, Integer> highest = sortedPrices.lastEntry();
}
