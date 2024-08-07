package pl.golo.demo.model.exercises;

import pl.golo.demo.model.Question;

import java.util.List;

/**
 * Flashcard class with original way of testing user knowledge (extends from Exercise class)
 * The given class will have many possible ways of responding to questions:
 *  a) with 4 possible responses (ABCD)
 *  b) with 2 possibilites (good, wrong)
 *  c) with only one with blank field needed to complete
 *
 * @param flashcardId   Unique identifier of flashcard
 */
public class Flashcard extends Exercise{

    private Long flashcardId;

    public Flashcard(Long exerciseId, String title, List<Question> questions, Long apprenticeId, Integer[] pagination, Long flashcardId) {
        super(exerciseId, title, questions, apprenticeId, pagination);
        this.flashcardId = flashcardId;
    }

    @Override
    public String getUserResponses() {
        return null;
    }

    @Override
    public void loadAllResponses() {

    }
}
