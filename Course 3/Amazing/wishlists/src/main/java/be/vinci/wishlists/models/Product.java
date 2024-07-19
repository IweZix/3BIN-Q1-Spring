package be.vinci.wishlists.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {

    private int id;
    private String name;
    private String category;
    private int price;
}
