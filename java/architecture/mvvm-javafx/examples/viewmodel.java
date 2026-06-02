package com.simplepos.ui.mvvm;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * MVVM pattern for JavaFX.
 * ViewModel has NO reference to View — exposes ObservableList/Property only.
 * View (FXML) binds to ViewModel properties.
 */
public class InventoryViewModel {

    // Observable state for binding
    private final ObservableList<ProductView> products = FXCollections.observableArrayList();
    private final StringProperty searchQuery = new SimpleStringProperty("");
    private final BooleanProperty loading = new SimpleBooleanProperty(false);
    private final ObjectProperty<ProductView> selectedProduct = new SimpleObjectProperty<>();

    // Dependencies — constructor injection
    private final InventoryService service;

    public InventoryViewModel(InventoryService service) {
        this.service = service;
    }

    // Properties for FXML binding
    public ObservableList<ProductView> getProducts() { return products; }
    public StringProperty searchQueryProperty() { return searchQuery; }
    public BooleanProperty loadingProperty() { return loading; }
    public ObjectProperty<ProductView> selectedProductProperty() { return selectedProduct; }

    // Commands (called from FXML onAction)
    public void onSearch() {
        loading.set(true);
        String query = searchQuery.get();
        service.searchAsync(query)
            .thenAcceptAsync(results ->
                products.setAll(results.stream()
                    .map(ProductView::fromEntity)
                    .toList()),
                Platform::runLater
            )
            .thenRunAsync(() -> loading.set(false), Platform::runLater);
    }

    public void onDelete() {
        ProductView selected = selectedProduct.get();
        if (selected == null) return;
        service.deleteAsync(selected.id())
            .thenRunAsync(() -> products.remove(selected), Platform::runLater);
    }
}
