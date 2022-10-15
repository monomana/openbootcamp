package com.company;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
//        devuelve una cadena al revés
//        System.out.println(textoReverso());

//        Crea un array unidimensional de Strings y recórrelo, mostrando únicamente sus valores.
        String[] array= {"uno","dos","tres"};
        for (String elemento: array) {
            System.out.println(elemento);
        }

//        Crea un array bidimensional de enteros y recórrelo, mostrando la posición
//        y el valor de cada elemento en ambas dimensiones.
        Integer[][] arrayNumeros = {
                {25, 11, 1,3},
                {0, 5, 55},
        };
        for (int i = 0; i < arrayNumeros.length; i++) {

            for (int j = 0; j < arrayNumeros[i].length; j++) {
                System.out.println("posicion fila: "+i+" col: "+j);
                System.out.println("valor: "+arrayNumeros[i][j]);
            }
        }

//        Crea un "Vector" del tipo de dato que prefieras, y añádele 5 elementos.
//        Elimina el 2o y 3er elemento y muestra el resultado final.
        Vector vector=new Vector();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        vector.add(4);
        vector.add(5);

        vector.remove(2);
        vector.remove(1);

        System.out.println("VECTOR: "+vector.toString());

//        Indica cuál es el problema de utilizar un Vector con la capacidad por defecto
//        si tuviésemos 1000 elementos para ser añadidos al mismo.

        /*
        * Se produce un problema de rendimiento en la memoria porque al ir agregando elementos
        * se va haciendo una copia del vector en memoria para poder incrementar el espacio restante
        * */


//        Crea un ArrayList de tipo String, con 4 elementos.
//        Cópialo en una LinkedList. Recorre ambos mostrando únicamente el valor de cada elemento.
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("uno");
        arrayList.add("dos");
        arrayList.add("tres");
        arrayList.add("cuatro");

        LinkedList<String> linkedList=new LinkedList<>(arrayList);

        for (String elemento: arrayList){
            System.out.println("Array elemento: "+elemento);
        }
        for (String elemento: linkedList){
            System.out.println("linkedList elemento: "+elemento);
        }

//        Crea un ArrayList de tipo int, y, utilizando un bucle rellénalo con elementos 1..10.
//        A continuación, con otro bucle, recórrelo y elimina los numeros pares.
//        Por último, vuelve a recorrerlo y muestra el ArrayList final. Si te atreves,
//        puedes hacerlo en menos pasos, siempre y cuando cumplas el primer "for" de relleno.
        ArrayList<Integer> arrayListInt=new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            arrayListInt.add(i+1);
        }
        for (int i = 0; i < arrayListInt.size(); i++) {
            if (arrayListInt.get(i) %2 ==0){
                arrayListInt.remove(i);
            }
        }
        for (int i = 0; i < arrayListInt.size(); i++) {
            System.out.println(arrayListInt.get(i));
        }
        System.out.println("ARRAY INTEGER "+arrayListInt.toString());

//        Crea una función DividePorCero. Esta, debe generar una excepción ("throws")
//        a su llamante del tipo ArithmeticException que será capturada por su llamante
//        (desde "main", por ejemplo). Si se dispara la excepción, mostraremos el mensaje
//        "Esto no puede hacerse". Finalmente, mostraremos en cualquier caso: "Demo de código".
        try {
            dividePorCero(1,0);
        }catch (Exception e){
            System.out.println("Esto no puede hacerse");
        }
        finally {
            System.out.println("Demo de código");
        }

//        Utilizando InputStream y PrintStream, crea una función que reciba dos parámetros:
//        "fileIn" y "fileOut". La tarea de la función será realizar la copia del fichero
//        dado en el parámetro "fileIn" al fichero dado en "fileOut".

        copiarArchivo("/etc/fstab","/home/mono/fstab_bkp");

    }
    private static void copiarArchivo(String fileIn, String fileOut) {
        try {
            InputStream in = new FileInputStream(fileIn);
            byte[] datos = in.readAllBytes();
            in.close();

            PrintStream out = new PrintStream(fileOut);
            out.write(datos);
            out.close();
        } catch (Exception e) {
            System.out.println("Excepicon: " + e.getMessage());
        }
    }

public static void dividePorCero(int a,int b) throws ArithmeticException{
       int division= a/b;
}

    public static String textoReverso(){
        Scanner entrada=new Scanner(System.in);
        System.out.println("Ingrese una cadena de texto");
        String in= entrada.nextLine();
        String reves = reverse(in);

        return reves;
    }



    public static String reverse(String texto) {
        String reverse = "";
        for (int i = texto.length() -1 ; i >= 0; i--) {
            reverse+=texto.charAt(i);
        }
        return reverse;
    }
}
