# Sidebar Shell — Checklist

## Implementation

- [ ] Shell loads once at startup; only center content changes on navigation
- [ ] Sidebar ToggleButtons bound to Route active state
- [ ] Collapsible sidebar toggles pseudo-class, not remove/add from scene graph
- [ ] Topbar (brand, search, user menu) included in shell FXML
- [ ] Content views receive navigation service via constructor injection

## Review

- [ ] No content controller directly manipulates shell nodes
- [ ] Collapse/expand uses animation (not instant width jump)
- [ ] Shell controller has no imports of individual content controllers
- [ ] Sidebar selection stays in sync with current route
