package be.vinci.gateway.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {

    private int id;
    private String pseudo;
    private int productId;

    public boolean invalid() {
        return pseudo == null || pseudo.isBlank() ||
                productId <= 0;
    }
}
