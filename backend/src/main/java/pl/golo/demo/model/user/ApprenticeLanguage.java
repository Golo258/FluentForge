package pl.golo.demo.model.user;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.service.managment.QueriesUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of connection class between Apprentice and LAnguage
 *
 * @param apprenticeId Foreign key to Apprentice
 * @param languageId   Foreign key to Language
 **/

@Getter
@Setter
public class ApprenticeLanguage {
    private Long apprenticeId;
    private Long languageId;
    @Override
    public String toString() {
        QueriesUtils utils = new QueriesUtils();
        return  utils.receiveQueryString(  this);
    }
}