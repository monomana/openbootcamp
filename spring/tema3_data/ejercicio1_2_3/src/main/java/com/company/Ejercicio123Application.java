package com.company;

import com.company.entity.Laptop;
import com.company.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Ejercicio123Application {


    public static LaptopRepository laptopRepository;

    @Autowired
    public Ejercicio123Application(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Ejercicio123Application.class, args);


        Laptop laptop1 = new Laptop(null, "HP", "1515HP");
        laptopRepository.save(laptop1);
        Laptop laptop2 = new Laptop(null, "ACER", "AC333");
        laptopRepository.save(laptop2);
        Laptop laptop3 = new Laptop(null, "SAMSUNG", "S4545J");
        laptopRepository.save(laptop3);

//        List<Laptop> laptopList = new ArrayList<>();
//        laptopList.add(laptop1);
//        laptopList.add(laptop2);
//        laptopList.add(laptop3);
//
//        laptopRepository.saveAll(laptopList);
    }

}
