package main;
import java.util.ArrayList;

import piscifactoria.Piscifactoria;
/**
 * Clase que representa al almacén central
 */
public class Almacen {

    private static int vegetal;
    private static int carne;
    private static int maxCapacidad;

    public static int getCarne() {
        return carne;
    }
    public static int getVegetal() {
        return vegetal;
    }
    public static void setCarne(int carne) {
        Almacen.carne = carne;
    }
    public static void setVegetal(int vegetal) {
        Almacen.vegetal = vegetal;
    }

    public static int getMaxCapacidad() {
        return maxCapacidad;
    }
  
    /**
     * Constructor para el almacén central
     */
    public Almacen(){
        carne = 0;
        vegetal = 0;
        comidaAnimal = 0;
        comidaVegetal = 0;
        maxCapacidad = 200;
    }

    /**
     * Añade una cantidad de comida al almacén
     * @param comida la cantidad de comida a añadir
     * @param tipo el tipo de comida (true animal, false vegetal)
     */
    public void addFood(int comida, boolean tipo){
        if(tipo && (carne+comida)<=200){
            carne += comida;
            System.out.println("Comida animal actual: "+carne);
        } else if(!tipo && (vegetal+comida)<=200){
            vegetal += comida;
            System.out.println("Comida vegetal actual: "+vegetal);
        }else{
            System.out.println("La cantidad que se intenta añadir es mayor a la posible");
        }
    }
  
  /**
     * Reparte de manera equivalente a todos los almacenes de todas las piscifactorias y mantiene lo restante y/o lo no divisible en este almacen
     * @param carneAdd Cantidad de carne a añadir al almacen
     * @param vegetalAdd Cantidad de alimento vegetal a añadir al almacen
     * @return Devuelve el nuevo array de piscifactorias
     */
    public static ArrayList<Piscifactoria> repartirComida(int carneAdd, int vegetalAdd){
        ArrayList<Piscifactoria> piscis = Simulador.getPiscis();
        if((carneAdd+carne)>maxCapacidad){
            System.out.println("Se esta intentando añadir mas carne de lo que se es capaz de almacenar");
        }else if((vegetalAdd+vegetal)>maxCapacidad){
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

    /**
     * Mejora la capacidad del almacén en 50 unidades
     */
    public void upgrade(){
        maxCapacidad += 50;
        System.out.println("Almacén central mejorado. Se ha aumentado en 50 la capacidad hasta un total de "+maxCapacidad+" unidades");
    }

    @Override
    /**
     * Devuelve la información relevante del almacén
     */
    public String toString(){
        return "------------------ Almacén central ------------------"+
        "\nCapacidad máxima: "+maxCapacidad+
        "\nComida animal: "+carne+"/"+maxCapacidad+". ("+((int)(carne/maxCapacidad)*100)+" %)"+
        "\nComida vegetal: "+vegetal+"/"+maxCapacidad+". ("+((int)(vegetal/maxCapacidad)*100)+" %)";
    }
}
