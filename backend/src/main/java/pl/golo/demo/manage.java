package pl.golo.demo;

import pl.golo.demo.service.DatabaseRunner;

public class manage {
    public static void main(String[] args) {
        DatabaseRunner runner = new DatabaseRunner();
        runner.runPostgres();
    }
}
