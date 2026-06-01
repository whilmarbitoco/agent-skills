# KickStartFX Anti-Patterns

## Using Maven instead of KickStartFX's Gradle setup

KickStartFX is tightly coupled with Gradle script plugins.
Trying to replicate it in Maven leads to broken CSS compilation and missing modules.

**Fix:** Use Gradle. Copy KickStartFX's gradle_scripts directory as a template.

## setStyle() instead of AtlantAFX CSS classes

```java
// WRONG — inline styles bypass theme system
label.setStyle("-fx-text-fill: red; -fx-font-size: 14;");
```

**Fix:** Use AtlantAFX style classes: `label.getStyleClass().add(Styles.DANGER)`

## Image-based icons instead of Ikonli

```java
// WRONG — loading PNG icons
new ImageView(new Image("icons/home.png"));
```

**Fix:** `new FontIcon(Material2AL.HOME)` — vector, theme-aware, no files needed.

## logback.xml for logging configuration

```java
// WRONG — KickStartFX uses JDK Platform Logging
org.slf4j:logback-classic
```

**Fix:** Remove logback dependency. Use `slf4j-jdk-platform-logging` instead.

## Raw ObjectProperty with manual listeners

```java
// WRONG — verbose, error-prone
ObjectProperty<String> name = new SimpleObjectProperty<>();
name.addListener((obs, old, new) -> updateUI());
```

**Fix:** Use `Var<String>` from fx-values — automatic, type-safe bindings.
