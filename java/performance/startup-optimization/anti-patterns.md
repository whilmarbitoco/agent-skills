# Startup Optimization — Anti-Patterns

## Problem 1: Eager initialization blocks the FX Application Thread

```java
// WRONG — blocks startup
public class App extends Application {
    private final Database db = new Database(); // blocks EDT
    private final HttpClient api = HttpClient.newHttpClient();
    private final CacheManager cache = new CacheManager();

    @Override
    public void start(Stage stage) {
        // UI only shows after all three finish
    }
}
```

```java
// FIX — lazy init with on-demand loading
public class App extends Application {
    private Database db;

    @Override
    public void start(Stage stage) {
        Scene scene = createSplashScene(); // instant
        stage.setScene(scene);
        stage.show();
        // Load heavy resources after UI is visible
        CompletableFuture.runAsync(() -> {
            db = new Database();
            Platform.runLater(() -> showMainStage(stage));
        });
    }
}
```

## Problem 2: Module scanning at startup

```java
// WRONG — ServiceLoader scans all modules
ServiceLoader.load(Plugin.class).forEach(p -> registry.register(p));
```

```java
// FIX — declare modules explicitly in module-info.java
provides com.pos.Plugin with com.pos.impl.InventoryPlugin;
// No runtime scanning needed
```

## Problem 3: Not using CDS

```java
// WRONG — cold start every time
java -jar pos-app.jar
```

```java
// FIX — generate and use CDS archive
// Step 1: Record class loading
java -XX:SharedArchiveFile=pos.jsa -Xshare:dump -jar pos-app.jar
// Step 2: Use archive
java -XX:SharedArchiveFile=pos.jsa -jar pos-app.jar
// Typical improvement: 30-40% faster class loading
```
