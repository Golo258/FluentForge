package pl.golo.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
public class Question {

    private Long questionId;
    private String questionContent;
    private String[] possibleResponses;
    private String chosenResponse;
    private Long exerciseId;
    public Question() {
    }

    public Question(Long questionId, String questionContent, String[] possibleResponses, String chosenResponse, Long exerciseId) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.possibleResponses = possibleResponses;
        this.chosenResponse = chosenResponse;
        this.exerciseId = exerciseId;
    }
}
