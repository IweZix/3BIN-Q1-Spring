package be.vinci.amazingproductservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * Product class.
 */
@Getter // Lombok annotation to generate all getters
@Setter // Lombok annotation to generate all setters
@ToString // Lombok annotation to generate toString method
@AllArgsConstructor // Lombok annotation to generate constructor with all arguments
public class Product {

    private String name, category;
    private int id, price;

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
