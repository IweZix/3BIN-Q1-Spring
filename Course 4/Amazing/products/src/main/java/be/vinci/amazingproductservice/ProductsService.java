package be.vinci.amazingproductservice;

import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    private final ProductsRepository repository;

    public ProductsService(ProductsRepository repository) {
        this.repository = repository;
    }

    /**
     * Reads all products in repository
     * @return an iterable of all products
     */
    public Iterable<Product> readAll() {
        return repository.findAll();
    }

    /**
     * Reads all products in repository with a price less than a certain value
     * @param price the price to compare
     * @return an iterable of all products found
     */
    public Iterable<Product> findByPriceLessThan(int price) {
        return repository.findByPriceLessThan(price);
    }

    /**
     * Reads all products in repository with a certain category
     * @param category the category to compare
     * @return an iterable of all products found
     */
    public Iterable<Product> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    /**
     * Reads all products in repository with a name containing a certain string
     * @param name the string to compare
     * @return an iterable of all products found
     */
    public Iterable<Product> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Reads a product with a certain id from repository
     * @param id the id to search for
     * @return the product, or null if the product couldn't be found
     */
    public Product findById(int id) {
        return repository.findById(String.valueOf(id)).orElse(null);
    }

    /**
     * Creates a product in repository
     * @param product the product to create
     * @return the product: if it was created
     *         null: if another product exists with same id
     */
    public Product createOne(Product product) {
        if (repository.existsById(String.valueOf(product.getId())))
            return null;
        repository.save(product);
        return product;
    }

    /**
     * Updates a product in repository
     * @param product the new values of the product
     * @return the product: if it was updated
     *         null: if the product couldn't be found
     */
    public Product updateOne(Product product) {
        if (!repository.existsById(String.valueOf(product.getId())))
            return null;
        repository.save(product);
        return product;
    }

    /**
     * Deletes a product with a certain id from repository
     * @param id the id of the product
     * @return the product: if it was deleted
     */
    public Product deleteOne(int id) {
        Product product = repository.findById(String.valueOf(id)).orElse(null);
        if (product == null)
            return null;
        repository.deleteById(String.valueOf(id));
        return product;
    }
}
