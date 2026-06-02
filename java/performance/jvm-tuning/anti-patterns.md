# Jvm Tuning Anti-Patterns

Setting -Xmx too high — wastes memory on desktop. Fix: -Xmx512m is plenty for most POS apps.

Using ParallelGC — G1GC is default and better for desktop. Fix: -XX:+UseG1GC (default in Java 21).

No -Xms — heap resizing causes pauses. Fix: set -Xms to 50% of -Xmx.

Ignoring CDS — slower startup every launch. Fix: -Xshare:on or AppCDS archive.

Disabling JIT for debugging in production — 10x slower. Fix: never disable JIT in prod.