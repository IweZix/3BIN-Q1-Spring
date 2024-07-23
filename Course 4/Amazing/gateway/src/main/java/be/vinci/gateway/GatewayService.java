package be.vinci.gateway;

import be.vinci.gateway.data.AuthenticationProxy;
import be.vinci.gateway.data.ProductsProxy;
import be.vinci.gateway.data.UsersProxy;
import be.vinci.gateway.data.WishlistsProxy;
import be.vinci.gateway.exceptions.ConflictException;
import be.vinci.gateway.models.User;
import be.vinci.gateway.models.UserWithCredentials;
import be.vinci.gateway.models.Wishlist;
import feign.FeignException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

    private final UsersProxy usersProxy;
    private final AuthenticationProxy authenticationProxy;
    private final WishlistsProxy wishlistsProxy;
    private final ProductsProxy productsProxy;

    public GatewayService(UsersProxy usersProxy, AuthenticationProxy authenticationProxy, WishlistsProxy wishlistsProxy, ProductsProxy productsProxy) {
        this.usersProxy = usersProxy;
        this.authenticationProxy = authenticationProxy;
        this.wishlistsProxy = wishlistsProxy;
        this.productsProxy = productsProxy;
    }

    public void createUser(UserWithCredentials user) throws BadRequestException, ConflictException {
        try {
            usersProxy.createUser(user.getPseudo(), user.toUser());
        } catch (FeignException e) {
            if (e.status() == 400) {
                throw new BadRequestException();
            }
            else if (e.status() == 409) {
                throw new ConflictException();
            } else {
                throw e;
            }
        }

        try {
            authenticationProxy.createCredentials(user.getPseudo(), user.toCredentials());
        } catch (FeignException e) {
            try {
                usersProxy.deleteUser(user.getPseudo());
            } catch (FeignException ignored) {

            }
            if (e.status() == 400) {
                throw new BadRequestException();
            } else if (e.status() == 409) {
                throw new ConflictException();
            } else {
                throw e;
            }
        }
    }

    public boolean deleteUser(String pseudo) {
        wishlistsProxy.deleteWishlistFromUser(pseudo);
        boolean found = true;
        try {
            authenticationProxy.deleteCredentials(pseudo);
        } catch (FeignException e) {
            if (e.status() == 404) found = false;
            else throw e;
        }
        try {
            usersProxy.deleteUser(pseudo);
        } catch (FeignException e) {
            if (e.status() == 404) found = false;
            else throw e;
        }
        return found;
    }

    public String verify(String token) {
        return authenticationProxy.verifyOne(token);
    }

    public void createWishlist(Wishlist wishlist) throws ConflictException {
        try {
            usersProxy.readOne(wishlist.getPseudo());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new BadRequestException();
            } else {
                throw e;
            }
        }

        try {
            productsProxy.getProductById(wishlist.getProductId());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new NotFoundException();
            } else {
                throw e;
            }
        }

        try {
            wishlistsProxy.addWishList(wishlist.getPseudo(), wishlist.getProductId());
        } catch (FeignException e) {
            if (e.status() == 400) {
                throw new BadRequestException();
            } else if (e.status() == 409) {
                throw new ConflictException();
            } else {
                throw e;
            }
        }
    }


}
