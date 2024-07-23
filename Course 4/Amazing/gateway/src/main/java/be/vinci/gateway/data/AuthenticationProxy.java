package be.vinci.gateway.data;

import be.vinci.gateway.models.Credentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "authentication", url = "http://localhost:9004")
public interface AuthenticationProxy {

    @PostMapping("/authentication/{pseudo}")
    void createCredentials(@PathVariable String pseudo, @RequestBody Credentials credentials);

    @DeleteMapping("/authentication/{pseudo}")
    void deleteCredentials(@PathVariable String pseudo);

    @PostMapping("/authentication/verify")
    String verifyOne(@RequestBody String token);
}
