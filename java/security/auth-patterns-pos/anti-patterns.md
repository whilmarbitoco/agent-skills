# Auth Patterns (POS) — Anti-Patterns

## Problem 1: Storing passwords in plain text

```java
// WRONG — never store plain text passwords
user.setPassword(password);
userRepo.save(user);
```

```java
// FIX — hash with BCrypt (prefer Spring Security's BCryptPasswordEncoder
// for new projects; jBCrypt is unmaintained since 2017)
import org.mindrot.jbcrypt.BCrypt;

String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
user.setPasswordHash(hashed);
userRepo.save(user);

// Verify
boolean valid = BCrypt.checkpw(inputPassword, user.getPasswordHash());
```

## Problem 2: No role-based access control

```java
// WRONG — everyone can do everything
public void deleteProduct(String id) {
    productRepo.delete(id); // any user can delete!
}
```

```java
// FIX — check role before action
public void deleteProduct(String id) {
    User currentUser = authService.getCurrentUser();
    if (!currentUser.hasRole("ADMIN")) {
        throw new AccessDeniedException("Only admins can delete products");
    }
    productRepo.delete(id);
}
```

## Problem 3: Hardcoded credentials

```java
// WRONG — credentials in source code
String dbPassword = "admin123";
```

```java
// FIX — load from environment or config
String dbPassword = System.getenv("DB_PASSWORD");
// Or from encrypted config
String dbPassword = config.getEncrypted("db.password");
```
