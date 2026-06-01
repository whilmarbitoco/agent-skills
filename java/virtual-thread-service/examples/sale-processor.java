import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CompletableFuture chain for sale processing:
 * validate → compute total → persist → send receipt.
 * All steps run on virtual threads via the executor.
 */
public class SaleProcessor implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(SaleProcessor.class);

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ReceiptService receiptService;
    private final java.util.concurrent.ExecutorService executor;

    public SaleProcessor(ProductRepository productRepository,
                         SaleRepository saleRepository,
                         ReceiptService receiptService) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.receiptService = receiptService;
        this.executor = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor();
    }

    /** Process a sale: validate stock → compute total → persist → send receipt. */
    public CompletableFuture<SaleResult> process(SaleRequest request) {
        return CompletableFuture
            .supplyAsync(() -> validateStock(request), executor)
            .thenApplyAsync(total -> computeDiscount(request, total), executor)
            .thenApplyAsync(total -> persistSale(request, total), executor)
            .thenComposeAsync(sale ->
                receiptService.sendAsync(sale)
                    .thenApply(receiptId -> new SaleResult(sale.id(), total(sale), receiptId)),
                executor
            )
            .exceptionally(ex -> {
                log.error("Sale processing failed: {}", ex.getMessage(), ex);
                throw new SaleProcessingException("Failed to process sale", ex);
            });
    }

    private BigDecimal validateStock(SaleRequest request) {
        for (SaleRequest.Line line : request.lines()) {
            Product product = productRepository.findById(line.productId())
                .orElseThrow(() -> new ProductNotFoundException(line.productId()));
            if (product.stockQuantity() < line.quantity()) {
                throw new InsufficientStockException(product.name(), line.quantity());
            }
        }
        return request.lines().stream()
            .map(l -> l.unitPrice().multiply(BigDecimal.valueOf(l.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal computeDiscount(SaleRequest request, BigDecimal total) {
        if (request.discountPercent() != null && request.discountPercent().signum() > 0) {
            return total.multiply(
                BigDecimal.ONE.subtract(request.discountPercent().divide(new BigDecimal("100")))
            );
        }
        return total;
    }

    private Sale persistSale(SaleRequest request, BigDecimal total) {
        log.info("Persisting sale: total={}", total);
        return saleRepository.save(new Sale(request.customerName(), total, request.lines()));
    }

    private BigDecimal total(Sale sale) {
        return sale.totalAmount();
    }

    @Override
    public void close() {
        executor.shutdown();
    }

    // ── supporting records ──
    public record SaleRequest(String customerName, java.util.List<Line> lines, BigDecimal discountPercent) {
        public record Line(long productId, BigDecimal unitPrice, int quantity) {}
    }
    public record SaleResult(long saleId, BigDecimal total, String receiptId) {}
}
