# Dark Mode — Checklist

## Implementation

- [ ] Palette CSS (colors only) separated from brand CSS (structure only)
- [ ] Runtime stylesheet swap on Scene, not individual nodes
- [ ] Theme persisted in `Preferences` under key `ui.theme`
- [ ] Persisted theme loaded in `init()`, applied before `stage.show()`
- [ ] Two palette files: `palette-light.css` and `palette-dark.css`

## Review

- [ ] No flash of wrong palette on startup
- [ ] Switching theme updates all visible nodes immediately
- [ ] Palette files contain only color tokens (no layout/spacing rules)
- [ ] User choice survives app restart
