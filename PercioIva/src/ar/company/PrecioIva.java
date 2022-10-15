package ar.company;

public class PrecioIva {

    public static void main(String[] args) {
           double precioConIva= precioConIva(200.22,10.5);
        System.out.println(precioConIva);
    }
    public static Double precioConIva(double precio,double iva){
        iva=iva+1;
        return precio*iva;
    }
}
