package main;
import java.util.ArrayList;

import com.google.gson.annotations.JsonAdapter;

import adapters.AlmacenAdapter;
import helpers.ErrorWriter;
import piscifactoria.Piscifactoria;
/**
 * Clase que representa al almacén central
 */
@JsonAdapter(AlmacenAdapter.class)
public class Almacen {

    /** Si se dispone del almacén. */
    private boolean disponible;
    /** Cantidad de comida vegetal. */
    private int vegetal;
    /** Cantidad de comida animal. */
    private int carne;
    /** Cantidad maxima de cada tipo de comida. */
    private int maxCapacidad;

    /** @return La cantidad de comida animal almacenada. */
    public int getCarne() {
        return carne;
    }

    /** @return La cantidad de comida vegetal almacenada. */
    public int getVegetal() {
        return vegetal;
    }

    /** @param carne La cantidad de comida animal a guardar en el almacen */
    public void setCarne(int carne) {
        this.carne = carne;
    }

    /** @param vegetal La cantidad de comida vegetal a guardar en el almacen */
    public void setVegetal(int vegetal) {
        this.vegetal = vegetal;
    }

    /** @return La capacidad máxima del almacén */
    public int getMaxCapacidad() {
        return maxCapacidad;
    }

    /** @return si se dispone del almacén */
    public boolean getDisponible(){
        return disponible;
    }

    /**
     * @param disponible si se dispone del almacén
     */
    public void setDisponible(boolean disponible){
        this.disponible = disponible;
    }

    /**
     * @param maxCapacidad la capacidad máxima del almacén
     */
    public void setMaxCapacidad(int maxCapacidad){
        this.maxCapacidad = maxCapacidad;
    }
  
    /**
     * Constructor para el almacén central
     */
    public Almacen(){
        disponible = false;
        carne = 0;
        vegetal = 0;
        maxCapacidad = 200;
    }

    /**
     * Añade una cantidad de comida al almacén
     * @param comida la cantidad de comida a añadir
     * @param tipo el tipo de comida (true animal, false vegetal)
     */
    public void addFood(int comida, boolean tipo){
        if(tipo && (carne+comida)<=maxCapacidad){
            carne += comida;
            System.out.println("Comida animal actual en almacen: "+carne);
        } else if(!tipo && (vegetal+comida)<=maxCapacidad){
            vegetal += comida;
            System.out.println("Comida vegetal actual en almacen: "+vegetal);
        }else{
            System.out.println("La cantidad que se intenta añadir es mayor a la posible");
        }
        repartirComida();
    }
  
  /**
     * Reparte de manera equivalente a todos los almacenes de todas las piscifactorias y mantiene lo restante y/o lo no divisible en este almacen
     */
    public void repartirComida(){
        ArrayList<Piscifactoria> piscis = Simulador.instancia.getPiscis();
        try {
            int numPiscis = piscis.size();
            int cantRepartCarne = carne/numPiscis;
            int cantRepartVeget = vegetal/numPiscis;
            for (Piscifactoria pisci : piscis) {
                if(cantRepartCarne>pisci.getComidaMax()){
                    pisci.addFood(pisci.getComidaMax(),0);
                    carne -= pisci.getComidaMax();
                }else{
                    pisci.addFood(cantRepartCarne,0);
                    carne -= cantRepartCarne;
                }
                if(cantRepartVeget>100){
                    pisci.addFood(0,pisci.getComidaMax());
                    vegetal -= pisci.getComidaMax();
                }else{
                    pisci.addFood(0,cantRepartVeget);
                    vegetal -= cantRepartVeget;
                }
            }
            Simulador.instancia.setPiscis(piscis);
        } catch (ArithmeticException e) {
            ErrorWriter.writeInErrorLog("Error al repartir comida desde el almacén central.");
        }
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
        try {
            return "------------------ Almacén central ------------------"+
            "\nCapacidad máxima: "+maxCapacidad+
            "\nComida animal: "+carne+"/"+maxCapacidad+". ("+((int)(carne/maxCapacidad)*100)+" %)"+
            "\nComida vegetal: "+vegetal+"/"+maxCapacidad+". ("+((int)(vegetal/maxCapacidad)*100)+" %)";
        } catch (ArithmeticException e) {
            ErrorWriter.writeInErrorLog("Error al devolver información del almacén central");
            return "";
        }
    }
}