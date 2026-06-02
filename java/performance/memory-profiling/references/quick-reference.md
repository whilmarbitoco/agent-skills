# Memory Profiling Quick Reference

| Tool | Purpose |
|------|---------|
| VisualVM | Live heap monitoring, CPU profiling |
| Heap dump | Post-mortem analysis of OOM |
| Epsilon GC | Isolate memory leak (no GC at all) |
| WeakListener | Auto-cleanup listener references |

| Pattern | Risk |
|---------|------|
| Anonymous lambda + Node | High — holds Scene |
| Platform.runLater in list | High — grows forever |
| Binding not unbound | Medium — keeps ViewModel |
| Static ObservableList | Medium — never GC'd |
