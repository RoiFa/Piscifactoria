package granjas;

import com.google.gson.annotations.JsonAdapter;

import adapters.FitoplactonAdapter;
import main.Simulador;

/** Esta clase se encarga de la logica relacionada con la granja de fitoplacton */
@JsonAdapter(FitoplactonAdapter.class)
public class Fitoplacton {

    /** Representa si la granja ha sido comprada */
    private static boolean disponible=false;

    /** Representa el numero de tanques en la granja */
    private static int tanques=1;

    /** Representa el ciclo actual de crecimiento de los tanques */
    private static int ciclo=0;

    /** Se encarga de avanzar en un dia la granja de fitoplacton junto con su ciclo */
    public static void nextDay(){
        ciclo++;
        if(ciclo==6){
            Simulador.instancia.almacen.addFood((500*tanques), false);
            ciclo = 0;
        }
    }

    /** Mejora de la granja, agregando un nuevo tanque */
    public static void mejora(){
        ciclo = 0;
        tanques++;
    }

    /** Devuelve el ciclo actual */
    public static int getCiclo() {
        return ciclo;
    }

    /** Devuelve el numero de tanques */
    public static int getTanques() {
        return tanques;
    }

    /** Devuelve si la granja esta o no comprada */
    public static boolean isDisponible() {
        return disponible;
    }

    /**
     * Fija el estado de compra de la granja al nuevo estado dado
     * @param disponible Nuevo estado de la granja
     */
    public static void setDisponible(boolean disponible) {
        Fitoplacton.disponible = disponible;
    }

    /**
     * Fija el ciclo actual de la granja al nuevo dado
     * @param ciclo Ciclo nuevo de la granja
     */
    public static void setCiclo(int ciclo) {
        Fitoplacton.ciclo = ciclo;
    }

    /**
     * Fija el nº actual de tanques al numero dado
     * @param tanques Nº de tanques a fijar en la granja
     */
    public static void setTanques(int tanques) {
        Fitoplacton.tanques = tanques;
    }

    @Override
    public String toString() {
        return "Fitoplacton\nNº de tanques:"+getTanques()+"\nCiclo:"+getCiclo();
    }
}