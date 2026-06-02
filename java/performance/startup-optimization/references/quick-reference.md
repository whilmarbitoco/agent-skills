# Startup Optimization — Quick Reference

## Flags for jpackage

```bash
jpackage \
  --java-options "-XX:SharedArchiveFile=\$APPDIR/app.jsa" \
  --java-options "-Xmx512m" \
  --java-options "--limit-modules=java.base,java.desktop,javafx.controls,javafx.fxml,java.sql" \
  --input target/ \
  --main-jar pos-app.jar \
  --main-class com.pos.App \
  --name "POS System" \
  --dest output/
```

## Startup Measurement

```java
long start = System.nanoTime();
// ... startup code ...
long elapsed = System.nanoTime() - start;
LoggerFactory.getLogger(getClass()).info("Startup took {} ms", elapsed / 1_000_000);
```

## Flag Reference

| Flag | Purpose |
|------|---------|
| `-Xshare:dump` | Create CDS archive |
| `-Xshare:on` | Use CDS archive |
| `--limit-modules` | Reduce module graph |
| `-Xms/-Xmx` | Heap bounds |
| `-XX:+UseZGC` | Low-pause GC for desktop |

## Lazy Module Loading Pattern

```java
public class ModuleLoader {
    private static final Map<String, Object> loaded = new ConcurrentHashMap<>();

    public static <T> T load(String moduleKey, Supplier<T> factory) {
        return (T) loaded.computeIfAbsent(moduleKey, k -> factory.get());
    }
}
```
