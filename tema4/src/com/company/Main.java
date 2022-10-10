package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here

        int numeroIf ;
        numeroIf=1;
        if (numeroIf < 0) {
           // System.out.println("Negativo");
        }else if (numeroIf > 0){
          //  System.out.println("Positivo");
        }else {
          //  System.out.println("Cero");
        }


        int numeroWhile=0;
        while (numeroWhile <3){
            numeroWhile++;
            System.out.println(numeroWhile);
        }

        numeroWhile=0;
     do {
         numeroWhile++;
         System.out.println(numeroWhile);
     } while (numeroWhile <3);



        for (int numeroFor = 0; numeroFor < 3; numeroFor++) {
            System.out.println(numeroFor);
        }

        String estacion="verano";
        switch (estacion){
            case "verano":
                System.out.println(estacion);
                break;
            case "otono":
                System.out.println(estacion);
                break;
            case "invierno":
                System.out.println(estacion);
                break;
            case "primavera":
                System.out.println(estacion);
                break;
            default:
                System.out.println("No es una estacion");
                break;
        }

    }
}
