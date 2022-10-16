package com.company.service;

import com.company.entity.Laptop;
import com.company.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class LaptopService {

    private LaptopRepository laptopRepository;

    @Autowired
    public LaptopService(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    public List<Laptop> getLaptops() {
        return laptopRepository.findAll();
    }

    public Optional<Laptop> getLaptopById(Long id){
        return laptopRepository.findById(id);
    }
    public Laptop save( Laptop laptop){
        return laptopRepository.save(laptop);
    }
    public void delete( Laptop laptop){
         laptopRepository.delete(laptop);
    }
}
