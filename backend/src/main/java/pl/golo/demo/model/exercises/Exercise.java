package pl.golo.demo.model.exercises;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.model.Question;

import java.util.List;

/**
 * Abstract class which represent simple exercise which user has to complete
 *
 * @param exerciseId   Unique identifier of exercise
 * @param title        Exercise title with which we can recognize given exercise
 * @param questions    List of questions created for apprentice
 * @param apprenticeId Foreign Key to apprentice
 * @param pagination   List which represent site pagination.
 *                     Number of elements depends on amount of created exercises
 */
@Getter
@Setter
public abstract class Exercise {
    private Long exerciseId;
    private String title;
    private Long apprenticeId; // foreign key one-to-many(one-user many exercises
    private Integer[] pagination;

    public Exercise() {
    }

    public Exercise(Long exerciseId, String title, Long apprenticeId, Integer[] pagination) {
        this.exerciseId = exerciseId;
        this.title = title;
        this.apprenticeId = apprenticeId;
        this.pagination = pagination;
    }

    public abstract String getUserResponses();

    public abstract void loadAllResponses();
}
