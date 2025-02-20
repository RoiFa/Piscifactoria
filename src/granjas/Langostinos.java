package granjas;

import java.util.ArrayList;

import com.google.gson.annotations.JsonAdapter;

import adapters.LangostinosAdapter;
import main.Simulador;

/** Esta clase se encarga de la logica relacionada con la granja de langostinos. */
@JsonAdapter(LangostinosAdapter.class)
public class Langostinos {

    /** Representa si la granja ha sido comprada */
    private static boolean disponible=false;

    /** Representa el nº de peces muertos */
    private static int muertos=0;

    /** Lista donde estan almacenados los tanques de langostinos */
    private static ArrayList<TanqueLangostinos> tanques = new ArrayList<TanqueLangostinos>();

    /** Devuelve el numero de peces muertos */
    public static int getMuertos() {
        return muertos;
    }

    /** Devuelve el estado de compra de esta granja */
    public static boolean isDisponible() {
        return disponible;
    }

    /** Fija el numero de peces muertos almacenados en el tanque
     * @param muertos Nº de peces muertos
     */
    public static void setMuertos(int muertos) {
        Langostinos.muertos = muertos;
    }

    /** Fija los tanques de langostinos a los nuevos dados
     * @param tanques Tanques de langostinos
     */
    public static void setTanques(ArrayList<TanqueLangostinos> tanques) {
        Langostinos.tanques = tanques;
    }

    /**
     * Devuelve la lista de tanques de langostinos
     */
    public static ArrayList<TanqueLangostinos> getTanques() {
        return tanques;
    }

    /** Recarga los almacenes de comida de cada tanque de langostinos */
    public static void replenish(){
        boolean replenished=false;
        for(int i=0;i<tanques.size();i++){
            if(i==0){
                replenished=false;
            }
            if (tanques.get(i).getComida()!=3) {
                if(Simulador.instancia.almacen.getVegetal()>49){
                    Simulador.instancia.almacen.setVegetal(Simulador.instancia.almacen.getVegetal()-50);
                    tanques.get(i).addFood();
                    replenished = true;
                }
            }
            if(replenished&&i==tanques.size()){
                i=-1;
            }
        }
    }

    /** 
     * Fija el estado de compra de la granja a la dada
     * @param disponible Nuevo estado de la granja
     */
    public static void setDisponible(boolean disponible) {
        Langostinos.disponible = disponible;
    }

    /** Pasa de dia en la granja de langostinos y en los tanques dentro */
    public static void nextDay(){
        replenish();
        for (TanqueLangostinos l : tanques) {
            setMuertos(l.nextDay(muertos));
        }
    }

    /** Mejora la granja de langostinos añadiendo un nuevo tanque */
    public static void mejora(){
        tanques.add(new TanqueLangostinos());
    }

    @Override
    public String toString() {
        return "Langostinos\nNº de Tanques:"+getTanques().size()+"\nPeces muertos:"+getMuertos();
    }
}