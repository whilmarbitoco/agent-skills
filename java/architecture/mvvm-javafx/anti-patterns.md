# MVVM with JavaFX — Anti-Patterns

## Pattern 1: ViewModel holds Node references

```java
// WRONG: ViewModel knows about UI widgets
public class InvoiceViewModel {
    public TableView<Invoice> invoiceTable;        // UI widget in ViewModel!
    public TextField searchField;                   // ViewModel has TextField!

    public void refresh(BorderPane container) {
        container.setCenter(invoiceTable);          // Building UI in ViewModel!
    }
}

// FIX: ViewModel only has observable properties
public class InvoiceViewModel {
    private final ObservableList<InvoiceDto> invoices = FXCollections.observableArrayList();
    public ObservableList<InvoiceDto> invoices() { return invoices; }

    private final StringProperty searchQuery = new SimpleStringProperty("");
    public StringProperty searchQuery() { return searchQuery; }

    public void loadInvoices(String query) {
        invoices.setAll(service.search(query));
    }
}
```

## Pattern 2: Business logic in FXML controller's initialize()

```java
// WRONG: Controller.initialize() runs business logic
public class InvoiceController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Heavy computation in controller
        List<Invoice> raw = Ebean.find(Invoice.class).findList();
        List<InvoiceDto> dtos = raw.stream()
            .map(i -> new InvoiceDto(i.getId(), taxService.apply(i.getAmount())))
            .toList();
        table.getItems().setAll(dtos); // tax calculation in controller!
    }
}

// FIX: Controller only binds ViewModel; ViewModel does the work
public class InvoiceViewModel {
    private final ObservableList<InvoiceDto> invoices = FXCollections.observableArrayList();
    private final InvoiceService service;

    public InvoiceViewModel(InvoiceService service) {
        this.service = service;
    }

    public void loadAll() {
        invoices.setAll(service.findAllDtos());
    }

    public ObservableList<InvoiceDto> invoices() { return invoices; }
}
```

## Pattern 3: Direct property mutation from controller

```java
// WRONG: Controller sets ViewModel property and immediately reads derived result
public class PaymentController {
    private final PaymentViewModel vm;

    public void onPay() {
        vm.rawAmount.setText("100");         // Controller touching raw fields
        vm.tax.setText(vm.calculateTax());   // Controller doing math
        vm.total.setText(vm.addTogether());  // Controller doing math
    }
}

// FIX: ViewModel exposes computed bindings; controller only triggers commands
public class PaymentViewModel {
    private final StringProperty rawAmount = new SimpleStringProperty("");
    private final ReadOnlyObjectWrapper<Money> total = new ReadOnlyObjectWrapper<>();

    public PaymentViewModel() {
        // Computed binding — auto-recalculates when rawAmount changes
        total.bind(Bindings.createObjectBinding(
            () -> {
                var amt = new BigDecimal(rawAmount.get().isBlank() ? "0" : rawAmount.get());
                var tax = amt.multiply(new BigDecimal("0.12"));
                return new Money(amt.add(tax), Currency.getInstance("PHP"));
            },
            rawAmount
        ));
    }

    public StringProperty rawAmount() { return rawAmount; }
    public ReadOnlyObjectProperty<Money> total() { return total.getReadOnlyProperty(); }
}
```

## Pattern 4: ViewModel is stateful singleton (static)

```java
// WRONG: Static mutable ViewModel — leaks state between sessions
public class AppState {
    public static final InvoiceViewModel INSTANCE = new InvoiceViewModel(); // global mutable state!
}

// FIX: ViewModel instantiated per-view (or scoped), never static
public class InvoiceView {
    public InvoiceView(InvoiceService service) {
        var vm = new InvoiceViewModel(service); // fresh instance per view
        var loader = new FXMLLoader(getClass().getResource("/view/invoice.fxml"));
        loader.setControllerFactory(clazz -> new InvoiceController(vm));
    }
}
```

## Pattern 5: Background thread mutates bound properties directly

```java
// WRONG: Virtual thread mutates observable list off FX thread
public class InvoiceViewModel {
    public void loadInvoices() {
        Thread.ofVirtual().start(() -> {
            var results = service.fetchAll(); // background
            invoices.setAll(results); // CRASH: not on FX Application Thread!
        });
    }
}

// FIX: Use Platform.runLater or Task
public void loadInvoices() {
    var task = new Task<List<InvoiceDto>>() {
        @Override
        protected List<InvoiceDto> call() { return service.fetchAll(); }
    };
    task.setOnSucceeded(e -> invoices.setAll(task.getValue())); // runs on FX thread
    Thread.ofVirtual().start(task);
}
```
