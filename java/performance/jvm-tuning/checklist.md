# Checklist

## Implementation
- [ ] G1GC enabled (default)
- [ ] -Xms set to 50% of -Xmx
- [ ] -Xshare:on for CDS
- [ ] Dynamic agent loading enabled

## Code Review
- [ ] Tested with VisualVM during load
- [ ] No GC pauses > 200ms observed
- [ ] Startup time < 3 seconds