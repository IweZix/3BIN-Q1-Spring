package be.vinci.wishlists.repositories;

import be.vinci.wishlists.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "products", url = "http://localhost:9001")
public interface ProductsProxy {

    @GetMapping("/products/id/{id}")
    Product readOne(@PathVariable int id);
}
