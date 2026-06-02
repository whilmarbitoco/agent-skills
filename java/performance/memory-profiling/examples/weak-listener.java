package com.simplepos.performance;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.control.Label;
import java.lang.ref.WeakReference;

/**
 * Memory leak pattern: anonymous listener captures Scene reference.
 * Fix: use WeakChangeListener or remove on dispose.
 */
public class MemoryLeakExample {

    // WRONG — lambda captures sceneNode reference forever
    static void bad(ObservableList<?> list, Label label) {
        list.addListener((javafx.collections.ListChangeListener.Change<?> c) -> {
            label.setText("Items: " + list.size()); // captures label + its entire Scene
        });
    }

    // CORRECT — weak reference, auto-cleared when label is GC'd
    static void good(ObservableList<?> list, Label label) {
        label.textProperty().bind(
            Bindings.size(list).asString("Items: %d")
        );
        // binding is stored in label, cleaned up when label is disposed
    }

    // CORRECT — manual cleanup on dispose
    static void alsoGood(ObservableList<?> list, Label label, Runnable onDispose) {
        var listener = (javafx.collections.ListChangeListener.Change<?> c) -> {
            if (c.next()) label.setText("Items: " + list.size());
        };
        list.addListener(listener);
        onDispose.run(); // caller removes listener in dispose()
    }
}
