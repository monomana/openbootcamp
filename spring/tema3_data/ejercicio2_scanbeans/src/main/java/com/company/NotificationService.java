package com.company;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    public NotificationService(){}

    public void  imprimirSaludo(){
        System.out.println("Hola mundo");
    }
}
