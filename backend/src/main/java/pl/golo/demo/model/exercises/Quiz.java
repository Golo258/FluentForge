package pl.golo.demo.model.exercises;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.model.Question;

import java.util.List;

/**
 * Quiz class with original way of testing user knowledge (extends from Exercise class)
 * The given class will have many questions with 4 possible responses (ABCD)
 *
 * @param quizId Unique identifier of quiz
 * @param rules  List of additional rules for given quiz
 */
@Getter
@Setter
public class Quiz extends Exercise {

    private Long quizId;
    private String[] rules;

    public Quiz(Long exerciseId, String title, List<Question> questions, Long apprenticeId, Integer[] pagination, Long quizId, String[] rules) {
        super(exerciseId, title, questions, apprenticeId, pagination);
        this.quizId = quizId;
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
