package pl.golo.demo.model.user;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.service.managment.QueriesUtils;

import java.awt.font.NumericShaper;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of possible to chose languages by apprentice
 *
 * @param languageId          Unique identifier of language
 * @param name                Unique language name which user can choose as his course part
 * @param addionalInformation Information about language, like in which countries it is used, and nr of people using it
 * @param apprentices         List of related apprentices with language
 */


@Getter
@Setter
public class Language {
    private Long languageId;
    private String name;
    private String additionalInformation;
//    private List<Apprentice> apprentices;  relationships


    public Language() {
    }

    public Language(Long languageId, String name, String additionalInformation) {
        this.languageId = languageId;
        this.name = name;
        this.additionalInformation = additionalInformation;
    }

    @Override
    public String toString() {
        QueriesUtils utils = new QueriesUtils();
        return  utils.receiveQueryString(  this);
    }
}
