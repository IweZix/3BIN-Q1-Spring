package be.vinci.amazingproductservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private static final List<Product> products = new ArrayList<>();

    static {
        products.add(new Product("MacBook Pro M1 Pro", "Computer", 1, 2899));
        products.add(new Product("MacBook", "Computer", 2, 1199));
        products.add(new Product("iPhone 15 Pro Max", "Phone", 3, 1499));
        products.add(new Product("iPhone 15", "Phone", 4, 999));
        products.add(new Product("iPad Pro", "Tablet", 5, 799));
        products.add(new Product("iPad", "Tablet", 6, 329));
        products.add(new Product("Apple Watch Series 7", "Watch", 7, 399));
        products.add(new Product("Apple Watch SE", "Watch", 8, 279));
        products.add(new Product("AirPods Pro", "Headphones", 9, 249));
    }

    /**
     * Get all products.
     * @request GET /products
     * @return List of products.
     */
    @GetMapping("/products")
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Get all products under x price
     * @param price Price to compare
     * @request GET /products/underprice/{price}
     * @return 404: Price is invalid.
     *         200: List of products under price.
     */
    @GetMapping("/products/underprice/{price}")
    public ResponseEntity<Object> getProductsUnderPrice(@PathVariable int price) {
        if (price <= 0) {
            return new ResponseEntity<>("This price is invalid", HttpStatus.BAD_REQUEST);
        }

        List<Product> returned = products.stream()
                .filter(p -> p.getPrice() < price)
                .toList();

        return new ResponseEntity<>(returned, HttpStatus.OK);
    }

    /**
     * Get all products in a category
     * @param category Category to compare
     * @request GET /products/category/{category}
     * @return 404: Category is invalid
     *         200: List of products in the category
     */
    @GetMapping("/products/category/{category}")
    public ResponseEntity<Object> getProductInACategory(@PathVariable String category) {
        if (category == null || category.isBlank()) {
            return new ResponseEntity<>("This category is invalid", HttpStatus.BAD_REQUEST);
        }

        List<Product> returned = products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .toList();

        return new ResponseEntity<>(returned, HttpStatus.OK);
    }

    /**
     * Get all products where name containing name
     * @param string name to compare
     * @request GET /products/category/{category}
     * @return 404: name is invalid
     *         200: List of products where name containing string
     */
    @GetMapping("/products/name/{string}")
    public ResponseEntity<Object> getProductByString(@PathVariable String string) {
        if (string == null || string.isBlank()) {
            return new ResponseEntity<>("This string is invalid", HttpStatus.BAD_REQUEST);
        }

        List<Product> returned = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(string.toLowerCase()))
                .toList();

        return new ResponseEntity<>(returned, HttpStatus.OK);
    }

    /**
     * Get a product by id
     * @param id id to compare
     * @request GET /products/id/{id}
     * @return 404: id is invalid
     *         200: product has the id
     */
    @GetMapping("/products/id/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable int id) {
        if (id <= -1) {
            return new ResponseEntity<>("This id is invalid", HttpStatus.BAD_REQUEST);
        }

        Product returned = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        return new ResponseEntity<>(returned, HttpStatus.OK);
    }

    /**
     * Get a product by id
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

        Product returned = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (returned == null) {
            return new ResponseEntity<>("This id doesn't exists in the list", HttpStatus.BAD_REQUEST);
        }

        products.remove(returned);

        return new ResponseEntity<>(returned, HttpStatus.OK);
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

        if (products.stream().anyMatch(p -> p.equals(product))) {
            return new ResponseEntity<>("This product already exists", HttpStatus.CONFLICT);
        }

        int higherId = products.stream()
                .map(Product::getId)
                .reduce(Integer::max)
                .orElse(-1);

        if (higherId == -1) {
            return new ResponseEntity<>("A problem is detected", HttpStatus.BAD_REQUEST);
        }

        product.setId(higherId+1);
        products.add(product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Put a product
     * @param product the product to put
     * @request PUT /products/{id}
     * @return 404: product not found
     *         200: product put
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> putProduct(@PathVariable int id, @RequestBody Product product) {
        Product toUpdate = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (toUpdate == null) {
            return new ResponseEntity<>("This product doesn't exists", HttpStatus.BAD_REQUEST);
        }

        if (!toUpdate.getName().equals(product.getName())
                && product.getName() != null
                && !product.getName().isBlank()) {
            toUpdate.setName(product.getName());
        }

        if (!toUpdate.getCategory().equals(product.getCategory())
                && product.getCategory() != null
                && !product.getCategory().isBlank()) {
            toUpdate.setName(product.getCategory());
        }

        if (toUpdate.getPrice() != product.getPrice()
                && product.getPrice() >= 1) {
            toUpdate.setPrice(product.getPrice());
        }

        return new ResponseEntity<>(toUpdate, HttpStatus.OK);
    }
}
