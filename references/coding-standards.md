# Coding Standards — Java 21 LTS Agent Skills
# Non-negotiable engineering conventions for all skills and projects.

version: "1.0.0"
last_updated: "2026-06-01"

---

## Dependency Injection

1. **Constructor injection only** — never field injection, never setter injection
2. **No static service locators** — pass dependencies explicitly
3. **Service interfaces in `services` package** — implementations inject into UI via constructor
4. **UI layer never instantiates services directly** — receives them via DI or factory

```java
// ✅ CORRECT
public class InventoryService {
    private final InventoryRepository repository;
    
    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }
}

// ❌ WRONG
public class InventoryService {
    @Inject
    private InventoryRepository repository;  // Field injection — forbidden
}
```

---

## Controller / UI Rules

1. **No business logic in controllers** — delegate to services immediately
2. **Max 150 lines per controller** — extract to helper classes if exceeded
3. **No Ebean queries in controllers** — all data access via service/repository layer
4. **Controllers are stateless beyond their injected dependencies** — no mutable static fields
5. **All long-running operations via virtual threads** — never block the FX Application Thread

```java
// ✅ CORRECT
public class InventoryController {
    private final InventoryService inventoryService;
    
    public InventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    
    private void onSearch() {
        var results = inventoryService.search(searchField.getText());
        productTable.setItems(FXCollections.observableArrayList(results));
    }
}

// ❌ WRONG
public class InventoryController {
    @FXML
    private void onSearch() {
        // Direct database access in controller — forbidden
        List<Product> products = DB.find(Product.class)
            .where().like("name", "%" + searchField.getText() + "%")
            .findList();
    }
}
```

---

## Domain Modeling

1. **Records for immutable value objects** — Money, Address, DateRange
2. **Sealed hierarchies for domain type closures** — PaymentType, StockMovementType
3. **Entities use Ebean annotations** — @DbDefault, @WhenCreated, @WhenModified
4. **No anemic domain models** — entities should have domain behavior, not just getters/setters
5. **DTOs for cross-layer data transfer** — never expose Ebean entities to UI layer

```java
// ✅ CORRECT — Record for value object
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);
    }
    
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
}

// ✅ CORRECT — Sealed hierarchy for domain types
public sealed interface PaymentType 
    permits PaymentType.Cash, PaymentType.Card, PaymentType.GCash {
    
    record Cash(BigDecimal tendered, BigDecimal change) implements PaymentType {}
    record Card(String reference, String provider) implements PaymentType {}
    record GCash(String reference, String mobile) implements PaymentType {}
}
```

---

## Concurrency & Threading

1. **FX Application Thread is sacred** — never block it with I/O or computation
2. **Use virtual threads for I/O-bound work** — network calls, file operations
3. **Use `Platform.runLater()` sparingly** — prefer `Task<ServiceResult>` pattern
4. **Never use `Thread.sleep()` on FX thread** — always offload
5. **Structured concurrency for related tasks** — `StructuredTaskScope` where applicable

```java
// ✅ CORRECT — Virtual thread for background work
public CompletableFuture<List<Product>> loadProductsAsync() {
    return CompletableFuture.supplyAsync(() -> {
        return productRepository.findAll();
    }, Executors.newVirtualThreadPerTaskExecutor());
}

// ✅ CORRECT — Platform.runLater for UI updates
loadProductsAsync().thenAcceptAsync(products -> {
    tableView.setItems(FXCollections.observableArrayList(products));
}, Platform::runLater);

// ❌ WRONG — Blocking the FX thread
var products = productRepository.findAll(); // Network call on FX thread!
```

---

## Error Handling

1. **Never swallow exceptions** — always log and/or propagate
2. **Use typed exceptions for domain errors** — InsufficientStockException, InvalidReceiptException
3. **Optional for nullable returns** — never return null from repository methods
4. **Consistent error messages** — include context (entity ID, operation name)
5. **Log at appropriate level** — WARN for recoverable, ERROR for data loss risk

```java
// ✅ CORRECT
public Optional<Product> findById(long id) {
    return Optional.ofNullable(DB.find(Product.class, id));
}

// ❌ WRONG
public Product findById(long id) {
    Product p = DB.find(Product.class, id);
    if (p == null) {
        return null; // NPE waiting to happen
    }
    return p;
}
```

---

## Testing Conventions

1. **Real SQLite in-memory database** — no mocks for repository tests
2. **Simple fake classes over Mockito** — constructor injection makes fakes trivial
3. **TestFX for UI interaction tests** — headless mode with Monocle
4. **One assert per test** — descriptive test method names
5. **AAA pattern** — Arrange, Act, Assert with clear section comments
6. **No @patch / MagicMock** — use real SQLite and fake implementations

```java
// ✅ CORRECT — Fake repository for testing
public class FakeInventoryRepository implements InventoryRepository {
    private final Map<Long, Product> store = new HashMap<>();
    
    @Override
    public Optional<Product> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }
    
    @Override
    public Product save(Product product) {
        store.put(product.id(), product);
        return product;
    }
}

// ✅ CORRECT — Integration test with real SQLite
@Test
void shouldPersistProductToDatabase() {
    // Arrange
    var config = new DatabaseConfig();
    config.setDatabasePlatform(new SQLitePlatform());
    config.setDdlGenerate(true);
    config.setDdlRun(true);
    var server = EbeanServerFactory.create(config);
    var repository = new EbeanInventoryRepository(server);
    
    // Act
    var product = repository.save(new Product(null, "Test", Money.of(100)));
    
    // Assertions
    assertNotNull(product.id());
    var found = repository.findById(product.id());
    assertTrue(found.isPresent());
    assertEquals("Test", found.get().name());
}
```

---

## Logging

1. **SLF4J + Logback** — no `System.out.println`, no `printStackTrace()`
2. **Parameterized log messages** — `log.info("Loaded {} products", count)` not string concat
3. **Log at INFO for operations, DEBUG for data, WARN for recoverable issues**
4. **Never log sensitive data** — passwords, tokens, personal information

---

## Package Visibility

1. **Minimize public surface** — package-private where possible
2. **Facade services as the only public interface** — internals stay package-private
3. **Repository interfaces + implementations separated** — UI depends on interface only

---

## Build & Packaging

1. **Maven Wrapper required** — `./mvnw` guarantees reproducible builds
2. **`-DskipTests` for quick iteration** — but never commit with tests failing
3. **jpackage for distribution** — native installers, not raw JARs
4. **`.env` for local secrets** — never commit credentials
5. **`.env.example` as template** — list all required keys with dummy values
