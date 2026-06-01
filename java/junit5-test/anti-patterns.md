# JUnit 5 + SQLite Testing Anti-Patterns

## Using @patch/MagicMock instead of real DB

```java
// WRONG — mock proves nothing about actual DB behavior
@Mock ProductRepository repo;
when(repo.findAll()).thenReturn(List.of(mockProduct));
```

**Use real SQLite in-memory + fake class: `new TestProductRepository(database)`.**

## Mocking the service under test

```java
// WRONG — mocking the thing you're testing
@Mock InventoryService service;
when(service.findLowStock()).thenReturn(List.of(...));
```

**Test the real `InventoryService` with a fake repository. You're testing integration, not mocking everything away.**

## Shared state between tests — test order matters

```java
// WRONG — test B depends on test A's data
@Test void test1() { repository.save(product); assertTrue(...); }
@Test void test2() { assertTrue(repository.findAll().size() == 1); } // fails if test1 didn't run
```

**Reset state in @BeforeEach: `database.find(Product.class).forEach(p -> database.delete(p))`.**

## One giant test method

```java
// WRONG — does everything, hard to diagnose when it fails
@Test void testEverything() {
    saveProduct(); findProduct(); updateProduct(); deleteProduct();
    // which step failed? who knows.
}
```

**One test per behavior. Use `@DisplayName("Should throw when stock is negative")`.**

## No assertions — test always passes

```java
// WRONG — no assertions at all
@Test void shouldSaveProduct() {
    service.save(new Product("Test", BigDecimal.TEN, 5, "CAT"));
    // no assert — always green even if save is broken
}
```

**Always assert: `assertNotNull(saved.getId()); assertEquals("Test", saved.getName());`.**
