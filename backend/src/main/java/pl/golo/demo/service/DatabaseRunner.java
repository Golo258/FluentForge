package pl.golo.demo.service;

import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.model.user.ApprenticeLanguage;
import pl.golo.demo.model.user.Language;
import pl.golo.demo.model.exercises.Question;
import pl.golo.demo.model.exercises.Flashcard;
import pl.golo.demo.model.exercises.KnowledgeTest;
import pl.golo.demo.model.exercises.Quiz;
import pl.golo.demo.service.managment.QueriesUtils;

import java.sql.SQLException;
import java.util.*;

public class DatabaseRunner {

    private PostgresService service;
    private static LinkedHashMap<String, Object> models;
    private static ArrayList<String> queryOperation;

    static {

        models = new LinkedHashMap<>() {{
            put("apprentice", new Apprentice());
            put("language", new Language());
            put("apprentice_language", new ApprenticeLanguage());
            put("question", new Question());
            put("exercise", Arrays.asList(
                    new Quiz(),
                    new Flashcard(),
                    new KnowledgeTest()
            ));
        }};
        queryOperation = new ArrayList<>(
                List.of("INSERT") //  "INSERT", "UPDATE", "DELETE", "DROP", "ALTER"
        );
    }

    public void runPostgres(){
        try{
            this.setService(new PostgresService(true, new QueriesUtils()));
            this.getService().connect();
            for (Map.Entry<String, Object> modelsEntry : models.entrySet()) {
                this.service.executeSqlQuery(
                        modelsEntry.getKey(), queryOperation, modelsEntry.getValue()
                );
                System.out.println("\n");
            }

            this.getService().disconnect();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }


    public PostgresService getService() {
        return service;
    }

    public void setService(PostgresService service) {
        this.service = service;
    }


}
