package pl.golo.demo.repository;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import pl.golo.demo.model.exercises.*;
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
            if (object.getApprenticeId().equals(id)) {
                return object;
            }
        }
        return null;
    }

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
            if (object.getLanguageId().equals(id)) {
                return object;
            }
        }
        return null;
    }
//    Questions

    public List<Question> getAllQuestions() {
        List<Object> records = this.service.runSelectQuery("question", new Question(), "all");
        List<Question> questions = this.castObjectListToIstances(records);
        if (questions.isEmpty()) {
            return List.of(new Question());
        } else {
            return questions;
        }
    }

    public Question getQuestionById(@PathVariable Long id) {
        List<Question> results = this.getAllQuestions();
        for (Question object : results) {
            if (object.getQuestionId().equals(id)) {
                return object;
            }
        }
        return null;
    }
//    Exercises

    public List<Exercise> getAllExercises(String type) {
        List<Object> records = null;
        if (type.contains("Quiz")){
            records = this.service.runSelectQuery("exercise", new Quiz(), "all");
        }
        else if (type.contains("Flashcard")){
            records = this.service.runSelectQuery("exercise", new Flashcard(), "all");
        }
        else if (type.contains("Test")){
            records = this.service.runSelectQuery("exercise", new KnowledgeTest(), "all");
        }
        assert records != null;
        List<Exercise> exercises = this.castObjectListToIstances(records);
        if (exercises.isEmpty()) {
            return List.of(new Quiz());
        } else {
            return exercises;
        }
    }

    public Exercise getExerciseById(@PathVariable Long id, @PathVariable String type) {
        List<Exercise> results = this.getAllExercises(type);
        for (Exercise object : results) {
            if (object.getExerciseId().equals(id)) {
                return object;
            }
        }
        return null;
    }

    public <T> List<T> castObjectListToIstances(List<Object> objectList) {
        return objectList.stream()
                .map(obj -> (T) obj)
                .collect(Collectors.toList());
    }
}
