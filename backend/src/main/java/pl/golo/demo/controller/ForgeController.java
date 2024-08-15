package pl.golo.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.model.user.Language;
import pl.golo.demo.repository.ForgeRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ForgeController {

    private final ForgeRepository repository;

    public ForgeController(ForgeRepository repository) {
        this.repository = repository;
    }
    //    Apprentice
    @GetMapping("/user/all")
    public List<Apprentice> getAllUsers() {
        return repository.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public Apprentice getUserById(@PathVariable Long id) {
        return repository.getUserById(id);
    }
//    Language

    @GetMapping("/language/all")
    public List<Language> getAllLanguages() {
        return repository.getAllLanguages();
    }

    @GetMapping("/language/{id}")
    public Language getLanguageById(@PathVariable Long id) {
        return repository.getLanguageById(id);
    }
//    Questions

    @GetMapping("/question/all")
    public List<Apprentice> getAllQuestions() {
        return null;
    }

    @GetMapping("/question/{id}")
    public Apprentice getQuestionById(@PathVariable Long id) {
        return null;
    }
//    Exercises

    @GetMapping("/exercise/all")
    public List<Apprentice> getAllExercises() {
        return null;
    }

    @GetMapping("/exercise/{id}")
    public Apprentice getExerciseById(@PathVariable Long id) {
        return null;
    }
}
