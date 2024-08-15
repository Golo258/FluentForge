package pl.golo.demo;

import pl.golo.demo.model.user.Apprentice;
import pl.golo.demo.repository.ForgeRepository;
import pl.golo.demo.service.DatabaseRunner;
import pl.golo.demo.service.ForgeService;
import pl.golo.demo.service.managment.QueriesUtils;

import java.util.List;
import java.util.Map;

public class manage {
    public static void main(String[] args) {

        ForgeRepository repository = new ForgeRepository();
        List<Apprentice> records = repository.getAllUsers();
        System.out.println(records);
//        var userById = repository.getUserById(2L);
//        System.out.println(userById);


    }
}
