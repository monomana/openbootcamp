//package com.company.service;
//
//import com.company.entity.Laptop;
//import com.company.repository.LaptopRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LaptopServiceTest {
//
//    @Autowired
//    private LaptopRepository laptopRepository;
//
//
//
//    @Test
//    void getLaptops() {
//
//        List<Laptop> laptopList = laptopRepository.findAll();
//        assertTrue(laptopList.size() >= 0);
//    }
//
//    @Test
//    void getLaptopById() {
//
//        assertTrue(laptopRepository.findById(1L).isPresent());
//        assertFalse(laptopRepository.findById(10L).isEmpty());
//
//    }
//
//    @Test
//    void save() {
//    }
//
//    @Test
//    void delete() {
//    }
//}