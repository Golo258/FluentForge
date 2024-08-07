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
    public Question() {
    }


    public Question(Long questionId, String questionContent,String[] possibleResponses, String chosenResponse) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.possibleResponses = possibleResponses;
        this.chosenResponse = chosenResponse;
    }
}
