package granjas;

import main.Simulador;

public class Fitoplacton {

    private static boolean disponible=false;

    private static int tanques=1;

    private static int ciclo=0;

    public static void nextDay(){
        ciclo++;
        if(ciclo==6){
            Simulador.instancia.almacen.addFood((500*tanques), false);
            ciclo = 0;
        }
    }

    public static void addTanque(){
        ciclo = 0;
        tanques++;
    }

    public static int getCiclo() {
        return ciclo;
    }

    public static int getTanques() {
        return tanques;
    }

    public static boolean isDisponible() {
        return disponible;
    }



}