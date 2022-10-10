package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(suma(5,10,1));

        Coche coche = new Coche();
        coche.incCantidadPuertas();
        System.out.println(coche.getCantidadPuertas());
    }

    private static int suma(int a, int b, int c){
        return  a+b+c;
    }
}


