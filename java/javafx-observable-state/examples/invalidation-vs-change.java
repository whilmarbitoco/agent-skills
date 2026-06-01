import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

/**
 * Demonstrates InvalidationListener vs ChangeListener.
 * InvalidationListener is preferred for derived values —
 * it fires on invalidate but defers the re-read until the value is actually needed.
 */
public class InvalidationVsChangeExample {

    private final StringProperty name = new SimpleStringProperty("");
    private final SimpleBooleanProperty valid = new SimpleBooleanProperty(false);

    private ChangeListener<String> changeListener;
    private javafx.beans.InvalidationListener invalidationListener;

    public void setupValidation() {
        // ✅ PREFERRED: InvalidationListener — cheaper, doesn't pass old/new values
        invalidationListener = obs -> {
            String value = name.get(); // lazy re-read only when accessed
            boolean isValid = value != null && value.length() >= 3 && value.length() <= 50;
            valid.set(isValid);
        };
        name.addListener(invalidationListener);

        // ❌ AVOID unless you specifically need old + new values:
        changeListener = (obs, oldVal, newVal) -> {
            boolean isValid = newVal != null && newVal.length() >= 3 && newVal.length() <= 50;
            valid.set(isValid);
        };
    }

    public StringProperty nameProperty() { return name; }
    public SimpleBooleanProperty validProperty() { return valid; }
}
