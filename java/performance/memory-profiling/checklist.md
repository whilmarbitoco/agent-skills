# Checklist

## Implementation
- [ ] Checked for VisualVM during development
- [ ] WeakListener used where listener outlives observable
- [ ] `-XX:+HeapDumpOnOutOfMemoryError` enabled
- [ ] Epsilon GC test run to confirm leaks

## Code Review
- [ ] No anonymous lambdas capturing Node references
- [ ] All bindings unbound on screen close
- [ ] No growing lists of Runnable/Listener objects
