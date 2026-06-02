# Theme Customization — Anti-Patterns

## 1. Hardcoding colors in component CSS

```css
/* WRONG — hex values duplicated across 12 CSS files */
.product-card { -fx-background-color: #2E7D32; }
.price-label  { -fx-text-fill: #2E7D32; }
.buy-button   { -fx-background-color: #2E7D32; }
/* Change brand color? Edit 12 files. */
```

```css
/* FIX: defined once in brand.css */
.root {
    -fx-brand-primary: #2E7D32;
    -fx-brand-danger:  #C62828;
    -fx-surface:       #FFFFFF;
}
/* Then referenced everywhere */
.product-card { -fx-background-color: -fx-brand-primary; }
.price-label  { -fx-text-fill: -fx-brand-primary; }
```

## 2. Inline setStyle() in controllers

```java
// WRONG — overrides all CSS, defeats theming
label.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
// Now this label ignores every CSS theme file.
```

```java
// FIX: use pseudo-class for state, let CSS handle colors
label.pseudoClassStateChanged(
    PseudoClass.getPseudoClass("error"), hasError
);
/* CSS: .label:error { -fx-text-fill: -fx-brand-danger; } */
```

## 3. Scattered pixel spacing values

```css
/* WRONG — magic numbers everywhere */
.card      { -fx-padding: 12px; }
.sidebar   { -fx-spacing: 6px; }
.topbar    { -fx-padding: 16px 10px 8px 24px; }
```

```css
/* FIX: CSS variables for spacing scale */
.root {
    --spacing-xs: 4px;
    --spacing-sm: 8px;
    --spacing-md: 16px;
    --spacing-lg: 24px;
}
.card    { -fx-padding: var(--spacing-md); }
.sidebar { -fx-spacing: var(--spacing-xs); }
```

## 4. Theme stylesheets set on individual nodes

```java
// WRONG — setting stylesheet on a VBox
sidebar.getStylesheets().add("dark.css");
// Only this node switches; rest of app stays light.
```

```java
// FIX: always set stylesheets on the Scene
scene.getStylesheets().setAll(
    "brand.css",
    isDark ? "palette-dark.css" : "palette-light.css"
);
```

## 5. No external theme override capability

```java
// WRONG — theme files locked inside resources; merchant can't customize
getClass().getResource("/theme-dark.css").toExternalForm();
```

```java
// FIX: load from external config dir first, fall back to bundled
Path external = Path.of(System.getProperty("user.home"), ".pos", "theme-dark.css");
if (Files.exists(external)) {
    scene.getStylesheets().add(external.toUri().toString());
} else {
    scene.getStylesheets().add(getClass().getResource("/theme-dark.css").toExternalForm());
}
```
