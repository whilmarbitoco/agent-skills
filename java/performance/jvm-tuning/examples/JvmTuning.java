package com.pos.performance;

/**
 * JVM tuning for desktop POS apps.
 * Focus: GC selection, heap sizing, JIT warmup.
 */
public class JvmTuning {

    // Recommended JVM flags for POS desktop app:
    /*
    java \
      -Xms256m -Xmx512m \
      -XX:+UseZGC \
      -XX:+ZGenerational \
      -XX:SoftMaxHeapSize=384m \
      -XX:+UseStringDeduplication \
      -XX:+OptimizeStringConcat \
      -Djava.awt.headless=false \
      -jar pos-app.jar
    */

    // GC Selection Guide:
    // ZGC (Java 21): <10ms pauses, good for responsive UI
    // G1GC: balanced throughput and latency
    // Shenandoah: low pause, alternative to ZGC

    // Heap sizing:
    // -Xms = -Xmx (fixed heap, avoids resize pauses)
    // Desktop: 256m-512m typical
    // Start small, measure, increase if needed

    // JIT warmup:
    // -XX:CompileThreshold=10000 (default)
    // -XX:+TieredCompilation (default on)
    // First few seconds may be slower — show splash screen

    // Monitoring:
    // -XX:+PrintFlagsFinal (show all flags)
    // -Xlog:gc* (GC logging)
    // -XX:+HeapDumpOnOutOfMemoryError
}
