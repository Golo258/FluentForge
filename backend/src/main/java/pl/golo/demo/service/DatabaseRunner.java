package pl.golo.demo.service;

import pl.golo.demo.model.Apprentice;
import pl.golo.demo.model.ApprenticeLanguage;
import pl.golo.demo.model.Language;
import pl.golo.demo.model.Question;
import pl.golo.demo.model.exercises.Flashcard;
import pl.golo.demo.model.exercises.KnowledgeTest;
import pl.golo.demo.model.exercises.Quiz;

import java.sql.SQLException;
import java.util.*;

public class DatabaseRunner {

    private PostgresService service;
    private  List<String> testQueries;
    private static LinkedHashMap<String, Object> models;
    private static ArrayList<String> queryOperation;

    static {

        models = new LinkedHashMap<>() {{
            put("apprentice", new Apprentice());
            put("language", new Language());
            put("apprentice_language", new ApprenticeLanguage());
            put("question", new Question());
            put("quiz", new Quiz());
            put("flashcard", new Flashcard());
            put("knowledge_test", new KnowledgeTest());
        }};
        queryOperation = new ArrayList<>(
                Arrays.asList("SELECT") //  "INSERT", "UPDATE", "DELETE", "DROP", "ALTER"
        );
    }

    public void runPostgres() throws SQLException {
        this.setService(new PostgresService());
        this.getService().connect();
        for (Map.Entry<String, Object> modelsEntry : models.entrySet()) {
            System.out.println(modelsEntry.getKey());
            this.service.executeSqlQuery(
                    modelsEntry.getKey(), queryOperation, modelsEntry.getValue()
            );
            System.out.println("\n");
        }

        this.getService().disconnect();
    }


    public PostgresService getService() {
        return service;
    }

    public void setService(PostgresService service) {
        this.service = service;
    }

    public List<String> getTestQueries() {
        return testQueries;
    }

    public void setTestQueries(List<String> testQueries) {
        this.testQueries = testQueries;
    }
}
