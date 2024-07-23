package be.vinci.gateway.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "products", url = "http://localhost:9001")
public interface ProductsProxy {

    @GetMapping("/products/id/{id}")
    ResponseEntity<Object> getProductById(@PathVariable int id);
}
