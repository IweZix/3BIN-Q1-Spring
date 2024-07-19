package be.vinci.wishlists.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "wishlist")
public class Wishlist {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String pseudo;
    @Column(nullable = false, name = "product_id")
    private int productId;

    public boolean invalid() {
        return pseudo == null || pseudo.isBlank() ||
                productId <= 0;
    }
}
