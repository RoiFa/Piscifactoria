package monedas;

/**
 * Clase que gestiona el sistema de monedas
 */
public class Monedas {

    private int cantidad;

    public Monedas() {
        this.cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void anadir(int mon){
        this.cantidad += mon;
    }

    public void gastar(int mon){
        this.cantidad -= mon;
    }
}