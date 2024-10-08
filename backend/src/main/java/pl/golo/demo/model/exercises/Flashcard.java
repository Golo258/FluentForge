package pl.golo.demo.model.exercises;

/**
 * Flashcard class with original way of testing user knowledge (extends from Exercise class)
 * The given class will have many possible ways of responding to questions:
 *  a) with 4 possible responses (ABCD)
 *  b) with 2 possibilites (good, wrong)
 *  c) with only one with blank field needed to complete
 */
public class Flashcard extends Exercise{


    public Flashcard(Long exerciseId, String title, Long apprenticeId, Integer[] pagination) {
        super(exerciseId, title, apprenticeId, pagination);
    }
    public Flashcard() {

    }
    public String getUserResponses() {
        return null;
    }

    public void loadAllResponses() {

    }
}
