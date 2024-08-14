package pl.golo.demo.service.managment;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Collections;
import java.util.Collections.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RemovalQueries implements QueryExecutor{
    private Connection connection;
    private Statement statement;
    private QueriesUtils utils;


    public RemovalQueries(Connection connection, Statement statement, QueriesUtils utils) {
        this.connection = connection;
        this.statement = statement;
        this.utils = utils;
    }

    @Override
    public void runQuery(String operation, String modelName, Object modelObject) {
        try{
            String removalQuery;
            if (operation.contains("DELETE")) {
                removalQuery = this.formRemovalQueryByModelField(
                        operation, modelName, modelObject, new String[]{"apprenticeId", "1"}
                );
            } else { // DROP
                removalQuery = this.formTableRemoval(operation, modelName);
            }
            this.getStatement().executeUpdate(removalQuery);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    //    Bierze wszystkie rekordy i wzgledem tego pola które chce usunąć sprawdzam czy jest taki rekord
    public String formRemovalQueryByModelField(String queryOperation, String modelName,
                                               Object modelObject, String[] fieldExpectation) {
        LinkedHashMap<String, String> objectFields = utils.getQueryObjectFields(modelObject);
        if (objectFields.containsKey(fieldExpectation[0])) {
            String formatted = utils.getFormatedString(
                    "{operation} FROM {model} WHERE {field} = {value} ;", Map.of(
                            "operation", queryOperation,
                            "model", modelName,
                            "field", fieldExpectation[0],
                            "value", fieldExpectation[1]
                    )
            );
            return formatted;
        } else {
            return "";
        }
    }

    public String formTableRemoval(String queryOperation, String modelName) {
        return utils.getFormatedString(
                "{operation} TABLE {model} ;", Map.of(
                        "operation", queryOperation,
                        "model", modelName
                )
        );
    }
}
