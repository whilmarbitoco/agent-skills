# Exception Strategy — Anti-Patterns

## 1. Returning null instead of Optional

```java
// WRONG — caller must remember null-check, easy to forget
public User findUser(String id) {
    return userMap.get(id); // returns null if missing
}
// Caller: User u = findUser("x"); u.getName(); // NPE!
```

```java
// FIX: return Optional
public Optional<User> findUser(String id) {
    return Optional.ofNullable(userMap.get(id));
}
// Caller: findUser("x").ifPresent(u -> System.out.println(u.name()));
```

## 2. Catching generic Exception

```java
// WRONG — catches everything including RuntimeExceptions you didn't anticipate
try {
    repository.save(order);
} catch (Exception e) { // too broad
    log.error("Failed: " + e.getMessage()); // string concat!
}
```

```java
// FIX: catch specific exceptions, use parameterized logging
try {
    repository.save(order);
} catch (DataAccessException e) {
    log.error("Failed to save order {}", orderId, e);
    throw new OrderPersistenceException(orderId, e);
}
```

## 3. String concatenation in logging

```java
// WRONG — string concat happens even if log level is disabled
log.info("Processing order " + order.getId() + " for user " + user.getName());
```

```java
// FIX: SLF4J parameterized logging — lazy evaluation
log.info("Processing order {} for user {}", order.getId(), user.getName());
```

## 4. Empty catch block (swallowing exceptions)

```java
// WRONG — exception silently lost
try {
    Files.deleteIfExists(path);
} catch (IOException e) {
    // nothing — whoops
}
```

```java: FIX: at minimum log, or propagate
try {
    Files.deleteIfExists(path);
} catch (IOException e) {
    log.warn("Could not delete file {}", path, e);
}
```

## 5. Using exceptions for control flow

```java
// WRONG — using exception to signal "not found" when it's a normal case
public User getUser(String id) {
    return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
}
```

```java: FIX: use Optional for normal "not found", domain exception for truly exceptional
public Optional<User> findUser(String id) {
    return repository.findById(id); // Optional.empty() is normal
}

// Only throw when the caller *requires* the user to exist:
public User getUserRequired(String id) {
    return findUser(id).orElseThrow(() -> new UserNotFoundException(id));
}
```
