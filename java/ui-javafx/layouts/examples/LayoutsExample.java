package com.example.layouts;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Demonstrates BorderPane + GridPane + HBox nesting for a
 * responsive product form layout.
 */
public class LayoutsExample {

    record Product(String sku, String name, BigDecimal price, int stock) {}

    /**
     * Builds a product management view using nested layouts.
     */
    static class ProductView {

        private static final Currency PHP = Currency.getInstance("PHP");
        private final ObservableList<Product> products = FXCollections.observableArrayList();

        BorderPane build() {
            BorderPane root = new BorderPane();
            root.setPadding(new Insets(0));

            // --- Header ---
            Label title = new Label("Product Manager");
            title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            HBox header = new HBox(title);
            header.setAlignment(Pos.CENTER_LEFT);
            header.setPadding(new Insets(12, 16, 12, 16));
            header.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 0 0 1 0;");
            root.setTop(header);

            // --- Center: Table ---
            TableView<Product> table = createProductTable();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            root.setCenter(table);

            // --- Right: Form ---
            GridPane form = createProductForm();
            root.setRight(form);

            // --- Footer: buttons ---
            Button addBtn = new Button("Add Product");
            Button refreshBtn = new Button("Refresh");
            HBox footer = new HBox(8, addBtn, refreshBtn);
            footer.setAlignment(Pos.CENTER_RIGHT);
            footer.setPadding(new Insets(12, 16, 12, 16));
            root.setBottom(footer);

            return root;
        }

        private TableView<Product> createProductTable() {
            TableView<Product> table = new TableView<>();
            table.setItems(products);

            TableColumn<Product, String> skuCol = new TableColumn<>("SKU");
            skuCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().sku()));

            TableColumn<Product, String> nameCol = new TableColumn<>("Name");
            nameCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().name()));

            TableColumn<Product, String> priceCol = new TableColumn<>("Price (" + PHP.getSymbol() + ")");
            priceCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                    d.getValue().price().setScale(2, java.math.RoundingMode.HALF_UP).toPlainString()));

            TableColumn<Product, String> stockCol = new TableColumn<>("Stock");
            stockCol.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                    String.valueOf(d.getValue().stock())));

            table.getColumns().addAll(skuCol, nameCol, priceCol, stockCol);
            return table;
        }

        private GridPane createProductForm() {
            GridPane form = new GridPane();
            form.setHgap(10);
            form.setVgap(8);
            form.setPadding(new Insets(16));
            form.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-width: 0 0 0 1;");

            ColumnConstraints labelCol = new ColumnConstraints();
            labelCol.setHalignment(HPos.RIGHT);
            ColumnConstraints fieldCol = new ColumnConstraints();
            fieldCol.setHgrow(Priority.ALWAYS);
            form.getColumnConstraints().addAll(labelCol, fieldCol);

            TextField skuField = new TextField();
            TextField nameField = new TextField();
            TextField priceField = new TextField();
            TextField stockField = new TextField();

            form.addRow(0, new Label("SKU"), skuField);
            form.addRow(1, new Label("Name"), nameField);
            form.addRow(2, new Label("Price"), priceField);
            form.addRow(3, new Label("Stock"), stockField);

            // Grow text fields with the form
            GridPane.setHgrow(skuField, Priority.ALWAYS);
            GridPane.setHgrow(nameField, Priority.ALWAYS);
            GridPane.setHgrow(priceField, Priority.ALWAYS);
            GridPane.setHgrow(stockField, Priority.ALWAYS);

            return form;
        }
    }
}
