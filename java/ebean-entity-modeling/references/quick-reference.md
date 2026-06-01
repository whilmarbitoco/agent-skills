# Ebean Quick Reference

| Annotation | Purpose |
|-----------|---------|
| @Entity | Marks class as persistent entity |
| @Table | Table name, indexes |
| @Id @GeneratedValue | Primary key |
| @Version | Optimistic locking (required) |
| @WhenCreated / @WhenModified | Auto timestamps |
| @Index | Column index |
| @Column | Column constraints |

Maven: Ebean enhancement via annotation processor (build-time bytecode enhancement).
