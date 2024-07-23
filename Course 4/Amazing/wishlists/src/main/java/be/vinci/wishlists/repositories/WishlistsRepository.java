package be.vinci.wishlists.repositories;

import be.vinci.wishlists.models.Wishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * WishlistsRepository class.
 */
@Repository
public interface WishlistsRepository extends CrudRepository<Wishlist, String> {

    /**
     * Find all wishlists by pseudo
     * @param pseudo the pseudo to compare
     * @return an iterable of all wishlists
     */
    Iterable<Wishlist> findAllByPseudo(String pseudo);

    /**
     * Find all wishlists by productId
     * @param productId the productId to compare
     * @return an iterable of all wishlists
     */
    Iterable<Wishlist> findAllByProductId(int productId);

    /**
     * Find a wishlist by pseudo and productId
     * @param pseudo the pseudo to compare
     * @param productId the productId to compare
     * @return the wishlist
     */
    Wishlist findByPseudoAndProductId(String pseudo, int productId);

    /**
     * Check if a wishlist exists by pseudo and productId
     * @param pseudo the pseudo to compare
     * @param productId the productId to compare
     * @return true if exists, false otherwise
     */
    boolean existsByPseudoAndProductId(String pseudo, int productId);


}
