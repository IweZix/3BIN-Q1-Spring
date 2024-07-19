package be.vinci.amazingproductservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductsRepository class.
 * extends CrudRepository<Product, String>
 *     CRUD = Create, Read, Update, Delete
 */
@Repository
public interface ProductsRepository extends CrudRepository<Product, String> {

    Iterable<Product> findByPriceLessThan(int price);

    Iterable<Product> findByCategory(String category);

    Iterable<Product> findByNameContainingIgnoreCase(String name);


}
