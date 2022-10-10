package ar.company;


/**
 * Crear un proyecto java desde cero.
 * Crear un paquete.
 * Dentro del paquete crear una clase.
 * Dentro de la clase crear un método main
 * Imprimir todos los tipos de datos vistos.
 *
 * Tipos de datos mas comunes:
 * int, long, double, boolean, String
 */
public class TiposDatos {

    public static void main(String[] args) {

        //  enteros
        byte variableByte = 5;
        System.out.println("byte: "+ variableByte);
        short variableShort = 10;
        System.out.println("short: "+ variableShort);
        int variableInt = 30;
        System.out.println("int: "+ variableInt);
        Integer variableInteger = null;
        variableInteger = 5;
        System.out.println("int: "+ variableInteger);
        long variableLong = 100;
        variableLong = 100L;
        // Long accept null same case of Integer
        System.out.println("long: "+ variableLong);


        // decimales
        float variableFloat = 5.5f;
        System.out.println("float: "+ variableFloat);
        double variableDouble = 10.5d;
        System.out.println("double: "+variableDouble);


        // booleano
        boolean variableBoolean = false;
        System.out.println("bollean: "+variableBoolean);


        // texto
        char variableChar = 'a';
        System.out.println("char: "+variableChar);
        String variableString = "Learning my path to the heaven.....";
        System.out.println("String: "+variableString);
    }
}
