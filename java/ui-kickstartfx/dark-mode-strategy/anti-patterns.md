# Dark Mode Strategy — Anti-Patterns

## 1. Mixing palette and structure in one CSS file

```css
/* WRONG — colors and layout in the same file; can't swap palette alone */
.root {
    -fx-background-color: #121212;
    -fx-font-family: "Inter";
    -fx-font-size: 14px;
    -fx-padding: 16px;
}
/* Want light mode? Rewrite the entire file including fonts and spacing. */
```

```css
/* FIX: two-layer approach */
/* brand.css — fonts, spacing, radii (structural, never changes) */
.root {
    -fx-font-family: "Inter";
    -fx-padding: var(--spacing-md);
}
/* palette-dark.css — only colors */
.root {
    -fx-surface: #1E1E1E;
    -fx-on-surface: #E0E0E0;
    -fx-brand-primary: #BB86FC;
}
```

## 2. Setting palette on individual nodes

```java
// WRONG — each controller manually updates its nodes
label.setStyle(isDark ? "-fx-text-fill: #E0E0E0" : "-fx-text-fill: #212121");
```

```java
// FIX: swap the scene stylesheet list; CSS cascades automatically
scene.getStylesheets().setAll(
    "brand.css",
    isDark ? "palette-dark.css" : "palette-light.css"
);
```

## 3. Not persisting the user's choice

```java
// WRONG — defaults to light on every launch
boolean isDark = false;  // user sets dark mode, restarts → back to light
```

```java
// FIX: persist in Preferences
Preferences prefs = Preferences.userNodeForPackage(App.class);
// Save
prefs.put("ui.theme", isDark ? "dark" : "light");
// Load at startup
String theme = prefs.get("ui.theme", "light");
boolean isDark = "dark".equals(theme);
```

## 4. Loading theme after stage.show() — flash of wrong palette

```java
// WRONG — light theme flashes before dark is applied
stage.show();
applyTheme(true);  /* user's dark preference */
// User sees a white flash for ~200ms
```

```java
// FIX: apply theme before showing the stage
@Override
public void init() {
    Preferences prefs = Preferences.userNodeForPackage(App.class);
    this.isDark = "dark".equals(prefs.get("ui.theme", "light"));
}
@Override
public void start(Stage stage) {
    Scene scene = createScene();
    applyTheme(scene, isDark);  // applied before show()
    stage.setScene(scene);
    stage.show();
}
```

## 5. Duplicate CSS rules in light and dark files

```css
/* WRONG — palette-dark.css repeats all non-color rules from palette-light.css */
/* palette-dark.css */
.button { -fx-background-radius: 4px; /* duplicated */ -fx-background-color: #BB86FC; }
.card   { -fx-padding: 16px; /* duplicated */ -fx-background-color: #1E1E1E; }
```

```css
/* FIX: structural rules live in brand.css, palette files only override colors */
/* brand.css */
.button { -fx-background-radius: 4px; }
.card   { -fx-padding: 16px; }
/* palette-dark.css — only what changes */
.button { -fx-background-color: -fx-brand-primary; }
.card   { -fx-background-color: -fx-surface; }
```
