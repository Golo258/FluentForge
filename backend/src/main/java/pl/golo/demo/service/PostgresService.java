package pl.golo.demo.service;

import org.ini4j.Ini;
import pl.golo.demo.service.managment.ModifyQueries;
import pl.golo.demo.service.managment.RemovalQueries;
import pl.golo.demo.service.managment.SelectQueries;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;

public class PostgresService {

    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = PostgresService.class.getClassLoader().getResourceAsStream("dbConfig.ini")) {
            assert input != null;
            Ini ini = new Ini(input);
            url = ini.get("database", "url");
            user = ini.get("database", "user");
            password = ini.get("database", "password");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public PostgresService() {
    }

    private Connection connection;
    private Statement statement;

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            if (this.getUrl() != null) {
                this.setConnection(
                        DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword())
                );
                if (this.getConnection() != null) {
                    this.setStatement(
                            this.getConnection().createStatement()
                    );
                } else {
                    throw new MalformedURLException("Wrong database credentials");
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void disconnect() throws SQLException {
        if (this.getStatement() != null) {
            this.getStatement().close();
        }
        if (this.getConnection() != null) {
            this.getConnection().close();
        }
    }

    void executeSqlQuery(String modelName, ArrayList<String> operations, Object modelObject) throws SQLException {
        for(String operation: operations){
            switch(operation){
                case "SELECT" -> {
                    SelectQueries selectQueries = new SelectQueries(getConnection(),getStatement());
                    String query = selectQueries.getQueryByModelName(modelName);
                    selectQueries.fetchQuery(query, modelObject);
                }
                case "INSERT", "ALTER","UPDATE" ->{
                    ModifyQueries insertQueries = new ModifyQueries();
                }
                case "DELETE" , "DROP"  ->{
                    RemovalQueries removalQueries = new RemovalQueries();
                }
                default -> {
                    System.out.println("There is no more opertaion");
                }
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

}
