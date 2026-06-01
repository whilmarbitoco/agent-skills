import io.ebean.Database;
import io.ebean.config.DatabaseConfig;
import io.ebean.platform.sqlite.SQLitePlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteDataSource;

import java.nio.file.*;

/**
 * SQLite database setup for desktop application.
 * Stores database in user home directory with WAL mode.
 */
public class DatabaseInitializer {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
    private static final String APP_DIR = ".simplepos";
    private static final String DB_FILE = "pos.db";

    private final Database database;

    public DatabaseInitializer() {
        this.database = createDatabase();
    }

    private Database createDatabase() {
        Path dbPath = getDatabasePath();
        Files.createDirectories(dbPath.getParent());

        String jdbcUrl = "jdbc:sqlite:" + dbPath.toAbsolutePath();

        log.info("Database location: {}", dbPath);

        // SQLite file with WAL mode for concurrent reads
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(jdbcUrl);
        // WAL mode — set via pragma on first connection
        dataSource.setEncoding("UTF-8");

        DatabaseConfig config = new DatabaseConfig();
        config.setDatabasePlatform(new SQLitePlatform());
        config.setDataSource(dataSource);
        config.setDefaultServer(true);
        config.setDdlGenerate(true);   // dev: auto-create tables
        config.setDdlRun(true);
        config.setRegister(false);     // don't register as JNDI

        // Apply WAL mode via config listener
        config.addListener(new io.ebean.config.ServerConfigStartup() {
            @Override
            public void onStart(Database database) {
                database.sqlUpdate("PRAGMA journal_mode=WAL").execute();
                database.sqlUpdate("PRAGMA synchronous=NORMAL").execute();
                log.info("WAL mode enabled");
            }
        });

        return io.ebean.EbeanServerFactory.create(config);
    }

    private Path getDatabasePath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, APP_DIR, DB_FILE);
    }

    public Database getDatabase() {
        return database;
    }

    /** Shutdown hook for graceful cleanup */
    public void shutdown() {
        if (database != null) {
            database.shutdown(false, false);
            log.info("Database connection closed");
        }
    }
}

// Usage:
// var db = new DatabaseInitializer();
// var productRepo = new EbeanProductRepository(db.getDatabase());
// Runtime.getRuntime().addShutdownHook(new Thread(db::shutdown));
