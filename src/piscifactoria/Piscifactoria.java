package piscifactoria;
import java.util.ArrayList;

import helpers.Reader;
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
        this.tanques.add(new Tanque(this.tanques.size()+1, tipo));
        if (tipo.equals("rio")) {
            this.comidaMax = 25;
        } else {
            this.comidaMax = 100;
        }
    }

    public String getNombre(){
        return nombre;
    }

    public String getTipo(){
        return tipo;
    }

    public ArrayList<Tanque> getTanques() {
        return tanques;
    }

    public int getComidaMax() {
        return comidaMax;
    }

    public int getComidaAnimal() {
        return comidaAnimal;
    }

    public int getComidaVegetal() {
        return comidaVegetal;
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
            "Ocupación: " +ocupTotal+"/"+maxTotal+"("+((int)(ocupTotal/maxTotal)*100)+"%)");
            if(ocupTotal!=0){
        System.out.println(
            "Peces vivos: "+ vivosTotal +"/"+ ocupTotal +"("+((int)(vivosTotal/ocupTotal)*100)+"%)\n"+
            "Peces alimentados: "+ alimTotal +"/"+ vivosTotal +"("+((int)(alimTotal/vivosTotal)*100)+"%)\n"+
            "Peces adultos: "+ adultTotal +"/"+ vivosTotal +"("+((int)(adultTotal/vivosTotal)*100)+"%)\n"+
            "Hembras / Machos: "+hembrasTotal+"/"+machosTotal+"\n"+
            "Fértiles: "+ fertilesTotal +"/"+ vivosTotal +"("+((int)(fertilesTotal/vivosTotal)*100)+"%)\n"
        );
            }
        System.out.println(
            "Almacén de comida animal: "+ this.comidaAnimal + "("+((int)(this.comidaAnimal/this.comidaMax)*100) + "%)\n" +
            "Almacén de comida vegetal: "+ this.comidaVegetal + "("+((int)(this.comidaVegetal/this.comidaMax)*100) + "%)"
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
            "Depósito de comida animal al " + ((int)(this.comidaAnimal/this.comidaMax)*100) + "% de su capacidad." +
            "Depósito de comida vegetal al " + ((int)(this.comidaVegetal/this.comidaMax)*100) + "% de su capacidad."
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

    /**
     * Devuelve la cantidad total (todos los tanques) 
     * de peces vivos de la piscifactoría
     * @return el número de peces vivos
     */
    public int getTotalAlive(){ 
        int alive = 0;
        for(Tanque tanque : tanques){
            alive += tanque.vivos();
        }
        return alive;
    }

    /**
     * Devuelve la cantidad total (todos los tanques)
     * de peces de la piscifactoría
     * @return el número de peces
     */
    public int getNum(){
        int num = 0;
        for(Tanque tanque : tanques){
            num += tanque.ocupacion();
        }
        return num;
    }

    /**
     * Devuelve el espacio total para peces de la piscifactoría
     * @return el número de espacios para peces
     */
    public int getTotal(){
        int total = 0;
        for(Tanque tanque : tanques){
            total += tanque.getMaxSize();
        }
        return total;
    }

    /**
     * Muestra el texto del menú con los posibles tanques a seleccionar
     */
    private void menuTank(){
        int i = 1;
        for(Tanque tanque : tanques){
            System.out.println(i+". Tanque "+tanque.getNumTanque()+": "+tanque.getTipoPez());
            i++;
        }
    }

    /**
     * Permite seleccionar un tanque y lo devuelve
     * @return el tanque seleccionado
     */
    public Tanque selectTank(){
        menuTank();
        int opcion = Reader.readTheNumber();
        while (opcion<1 || opcion>tanques.size()) {
            System.out.println("Introduzca un número entero entre 1 y "+tanques.size());
            opcion = Reader.readTheNumber();
        }
        return tanques.get(opcion-1);
    }

    /**
     * Añade comida a la piscifactoría
     * @param food la cantidad de comida
     * @param tipoComida el tipo de comida (true animal, false vegetal)
     */
    public void addFood(int food, boolean tipoComida){
        if(tipoComida){
            comidaAnimal += food;
            System.out.println("Añadida "+food+" de comida animal");
        } else{
            comidaVegetal += food;
            System.out.println("Añadida "+food+" de comida vegetal");
        }
        showFood();
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

    /**
     * Añade un nuevo tanque a la piscifactoría
     */
    public void addTank(){
        this.tanques.add(new Tanque(this.tanques.size()+1, this.tipo));
        System.out.println("Nuevo tanque añadido a la piscifactoría "+this.nombre);
    }
}
