package pl.golo.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import lombok.Getter;
import lombok.Setter;
import org.ini4j.Ini;
import pl.golo.demo.model.exercises.Flashcard;
import pl.golo.demo.model.exercises.KnowledgeTest;
import pl.golo.demo.model.exercises.Question;
import pl.golo.demo.model.exercises.Quiz;
import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.model.user.ApprenticeLanguage;
import pl.golo.demo.model.user.Language;
import pl.golo.demo.service.managment.*;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class ForgeService {

    private static String url;
    private static String user;
    private static String password;
    private static LinkedHashMap<String, Object> models;
    private static ArrayList<String> queryAvailableOperations;

    static {
        models = new LinkedHashMap<>() {{
            put("apprentice", new Apprentice());
            put("language", new Language());
            put("apprentice_language", new ApprenticeLanguage());
            put("question", new Question());
            put("exercise", Arrays.asList(
                    new Quiz(),
                    new Flashcard(),
                    new KnowledgeTest()
            ));
        }};
        queryAvailableOperations = new ArrayList<>(
                List.of("INSERT") //  "INSERT", "UPDATE", "DELETE", "DROP", "ALTER"
        );
        try (InputStream input = ForgeService.class.getClassLoader().getResourceAsStream("Static/database/config.ini")) {
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
    private QueriesUtils utils;
    private boolean isTestMode;

    private ModifyQueries modifyQueries;
    private RemovalQueries removalQueries;

    public ForgeService(boolean isTestMode, QueriesUtils utils) {
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
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (this.getConnection() != null) {
                this.getConnection().close();
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<Object> runSelectQuery(String modelName, Object modelObject, String option)  {
        SelectQueries selectQueries = new SelectQueries(
                this.getConnection(), this.getUtils()
        );
        List<Object> allJsonObjects = selectQueries.getListOfObjects(modelName, modelObject);
        if (option.contains("all")){
            return allJsonObjects;
        }
        return null;
    }


    void executeSqlQuery(String modelName, ArrayList<String> operations, Object modelObject) throws Exception {
        if (this.isTestMode) {
            this.getConnection().setAutoCommit(false);
        }
        List<String> selectOptions = List.of("SELECT");
        List<String> insertOptions = Arrays.asList("INSERT", "ALTER", "UPDATE");
        List<String> removeOptions = Arrays.asList("DELETE", "DROP");
        for (String operation : operations) {
            QueryExecutor queryClass = null;
            if (selectOptions.contains(operation)) {
                queryClass = new SelectQueries(this.getConnection(), this.getUtils());
            } else if (insertOptions.contains(operation)) {
                queryClass = new ModifyQueries(this.getConnection(), this.getUtils());
            } else if (removeOptions.contains(operation)) {
                queryClass = new RemovalQueries(this.getConnection(), this.getUtils());
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
