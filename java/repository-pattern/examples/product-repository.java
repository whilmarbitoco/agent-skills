import java.util.List;
import java.util.Optional;

/** Repository interface — lives in service layer. */
public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    List<Product> findByCategory(String category);
    List<Product> findAll();
    List<Product> findLowStock(int threshold);
    void delete(Long id);
}

// ---

import io.ebean.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/** Ebean-backed implementation — lives in persistence layer. */
public class EbeanProductRepository implements ProductRepository {

    private static final Logger log = LoggerFactory.getLogger(EbeanProductRepository.class);
    private final Database database;

    public EbeanProductRepository(Database database) {
        this.database = database;
    }

    @Override
    public Product save(Product product) {
        database.save(product);
        log.info("Saved product: id={}, name={}", product.getId(), product.getName());
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(database.find(Product.class, id));
    }

    @Override
    public Optional<Product> findByName(String name) {
        return database.find(Product.class)
            .where().eq("name", name)
            .findOneOrEmpty();
    }

    @Override
    public List<Product> findByCategory(String category) {
        return database.find(Product.class)
            .where()
            .eq("category", category)
            .eq("active", true)
            .orderBy("name asc")
            .findList();
    }

    @Override
    public List<Product> findAll() {
        return database.find(Product.class)
            .where().eq("active", true)
            .orderBy("name asc")
            .findList();
    }

    @Override
    public List<Product> findLowStock(int threshold) {
        return database.find(Product.class)
            .where()
            .le("stock", threshold)
            .eq("active", true)
            .orderBy("stock asc")
            .findList();
    }

    @Override
    public void delete(Long id) {
        database.find(Product.class, id).ifPresent(entity -> {
            entity.deactivate();
            database.save(entity);
            log.info("Deactivated product: id={}", id);
        });
    }
}
