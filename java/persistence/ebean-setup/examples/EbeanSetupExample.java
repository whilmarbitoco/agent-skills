import io.ebean.DB;
import io.ebean.Database;
import org.sqlite.SQLiteDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ebean + SQLite setup. Programmatic configuration, shutdown hook,
 * transaction management, and health check.
 * Java 21, Ebean 15, SQLite 3.45.
 */
public final class EbeanSetupExample {

    private static final Logger log = LoggerFactory.getLogger(EbeanSetupExample.class);

    private EbeanSetupExample() {}

    public static void main(String[] args) {
        // 1. Configure DataSource
        SQLiteDataSource sqliteDs = new SQLiteDataSource();
        sqliteDs.setUrl("jdbc:sqlite:example.db");

        // 2. Ebean picks up system properties or ebean.properties file
        //    This shows programmatic configuration via properties.
        System.setProperty("datasource.db.driver", "org.sqlite.JDBC");
        System.setProperty("datasource.db.url", "jdbc:sqlite:example.db");
        System.setProperty("datasource.db.username", "");
        System.setProperty("datasource.db.password", "");
        System.setProperty("ebean.dbName", "main");
        System.setProperty("ebean.packages", "com.example.domain");

        // 3. Get default database (created from config)
        Database db = DB.getDefault();

        // 4. Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down Ebean database");
            db.shutdown();
        }));

        // 5. Health check query
        var sqlQuery = db.sqlQuery("SELECT 1 AS alive");
        var row = sqlQuery.findOne();
        log.info("DB health check: alive={}", row.getInteger("alive"));

        // 6. Execute within a read-only transaction
        try (var txn = db.beginTransaction()) {
            txn.setReadOnly(true);
            // query here
            txn.commit();
            log.info("Read-only transaction committed");
        }

        db.shutdown();
        log.info("Ebean setup example complete");
    }
}
