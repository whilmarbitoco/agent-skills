import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;

/**
 * Java 21 SequencedCollection / SequencedSet / SequencedMap examples.
 * Real compilable code.
 */
public class SequencedCollectionsExample {

    // --- SequencedCollection: ArrayList ---
    static void arrayListDemo() {
        SequencedCollection<String> tasks = new ArrayList<>(List.of("build", "test", "deploy"));
        System.out.println("First: " + tasks.getFirst()); // build
        System.out.println("Last: " + tasks.getLast());   // deploy

        tasks.addFirst("plan");
        tasks.addLast("monitor");
        System.out.println("After adds: " + tasks);

        // reversed() view — no copy
        System.out.print("Reversed: ");
        tasks.reversed().forEach(t -> System.out.print(t + " "));
        System.out.println();
    }

    // --- SequencedCollection: LinkedList for fast head/tail ops ---
    static void linkedListDemo() {
        SequencedCollection<Integer> queue = new LinkedList<>();
        queue.addLast(1);
        queue.addLast(2);
        queue.addFirst(0);
        System.out.println("Queue: " + queue);
        System.out.println("Removed first: " + queue.removeFirst());
        System.out.println("Queue now: " + queue);
    }

    // --- SequencedSet: LinkedHashSet ---
    static void sequencedSetDemo() {
        SequencedSet<String> tags = new LinkedHashSet<>(List.of("java", "21", "sequenced"));
        tags.add("collections");
        tags.addFirst("java"); // already present — no-op for set
        System.out.println("Tags: " + tags);
        System.out.println("Tag order reversed: " + tags.reversed());
    }

    // --- SequencedMap: LinkedHashMap ---
    static void sequencedMapDemo() {
        SequencedMap<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Alice", 100);
        scores.put("Bob", 85);
        scores.put("Charlie", 92);

        scores.putFirst("Dave", 95); // Dave is now first
        System.out.println("First entry: " + scores.firstEntry());  // Dave=95
        System.out.println("Last entry: " + scores.lastEntry());    // Charlie=92

        var removed = scores.pollLastEntry();
        System.out.println("Removed last: " + removed); // Charlie=92

        System.out.println("All entries in order:");
        scores.sequencedEntrySet().forEach(e ->
                System.out.println("  " + e.getKey() + " = " + e.getValue()));

        System.out.println("Reversed map:");
        scores.reversed().forEach((k, v) ->
                System.out.println("  " + k + " = " + v));
    }

    // --- Polymorphic parameter: accept any SequencedCollection ---
    static <E> E peekMiddle(SequencedCollection<E> coll) {
        return coll.getFirst(); // or any sequenced operation
    }

    public static void main(String[] args) {
        System.out.println("=== ArrayList ===");
        arrayListDemo();

        System.out.println("\n=== LinkedList ===");
        linkedListDemo();

        System.out.println("\n=== SequencedSet ===");
        sequencedSetDemo();

        System.out.println("\n=== SequencedMap ===");
        sequencedMapDemo();

        System.out.println("\n=== peekMiddle ===");
        System.out.println("peekMiddle: " + peekMiddle(new ArrayList<>(List.of("a", "b", "c"))));
    }
}
