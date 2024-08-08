package pl.golo.demo.service.managment;

import pl.golo.demo.model.exercises.Exercise;
import pl.golo.demo.model.exercises.Flashcard;
import pl.golo.demo.model.exercises.KnowledgeTest;
import pl.golo.demo.model.exercises.Question;
import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.model.user.ApprenticeLanguage;
import pl.golo.demo.model.user.Language;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class QueriesUtils {
    public QueriesUtils() {
    }

    public LinkedHashMap<String, String> getQueryObjectFields(String givenQuery, Object queryModel) throws Exception {
        Class<?> clazz = queryModel.getClass();
        Field[] fields;
        if (queryModel.getClass().getSuperclass() == Exercise.class) {
            fields = clazz.getSuperclass().getDeclaredFields();
        } else {
            fields = clazz.getDeclaredFields();
        }
        LinkedHashMap<String, String> fieldAttributes = new LinkedHashMap<>();
        for (Field field : fields) {
            fieldAttributes.put(
                    field.getName(),
                    String.valueOf(field.getType()));
        }
        return fieldAttributes;
    }

    public Object getClassByTableName(String tableName) {
        Object searchedObject = null;
        switch (tableName) {
            case "apprentice" -> searchedObject = new Apprentice();
            case "language" -> searchedObject = new Language();
            case "apprentice_language" -> searchedObject = new ApprenticeLanguage();
            case "question" -> searchedObject = new Question();
            case "flashcard" -> searchedObject = new Flashcard();
            case "knowledge_test" -> searchedObject = new KnowledgeTest();
        };
        return searchedObject;
    }

    public String getModelFields(Object modelObject) {
        Field[] fields = modelObject.getClass().getDeclaredFields();
        String fieldsNames = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        return "( " +  fieldsNames + " )";
    }
    public String receiveQueryString() {
        Field[] fields = this.getClass().getDeclaredFields();

        List<String> fieldValues = Arrays.stream(fields).map(field -> {
            try {
                Object fieldValue = field.get(this);
                if (fieldValue instanceof Number){
                    return fieldValue.toString();
                }
                else{
                    return "'" + fieldValue.toString() + "'"; //
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return "null";
            }
        }).toList();
        return "( " + String.join(", ", fieldValues) + " )";
    }
}
