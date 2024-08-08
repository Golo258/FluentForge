package pl.golo.demo.service.managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.golo.demo.model.creation.JsonDataInsertion;
import pl.golo.demo.service.PostgresService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModifyQueries {
    QueriesUtils utils = new QueriesUtils();

    public ModifyQueries() {
    }

    public String getFormatForInsertionQuery(String givenQuery, Object modelObject, String modelName) throws Exception {

        LinkedHashMap<String, String> objectFields = utils.getQueryObjectFields(givenQuery, modelObject);

        String objectFieldsNames = utils.getModelFields(modelObject);
        givenQuery = givenQuery + " INTO " + modelName  + objectFieldsNames + " VALUES";
        ArrayList<Object> modelsList = getInsertionModelsFromJsonFile("Static/dummy_data.json", modelName);
        for (int modelIndex =0 ; modelIndex < modelsList.size(); modelIndex++){
            if (modelIndex == modelsList.size() - 1 ){
                givenQuery += modelsList.get(modelIndex).toString() + ";";
            }
            else{
                givenQuery += modelsList.get(modelIndex).toString() + ",\n";
            }
        }
        return givenQuery;
    }

    public ArrayList<Object> getInsertionModelsFromJsonFile(String filePath, String modelName) {
        try {
            InputStream inputStream = PostgresService.class.getClassLoader().getResourceAsStream(
                    filePath
            );
            assert inputStream != null;
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            JsonDataInsertion[] dataArray = mapper.readValue(inputStream, JsonDataInsertion[].class);
            for (JsonDataInsertion jsonData : dataArray) {
                if (jsonData.getModelName().contains(modelName)) {
                    Object wantedSQLModel = utils.getClassByTableName(modelName);
                    ArrayList<Object> jsonModels = new ArrayList<>(jsonData.getModelObjects());
                    System.out.println(jsonModels);
                    jsonModels.replaceAll(beginValue -> mapper.convertValue(
                            beginValue, wantedSQLModel.getClass()
                    ));
                    return jsonModels;
                }
            }
            return null;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

}
