package pl.golo.demo;

import pl.golo.demo.model.exercises.Exercise;
import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.repository.ForgeRepository;

import java.util.List;

public class manage {
    public static void main(String[] args) {
        ForgeRepository repository = new ForgeRepository();
//        List<Apprentice> records = repository.getAllUsers();
//        System.out.println(records);
        List<Exercise> exercises = repository.getAllExercises("Quiz");
        System.out.println(exercises);
    }
}
