package pl.golo.demo.service.managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.golo.demo.service.PostgresService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModifyQueries {
    public ModifyQueries() {
    }

    String getFormatForInsertionQuery(String givenQuery, Object queryModel, String modelName) throws Exception {
        QueriesUtils utils = new QueriesUtils();
        LinkedHashMap<String, String> objectFields = utils.getQueryObjectFields(givenQuery, queryModel);
        StringBuilder argumentsBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : objectFields.entrySet()) {
//            if (entry.getKey().contains(objectFields.lastEntry().getKey())) {
//                argumentsBuilder.append(entry.getKey()).append(") VALUES(");
//            } else {
//                argumentsBuilder.append(entry.getKey()).append(", ");
//            }
        }
        givenQuery += argumentsBuilder.toString();
        System.out.println(givenQuery);
        ArrayList<Object>  modelsList = getInsertionModelsFromJsonFile("Static/dummy_data.json", modelName);
        return "";
    }

    ArrayList<Object> getInsertionModelsFromJsonFile(String filePath, String modelName) {
        try {
            InputStream inputStream = PostgresService.class.getClassLoader().getResourceAsStream(
                    filePath
            );
            assert inputStream != null;
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
//            JsonInsertedData[] dataArray = mapper.readValue(inputStream, JsonInsertedData[].class);
//            for (JsonInsertedData jsonData : dataArray) {
//                if (jsonData.getTable().contains(modelName)) {
//                    Object wantedSQLModel = getClassByTableName(modelName);
//                    ArrayList<Object> jsonModels = new ArrayList<>(jsonData.getData());
//                    System.out.println(jsonModels);
//                    for (Object jsonModel : jsonModels) {
//                        jsonModel = mapper.convertValue(jsonModel, wantedSQLModel.getClass());
//                        System.out.println(jsonModel);
//                    }
//                    return jsonModels;
//                }
//            }
            return null;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

}
