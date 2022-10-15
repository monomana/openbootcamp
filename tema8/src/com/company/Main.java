package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Persona persona = new Persona();
        persona.setEdad(28);
        persona.setNombre("Juan Perez");
        persona.setTelefono("385555555");

        System.out.println("Nombre: " + persona.getNombre() +
                ", Edad: " + persona.getEdad() + ", telefono: " + persona.getTelefono());
    }
}
