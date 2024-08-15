package pl.golo.demo.service.managment;

import pl.golo.demo.model.exercises.Exercise;
import pl.golo.demo.model.exercises.Flashcard;
import pl.golo.demo.model.exercises.KnowledgeTest;
import pl.golo.demo.model.exercises.Question;
import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.model.user.ApprenticeLanguage;
import pl.golo.demo.model.user.Language;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class QueriesUtils {
    public QueriesUtils() {
    }

    /**
     * Implementation of python function:
     * String query = utils.getFormattedString("Some kind of {thing} just {have} another one {}", dict(key:value))
     */
    public String getFormatedString(String content, Map<String, String> valuesToFormat) {
        List<String> splitContent = Arrays.asList(content.split(" "));
        for (int value_index = 0; value_index < splitContent.size(); value_index++) {
            String element = splitContent.get(value_index);
            if (element.startsWith("{") && element.endsWith("}")) {
                String keyEquivalent = element.substring(1, element.length() - 1);
                splitContent.set(value_index, valuesToFormat.get(keyEquivalent));
            }
        }
        return String.join(" ", splitContent);
    }

    public LinkedHashMap<String, String> getQueryObjectFields(Object queryModel) {
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
        }
        ;
        return searchedObject;
    }

    public String getModelFields(Object modelObject) {
        Field[] fields = modelObject.getClass().getDeclaredFields();
        String fieldsNames = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        return "( " + fieldsNames + " )";
    }

    public String receiveQueryString(Object classObject) {
        Field[] fields = classObject.getClass().getDeclaredFields();

        List<String> fieldValues = Arrays.stream(fields)
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        Object fieldValue = field.get(classObject);
                        if (fieldValue instanceof Number) {
                            return fieldValue.toString();
                        } else {
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
