package be.vinci.wishlists;

import be.vinci.wishlists.models.Product;
import be.vinci.wishlists.models.Wishlist;
import be.vinci.wishlists.repositories.ProductsProxy;
import be.vinci.wishlists.repositories.UsersProxy;
import be.vinci.wishlists.repositories.WishlistsRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

/**
 * WishlistsService class.
 */
@Service
public class WishlistsService {

    private final WishlistsRepository repository;
    private final ProductsProxy productsProxy;
    private final UsersProxy usersProxy;

    public WishlistsService(WishlistsRepository repository, ProductsProxy productsProxy, UsersProxy usersProxy) {
        this.repository = repository;
        this.productsProxy = productsProxy;
        this.usersProxy = usersProxy;
    }

    /**
     * Get all wishlists of user
     * @param pseudo the pseudo to compare
     * @return an iterable of all wishlists
     */
    public Iterable<Wishlist> getWishlist(String pseudo) {
        try {
            usersProxy.readOne(pseudo);
        } catch (FeignException.NotFound e) {
            return null;
        }
        return repository.findAllByPseudo(pseudo);
    }

    /**
     * Put a wishlists for user
     * @param pseudo the pseudo to compare
     * @param productId the productId to compare
     * @return null: if already exists, product doesn't exist, user doesn't exist
     *         the product: if it was added
     */
    public Wishlist putWishlist(String pseudo, int productId) {
        if (repository.existsByPseudoAndProductId(pseudo, productId)) {
            return null;
        }

        try {
            productsProxy.readOne(productId);
            usersProxy.readOne(pseudo);
        } catch (FeignException.NotFound e) {
            return null;
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setPseudo(pseudo);
        wishlist.setProductId(productId);

        repository.save(wishlist);

        return wishlist;
    }

    /**
     * Delete a wishlist
     * @param pseudo the pseudo to compare
     * @param productId the productId to compare
     * @return null: if doesn't exist
     *         the product: if it was deleted
     */
    public Wishlist deleteAllWishlistOf(String pseudo, int productId) {
        Wishlist wishlist = repository.findByPseudoAndProductId(pseudo, productId);
        if (wishlist == null) {
            return null;
        }

        repository.delete(wishlist);

        return wishlist;
    }

    /**
     * Delete all wishlists of user
     * @param pseudo the pseudo to compare
     * @return null: if doesn't exist
     *         the product: if it was deleted
     */
    public Iterable<Wishlist> deleteAllWishlistOf(String pseudo) {
        Iterable<Wishlist> wishlistIterable = repository.findAllByPseudo(pseudo);
        if (wishlistIterable == null) {
            return null;
        }

        repository.deleteAll(wishlistIterable);

        return wishlistIterable;
    }

    /**
     * Delete all wishlists containing a product
     * @param productId the productId to compare
     * @return null: if doesn't exist
     *         the product: if it was deleted
     */
    public Iterable<Wishlist> deleteAllWishlistContaining(int productId) {
        Iterable<Wishlist> wishlistIterable = repository.findAllByProductId(productId);
        if (wishlistIterable == null) {
            return null;
        }

        repository.deleteAll(wishlistIterable);

        return wishlistIterable;
    }

}
