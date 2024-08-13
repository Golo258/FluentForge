package pl.golo.demo.service.managment;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.model.exercises.Exercise;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

@Getter
@Setter
public class SelectQueries implements QueryExecutor{

    private Connection connection;
    private Statement statement;
    private QueriesUtils utils;

    private static List<String> selectQueries;
    static {
        selectQueries =  Arrays.asList(
                "SELECT * FROM apprentice",
                "SELECT * FROM language",
                "SELECT * FROM apprentice_language",
                "SELECT * FROM question",
                "SELECT * FROM exercise  WHERE title LIKE '%Quiz%';",
                "SELECT * FROM exercise  WHERE title LIKE '%Flashcard%';",
                "SELECT * FROM exercise  WHERE title LIKE '%Test%';");
    }

    public SelectQueries(Connection connection, PreparedStatement statement, QueriesUtils utils) {
        this.connection = connection;
        this.statement = statement;
        this.utils = utils;
    }

    @Override
    public void runQuery(String operation, String modelName, Object modelObject) {
        String query = this.getQueryByModelName(modelName);
        this.fetchQuery(query, modelObject);
    };

    public String getQueryByModelName(String modelName){
        return selectQueries.stream().filter(
                element -> element.toLowerCase().contains(modelName)
        ).findFirst().orElseThrow();
    }

    public void fetchQuery(String query, Object queryModel)  {
        if (this.connection != null) {
            try (var recordResult = this.statement.executeQuery(query)) {
                while (recordResult.next()) {
                    var objectFields = this.getUtils().getQueryObjectFields(queryModel);
                    this.formFetchOutput(objectFields, recordResult);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Connection failed. Cannot execute query");
        }
    }

    public void formFetchOutput(LinkedHashMap<String, String> objectFields, ResultSet resultSet) throws Exception {
        StringBuilder outputForm = new StringBuilder();
        StringBuilder outputFormat = new StringBuilder();
        ArrayList<Integer> stringLengths = new ArrayList<>();
        for (Map.Entry<String, String> entry : objectFields.entrySet()) {
            String name = entry.getKey();
            String type = entry.getValue();
            if (type.contains("Long")) {
                outputFormat.append("%-10s");
                outputForm.append(
                        resultSet.getInt(name)
                );
            } else if (type.contains("String") && !type.contains("L")) {
                outputForm.append(
                        resultSet.getString(name)
                );
                outputFormat
                        .append("%-")
                        .append(resultSet.getString(name).length() + 7)
                        .append("s");
            } else if (type.contains("L") && (type.contains("Integer") || type.contains("String"))) {
                outputFormat.append("%-15s");
                outputForm.append(
                        resultSet.getArray(
                                name
                        )
                );
            } else {
                throw new Exception("Given type" + type + "doesn't fit with formatting");
            }
            outputForm.append("|");
        }
        outputFormat.append("%n");
        String[] elements = outputForm.toString().split("\\|");
        System.out.printf(outputFormat.toString(), (Object[]) elements);
    }


}
