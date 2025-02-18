package granjas;

import com.google.gson.annotations.JsonAdapter;

import adapters.FitoplactonAdapter;
import main.Simulador;

@JsonAdapter(FitoplactonAdapter.class)
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

    public static void mejora(){
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

    public static void setDisponible(boolean disponible) {
        Fitoplacton.disponible = disponible;
    }
    public static void setCiclo(int ciclo) {
        Fitoplacton.ciclo = ciclo;
    }
    public static void setTanques(int tanques) {
        Fitoplacton.tanques = tanques;
    }

    @Override
    public String toString() {
        return "Fitoplacton\nNº de tanques:"+getTanques()+"\nCiclo:"+getCiclo();
    }
}