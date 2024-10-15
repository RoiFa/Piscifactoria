package piscifactoria;
import java.io.Reader;
import java.util.ArrayList;

import tanque.Tanque;

public class Piscifactoria {
    /** El nombre de la piscifactoría. */
    private String nombre;
    /** El tipo de piscifactoría (rio o mar) */
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
     * @param tipo  El tipo de piscifactoría (rio o mar)
     * @param nombre El nombre de la piscifactoría
     */
    public Piscifactoria(String tipo,String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
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
    public void showStatus() {
        int maxTotal=0;
        int ocupTotal=0;
        int vivosTotal=0;
        int alimTotal=0;
        int adultTotal=0;
        int machosTotal=0;
        int hembrasTotal=0;
        int fertilesTotal=0;
        for(int i=0;i<tanques.size();i++){
            maxTotal += tanques.get(i).getMaxSize();
            ocupTotal += tanques.get(i).ocupacion();
            vivosTotal += tanques.get(i).vivos();
            alimTotal += tanques.get(i).alimentados();
            adultTotal += tanques.get(i).adultos();
            machosTotal += tanques.get(i).machos();
            hembrasTotal += tanques.get(i).hembras();
            fertilesTotal += tanques.get(i).fertiles();
        }
        System.out.println(
            "========== " + this.nombre + " ==========\n" +
            "Tanques: " + this.tanques.size() + "\n" +
            "Ocupación: " +ocupTotal+"/"+maxTotal+"("+((ocupTotal/maxTotal)*100)+"%)");
            if(ocupTotal!=0){
        System.out.println(
            "Peces vivos: "+ vivosTotal +"/"+ ocupTotal +"("+((vivosTotal/ocupTotal)*100)+"%)\n"+
            "Peces alimentados: "+ alimTotal +"/"+ vivosTotal +"("+((alimTotal/vivosTotal)*100)+"%)\n"+
            "Peces adultos: "+ adultTotal +"/"+ vivosTotal +"("+((adultTotal/vivosTotal)*100)+"%)\n"+
            "Hembras / Machos: "+hembrasTotal+"/"+machosTotal+"\n"+
            "Fértiles: "+ fertilesTotal +"/"+ vivosTotal +"("+((fertilesTotal/vivosTotal)*100)+"%)\n"
        );
            }
        System.out.println(
            "Almacén de comida animal: "+ this.comidaAnimal + "("+((this.comidaAnimal/this.comidaMax)*100) + "%)\n" +
            "Almacén de comida vegetal: "+ this.comidaVegetal + "("+((this.comidaVegetal/this.comidaMax)*100) + "%)"
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



    public int selectTank(){
        int i = 1;
        for(Tanque tanque : tanques){
            System.out.println(i+". Tanque "+tanque.getNumTanque()+": "); //TODO acabar
        }
    }

    /**
     * Elimina los peces muertos de los tanques de la piscifactoría
     */
    public void cleanTank(){
        for(Tanque tanque : tanques){
            tanque.cleanTank();
        }
    }

    /**
     * Elimina todos los peces de un tanque de la piscifactoría
     * independientemente de su estado
     */
    public void emptyTank(Tanque tanque){
        tanques.get(tanques.indexOf(tanque)).emptyTank();
    }
}
