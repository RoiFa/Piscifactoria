package monedas;

import com.google.gson.annotations.JsonAdapter;

import adapters.MonedasAdapter;
import helpers.Reader;

/**
 * Clase que gestiona el sistema de monedas
 */
@JsonAdapter(MonedasAdapter.class)
public class Monedas {

    /** Las cantidad de monedas disponibles */
    private static int cantidad;
    
        /**
         * Constructor básico para el sistema de monedas
         */
        public Monedas() {
            cantidad = 0;
        }
    
        public static int getCantidad() {
            return cantidad;
    }

    public static void setCantidad(int cantidad) {
        Monedas.cantidad = cantidad;
    }

    /**
     * Añade una cantidad de monedas dada
     * @param mon las monedas a añadir
     */
    public void anadir(int mon){
        cantidad += mon;
    }

    /**
     * Resta una cantidad de monedas dada
     * @param mon las monedas a restar
     */
    public void gastar(int mon){
        cantidad -= mon;
    }

    public boolean comprar(int coste){
        System.out.println("Esto tiene un valor de: "+coste+"       Dinero actual: "+cantidad);
        if(coste>cantidad){
            System.out.println("No tiene dinero suficiente para costear esta compra");
            return false;
        }else{
            System.out.println("Quiere comprarlo?(1.Si/2.No)");
            int opcion = Reader.readTheNumber(1,2);
            if(opcion==1){
                gastar(coste);
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public String toString() {
        return "Actualmente hay "+cantidad+" monedas";
    }

}