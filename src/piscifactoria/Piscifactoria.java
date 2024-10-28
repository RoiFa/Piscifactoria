package piscifactoria;
import java.util.ArrayList;

import helpers.RNG;
import main.Almacen;
import peces.Pez;
import peces.rio.Koi;
import tanque.Tanque;

/**Objeto representativo de la piscifactoria */
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

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getComidaAnimal() {
        return comidaAnimal;
    }

    public int getComidaVegetal() {
        return comidaVegetal;
    }

    /**
     * El constructor de una piscifactoría.
     * 
     * @param tipo  El tipo de piscifactoría (Río o mar)
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
        this.comidaAnimal = 0;
        this.comidaVegetal = 0;
    }

    /**
     * Método para añadir comida en los almacenes.
     * 
     * @param addAnimal     La comida animal a añadir
     * @param addVegetal    La comida vegetal a añadir
     * @return              Las sobras de cada tipo de comida
     */
    public int[] addFood(int addAnimal, int addVegetal) {
        int sobraAnimal = 0;
        int sobraVegetal = 0;

        if (comidaAnimal + addAnimal > comidaMax) {
            comidaAnimal = comidaMax;
            sobraAnimal = addAnimal - comidaMax;
        } else {
            comidaAnimal += addAnimal;
        }

        if (comidaVegetal + addVegetal > comidaMax) {
            comidaVegetal = comidaMax;
            sobraVegetal = addVegetal - comidaMax;
        } else {
            comidaVegetal += addVegetal;
        }

        return new int[]{sobraAnimal, sobraVegetal};
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

    /**
     * Método que muestra la capacidad de un tanque que haya en la piscifactoría.
     * 
     * @param tanque    El tanque especificado
     */
    public void showCapacity(Tanque tanque) {
        tanque.showCapacity(nombre);
    }

    /**
     * Método que muestre la cantidad comida actual de cada almacén en la piscifactoría.
     */
    public void showFood() {
        System.out.println(
            "Depósito de comida animal al " + ((this.comidaAnimal/this.comidaMax)*100) + "% de su capacidad." +
            "Depósito de comida vegetal al " + ((this.comidaVegetal/this.comidaMax)*100) + "% de su capacidad."
        );
    }

    /**
     * Método que se encarga de hacer crecer y alimentar a todos los peces
     * 
     * @return  La cantidad de dinero conseguido por vender peces.
     */
    public int nextDay() {
        int dineroVendido = 0;
        for (Tanque tank : tanques) {
            for (Pez pez : tank.getPeces()) {
                int[] comida = pez.grow(comidaAnimal, comidaVegetal);
                comidaAnimal -= comida[0];
                comidaVegetal -= comida[1];

                if (comidaAnimal <= 0 || comidaVegetal <= 0) {
                    Almacen.repartirComida(0,0);
                }

                if (pez.getEdad() == pez.getOptimo()) {
                    if (pez instanceof Koi && RNG.RandomInt(10) == 1) {
                        pez.setMonedas(pez.getMonedas()+5);
                    } else {
                        dineroVendido += pez.getMonedas();
                        pez = null;
                    }
                }
            }
        }
        return dineroVendido;
    }

    /**
     * Método que vende los peces óptimos en cada tanque.
     * 
     * @return  La cantidad de dinero ganado por vender a los peces
     */
    public int sellFish() {
        int dineroVendido = 0;
        for (Tanque tank : tanques) {
            for (Pez pez : tank.getPeces()) {
                if (pez.isAdulto() && pez.isVivo()) {
                    dineroVendido += pez.getMonedas();
                    pez = null;
                }
            }
        }
        return dineroVendido;
    }

    /**
     * Método que mejora los almacenes de comida.
     * 
     * @param dinero    El dinero actual de la simulación.
     * @return          Si se ha mejorado o no.
     */
    public boolean upgradeFood(int dinero) {
        if (this.tipo.equals("mar")) {
            if (dinero >= 200 && this.comidaMax < 1000) {
                this.comidaMax += 100;
                return true;
            }
            
        } else if (this.tipo.equals("rio")) {
            if (dinero >= 50 && this.comidaMax < 250) {
                this.comidaMax += 25;
                return true;
            }
        }
        return false;
    }
}
