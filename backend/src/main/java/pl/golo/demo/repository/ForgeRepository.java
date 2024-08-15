package pl.golo.demo.repository;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.model.user.Language;
import pl.golo.demo.service.ForgeService;
import pl.golo.demo.service.managment.QueriesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ForgeRepository {

    ForgeService service;

    public ForgeRepository() {
        this.service = new ForgeService(
                true, new QueriesUtils()
        );
        this.service.connect();
    }

    public List<Apprentice> getAllUsers() {
        List<Object> records = this.service.
                runSelectQuery("apprentice", new Apprentice(), "all");
        List<Apprentice> apprentices = castObjectListToIstances(records);
        if (apprentices.isEmpty()) {
            return List.of(new Apprentice());
        } else {
            return apprentices;
        }
    }

    public Apprentice getUserById(@PathVariable Long id) {
        List<Apprentice> results = this.getAllUsers();
        for (Apprentice object : results) {
            try {
                if (object.getApprenticeId().equals(id)) {
                    return object;
                }
                return null;
            } catch (Exception exception) {
                System.out.println(exception.getCause());
            }
        }
        return null;
    }
//    Language

    public List<Language> getAllLanguages() {
        List<Object> records = this.service.runSelectQuery("language", new Language(), "all");
        List<Language> languages = this.castObjectListToIstances(records);
        if (languages.isEmpty()) {
            return List.of(new Language());
        } else {
            return languages;
        }
    }

    public Language getLanguageById(@PathVariable Long id) {
        List<Language> results = this.getAllLanguages();
        for (Language object : results) {
            try {
                if (object.getLanguageId().equals(id)) {
                    return object;
                }
                return null;
            } catch (Exception exception) {
                System.out.println(exception.getCause());
            }
        }
        return null;
    }
//    Questions

    public List<Apprentice> getAllQuestions() {
        return null;
    }

    public Apprentice getQuestionById(@PathVariable Long id) {
        return null;
    }
//    Exercises

    public List<Apprentice> getAllExercises() {
        return null;
    }

    public Apprentice getExerciseById(@PathVariable Long id) {
        return null;
    }

    public <T> List<T> castObjectListToIstances(List<Object> objectList) {
        return objectList.stream()
                .map(obj -> (T) obj)
                .collect(Collectors.toList());
    }
}
