package main;
import java.util.ArrayList;
import piscifactoria.Piscifactoria;

/**Objeto representativo del almacen general, donde se almacena toda la comida de la piscifactoria antes de repartirse entre las piscifactorias */
public class Almacen {
    private static int vegetal;
    private static int vegetalMax;
    private static int carne;
    private static int carneMax;

    public static int getCarne() {
        return carne;
    }
    public static int getCarneMax() {
        return carneMax;
    }
    public static int getVegetal() {
        return vegetal;
    }
    public static int getVegetalMax() {
        return vegetalMax;
    }
    public static void setCarne(int carne) {
        Almacen.carne = carne;
    }
    public static void setCarneMax(int carneMax) {
        Almacen.carneMax = carneMax;
    }
    public static void setVegetal(int vegetal) {
        Almacen.vegetal = vegetal;
    }
    public static void setVegetalMax(int vegetalMax) {
        Almacen.vegetalMax = vegetalMax;
    }

    /**
     * Reparte de manera equivalente a todos los almacenes de todas las piscifactorias y mantiene lo restante y/o lo no divisible en este almacen
     * @param carneAdd Cantidad de carne a añadir al almacen
     * @param vegetalAdd Cantidad de alimento vegetal a añadir al almacen
     * @return Devuelve el nuevo array de piscifactorias
     */
    public static ArrayList<Piscifactoria> repartirComida(int carneAdd, int vegetalAdd){
        ArrayList<Piscifactoria> piscis = Simulador.getPiscis();
        if((carneAdd+carne)>carneMax){
            System.out.println("Se esta intentando añadir mas carne de lo que se es capaz de almacenar");
        }else if((vegetalAdd+vegetal)>carneMax){
            System.out.println("Se esta intentando añadir mas alimento vegetal de lo que se es capaz de almacenar");
        }else{
            int numPiscis = piscis.size();
            if(numPiscis<carneAdd){
                System.out.println("No se alcanza el numero minimo de alimento de carnivoros para repartirse entre las piscifactorias");
            }else if(numPiscis<vegetalAdd){
                System.out.println("No se alcanza el numero minimo de alimento de herbivoros para repartirse entre las piscifactorias");
            }else {
                int cantRepartCarne = (int)numPiscis/carneAdd;
                int cantRepartVeget = (int)numPiscis/vegetalAdd;
                int[] restos = {(numPiscis%carneAdd),(numPiscis%vegetalAdd)};
                int[] probisional = {0,0};
                for (Piscifactoria pisci : piscis) {
                    probisional = pisci.addFood(cantRepartCarne,cantRepartVeget);
                }
                setCarne(restos[0]+probisional[0]);
                setVegetal(restos[1]+probisional[1]);
            }
        }
        return piscis;
    }
}
