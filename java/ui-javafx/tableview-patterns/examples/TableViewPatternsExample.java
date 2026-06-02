package com.example.tableview;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Demonstrates TableView patterns: sorting, filtering, custom cells,
 * and inline editing with BigDecimal money fields.
 */
public class TableViewPatternsExample {

    private static final Logger log = LoggerFactory.getLogger(TableViewPatternsExample.class);
    private static final Currency PHP = Currency.getInstance("PHP");

    // ---- Value record for order line ----
    record OrderLine(String productName, int quantity, BigDecimal unitPrice) {
        BigDecimal lineTotal() {
            return unitPrice.multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
        }
    }

    // ---- ViewModel ----
    static class OrderTableViewModel {
        private final ObservableList<OrderLine> source = FXCollections.observableArrayList();

        // Wrapped lists for live filter + sort
        private final FilteredList<OrderLine> filtered = new FilteredList<>(source, p -> true);
        private final SortedList<OrderLine> sorted = new SortedList<>(filtered);

        // Filter query
        private final StringProperty filterQuery = new SimpleStringProperty("");

        // Status
        private final IntegerProperty totalItems = new SimpleIntegerProperty(0);
        private final ObjectProperty<BigDecimal> grandTotal = new SimpleObjectProperty<>(BigDecimal.ZERO);

        OrderTableViewModel() {
            // React to source changes
            source.addListener((javafx.collections.ListChangeListener<OrderLine>) c -> {
                totalItems.set(source.size());
                grandTotal.set(source.stream()
                    .map(OrderLine::lineTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP));
            });

            // Live filter
            filterQuery.addListener((obs, o, n) -> {
                String lower = n == null ? "" : n.toLowerCase();
                filtered.setPredicate(line ->
                    lower.isBlank() || line.productName().toLowerCase().contains(lower));
            });
        }

        SortedList<OrderLine> sortedItems() { return sorted; }
        StringProperty filterQueryProperty() { return filterQuery; }
        ReadOnlyIntegerProperty totalItemsProperty() { return totalItems; }
        ReadOnlyObjectProperty<BigDecimal> grandTotalProperty() { return grandTotal; }

        void addLine(String name, int qty, BigDecimal price) {
            source.add(new OrderLine(name, qty, price.setScale(2, RoundingMode.HALF_UP)));
        }

        void removeLine(OrderLine line) {
            source.remove(line);
        }
    }

    /**
     * Custom cell that colors negative values red (overpayment scenario).
     */
    static class CurrencyCell extends TableCell<OrderLine, BigDecimal> {
        @Override
        protected void updateItem(BigDecimal value, boolean empty) {
            super.updateItem(value, empty);
            if (empty || value == null) {
                setText(null);
                setStyle("");
            } else {
                setText("₱ " + value.toPlainString());
                if (value.signum() < 0) {
                    setStyle("-fx-text-fill: red;");
                } else {
                    setStyle("-fx-text-fill: black;");
                }
            }
        }
    }

    /**
     * Builds the TableView with filterable, sortable columns.
     */
    static TableView<OrderLine> buildTable(OrderTableViewModel vm) {
        TableView<OrderLine> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setEditable(true);

        // SortedList comparator drives column sort order
        vm.sortedItems().comparatorProperty().bind(table.comparatorProperty());
        table.setItems(vm.sortedItems());

        // Product name column (editable)
        TableColumn<OrderLine, String> nameCol = new TableColumn<>("Product");
        nameCol.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().productName()));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(e ->
            table.getItems().set(e.getTablePosition().getRow(),
                new OrderLine(e.getNewValue(), e.getRowValue().quantity(), e.getRowValue().unitPrice())));

        // Quantity column (editable, integer)
        TableColumn<OrderLine, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(data ->
            new SimpleObjectProperty<>(data.getValue().quantity()));
        qtyCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            public String toString(Integer v) { return v == null ? "0" : v.toString(); }
            public Integer fromString(String s) {
                try { return Math.max(0, Integer.parseInt(s)); }
                catch (NumberFormatException e) { return 0; }
            }
        }));
        qtyCol.setOnEditCommit(e ->
            table.getItems().set(e.getTablePosition().getRow(),
                new OrderLine(e.getRowValue().productName(), e.getNewValue(), e.getRowValue().unitPrice())));

        // Unit price column (editable, BigDecimal, PHP currency)
        TableColumn<OrderLine, BigDecimal> priceCol = new TableColumn<>("Unit Price (" + PHP.getSymbol() + ")");
        priceCol.setCellValueFactory(data ->
            new SimpleObjectProperty<>(data.getValue().unitPrice()));
        priceCol.setCellFactory(c -> new CurrencyCell());

        // Line total (read-only, derived)
        TableColumn<OrderLine, BigDecimal> totalCol = new TableColumn<>("Line Total");
        totalCol.setCellValueFactory(data ->
            new SimpleObjectProperty<>(data.getValue().lineTotal()));
        totalCol.setCellFactory(c -> new CurrencyCell());

        table.getColumns().addAll(nameCol, qtyCol, priceCol, totalCol);
        return table;
    }
}
