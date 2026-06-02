# Responsive Desktop Layouts — Checklist

## Implementation

- [ ] Dimensions bound to scene/window size, not hard-coded pixels
- [ ] Three breakpoints defined: compact (<1024), medium (<1440), expanded (≥1440)
- [ ] Sidebar switches docked/overlay based on breakpoint
- [ ] FlowPane/TilePane used for card grids instead of manual column logic
- [ ] Stage has reasonable min-width/min-height; `setResizable(true)`

## Review

- [ ] No `Platform.runLater` used for layout adjustments
- [ ] App usable at 960px, 1280px, 1440px, 1920px widths
- [ ] No horizontal scrollbar at any supported width
- [ ] DHI scaling handled via rem-like units, not raw pixels
