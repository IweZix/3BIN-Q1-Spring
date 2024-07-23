package be.vinci.gateway.data;

import be.vinci.gateway.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "users", url = "http://localhost:9002")
public interface UsersProxy {

    @PostMapping("/users/{pseudo}")
    void createUser(@PathVariable String pseudo, @RequestBody User user);

    @DeleteMapping("/users/{pseudo}")
    void deleteUser(@PathVariable String pseudo);

    @GetMapping("/users/{pseudo}")
    ResponseEntity<User> readOne(@PathVariable String pseudo);

}
