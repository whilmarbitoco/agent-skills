# Ebean Entities — Anti-Patterns

## 1. Using records for Ebean entities

```java
// WRONG — records are final; Ebean needs field access for lazy loading/proxying
@Entity
@Table(name = "customers")
public record Customer(
    @Id Long id,
    String name,
    String email
) {}
```

```java
// FIX: use a plain class with setters
import jakarta.persistence.*;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import java.time.Instant;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Version
    private Long version;

    @WhenCreated
    private Instant createdAt;

    @WhenModified
    private Instant updatedAt;

    // JPA requires a no-arg constructor
    protected Customer() {}

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    // Setters (needed by Ebean for dirty checking)
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() { return id != null ? id.hashCode() : 0; }
}
```

## 2. Missing `@Version` on concurrent-write entities

```java
// WRONG — no optimistic locking, last-write-wins silently overwrites
@Entity
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;
    // two concurrent updates → one is lost
}
```

```java
// FIX: add @Version for optimistic locking
import jakarta.persistence.Version;
import java.math.BigDecimal;

@Entity
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private BigDecimal balance;

    protected Account() {}

    // getters/setters omitted for brevity
}
```

## 3. Eager fetching on `@OneToMany` by default

```java
// WRONG — loads ALL orders every time a Customer is loaded (N+1 problem)
@Entity
public class Customer {
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();
}
```

```java
// FIX: stay with LAZY, fetch explicitly when needed
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    // When you need orders, use:
    // DB.find(Customer.class).fetch("orders").where().idEq(id).findOne();
}
```

## 4. Storing enums as ordinals (default) instead of strings

```java
// WRONG — ordinal encoding breaks if enum order changes
@Entity
public class Order {
    @Enumerated(EnumType.ORDINAL) // default
    private Status status;
}
```

```java
// FIX: store as string for readability and stability
import io.ebean.annotation.DbEnumValue;
import jakarta.persistence.*;

@Entity
public class Order {

    @DbEnumValue(storage = DbEnumType.VARCHAR) // stores "PENDING", "SHIPPED" etc.
    @Column(nullable = false)
    private Status status;

    protected Order() {}
}

enum Status { PENDING, SHIPPED, DELIVERED, CANCELLED }
```

## 5. Not using `@SoftDelete` when data must be retained

```java
// WRONG — hard delete loses data forever
@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
// DB.find(Product.class).ref(product); DB.delete(product); // gone!
```

```java
// FIX: soft delete preserves rows
import io.ebean.annotation.SoftDelete;
import jakarta.persistence.*;

@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @SoftDelete
    private boolean deleted;

    protected Product() {}

    // Ebean's find methods auto-filter deleted=false
    // Query: DB.find(Product.class) — only returns non-deleted rows
}
```
