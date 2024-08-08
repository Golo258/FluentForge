package pl.golo.demo.model.creation;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.model.exercises.Exercise;
import pl.golo.demo.model.exercises.Flashcard;
import pl.golo.demo.model.exercises.KnowledgeTest;
import pl.golo.demo.model.exercises.Question;
import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.model.user.ApprenticeLanguage;
import pl.golo.demo.model.user.Language;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JsonDataInsertion {
    private String modelName;
    private List modelObjects;

    public JsonDataInsertion() {
    }

    public JsonDataInsertion(String modelName, List modelObjects) {
        this.modelName = modelName;
        this.modelObjects = modelObjects;
    }

    private void initList(String wantedModel) {
        switch (wantedModel) {
            case "apprentice" -> this.modelObjects = new ArrayList<Apprentice>();
            case "language" -> this.modelObjects = new ArrayList<Language>();
            case "apprentice_language" -> this.modelObjects = new ArrayList<ApprenticeLanguage>();
            case "question" -> this.modelObjects = new ArrayList<Question>();
            case "exercise" -> this.modelObjects = new ArrayList<Exercise>();
            case "flashcard" -> this.modelObjects = new ArrayList<Flashcard>();
            case "knowledge_test" -> this.modelObjects = new ArrayList<KnowledgeTest>();
            default -> this.modelObjects = new ArrayList<>();
        }
    }
}