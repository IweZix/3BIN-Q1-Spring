package be.vinci.gateway.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Repository
@FeignClient(name = "wishlists", url = "http://localhost:9003")
public interface WishlistsProxy {

    @DeleteMapping("/wishlists/user/{pseudo}")
    void deleteWishlistFromUser(@PathVariable String pseudo);

    @PutMapping("/wishlists/{pseudo}/{productId}")
    ResponseEntity<Object> addWishList(@PathVariable String pseudo, @PathVariable int productId);
}
