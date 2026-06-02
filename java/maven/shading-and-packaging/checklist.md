# Shading and Packaging — Checklist

## Implementation

- [ ] `maven-shade-plugin` at latest version
- [ ] `ServicesResourceTransformer` configured
- [ ] `ManifestResourceTransformer` with `Main-Class`
- [ ] Relocations for known-conflicting deps (protobuf, gRPC, Guava)
- [ ] `<minimizeJar>true</minimizeJar>` if size matters
- [ ] Provided-scope deps excluded from artifact set
- [ ] `shade` goal bound to `package` phase

## Review

- [ ] `java -jar target/app-shaded.jar` starts without error
- [ ] `jar tf target/app-shaded.jar | grep META-INF/services` shows
      merged SPI files
- [ ] No duplicate classes: `jar tf ... | sort | uniq -d`
- [ ] Shaded jar size is reasonable (< 100 MB for desktop apps)
