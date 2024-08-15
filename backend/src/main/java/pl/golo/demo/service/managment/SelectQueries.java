package pl.golo.demo.service.managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.DirectFieldAccessor;
import pl.golo.demo.model.exercises.*;
import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.model.user.ApprenticeLanguage;
import pl.golo.demo.model.user.Language;

import javax.xml.transform.Result;
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

    public SelectQueries(Connection connection, QueriesUtils utils) {
        this.connection = connection;
        this.utils = utils;
    }

    public List<Object> getListOfObjects(String modelName, Object modelObject) {
        List<String> selectQueryComposition = this.getSelectionQueryBasedOnModel(modelName, modelObject);
        var objectFields = this.getUtils().getQueryObjectFields(modelObject);
        List<Object> objectsAsJsons = new ArrayList<>();
        try (ResultSet resultSet = this.getFetchedQUeryResultSet(selectQueryComposition)) {
            assert resultSet != null;
            while (resultSet.next()) {
                List<Object> fieldsValue = this.getModelFieldsValue(resultSet, objectFields); // shoow
                objectsAsJsons.add(this.collectFetchOutput(fieldsValue, modelObject));
            }
            return objectsAsJsons;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            if (this.getStatement() != null) {
                try {
                    this.getStatement().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Object collectFetchOutput(List<Object> fieldsValues, Object modelObject) {
        try {
            Object forgeModelObject = null;
            if (modelObject instanceof Apprentice) {
                forgeModelObject = this.mapToObject(Apprentice.class, fieldsValues);
            } else if (modelObject instanceof Language) {
                forgeModelObject = this.mapToObject(Language.class, fieldsValues);

            } else if (modelObject instanceof Question) {
                forgeModelObject = this.mapToObject(Question.class, fieldsValues);

            } else if (modelObject instanceof Exercise) {
                forgeModelObject = this.mapToObject(Exercise.class, fieldsValues);
            }
            return forgeModelObject;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public <T> T mapToObject(Class<T> objectClass, List<Object> modelFields) throws Exception {
        T object = objectClass.getDeclaredConstructor().newInstance();
        Field[] fields = objectClass.getDeclaredFields();
        for (int index = 0; index < fields.length; index++) {
            fields[index].setAccessible(true);
            Object fieldValue = modelFields.get(index);
            if (fieldValue instanceof Array) {
                Array sqlArray = (Array) fieldValue;
                fieldValue = sqlArray.getArray();
            }
            fields[index].set(object, fieldValue);
        }
        return object;
    }

    public List<Object> getModelFieldsValue(ResultSet queryResult, LinkedHashMap<String, String> objectFields) throws SQLException {
        List<Object> modelFields = new ArrayList<>();
        for (Map.Entry<String, String> entry : objectFields.entrySet()) {
            String fieldName = entry.getKey();
            String fieldType = entry.getValue();
            if (fieldType.contains("Long")) {
                modelFields.add(queryResult.getLong(fieldName));
            } else if (fieldType.contains("L") &&
                    (fieldType.contains("Integer") || fieldType.contains("String"))) {
                modelFields.add(queryResult.getArray(fieldName));
            } else {
                modelFields.add(queryResult.getString(fieldName));
            }
        }
        return modelFields;
    }


    public List<String> getSelectionQueryBasedOnModel(String modelName, Object modelObject) {
        List<String> selectQueryComposition = new ArrayList<>();
        if (modelName.contains("exercise")) {
            selectQueryComposition.add("SELECT * FROM exercise WHERE title LIKE ? ;");
            if (modelObject instanceof Quiz) {
                selectQueryComposition.add("%Quiz%");
            } else if (modelObject instanceof Flashcard) {
                selectQueryComposition.add("%Flashcard%");
            } else {
                selectQueryComposition.add("%Test%");
            }
        } else {
            selectQueryComposition = List.of(
                    "SELECT * FROM " + modelName + " ;"
            );
        }
        return selectQueryComposition;
    }

    @Override
    public void runQuery(String operation, String modelName, Object modelObject) {
        if (operation.contains("SELECT")) {
            List<String> selectQueryComposition = this.getSelectionQueryBasedOnModel(modelName, modelObject);
            this.fetchQuery(selectQueryComposition, modelObject);
        }
    }

    public ResultSet getFetchedQUeryResultSet(List<String> queryComponents) {
        try {
            PreparedStatement statement = this.getConnection().prepareStatement(queryComponents.get(0));
            this.setStatement(statement);
            if (queryComponents.size() > 1) this.getStatement().setString(1, queryComponents.get(1));
            return this.getStatement().executeQuery();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public void fetchQuery(List<String> queryComponents, Object modelObject) {
        var objectFields = this.getUtils().getQueryObjectFields(modelObject);
        try (ResultSet resultSet = this.getFetchedQUeryResultSet(queryComponents)) {
            assert resultSet != null;
            while (resultSet.next()) {
                this.showFetchedTable(objectFields, resultSet); // shoow
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    public void showFetchedTable(LinkedHashMap<String, String> objectFields, ResultSet resultSet) throws Exception {

        StringBuilder outputForm = new StringBuilder();
        StringBuilder outputFormat = new StringBuilder();
        for (Map.Entry<String, String> entry : objectFields.entrySet()) {
            String name = entry.getKey();
            String type = entry.getValue();
            if (type.contains("Long")) {
                outputFormat.append("%-10s");
                outputForm.append(resultSet.getInt(name));
            } else if (type.contains("String") && !type.contains("L")) {
                outputForm.append(resultSet.getString(name));
                outputFormat.append("%-")
                        .append(resultSet.getString(name).length() + 7)
                        .append("s");
            } else if (type.contains("L") && (type.contains("Integer") || type.contains("String"))) {
                outputFormat.append("%-15s");
                outputForm.append(resultSet.getArray(name));
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
