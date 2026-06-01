import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ReentrantLock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ProductService runs all database queries on virtual threads.
 * Uses newVirtualThreadPerTaskExecutor — never pool VTs.
 * Uses ReentrantLock (not synchronized) to avoid VT pinning.
 */
public class ProductService implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;
    private final ExecutorService executor;
    private final ReentrantLock writeLock = new ReentrantLock();

    public ProductService(ProductRepository repository) {
        this.repository = repository;
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    /** Fetch a single product by ID on a virtual thread. */
    public java.util.concurrent.CompletableFuture<Optional<Product>> findById(long id) {
        return java.util.concurrent.CompletableFuture.supplyAsync(
            () -> {
                log.debug("Fetching product {} on {}", id, Thread.currentThread());
                return repository.findById(id);
            },
            executor
        );
    }

    /** Save with write lock — ReentrantLock avoids VT pinning vs synchronized. */
    public java.util.concurrent.CompletableFuture<Product> save(Product product) {
        return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
            writeLock.lock();
            try {
                log.info("Saving product: {}", product.name());
                return repository.save(product);
            } finally {
                writeLock.unlock();
            }
        }, executor);
    }

    /** Find all products on virtual thread. */
    public java.util.concurrent.CompletableFuture<List<Product>> findAll() {
        return java.util.concurrent.CompletableFuture.supplyAsync(
            () -> {
                log.debug("Loading all products on {}", Thread.currentThread());
                return repository.findAll();
            },
            executor
        );
    }

    @Override
    public void close() {
        executor.shutdown();
    }
}
