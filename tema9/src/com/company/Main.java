package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Cliente cliente = new Cliente();
        cliente.setEdad(28);
        cliente.setNombre("Juan Perez");
        cliente.setTelefono("385555555");
        cliente.setCredito(20.5);

        System.out.println("Nombre: "+cliente.getNombre()+
                ", Edad: "+cliente.getEdad()+", telefono: "+cliente.getTelefono()+
                ", credito: "+cliente.getCredito());

        Trabajador trabajador = new Trabajador();
        trabajador.setEdad(28);
        trabajador.setNombre("Juan Perez");
        trabajador.setTelefono("385555555");
        trabajador.setSalario(20000.5);

        System.out.println("Nombre: "+trabajador.getNombre()+
                ", Edad: "+trabajador.getEdad()+", telefono: "+trabajador.getTelefono()+
                ", salario: "+trabajador.getSalario());

    }
}
