package com.company.controller;

import com.company.entity.Laptop;
import com.company.repository.LaptopRepository;
import com.company.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/laptop")
public class LaptopController {
    private LaptopService laptopService;

    @Autowired
    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @GetMapping
    public List<Laptop> findAll() {
        return laptopService.getLaptops();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id){
        if (id == null) {
            return ResponseEntity.noContent().build();
        }
        Optional laptop= laptopService.getLaptopById(id);
        if (laptop.isPresent()) {
            return ResponseEntity.ok((Laptop)laptop.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop){
        if (laptop.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(laptopService.save(laptop));
    }

    @PostMapping
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){
        if (laptop.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional laptopOptional= laptopService.getLaptopById(laptop.getId());
        if (!laptopOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(laptopService.save(laptop));
    }

    @PostMapping
    public ResponseEntity delete(@RequestBody Laptop laptop){
        if (laptop.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional laptopOptional= laptopService.getLaptopById(laptop.getId());
        if (!laptopOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        laptopService.delete(laptop);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity deleteAll(@RequestBody Laptop laptop){
         laptopService.save(laptop);
        return ResponseEntity.ok().build();
    }
}
