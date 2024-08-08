package pl.golo.demo.service.managment;

import pl.golo.demo.model.exercises.Exercise;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

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
}
