import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite best practices: WAL mode, busy timeout, parameterized queries,
 * explicit transactions, proper resource cleanup, and ANALYZE.
 * Java 21, SQLite JDBC driver.
 */
public final class SQLiteBestPracticesExample {

    private SQLiteBestPracticesExample() {}

    /** Create a configured SQLite connection with WAL mode. */
    public static Connection open(String dbPath) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA journal_mode=WAL");
            stmt.execute("PRAGMA busy_timeout=5000");
            stmt.execute("PRAGMA foreign_keys=ON");
        }
        return conn;
    }

    /** Insert a row using PreparedStatement — parameterized, no concat. */
    public static void insertCustomer(Connection conn, String name, String email)
            throws SQLException {
        String sql = "INSERT INTO customers (name, email) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }

    /** Transfer with explicit transaction. */
    public static void transfer(Connection conn,
                                 long fromId, long toId,
                                 java.math.BigDecimal amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            String debit  = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
            String credit = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

            try (PreparedStatement ps1 = conn.prepareStatement(debit);
                 PreparedStatement ps2 = conn.prepareStatement(credit)) {
                ps1.setBigDecimal(1, amount);
                ps1.setLong(2, fromId);
                ps1.executeUpdate();

                ps2.setBigDecimal(1, amount);
                ps2.setLong(2, toId);
                ps2.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /** Query with try-with-resources — no resource leak. */
    public static List<String> findCustomerNames(Connection conn, String prefix)
            throws SQLException {
        String sql = "SELECT name FROM customers WHERE name LIKE ? ORDER BY name LIMIT 50";
        List<String> names = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    names.add(rs.getString("name"));
                }
            }
        }
        return names;
    }

    /** Run ANALYZE after bulk load. */
    public static void finalizeBulkLoad(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("ANALYZE");
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = open("example.db");
        System.out.println("SQLite best practices: WAL, prepared statements, transactions");
        conn.close();
    }
}
