# Skill: Virtual Thread Service

Background work with Java 21 virtual threads.

## Core Concepts
- `Thread.ofVirtual().factory()` or `Executors.newVirtualThreadPerTaskExecutor()`
- `StructuredTaskScope` — structured concurrency, fail-fast or shutdown-on-success
- `CompletableFuture` — compose async operations
- Pinning — `synchronized` pins VT to carrier thread; use `ReentrantLock` instead
- Carrier threads — ForkJoinPool (default parallelism = CPU cores)

## Rules
1. Never pool virtual threads — create per-task via `newVirtualThreadPerTaskExecutor()`
2. Use `ReentrantLock` not `synchronized` in any VT-backed code (avoids pinning)
3. Offload ALL I/O (DB, network, file) to virtual threads
4. Use `StructuredTaskScope` for concurrent subtasks with lifecycle control
5. Handle partial failure — collect all exceptions, don't lose results
6. `CompletableFuture` chains belong in service layer, not UI code

## Anti-patterns
- `synchronized` methods in VT-backed services (causes pinning)
- `ExecutorServiceFixedPool` wrapping virtual threads
- Swallowing exceptions in `CompletableFuture`
- Sharing mutable state without locks

## Relates to
- javafx-threading
- repository-pattern
- junit5-test
