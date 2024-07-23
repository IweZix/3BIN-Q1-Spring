package be.vinci.gateway;

import be.vinci.gateway.exceptions.ConflictException;
import be.vinci.gateway.models.UserWithCredentials;
import be.vinci.gateway.models.Wishlist;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }

    @PostMapping("/users/{pseudo}")
    public ResponseEntity<Objects> createUser(@PathVariable String pseudo, @RequestBody UserWithCredentials user) {
        if (!user.getPseudo().equals(pseudo)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            service.createUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/users/{pseudo}")
    public ResponseEntity<Void> deleteUser(@PathVariable String pseudo, @RequestHeader("Authorization") String token) {
        String user = service.verify(token);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (!Objects.equals(user, pseudo)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        boolean found = service.deleteUser(pseudo);
        if (!found) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/wishlist/{pseudo}/{productId}")
    public ResponseEntity<Void> createReview(@PathVariable String pseudo,
                                             @PathVariable int productId,
                                             @RequestBody Wishlist wishlist,
                                             @RequestHeader("Authorization") String token) {
        if (!wishlist.getPseudo().equals(pseudo) || wishlist.getProductId() != productId) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String user = service.verify(token);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            service.createWishlist(wishlist);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
