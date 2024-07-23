package be.vinci.authentification;

import be.vinci.authentification.models.SafeCredentials;
import be.vinci.authentification.models.UnsafeCredentials;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationRepository repository;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier jwtVerifier;

    public AuthenticationService(AuthenticationRepository repository, AuthenticationProperties properties) {
        this.repository = repository;
        this.jwtAlgorithm = Algorithm.HMAC512(properties.getSecret());
        this.jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();
    }

    /**
     * Creates credentials in repository
     * @param unsafeCredentials The credentials with insecure password
     * @return True if the credentials were created, or false if they already exist
     */
    public boolean createOne(UnsafeCredentials unsafeCredentials) {
        if (repository.existsById(unsafeCredentials.getPseudo())) return false;
        String hashedPassword = BCrypt.hashpw(unsafeCredentials.getPassword(), BCrypt.gensalt());
        repository.save(unsafeCredentials.makeSafe(hashedPassword));
        return true;
    }

    /**
     * Connects user with credentials
     * @param unsafeCredentials The credentials with insecure password
     * @return The JWT token, or null if the user couldn't be connected
     */
    public String connect(UnsafeCredentials unsafeCredentials) {
        SafeCredentials safeCredentials = repository.findById(unsafeCredentials.getPseudo()).orElse(null);
        if (safeCredentials == null) return null;
        if (!BCrypt.checkpw(unsafeCredentials.getPassword(), safeCredentials.getHashedPassword())) return null;
        return JWT.create().withIssuer("auth0").withClaim("pseudo", safeCredentials.getPseudo()).sign(jwtAlgorithm);
    }

    /**
     * Verifies JWT token
     * @param token The JWT token
     * @return The pseudo of the user, or null if the token couldn't be verified
     */
    public String verify(String token) {
        try {
            String pseudo = jwtVerifier.verify(token).getClaim("pseudo").asString();
            if (!repository.existsById(pseudo)) return null;
            return pseudo;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public String deleteOne(String pseudo) {
        repository.deleteById(pseudo);
        return pseudo;
    }
}
