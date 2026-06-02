import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Virtual threads example: concurrent I/O fan-out with StructuredTaskScope.
 * Java 21 — real compilable code.
 */
public class VirtualThreadsExample {

    // --- ReentrantLock instead of synchronized to avoid pinning ---
    private final ReentrantLock lock = new ReentrantLock();
    private int counter = 0;

    public void safeIncrement() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    // --- StructuredTaskScope for concurrent I/O ---
    record User(String name, String email) {}
    record Order(String id, double amount) {}
    record Dashboard(User user, List<Order> orders) {}

    Dashboard loadDashboard(String userId) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var userTask   = scope.fork(() -> fetchUser(userId));
            var orderTask  = scope.fork(() -> fetchOrders(userId));
            scope.join().throwIfFailed();
            return new Dashboard(userTask.get(), orderTask.get());
        }
    }

    // --- Virtual thread per task executor ---
    List<String> fetchAllUrls(List<String> urls) throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            return urls.stream()
                    .map(url -> executor.submit(() -> httpGet(url)))
                    .toList()
                    .stream()
                    .map(f -> {
                        try { return f.get(); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    })
                    .toList();
        }
    }

    // --- Helpers (simulated I/O) ---
    private User fetchUser(String id) {
        return new User("Alice", "alice@example.com");
    }

    private List<Order> fetchOrders(String userId) {
        return List.of(new Order("ORD-1", 49.99), new Order("ORD-2", 12.50));
    }

    private String httpGet(String url) {
        return "Response from " + url;
    }

    public static void main(String[] args) throws Exception {
        var example = new VirtualThreadsExample();
        example.safeIncrement();
        System.out.println("Counter: " + example.counter);

        var dashboard = example.loadDashboard("u-1");
        System.out.println("Dashboard for " + dashboard.user().name()
                + " has " + dashboard.orders().size() + " orders");

        var responses = example.fetchAllUrls(List.of("https://a.com", "https://b.com"));
        responses.forEach(System.out::println);
    }
}
