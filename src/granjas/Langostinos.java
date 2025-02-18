package granjas;

import java.util.ArrayList;

import com.google.gson.annotations.JsonAdapter;

import adapters.LangostinosAdapter;
import main.Simulador;

@JsonAdapter(LangostinosAdapter.class)
public class Langostinos {

    private static boolean disponible=false;

    private static int muertos=0;

    private static ArrayList<TanqueLangostinos> tanques = new ArrayList<TanqueLangostinos>();

    public static int getMuertos() {
        return muertos;
    }

    public static boolean isDisponible() {
        return disponible;
    }

    public static void setMuertos(int muertos) {
        Langostinos.muertos = muertos;
    }

    public static void setTanques(ArrayList<TanqueLangostinos> tanques) {
        Langostinos.tanques = tanques;
    }

    public static ArrayList<TanqueLangostinos> getTanques() {
        return tanques;
    }

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

    public static void setDisponible(boolean disponible) {
        Langostinos.disponible = disponible;
    }

    public static void nextDay(){
       replenish();
        for (TanqueLangostinos l : tanques) {
            setMuertos(l.nextDay(muertos));
        }
    }

    public static void mejora(){
        tanques.add(new TanqueLangostinos());
    }

    @Override
    public String toString() {
        return "Langostinos\nNº de Tanques:"+getTanques().size()+"\nPeces muertos:"+getMuertos();
    }
}