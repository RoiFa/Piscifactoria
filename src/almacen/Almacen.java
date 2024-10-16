package almacen;

/**
 * Clase que representa al almacén central
 */
public class Almacen {

    /** La comida animal almacenada */
    private int comidaAnimal;
    /** La comida vegetal almacenada*/
    private int comidaVegetal;
    /** La capacidad máxima de comida de cada tipo que se puede almacenar */
    private int maxCapacidad;

    /**
     * Constructor para el almacén central
     */
    public Almacen(){
        comidaAnimal = 0;
        comidaVegetal = 0;
        maxCapacidad = 200;
    }

    /**
     * @return la cantidad de comida animal disponible
     */
    public int getComidaAnimal() {
        return comidaAnimal;
    }

    /**
     * @return la cantidad de comida vegetal disponible
     */
    public int getComidaVegetal() {
        return comidaVegetal;
    }

    /**
     * @return la cantidad máxima de comida que se puede almacenar
     */
    public int getMaxCapacidad() {
        return maxCapacidad;
    }

    /**
     * Añade una cantidad de comida al almacén
     * @param comida la cantidad de comida a añadir
     * @param tipo el tipo de comida (true animal, false vegetal)
     */
    public void addFood(int comida, boolean tipo){
        if(tipo){
            comidaAnimal += comida;
            System.out.println("Comida animal actual: "+comidaAnimal);
        } else{
            comidaVegetal += comida;
            System.out.println("Comida vegetal actual: "+comidaAnimal);
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
        return "------------------ Almacén central ------------------"+
        "\nCapacidad máxima: "+maxCapacidad+
        "\nComida animal: "+comidaAnimal+"/"+maxCapacidad+". ("+((comidaAnimal/maxCapacidad)*100)+" %)"+
        "\nComida vegetal: "+comidaVegetal+"/"+maxCapacidad+". ("+((comidaVegetal/maxCapacidad)*100)+" %)";
    }
}