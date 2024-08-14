package pl.golo.demo.service.managment;

import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.model.exercises.*;
import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.model.user.ApprenticeLanguage;
import pl.golo.demo.model.user.Language;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

@Getter
@Setter
public class SelectQueries implements QueryExecutor {

    //    private Statement statement;
    private PreparedStatement statement;
    private Connection connection;
    private QueriesUtils utils;

    public SelectQueries(Connection connection,  QueriesUtils utils) {
        this.connection = connection;
        this.utils = utils;
    }

    @Override
    public void runQuery(String operation, String modelName, Object modelObject) {
        if(operation.contains("SELECT")){
            List<String> selectQueryComposition = new ArrayList<>();
            if (modelName.contains("exercise")) {
                selectQueryComposition.add("SELECT * FROM exercise WHERE title LIKE ? ;");
                List<Exercise> modelObjects = (List<Exercise>) modelObject;
                for (Exercise childObject : modelObjects) {
                    selectQueryComposition.removeIf( element -> ( element.contains("%"))); // Previous element removal
                    if (childObject instanceof Quiz) {
                        selectQueryComposition.add("%Quiz%");
                    } else if (childObject instanceof Flashcard) {
                        selectQueryComposition.add("%Flashcard%");
                    } else {
                        selectQueryComposition.add("%Test%");
                    }
                    this.fetchQuery(selectQueryComposition, childObject);
                }
            } else {
                selectQueryComposition = List.of(
                        "SELECT * FROM " + modelName + " ;"
                );
                this.fetchQuery(selectQueryComposition, modelObject);
            }
        }

    }


    public void fetchQuery(List<String> queryComponents, Object modelObject) {
        try (PreparedStatement statement = this.getConnection().prepareStatement(queryComponents.get(0))) {
            this.setStatement(statement);
            if (queryComponents.size() > 1)  this.getStatement().setString(1, queryComponents.get(1));
            try (ResultSet resultSet = this.getStatement().executeQuery()) {
                while (resultSet.next()) {
                    var objectFields = this.getUtils().getQueryObjectFields(modelObject);
                    this.formFetchOutput(objectFields, resultSet);
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
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
