---
name: kickstartfx-migration
description: >
  Extends agent's knowledge of KickStartFX framework for JavaFX applications.
  Covers Gradle setup, AtlantAFX theming, fx-values reactive patterns, Ikonli icons,
  window-based navigation, and Css2Bin binary stylesheets. Use when migrating a
  vanilla JavaFX+Maven project to KickStartFX+Gradle or when building a new app
  with KickStartFX.
compatibility: Java 21+, JavaFX 21+, Gradle 8+
metadata:
  domain: ui-framework
  level: intermediate
  stack: [java-21, javafx, gradle, atlantafx, fx-values, ikonli]
  related: [javafx-threading, javafx-layouts, mvvm-javafx, record-value-objects]
  version: "1.0.0"
---

# KickStartFX Migration

KickStartFX (xpipe-io/kickstartfx) is a Gradle-based JavaFX framework with
AtlantAFX theming, reactive value bindings, and binary CSS compilation.
This skill covers the migration path from vanilla JavaFX+Maven.

## Core Concepts

- **AtlantAFX** — theme framework with built-in dark mode (Primer, Nord, Cupertino themes)
- **fx-values / fx-builders** — type-safe reactive bindings (cleaner than raw Property API)
- **Ikonli** — material icon packs for JavaFX (Material2, MaterialDesign2, Feather)
- **Css2Bin** — binary CSS compilation at build time (faster loading)
- **Window-based navigation** — no traditional sidebar shell, window-per-feature
- **Modular** — module-info.java with automatic module discovery
- **SLF4J 2.x + JDK Platform Logging** — zero-config logging

## Rules
1. Use `fx-values` `Val`/`Var` bindings instead of raw `ObjectProperty`
2. Use Ikonli `FontIcon` for all icons — not FontAwesome or image files
3. Theme via AtlantAFX `UserAgent` — set in Main, toggle dark mode at runtime
4. Compile CSS to binary via Gradle `processResources` Css2Bin task
5. One window per feature — navigation via `Window.show()` not sidebar switching
6. Platform logging via SLF4J — no logback.xml needed
7. Dependency versions managed via `gradle/libs.versions.toml`

## Anti-patterns
- Using Maven (KickStartFX is Gradle-only)
- Inline `setStyle()` calls — use AtlantAFX CSS classes
- Raw `ObjectProperty` binding — use `Val`/`Var` from fx-values
- Image-based icons — use Ikonli FontIcon
- logback.xml + Logback — KickStartFX uses JDK Platform Logging

## Relates to
- javafx-threading
- javafx-layouts
- mvvm-javafx
- record-value-objects
