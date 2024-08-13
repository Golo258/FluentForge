package pl.golo.demo.service.managment;

public interface QueryExecutor {
    void runQuery(String operation, String modelName, Object modelObject);
}
