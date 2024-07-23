package be.vinci.wishlists;

import be.vinci.wishlists.models.Wishlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * WishlistsController class.
 */
@RestController
public class WishlistsController {

    private final WishlistsService service;

    public WishlistsController(WishlistsService service) {
        this.service = service;
    }

    /**
     * Get all wishlist of user
     * @param pseudo Pseudo to compare
     * @request GET /wishlists/user/{pseudo}
     * @return 400: Pseudo is invalid.
     *         200: List of products under price.
     */
    @GetMapping("/wishlists/user/{pseudo}")
    public ResponseEntity<Object> getWishList(@PathVariable String pseudo) {
        if (pseudo == null || pseudo.isBlank()) {
            return new ResponseEntity<>("Pseudo is invalid", HttpStatus.BAD_REQUEST);
        }

        Iterable<Wishlist> wishlistIterable = service.getWishlist(pseudo);
        if (wishlistIterable == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(wishlistIterable, HttpStatus.OK);
    }

    /**
     * Put a product in the wishlist of user
     * @param pseudo Pseudo to compare
     * @param productId Product id to compare
     * @request PUT /wishlists/{pseudo}/{productId}
     * @return 400: Pseudo or ProductId is invalid.
     *         409: Wishlist already exists.
     *         200: Wishlist updated.
     */
    @PutMapping("/wishlists/{pseudo}/{productId}")
    public ResponseEntity<Object> addWishList(@PathVariable String pseudo, @PathVariable int productId) {
        if (pseudo == null || pseudo.isBlank()) {
            return new ResponseEntity<>("Pseudo is invalid", HttpStatus.BAD_REQUEST);
        }
        if (productId <= 0) {
            return new ResponseEntity<>("ProductId is invalid", HttpStatus.BAD_REQUEST);
        }

        Wishlist updated = service.putWishlist(pseudo, productId);
        if (updated == null) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Delete a product in the wishlist of user
     * @param pseudo Pseudo to compare
     * @param productId Product id to compare
     * @request DELETE /wishlists/{pseudo}/{productId}
     * @return 400: Pseudo or ProductId is invalid.
     *         404: Wishlist does not exist.
     *         200: Wishlist deleted.
     */
    @DeleteMapping("/wishlists/{pseudo}/{productId}")
    public ResponseEntity<Object> deleteAWishList(@PathVariable String pseudo, @PathVariable int productId) {
        if (pseudo == null || pseudo.isBlank()) {
            return new ResponseEntity<>("Pseudo is invalid", HttpStatus.BAD_REQUEST);
        }
        if (productId <= 0) {
            return new ResponseEntity<>("productId is invalid", HttpStatus.BAD_REQUEST);
        }

        Wishlist deleted = service.deleteAllWishlistOf(pseudo, productId);
        if (deleted == null) {
            return new ResponseEntity<>("Wishlist does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    /**
     * Delete all wishlist of user
     * @param pseudo Pseudo to compare
     * @request DELETE /wishlists/user/{pseudo}
     * @return 400: Pseudo is invalid.
     *         200: All wishlist deleted.
     */
    @DeleteMapping("/wishlists/user/{pseudo}")
    public ResponseEntity<Object> deleteAllWishlistOfUser(@PathVariable String pseudo) {
        if (pseudo == null || pseudo.isBlank()) {
            return new ResponseEntity<>("Pseudo is invalid", HttpStatus.BAD_REQUEST);
        }

        Iterable<Wishlist> wishlistIterable = service.deleteAllWishlistOf(pseudo);

        return new ResponseEntity<>(wishlistIterable, HttpStatus.OK);
    }

    /**
     * Delete all wishlist containing the product
     * @param productId Product id to compare
     * @request DELETE /wishlists/product/{productId}
     * @return 400: ProductId is invalid.
     *         200: All wishlist deleted.
     */
    @DeleteMapping("/wishlists/product/{productId}")
    public ResponseEntity<Object> deleteAllWishlistContaining(@PathVariable int productId) {
        if (productId <= 0) {
            return new ResponseEntity<>("productId is invalid", HttpStatus.BAD_REQUEST);
        }

        Iterable<Wishlist> wishlistIterable = service.deleteAllWishlistContaining(productId);

        return new ResponseEntity<>(wishlistIterable, HttpStatus.OK);
    }

}
