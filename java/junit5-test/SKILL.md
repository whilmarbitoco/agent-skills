# Skill: JUnit 5 Testing

Unit testing with JUnit 5 and real SQLite in-memory.

## Core Concepts
- `@Test`, `@BeforeEach`, `@AfterEach` — lifecycle
- `@DisplayName("...")` — human-readable test names
- `Assertions.assertXxx()` — standard assertions
- Real SQLite in-memory — `jdbc:sqlite::memory:` (not mocks)
- Fake classes — hand-written test doubles via constructor injection

## Rules
1. Use real SQLite in-memory (`jdbc:sqlite::memory:`) — never mock the DB
2. No `@patch` / `MagicMock` — use hand-written fake classes
3. One logical assertion per test method (multiple `assertXxx` OK if same concept)
4. Always use `@DisplayName` with descriptive names
5. `@BeforeEach` sets up fresh schema + Ebean server per test
6. Constructor injection for all dependencies — no `@InjectMocks`

## Anti-patterns
- `@Mock` / `MagicMock` for repositories (use fakes)
- Testing against production database
- Multiple unrelated assertions in one test
- No `@DisplayName` (unreadable CI output)
- Shared mutable state between tests

## Relates to
- repository-pattern
- ebean-entity-modeling
- sqlite-desktop
