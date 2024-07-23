package be.vinci.authentification;

import be.vinci.authentification.models.UnsafeCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/authentication/{pseudo}")
    public ResponseEntity<Object> createOne(@PathVariable String pseudo, @RequestBody UnsafeCredentials unsafeCredentials) {
        if (!pseudo.equals(unsafeCredentials.getPseudo())) {
            return new ResponseEntity<>("Pseudo in path and body must be the same", HttpStatus.BAD_REQUEST);
        }
        if (unsafeCredentials.invalid()) {
            return new ResponseEntity<>("Pseudo and password must not be empty", HttpStatus.BAD_REQUEST);
        }

        boolean created = service.createOne(unsafeCredentials);
        if (!created) {
            return new ResponseEntity<>("Credentials already exist", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(unsafeCredentials.getPseudo(), HttpStatus.OK);
    }

    @DeleteMapping("/authentication/{pseudo}")
    public ResponseEntity<Object> deleteOne(@PathVariable String pseudo) {
        String deleted = service.deleteOne(pseudo);
        if (deleted == null) {
            return new ResponseEntity<>("Credentials does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @PostMapping("/authentication/connect")
    public ResponseEntity<Object> connectOne(@RequestBody UnsafeCredentials unsafeCredentials) {
        if (unsafeCredentials.invalid()) {
            return new ResponseEntity<>("Pseudo and password must not be empty", HttpStatus.BAD_REQUEST);
        }

        String token = service.connect(unsafeCredentials);
        if (token == null) {
            return new ResponseEntity<>("Pseudo or password is invalid", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/authentication/verify")
    public ResponseEntity<Object> verifyOne(@RequestBody String token) {
        String verified = service.verify(token);
        if (verified == null) {
            return new ResponseEntity<>("Pseudo or password is invalid", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(verified, HttpStatus.OK);
    }
}
