package pl.golo.demo.service.managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import pl.golo.demo.model.creation.JsonDataInsertion;
import pl.golo.demo.service.PostgresService;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


@Getter
@Setter
public class ModifyQueries implements QueryExecutor {
    private Connection connection;
    private Statement statement;
    private QueriesUtils utils;


    public ModifyQueries(Connection connection, Statement statement, QueriesUtils utils) {
        this.connection = connection;
        this.statement = statement;
        this.utils = utils;
    }

    @Override
    public void runQuery(String operation, String modelName, Object modelObject) {
        try {
            if (operation.contains("INSERT")) {
                String insertquery = this.getFormatForInsertionQuery(operation, modelObject, modelName);
                this.getStatement().executeUpdate(insertquery);
            } else {
                System.out.println("NOT IMPLEMENTED YET");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public String getFormatForInsertionQuery(String givenQuery, Object modelObject, String modelName) {
        String objectFieldsNames = utils.getModelFields(modelObject);
        givenQuery = givenQuery + " INTO " + modelName + objectFieldsNames + " VALUES";
        ArrayList<Object> modelsList = getInsertionModelsFromJsonFile("Static/dummy_data.json", modelName);
        String separator;
        for (int modelIndex = 0; modelIndex < modelsList.size(); modelIndex++) {
            separator = modelIndex == modelsList.size() - 1 ? ";" : ",\n";
            givenQuery += modelsList.get(modelIndex).toString() + separator;
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
