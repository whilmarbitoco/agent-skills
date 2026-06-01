import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests InventoryService with real SQLite in-memory database.
 * No @patch, no MagicMock — just real DB and fake repositories via constructor injection.
 */
@DisplayName("InventoryService Tests")
class InventoryServiceTest {

    private static Database database;
    private InventoryService service;

    @BeforeAll
    static void initDatabase() {
        // Real SQLite in-memory — creates schema from Ebean entities
        var config = new io.ebean.config.DatabaseConfig();
        config.setDdlGenerate(true);
        config.setDdlRun(true);
        config.setDefaultServer(true);
        config.setDatabasePlatform(new io.ebean.platform.sqlite.SQLitePlatform());
        config.setDataSource(new org.sqlite.SQLiteDataSource() {{
            setUrl("jdbc:sqlite::memory:");
        }});
        database = io.ebean.EbeanServerFactory.create(config);
    }

    @BeforeEach
    void setUp() {
        // Fake repository via constructor injection — real class, no mocking
        var fakeRepository = new FakeProductRepository(database);
        service = new InventoryService(fakeRepository, fakeRepository);
    }

    @Test
    @DisplayName("Should find product by name")
    void shouldFindProductByName() {
        fakeRepository.save(new Product("Laptop", new BigDecimal("45000.00"), 10, "ELECTRONICS"));

        Optional<Product> found = service.findByName("Laptop");

        assertTrue(found.isPresent());
        assertEquals("Laptop", found.get().getName());
    }

    @Test
    @DisplayName("Should throw when stock insufficient")
    void shouldThrowWhenStockInsufficient() {
        fakeRepository.save(new Product("Mouse", new BigDecimal("500.00"), 2, "ELECTRONICS"));

        var ex = assertThrows(IllegalArgumentException.class, () ->
            service.sell(1L, 5)  // want 5, only 2 in stock
        );

        assertTrue(ex.getMessage().contains("insufficient stock"));
    }

    @Test
    @DisplayName("Should return empty for missing product")
    void shouldReturnEmptyForMissingProduct() {
        Optional<Product> found = service.findByName("NonExistent");
        assertTrue(found.isEmpty());
    }

    // Saved for last — state persists between tests in SQLite in-memory
    @AfterEach
    void tearDown() {
        database.find(Product.class).findList().forEach(p ->
            database.delete(p)
        );
    }
}

// Fake repository — real class, implements interface, no Mockito
class FakeProductRepository implements ProductRepository {
    private final Database db;

    FakeProductRepository(Database db) { this.db = db; }

    @Override
    public Product save(Product product) {
        db.save(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(db.find(Product.class, id));
    }

    @Override
    public Optional<Product> findByName(String name) {
        return db.find(Product.class)
            .where().eq("name", name)
            .findOneOrEmpty();
    }

    @Override
    public List<Product> findAll() {
        return db.find(Product.class).findList();
    }
}
