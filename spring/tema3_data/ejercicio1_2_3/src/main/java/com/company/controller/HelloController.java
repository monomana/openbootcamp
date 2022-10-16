package com.company.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/saludo")
    public String Saludo(){
        return "Hola mundo desde spring Rest api";
    }


}
