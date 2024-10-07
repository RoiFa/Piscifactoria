package piscifactoria;
import java.util.ArrayList;

import tanque.Tanque;

public class Piscifactoria {
    /** El nombre de la piscifactoría. */
    private String nombre;
    /** El tipo de piscifactoría (Río o mar) */
    private String tipo;
    /** La lista de tanques en la piscifactoría */
    private ArrayList<Tanque> tanques;
    /** La comida máxima que puede ser almacenada en los almacenes */
    private int comidaMax;
    /** El almacén de comida animal */
    private int comidaAnimal;
    /** El almacén de comida vegetal */
    private int comidaVegetal;

    /**
     * El constructor de una piscifactoría.
     * 
     * @param tipo  El tipo de piscifactoría (Río o mar)
     */
    public Piscifactoria(String tipo) {
        this.tipo = tipo;
        this.tanques.add(new Tanque(0, tipo));
        if (tipo.equals("rio")) {
            this.comidaMax = 25;
        } else {
            this.comidaMax = 100;
        }
    }

    /**
     * Método que muestra el estado actual de la piscifactoría.
     */
    public void showStatus() { //TODO encontrar una forma de conseguir la informacion de los peces
        System.out.println(
            "========== " + this.nombre + " ==========\n" +
            "Tanques: " + this.tanques.size() + "\n" +
            "Ocupación: " + "\n" +
            "Almacén de comida animal: " + ((this.comidaAnimal/this.comidaMax)*100) + "%\n" +
            "Almacén de comida vegetal: " + ((this.comidaVegetal/this.comidaMax)*100) + "%"
        );
    }

    /**
     * Método que muestra el estado actual de todos los tanques que hay en la piscifactoría.
     */
    public void showTankStatus() {
        for (Tanque tanque : tanques) {
            tanque.showStatus();
        }
    }

    /**
     * Método que muestra el estado de todos los peces de un tanque determinado.
     * 
     * @param tanque    El tanque del que mostrar el estado.
     */
    public void showFishStatus(Tanque tanque) {
        tanque.showFishStatus();
    }

    public void showCapacity(Tanque tanque) {
        tanque.showCapacity(nombre);
    }

    public void showFood() {
        System.out.println(
            "Depósito de comida animal al " + ((this.comidaAnimal/this.comidaMax)*100) + "% de su capacidad." +
            "Depósito de comida vegetal al " + ((this.comidaVegetal/this.comidaMax)*100) + "% de su capacidad."
        );
    }

    public void nextDay() {
        //TODO terminar
    }

    public void sellFish() {
        //TODO termiar
    }

    public void upgradeFood() {
        //TODO terminar
    }
}
