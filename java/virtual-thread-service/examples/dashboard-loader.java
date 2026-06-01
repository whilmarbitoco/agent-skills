import java.util.List;
import java.util.concurrent.StructuredTaskScope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DashboardLoader loads multiple data sources concurrently
 * using StructuredTaskScope. Handles partial failure —
 * if orders fail, still returns products and stats.
 */
public class DashboardLoader implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(DashboardLoader.class);

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StatsService statsService;

    public DashboardLoader(ProductRepository productRepository,
                           OrderRepository orderRepository,
                           StatsService statsService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.statsService = statsService;
    }

    /** Load dashboard data with StructuredTaskScope.ShutdownOnFailure. */
    public DashboardData load() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            StructuredTaskScope.Subtask<List<Product>> products =
                scope.fork(() -> productRepository.findAll());

            StructuredTaskScope.Subtask<List<Order>> orders =
                scope.fork(() -> orderRepository.findRecent(10));

            StructuredTaskScope.Subtask<DashboardStats> stats =
                scope.fork(() -> statsService.computeStats());

            scope.join();           // wait for all forked tasks
            scope.throwIfFailed();  // propagate first exception

            return new DashboardData(
                products.get(),
                orders.get(),
                stats.get()
            );
        }
    }

    /** Load with partial failure — collects results even if one subtask fails. */
    public DashboardData loadPartial() throws Exception {
        try (var scope = new StructuredTaskScope<>()) {

            StructuredTaskScope.Subtask<List<Product>> products =
                scope.fork(() -> productRepository.findAll());

            StructuredTaskScope.Subtask<List<Order>> orders =
                scope.fork(() -> orderRepository.findRecent(10));

            StructuredTaskScope.Subtask<DashboardStats> stats =
                scope.fork(() -> statsService.computeStats());

            scope.join();

            // Collect results, tolerate individual failures
            List<Product> productList = getOrNull(products);
            List<Order> orderList = getOrNull(orders);
            DashboardStats statsData = getOrNull(stats);

            log.warn("Loaded: products={}, orders={}, stats={}",
                productList != null, orderList != null, statsData != null);

            return new DashboardData(productList, orderList, statsData);
        }
    }

    private <T> T getOrNull(StructuredTaskScope.Subtask<T> subtask) {
        return subtask.state() == StructuredTaskScope.Subtask.State.SUCCESS
            ? subtask.get() : null;
    }

    @Override
    public void close() {
        // cleanup if needed
    }

    public record DashboardData(List<Product> products,
                                 List<Order> orders,
                                 DashboardStats stats) {}
    public record DashboardStats(long totalSales, long totalProducts) {}
}
