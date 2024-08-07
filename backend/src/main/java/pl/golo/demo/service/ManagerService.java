package pl.golo.demo.service;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ManagerService {
    public static void main(String[] args) {

        SQLiteManager manager = new SQLiteManager("jdbc:sqlite:simple_database.db");
        LinkedHashMap<String,Object> modelsRepresentation = new LinkedHashMap<>()
        {{
//            put("employees", new Employee());
//            put("projects", new Project());
//            put("tasks", new Task());
//            put("ProjectAssignments", new ProjectAssignment());
        }};


        try{
            manager.connect();
            for (Map.Entry<String, Object> entry : modelsRepresentation.entrySet()) {
                runSelectQuery(entry.getKey(), entry.getValue(), manager);
                runInsertQuery(entry.getKey(), entry.getValue(), manager);
            }
            manager.disconnect();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }

    }
     static void runSelectQuery(String modelName, Object modelInstance, SQLiteManager manager) throws SQLException {
        String selectQuery= "SELECT * FROM " + modelName;
        manager.executeSqlQuery(selectQuery, modelInstance);
        System.out.println("\n");
    }
    static void runInsertQuery(String modelName, Object modelInstance, SQLiteManager manager) throws Exception {
        String insertQuery = "INSERT INTO " + modelName + "(";
        String completedInsertionQuery = manager.getFormatForInsertionQuery(insertQuery, modelInstance, modelName);
//        manager.executeSqlQuery(completedInsertionQuery, modelInstance);
    }

}
