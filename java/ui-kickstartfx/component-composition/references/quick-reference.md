# Component Composition — Quick Reference

| Concept | Convention |
|---|---|
| Control trio | `MyCard.java` + `MyCard.fxml` + `MyCard.css` |
| FXML include | `<fx:include source="...">` in parent FXML |
| State | `PseudoClass.getPseudoClass("error")` for transient states |
| Properties | `ObjectProperty<T>` for external binding |
| Internal access | Never expose child nodes; expose properties instead |
