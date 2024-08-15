package pl.golo.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.golo.demo.model.exercises.Exercise;
import pl.golo.demo.model.exercises.Question;
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
    public List<Question> getAllQuestions() {
        return repository.getAllQuestions();
    }

    @GetMapping("/question/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return repository.getQuestionById(id);
    }
//    Exercises

    @GetMapping("/exercise/all/{type}")
    public List<Exercise> getAllExercises(@PathVariable String type) {
        return repository.getAllExercises(type);
    }

    @GetMapping("/exercise/{id}")
    public Exercise getExerciseById(@PathVariable Long id, @PathVariable String type) {
        return repository.getExerciseById(id, type);
    }
}
