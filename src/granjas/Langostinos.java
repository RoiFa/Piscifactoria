package granjas;

import java.util.ArrayList;

import main.Simulador;

public class Langostinos {

    private static boolean disponible=false;

    private static int muertos=0;

    private static ArrayList<TanqueLangostinos> langs = new ArrayList<TanqueLangostinos>();

    public static int getMuertos() {
        return muertos;
    }

    public static boolean isDisponible() {
        return disponible;
    }

    public static void setMuertos(int muertos) {
        Langostinos.muertos = muertos;
    }

    public static void comprado(){
        langs.add(new TanqueLangostinos());
    }

    public static void replenish(){
        boolean replenished=false;
        for(int i=0;i<langs.size()&&replenished;i++){
            if (langs.get(i).getComida()!=150) {
                if(Simulador.instancia.almacen.getVegetal()>49){
                    Simulador.instancia.almacen.setVegetal(Simulador.instancia.almacen.getVegetal()-50);
                    langs.get(i).addFood();
                }
            }
        }
    }
}