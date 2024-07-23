package be.vinci.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String pseudo;
    private String firstname;
    private String lastname;

    public boolean invalid() {
        return pseudo == null || pseudo.isBlank() ||
                firstname == null || firstname.isBlank() ||
                lastname == null || lastname.isBlank();
    }
}