package com.company;

public class Main {

    public static void main(String[] args) {

        SmartDevice smartPhone = new SmartDevice.SmartPhone("motorola", "G20", 20,
                8, "negro");


        SmartDevice smartWatch = new SmartDevice.SmartWatch("samsung", "J7",12,20, "gold",
                3.5, "Bluetooth",true);

        System.out.println("Class smartphone: " + smartPhone );
        System.out.println("Class smartwatch: " + smartWatch);

    }
}
