---
name: navigation-patterns
description: >
  Extends agent's knowledge of JavaFX view navigation and routing patterns.
  Use when designing screen-to-screen flows, implementing breadcrumbs, or
  managing back/forward navigation history in a desktop POS app.
compatibility: Java 21+
metadata:
  domain: ui-kickstartfx
  level: intermediate
  stack: [java-21, javafx-21]
  version: "1.0.0"
---

# Navigation Patterns

Decouple view switching from business logic. A navigation service owns the
routing table and history stack; controllers request navigation by route key.

## Concepts

- **Route registry**: `Map<Route, Supplier<Node>>` or Supplier<Parent> for lazy loading
- **Navigation service**: singleton or injected port that `setRoot(Node)` on the scene
- **Breadcrumb stack**: `Deque<Route>` tracking navigation history for back-button support
- **Parameterized routes**: pass a `Map<String, Object>` context instead of coupling controllers

## Rules

1. Controllers never directly instantiate other controllers — go through the navigation service.
2. Use a sealed interface for `Route` so all known destinations are compile-time checked.
3. Lazy-load views — do not construct all FXML screens at startup.
4. Maintain a `Deque<Route>` history for back navigation; cap depth at 20 entries.
5. Pass context between screens via a shared `NavigationContext` map, never via static singletons.

## Anti-patterns

See [anti-patterns.md](./anti-patterns.md).

## Related

- workspace-layout — project structure makes navigation modules possible
- component-composition — building reusable view fragments
