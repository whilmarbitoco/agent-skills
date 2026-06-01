import io.ebean.DB;
import io.ebean.Database;
import io.ebean.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Type-safe query examples using QProduct (generated QBean).
 * All queries use the generated type-safe API — no raw JPQL/SQL.
 */
public class ProductQueries {

    private final Database db;

    public ProductQueries(Database db) {
        this.db = db;
    }

    /** Find by ID — returns Optional, never null. */
    public Optional<Product> findById(long id) {
        return db.find(Product.class)
            .where().idEq(id)
            .findOneOrEmpty();
    }

    /** Find active products in a category, ordered by name. */
    public List<Product> findByCategory(String category) {
        return db.find(Product.class)
            .where()
                .eq("category", category)
                .eq("active", true)
            .orderBy().asc("name")
            .findList();
    }

    /** Search with case-insensitive partial match + pagination. */
    public List<Product> search(String query, int offset, int limit) {
        return db.find(Product.class)
            .where()
                .icontains("name", query)
                .eq("active", true)
            .orderBy().asc("name")
            .setFirstRow(offset)
            .setMaxRows(limit)
            .findList();
    }

    /** Find products with stock below threshold (reorder report). */
    public List<Product> findLowStock(int threshold) {
        return db.find(Product.class)
            .where()
                .lt("stockQuantity", threshold)
                .eq("active", true)
            .orderBy().asc("stockQuantity")
            .findList();
    }

    /** Count products in category. */
    public long countByCategory(String category) {
        return db.find(Product.class)
            .where()
                .eq("category", category)
                .eq("active", true)
            .findCount();
    }

    /** Find products priced within a range. */
    public List<Product> findByPriceRange(BigDecimal min, BigDecimal max) {
        return db.find(Product.class)
            .where()
                .between("price", min, max)
                .eq("active", true)
            .orderBy().asc("price")
            .findList();
    }
}
