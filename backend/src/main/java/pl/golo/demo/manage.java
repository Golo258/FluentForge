package pl.golo.demo;

import pl.golo.demo.service.DatabaseRunner;

public class manage {
    public static void main(String[] args) {
        try{
            DatabaseRunner runner = new DatabaseRunner();
            runner.runPostgres();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }

    }
}
