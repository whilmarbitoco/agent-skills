# Ebean Entities — Quick Reference

| Annotation | Purpose |
|---|---|
| `@Entity` / `@Table` | Mark class as persistent entity |
| `@Id` + `@GeneratedValue` | Auto-increment identity column |
| `@Version` | Optimistic locking |
| `@ManyToOne` / `@OneToMany` | Relationships (LAZY by default) |
| `@JoinColumn(name)` | Explicit FK column name |
| `@WhenCreated` / `@WhenModified` | Auto timestamp auditing |
| `@SoftDelete` | Logical delete flag |
| `@DbEnumType(ENUM)` | Store enum as string |
| `@Index` | DDL index hint |

| Relationship | Default Fetch | Override |
|---|---|---|
| `@ManyToOne` | EAGER | `fetch = FetchType.LAZY` |
| `@OneToMany` | LAZY | — |
| `@ManyToMany` | LAZY | — |
 | `@OneToOne` | EAGER | `fetch = FetchType.LAZY` |
