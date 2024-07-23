package be.vinci.wishlists.repositories;

import be.vinci.wishlists.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "users", url = "http://localhost:9002")
public interface UsersProxy {

    @GetMapping("/users/{pseudo}")
    User readOne(@PathVariable String pseudo);
}
