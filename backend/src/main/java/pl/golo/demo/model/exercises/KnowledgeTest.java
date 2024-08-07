package pl.golo.demo.model.exercises;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.model.Question;

import java.util.List;
/**
 * KnowledgeTest class with original way of testing user knowledge (extends from Exercise class)
 * The given class will have many possible ways of responding to questions:
 *  a) with 4 possible responses (ABCD)
 *  b) with 2 possibilites (True, False)
 *  c) with only one with blank field needed to complete
 *  d) writing text to open question
 *
 * @param testId   Unique identifier of flashcard
 */

@Getter
@Setter
public class KnowledgeTest extends Exercise{

    private Long testId;

    public KnowledgeTest(Long exerciseId, String title, List<Question> questions, Long apprenticeId, Integer[] pagination, Long testId) {
        super(exerciseId, title, questions, apprenticeId, pagination);
        this.testId = testId;
    }

    @Override
    public String getUserResponses() {
        return null;
    }

    @Override
    public void loadAllResponses() {

    }
}
