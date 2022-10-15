package com.company.controller;

import com.company.entity.Laptop;
import com.company.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/laptop")
public class LaptopController {
    private LaptopRepository laptopRepository;

    @Autowired
    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @GetMapping
    public List<Laptop> getLaptopRepository() {
        return laptopRepository.findAll();
    }

    @PostMapping
    public Laptop crear(@RequestBody Laptop laptop){
        return laptopRepository.save(laptop);
    }

}
