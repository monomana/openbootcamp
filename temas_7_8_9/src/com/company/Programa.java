package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Integer option = 10;
        Scanner scanner = new Scanner(System.in);
        while (option != 0) {
            System.out.println("1. buscar tarea, usuario, fecha");
            System.out.println("2. crear tarea");
            System.out.println("0. salir");
            System.out.println("============================");
            System.out.print("Ingrese su opcion: ");
            option = scanner.nextInt();
            if (option == 2) menuGuardar();
            if (option == 1) menuBuscar();
        }
    }

    public static void menuBuscar() {
        String textoBuscar = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print("ingrese texto a buscar: ");
        textoBuscar = scanner.next();
        buscar(textoBuscar);
    }

    public static void menuGuardar() {
        String fecha = "";
        String usuario = "";
        String tarea = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print("ingrese fecha (ddmmaaaa):");
        fecha = scanner.next();
        System.out.print("Ingrese usuario: ");
        usuario = scanner.next();
        System.out.print("Ingrese tarea: ");
        tarea = scanner.next();
        String concatenar = fecha + "|" + usuario + "|" + tarea;
        guardar(concatenar);
    }

    public static void guardar(String concatenar) {
        byte[] datos = concatenar.getBytes();
        PrintStream out = null;
        try {
            FileOutputStream fos = new FileOutputStream(new File("/home/mono/Descargas/agenda.dat"), true);

            out = new PrintStream(fos);
//        out.write(datos);
//        out.append(concatenar);
//        out.append(concatenar);
            out.println(concatenar);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.close();
    }

    private static void buscar(String texto) {
        InputStream in = null;
        LinkedList<Tarea> tareaLinkedList = new LinkedList<>();
        Map<Integer, Tarea> tareaHashMap = new HashMap<>();
        int numeroLinea = 1;
        try {
            in = new FileInputStream("/home/mono/Descargas/agenda.dat");
            byte[] datos = in.readAllBytes();
            String linea = "";
            linea = "";
            System.out.println("==============================================");
            System.out.println("Resultados de la Busqueda");
            System.out.println("==============================================");
            System.out.println("Nro linea   Datos");
            System.out.println("----------------------------------------------");
            for (byte dato : datos) {

                linea += (char) dato + "";
                if ((char) dato == '\n') {
                    if (linea.contains(texto)) {
                        String[] splitLinea = linea.split("|");
                        Tarea tarea = new Tarea(splitLinea[0], splitLinea[1], splitLinea[2]);
                        tareaLinkedList.add(tarea);
                        tareaHashMap.put(numeroLinea, tarea);
                        System.out.println(numeroLinea + "  -->  " + linea);

                    }
                    linea = "";
                    numeroLinea++;
                }
            }
            System.out.println("==============================================");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void copiar(String fileIn, String fileOut) {
        try {
            InputStream in = new FileInputStream(fileIn);
            byte[] datos = in.readAllBytes();
            in.close();

            PrintStream out = new PrintStream(fileOut);
            out.write(datos);
            out.close();
        } catch (Exception e) {
            System.out.println("Excepcion: " + e.getMessage());
        }
    }


}

//            Scanner scan = new Scanner(new File("/home/mono/Descargas/agenda.dat"));
//            while (scan.hasNextLine()) {
//                String line = scan.nextLine();
//                if(line.contains(texto)) System.out.println("Encontrado");
//            }


//            int caracter;
//            do {
//                caracter = in.read();
//                if (caracter == '\n') {
//                    System.out.println("LINEA: " + linea);
//                    break;
//                }
//                linea += (char) caracter + "";
//            } while (caracter != -1);