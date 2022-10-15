package com.company;

public class Cliente extends Persona{
    private double credito;

    public Cliente(int edad, String nombre, String telefono) {
        super(edad, nombre, telefono);
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }
}
