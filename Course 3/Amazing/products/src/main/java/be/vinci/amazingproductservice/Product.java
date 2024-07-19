package be.vinci.amazingproductservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

/**
 * Product class.
 */
@Getter // Lombok annotation to generate all getters
@Setter // Lombok annotation to generate all setters
@ToString // Lombok annotation to generate toString method
@NoArgsConstructor // JPA need
@Entity(name= "products") // create table products
public class Product {

    @Id // define PK
    @Column(nullable = false)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private int price;

    /**
     * Check if the product is invalid.
     * @return True if the product is invalid, false otherwise.
     */
    public boolean invalid() {
        return name == null || name.isBlank() ||
                category == null || category.isBlank() ||
                price <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                price == product.price &&
                Objects.equals(name, product.name) &&
                Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, id, price);
    }
}
