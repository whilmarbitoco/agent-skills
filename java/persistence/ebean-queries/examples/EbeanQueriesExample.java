import io.ebean.DB;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Ebean query examples: ExpressionList, fetch, pagination,
 * bulk update, RawSql, and findIterate streaming.
 * Java 21, Ebean 15.
 */
public final class EbeanQueriesExample {

    private static final Logger log = LoggerFactory.getLogger(EbeanQueriesExample.class);

    private EbeanQueriesExample() {}

    // --- Entity stubs (would normally be real entity classes) ---
    enum OrderStatus { PENDING, SHIPPED, DELIVERED, CANCELLED }

    @jakarta.persistence.Entity
    @jakarta.persistence.Table(name = "customers")
    static class Customer {
        @jakarta.persistence.Id
        @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        Long id;
        String name;
        String email;
        protected Customer() {}
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }

    @jakarta.persistence.Entity
    @jakarta.persistence.Table(name = "orders")
    static class Order {
        @jakarta.persistence.Id
        @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        Long id;
        OrderStatus status = OrderStatus.PENDING;
        Instant createdAt = Instant.now();
        protected Order() {}
    }

    // --- Query methods ---

    /** Type-safe query with ExpressionList */
    static List<Customer> findCustomers(String nameContains, String email) {
        ExpressionList<Customer> where = DB.find(Customer.class).where();
        if (nameContains != null) {
            where.like("name", "%" + nameContains + "%");
        }
        if (email != null) {
            where.eq("email", email);
        }
        return where.orderBy("name").findList();
    }

    /** Eager fetch relationship to avoid N+1 */
    static List<Order> findOrdersByStatus(OrderStatus status) {
        return DB.find(Order.class)
            .where().eq("status", status)
            .orderBy("createdAt desc")
            .findList();
    }

    /** Pagination */
    static List<Order> findOrdersPage(int page, int pageSize) {
        PagedList<Order> paged = DB.find(Order.class)
            .setFirstRow(page * pageSize)
            .setMaxRows(pageSize)
            .orderBy("createdAt desc")
            .findPagedList();
        paged.loadCount();
        log.info("Page {}/{}, total {}", page, paged.getTotalPageCount(), paged.getTotalCount());
        return paged.getList();
    }

    /** Bulk update — no entities loaded into memory */
    static int cancelOldOrders(int daysOld) {
        return DB.update(Order.class)
            .set("status", OrderStatus.CANCELLED)
            .where()
            .eq("status", OrderStatus.PENDING)
            .lt("createdAt", Instant.now().minus(Duration.ofDays(daysOld)))
            .update();
    }

    /** Streaming iterate for large datasets */
    static void exportAllCustomers() {
        try (var cursor = DB.find(Customer.class).findIterate()) {
            while (cursor.hasNext()) {
                Customer c = cursor.next();
                log.info("Export customer id={} name={}", c.getId(), c.getName());
            }
        }
    }

    /** RawSql for complex aggregation */
    static List<Map<String, Object>> salesByCustomer() {
        String sql = """
            SELECT c.name AS customer_name,
                   COUNT(o.id) AS order_count
            FROM customers c
            JOIN orders o ON o.customer_id = c.id
            GROUP BY c.id, c.name
            ORDER BY COUNT(o.id) DESC
            """;
        return DB.sqlQuery(sql).findList().stream()
            .map(row -> Map.of(
                "customer", row.getString("customer_name"),
                "orders", row.getInteger("order_count")
            ))
            .toList();
    }

    public static void main(String[] args) {
        log.info("Ebean query patterns loaded.");
        log.info("See: ExpressionList, fetch(), pagination, bulk update, RawSql, findIterate");
    }
}
