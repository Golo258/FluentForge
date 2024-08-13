package pl.golo.demo.service;

import lombok.Getter;
import lombok.Setter;
import org.ini4j.Ini;
import pl.golo.demo.service.managment.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PostgresService {

    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = PostgresService.class.getClassLoader().getResourceAsStream("Static/database/config.ini")) {
            assert input != null;
            Ini ini = new Ini(input);
            url = ini.get("database", "url");
            user = ini.get("database", "user");
            password = ini.get("database", "password");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    private Connection connection;
    private Statement statement;
    private QueriesUtils utils;
    private boolean isTestMode;

    public PostgresService(boolean isTestMode, QueriesUtils utils) {
        this.isTestMode = isTestMode;
        this.utils = utils;
    }

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

    void executeSqlQuery(String modelName, ArrayList<String> operations, Object modelObject) throws Exception {
        if (this.isTestMode) {
            this.getConnection().setAutoCommit(false);
        }
        List<String> selectOptions = List.of("SELECT");
        List<String> insertOptions = Arrays.asList("INSERT", "ALTER", "UPDATE");
        List<String> removeOptions = Arrays.asList("DELETE, DROP");
        for (String operation : operations) {
            QueryExecutor queryClass = null;
            if (selectOptions.contains(operation)){
                queryClass = new SelectQueries(this.getConnection(), this.getStatement(), this.getUtils());
            }
            else if (insertOptions.contains(operation)){
                queryClass = new ModifyQueries(this.getConnection(), this.getStatement(), this.getUtils());
            }
            else if (removeOptions.contains(operation)){
                queryClass = new RemovalQueries(this.getConnection(), this.getStatement(), this.getUtils());
            }
            assert queryClass != null;
            queryClass.runQuery(operation, modelName, modelObject);
            if (this.isTestMode) {
                this.getConnection().rollback();
            } else {
                this.getConnection().commit();
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

}
