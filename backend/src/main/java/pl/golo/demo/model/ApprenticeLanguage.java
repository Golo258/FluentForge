package pl.golo.demo.model;

import lombok.Getter;
import lombok.Setter;

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
}