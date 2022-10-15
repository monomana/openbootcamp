package com.company;

public class Main {

    public static void main(String[] args) {

        String[] arrayTexto=new String[]{"uno","dos","tres"};
        System.out.println(concatenar(arrayTexto));

    }

    static String concatenar(String[] array){
        String concatenado="";
        for (String texto:array) {
            concatenado=concatenado+texto;
        }
        return concatenado;
    }
}
