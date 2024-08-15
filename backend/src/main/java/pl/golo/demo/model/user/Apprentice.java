package pl.golo.demo.model.user;


import lombok.Getter;
import lombok.Setter;
import org.postgresql.core.Utils;
import pl.golo.demo.service.managment.QueriesUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of application user ( Apprentice because of Application Name)
 *
 * @param apprenticeId Unique user identifier
 * @param username     Unique user nickname with which we refer to the user
 * @param password     Hashed password with special security authentication
 * @param email        Unique user email to which we send private information
 * @param languages    List of related languages with current apprentice
 */
@Getter
@Setter
public class Apprentice {

    private Long apprenticeId;
    private String username;
    private String password;
    private String email;
//    private List<Language> languages;

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
        QueriesUtils utils = new QueriesUtils();
        return  utils.receiveQueryString(  this);
    }
}
