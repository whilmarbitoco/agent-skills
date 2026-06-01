# Checklist

## Implementation
- [ ] Gradle build.gradle has java + javafx script plugins
- [ ] Dependencies: atlantafx-base, fx-values, fx-builders, ikonli packs
- [ ] setUserAgentStylesheet() called in Main.start()
- [ ] Ikonli FontIcon used for all icons (no image files)
- [ ] SLF4J JDK Platform Logging (no logback.xml)
- [ ] Css2Bin task configured in processResources

## Code Review
- [ ] No inline setStyle() — all styling via AtlantAFX classes
- [ ] No raw ObjectProperty — using Var/Val from fx-values
- [ ] No image-based icons — all via Ikonli
- [ ] Module-info.java uses automatic module names
