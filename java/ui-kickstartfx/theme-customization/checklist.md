# Theme Customization — Checklist

## Implementation

- [ ] All colors defined as looked-up colors in `brand.css`
- [ ] Structural CSS (spacing, radii, fonts) separated from palette CSS
- [ ] Theme stylesheets set on Scene, not individual nodes
- [ ] Spacing uses CSS variables (`--spacing-xs`, etc.)
- [ ] External theme directory checked before falling back to bundled resources

## Review

- [ ] No `node.setStyle("-fx-…")` calls in controllers
- [ ] Hex color values never appear outside palette CSS files
- [ ] Switching themes at runtime updates all nodes on screen
- [ ] No hard-coded pixel spacing values outside CSS variable definitions
