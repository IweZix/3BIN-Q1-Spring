package be.vinci.amazingproductservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductsController {

    private final ProductsService service;

    public ProductsController(ProductsService service) {
        this.service = service;
    }

    /**
     * Get all products.
     * @request GET /products
     * @return List of products.
     */
    @GetMapping("/products")
    public Iterable<Product> getProducts() {
        return service.readAll();
    }

    /**
     * Get all products under x price
     * @param price Price to compare
     * @request GET /products/underprice/{price}
     * @return 404: Price is invalid.
     *         200: List of products under price.
     */
    @GetMapping("/products/underprice/{price}")
    public ResponseEntity<Object> getProductByPriceLessThan(@PathVariable int price) {
        if (price <= -1) {
            return new ResponseEntity<>("This price is invalid.", HttpStatus.BAD_REQUEST);
        }

        Iterable<Product> returned = service.findByPriceLessThan(price);

        return new ResponseEntity<>(returned, HttpStatus.OK);
    }

    /**
     * Get all products in a category
     * @param category Category to compare
     * @request GET /products/category/{category}
     * @return 404: Category is invalid.
     *         200: List of products in category.
     */
    @GetMapping("/products/category/{category}")
    public ResponseEntity<Object> getProductByCategory(@PathVariable String category) {
        if (category.isBlank()) {
            return new ResponseEntity<>("This category is invalid.", HttpStatus.BAD_REQUEST);
        }

        Iterable<Product> returned = service.findByCategory(category);

        return new ResponseEntity<>(returned, HttpStatus.OK);
    }

    /**
     * Get all products where name containing name
     * @param name Name to compare
     * @request GET /products/name/{name}
     * @return 404: Name is invalid.
     *         200: List of products where name containing name.
     */
    @GetMapping("/products/name/{name}")
    public ResponseEntity<Object> getProductByName(@PathVariable String name) {
        if (name.isBlank()) {
            return new ResponseEntity<>("This name is invalid.", HttpStatus.BAD_REQUEST);
        }

        Iterable<Product> returned = service.findByNameContainingIgnoreCase(name);

        return new ResponseEntity<>(returned, HttpStatus.OK);
    }

    /**
     * Get a product by id
     * @param id id to compare
     * @request GET /products/id/{id}
     * @return 404: id is invalid
     *         200: product has the id
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable int id) {
        if (id <= -1) {
            return new ResponseEntity<>("This id is invalid", HttpStatus.BAD_REQUEST);
        }

        Product product = service.findById(id);

        if (product == null) {
            return new ResponseEntity<>("This doesn't exists", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Post a product
     * @param product the product to add
     * @request POST /products
     * @return 404: id is invalid, higherId is invalid
     *         403: product already exists
     *         200: product added
     */
    @PostMapping("/products")
    public ResponseEntity<Object> postProduct(@RequestBody Product product) {
        if (product.invalid()) {
            return new ResponseEntity<>("This product is invalid", HttpStatus.BAD_REQUEST);
        }

        Product created = service.createOne(product);
        if (created == null) return new ResponseEntity<>(HttpStatus.CONFLICT);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    /**
     * Put a product
     * @param product the product to put
     * @request PUT /products/{id}
     * @return 404: product not found
     *         200: product put
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> putProduct(@RequestBody Product product) {
        Product updated = service.updateOne(product);

        if (updated == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Delete a product by id
     * @param id id to compare
     * @request DELETE /products/{id}
     * @return 404: id is invalid
     *         200: product has the id
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable int id) {
        if (id <= -1) {
            return new ResponseEntity<>("This id is invalid", HttpStatus.BAD_REQUEST);
        }

        Product deleted = service.deleteOne(id);

        if (deleted == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }


}
