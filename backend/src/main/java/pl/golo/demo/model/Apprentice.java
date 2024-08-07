package pl.golo.demo.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Representation of application user ( Apprentice because of Application Name)
 *
 * @param apprenticeId       Unique user identifier
 * @param username Unique user nickname with which we refer to the user
 * @param password Hashed password with special security authentication
 * @param email    Unique user email to which we send private information
 * @param languages  List of related languages with current apprentice
 */
@Getter
@Setter
public class Apprentice {

    private Long apprenticeId;
    private String username;
    private String password;
    private String email;
    private List<Language> languages;

    public Apprentice() {
    }

    public Apprentice(Long apprenticeId, String username, String password, String email) {
        this.apprenticeId = apprenticeId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Apprentice ( " + this.getUsername() + ", " + this.getEmail() + ") ";
    }
}
