# Ebean Entity Modeling Anti-Patterns

## Missing @Version — lost updates in concurrent use

```java
// WRONG — optimistic locking missing, last-write-wins silently
@Entity
public class Product {
    @Id @GeneratedValue private Long id;
    private int stock;
}
```

**Add `@Version private Long version;`. Ebean checks version on update — throws if stale.**

## FetchType.EAGER on @OneToMany — N+1 queries

```java
// WRONG — loads ALL sale-lines for EVERY sale, even when not needed
@OneToMany(fetch = FetchType.EAGER)
private List<SaleLine> lines;
```

**Change to `@OneToMany(fetch = FetchType.LAZY, mappedBy = "sale")`. Use `.fetch()` in queries when you need them.**

## Business logic in entity

```java
// WRONG — entity does calculations, uses repositories
public class Sale {
    public void calculateTotal() {
        // accessing other entities, doing math
    }
}
```

**Keep entities as data holders + mapping. Move calculations to services.**

## No @Index on lookup columns — full table scans

```java
// WRONG — queries by name will scan every row
@Column private String name;
```

**Add `@Index(name = "idx_product_name", columnList = "name")` or `@Column(unique = true)` for constrained columns.**

## Raw SQL in services — bypasses type safety

```java
// WRONG — string concatenation, no compile-time checking
String sql = "SELECT * FROM product WHERE name = '" + name + "'";
```

**Use QBean: `QProduct p = QProduct.alias(); DB.find(Product.class).where().eq(p.name, name).findList()`.**
