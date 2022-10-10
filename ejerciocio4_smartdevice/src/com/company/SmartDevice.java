package com.company;

public abstract class SmartDevice {

    // Se ha llamado a la clase ejercicio4 para una mejor organización, pero es la SmartDevice

    String marca;
    String modelo;
    float largo;
    float ancho;
    String color;

    public SmartDevice() {

    }

    public SmartDevice(String marca, String modelo, float largo, float ancho, String color) {
        this.marca = marca;
        this.modelo = modelo;
        this.largo = largo;
        this.ancho = ancho;
        this.color = color;
    }


    public static class SmartPhone extends SmartDevice {
        String microprocesador;
        int ram;
        String sistema;
        String bateria;

        public SmartPhone(String marca, String modelo, float largo, float ancho, String color) {
            super(marca, modelo, largo, ancho, color);
            this.ram = ram;
            this.sistema = sistema;

        }

        @Override
        public String toString() {
            return "SmartPhone{" +
                    "marca='" + marca + '\'' +
                    ", modelo='" + modelo + '\'' +
                    ", largo=" + largo +
                    ", ancho=" + ancho +
                    ", color='" + color + '\'' +
                    ", microprocesador='" + microprocesador + '\'' +
                    ", ram=" + ram +
                    ", sistema='" + sistema + '\'' +
                    ", bateria='" + bateria + '\'' +
                    '}';
        }
    }

    public static class SmartWatch extends SmartDevice {
        double pulgadas;
        String conectividad;
        boolean cardio;

        public SmartWatch() {
            super();
        }

        public SmartWatch(String marca, String modelo, float largo, float ancho, String color, double pulgadas, String conectividad, boolean cardio) {
            super(marca, modelo, largo, ancho, color);
            this.pulgadas = pulgadas;
            this.conectividad = conectividad;
            this.cardio = cardio;
        }

        @Override
        public String toString() {
            return "SmartWatch{" +
                    "marca='" + marca + '\'' +
                    ", modelo='" + modelo + '\'' +
                    ", largo=" + largo +
                    ", ancho=" + ancho +
                    ", color='" + color + '\'' +
                    ", pulgadas=" + pulgadas +
                    ", conectividad='" + conectividad + '\'' +
                    ", cardio=" + cardio +
                    '}';
        }
    }


}
