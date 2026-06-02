package com.pos.architecture.service;

/**
 * Service-Repository pattern: services contain business logic,
 * repositories handle data access. Never access DB from UI.
 */
public class ServiceRepositoryDemo {

    // Repository: pure data access
    public interface ProductRepository {
        Product findById(String id);
        List<Product> findByCategory(String category);
        void save(Product product);
    }

    // Service: business logic, uses repository
    public class ProductService {
        private final ProductRepository repo;

        public ProductService(ProductRepository repo) {
            this.repo = repo;
        }

        public Product updatePrice(String productId, BigDecimal newPrice) {
            Product product = repo.findById(productId);
            if (product == null) throw new NotFoundException(productId);
            product.setPrice(newPrice);
            product.setUpdatedAt(Instant.now());
            repo.save(product);
            return product;
        }
    }
}
