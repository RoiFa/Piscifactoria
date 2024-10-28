package monedas;

/**
 * Clase que gestiona el sistema de monedas
 */
public class Monedas {

    /** Las cantidad de monedas disponibles */
    private int cantidad;

    /**
     * Constructor básico para el sistema de monedas
     */
    public Monedas() {
        this.cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Añade una cantidad de monedas dada
     * @param mon las monedas a añadir
     */
    public void anadir(int mon){
        this.cantidad += mon;
    }

    /**
     * Resta una cantidad de monedas dada
     * @param mon las monedas a restar
     */
    public void gastar(int mon){
        this.cantidad -= mon;
    }
}