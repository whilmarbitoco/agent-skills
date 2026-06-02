package com.pos.ui.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

/**
 * FXML patterns: separation of view (FXML) and logic (Controller).
 * Use fx:controller, @FXML injection, and controller factory for DI.
 */
public class FxmlPatterns {

    // FXML file (product-view.fxml):
    /*
    <?xml version="1.0" encoding="UTF-8"?>
    <?import javafx.scene.layout.*?>
    <?import javafx.scene.control.*?>

    <VBox xmlns:fx="http://javafx.com/fxml"
          fx:controller="com.pos.ui.ProductController"
          spacing="10" styleClass="card">
        <Label fx:id="titleLabel" styleClass="title"/>
        <TableView fx:id="productTable">
            <columns>
                <TableColumn fx:id="nameCol" text="Name"/>
                <TableColumn fx:id="priceCol" text="Price"/>
            </columns>
        </TableView>
        <HBox spacing="8">
            <Button fx:id="addButton" text="Add" onAction="#onAdd"/>
            <Button fx:id="deleteButton" text="Delete" onAction="#onDelete"/>
        </HBox>
    </VBox>
    */

    // Controller with DI
    /*
    public class ProductController {
        @FXML private TableView<Product> productTable;
        @FXML private TableColumn<Product, String> nameCol;

        private final ProductService service;

        // Constructor injection
        public ProductController(ProductService service) {
            this.service = service;
        }

        @FXML
        void initialize() {
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productTable.setItems(service.getProducts());
        }

        @FXML
        void onAdd() { service.addNew(); }
    }
    */

    // Loading FXML with DI
    Parent loadView(String fxmlPath, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setController(controller);
        return loader.load();
    }
}
