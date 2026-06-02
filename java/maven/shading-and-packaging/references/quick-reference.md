# Shading and Packaging — Quick Reference

| Transformer | Purpose |
|---|---|
| `ManifestResourceTransformer` | Sets `Main-Class` manifest entry |
| `ServicesResourceTransformer` | Merges `META-INF/services` files |
| `AppendingTransformer` | Appends text files (e.g., `spring.handlers`) |
| `DontIncludeResourceTransformer` | Excludes specific resources |

| Relocation | When |
|---|---|
| Guava, protobuf, gRPC | Shipped library used by consumers |
| Netty, Jackson | Version conflicts across modules |
| SLF4J, Logback | Logging framework must not clash |

| Goal | Command |
|---|---|
| Build shaded jar | `mvn package shade:shade` |
| Inspect contents | `jar tf target/app-shaded.jar` |
| Run | `java -jar target/app-shaded.jar` |
| Check manifest | `unzip -p target/app-shaded.jar META-INF/MANIFEST.MF` |
