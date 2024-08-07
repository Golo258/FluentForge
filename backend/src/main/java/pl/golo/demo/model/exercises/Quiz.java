package pl.golo.demo.model.exercises;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.model.Question;

import java.util.List;

/**
 * Quiz class with original way of testing user knowledge (extends from Exercise class)
 * The given class will have many questions with 4 possible responses (ABCD)
 *
 * @param rules List of additional rules for given quiz
 */
@Getter
@Setter
public class Quiz extends Exercise {

    private String[] rules;

    public Quiz(Long exerciseId, String title, List<Question> questions, Long apprenticeId, Integer[] pagination , String[] rules) {
        super(exerciseId, title, questions, apprenticeId, pagination);
        this.rules = rules;
    }

    @Override
    public String getUserResponses() {
        return null;
    }

    @Override
    public void loadAllResponses() {

    }
}
