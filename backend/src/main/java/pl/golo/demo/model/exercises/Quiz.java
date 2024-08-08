package pl.golo.demo.model.exercises;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.model.Question;

import java.util.List;

/**
 * Quiz class with original way of testing user knowledge (extends from Exercise class)
 * The given class will have many questions with 4 possible responses (ABCD)
 *
 */
@Getter
@Setter
public class Quiz extends Exercise {

    public Quiz() {

    }

    public Quiz(Long exerciseId, String title, Long apprenticeId, Integer[] pagination) {
        super(exerciseId, title, apprenticeId, pagination);
    }

    @Override
    public String getUserResponses() {
        return null;
    }

    @Override
    public void loadAllResponses() {

    }
}
