package piscifactoria;
import java.util.ArrayList;

import com.google.gson.annotations.JsonAdapter;

import adapters.PiscifactoriaAdapter;
import helpers.ErrorWriter;
import helpers.PremadeLogs;
import helpers.Reader;
import main.Simulador;
import peces.Pez;
import tanque.Tanque;

/**Objeto representativo de la piscifactoria */
@JsonAdapter(PiscifactoriaAdapter.class)
public class Piscifactoria {
    
    /** El nombre de la piscifactoría. */
    private String nombre;
    /** El tipo de piscifactoría (rio o mar) */
    private String tipo;
    /** La comida máxima que puede ser almacenada en los almacenes */
    private int comidaMax;
    /** El almacén de comida animal */  
    private int comidaAnimal;
    /** El almacén de comida vegetal */
    private int comidaVegetal;
    /** La lista de tanques en la piscifactoría */
    public ArrayList<Tanque> tanques;

    /** @return El nombre de la piscifactoría. */
    public String getNombre() {
        return nombre;
    }

    /** @return El tipo de piscifactoría (mar o rio) */
    public String getTipo() {
        return tipo;
    }

    /** @return La cantidad de comida animal almacenada. */
    public int getComidaAnimal() {
        return comidaAnimal;
    }

    /** @return La cantidad de comida vegetal almacenada */
    public int getComidaVegetal() {
        return comidaVegetal;
    }
    
    /** @return La lista de tanques en la piscifactoria */
    public ArrayList<Tanque> getTanques() {
        return tanques;
    }

    /** @return La cantidad de comida maxima que puede ser almacenada en cada almacen */
    public int getComidaMax() {
        return comidaMax;
    }

    /**
     * @param nombre el nombre de la piscifactoría
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param tipo el tipo de piscifactoría (rio o mar)
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @param comidaMax la capacidad para comida máxima
     */
    public void setComidaMax(int comidaMax) {
        this.comidaMax = comidaMax;
    }

    /**
     * @param comidaAnimal la cantidad de comida animal
     */
    public void setComidaAnimal(int comidaAnimal) {
        this.comidaAnimal = comidaAnimal;
    }

    /**
     * @param comidaVegetal la cantidad de comida vegetal
     */
    public void setComidaVegetal(int comidaVegetal) {
        this.comidaVegetal = comidaVegetal;
    }

    /**
     * @param tanques el array con los tanques
     */
    public void setTanques(ArrayList<Tanque> tanques) {
        this.tanques = tanques;
    }

    /** Constructor para la carga de datos */
    public Piscifactoria(){}

    /**
     * El constructor de una piscifactoría.
     * 
     * @param tipo  El tipo de piscifactoría (rio o mar)
     * @param nombre El nombre de la piscifactoría
     */
    public Piscifactoria(String tipo,String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.tanques = new ArrayList<>();
        this.tanques.add(new Tanque(1, tipo,nombre));
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
        try {
            System.out.println(
                "========== " + this.nombre + " ==========\n" +
                "Tanques: " + this.tanques.size() + "\n" +
                "Ocupación: " +ocupTotal+"/"+maxTotal+"("+(int)(((double)ocupTotal)/maxTotal*100)+"%)");
                if(ocupTotal!=0){
                    System.out.println("Peces vivos: "+ vivosTotal +"/"+ ocupTotal +"("+(int)(((double)vivosTotal)/ocupTotal*100)+"%)");
                }else{System.out.println("Peces vivos: "+ vivosTotal +"/"+ ocupTotal +"(0%)");}
                if(alimTotal!=0&&vivosTotal!=0){
                    System.out.println("Peces alimentados: "+ alimTotal +"/"+ vivosTotal +"("+""+(int)(((double)alimTotal)/vivosTotal*100)+"%)");
                }else{System.out.println("Peces alimentados: "+ alimTotal +"/"+ vivosTotal +"(0%)");}
                if(adultTotal!=0&&vivosTotal!=0){
                    System.out.println("Peces adultos: "+ adultTotal +"/"+ vivosTotal +"("+(int)(((double)adultTotal)/vivosTotal*100)+"%)");
                }else{System.out.println("Peces adultos: "+ adultTotal +"/"+ vivosTotal +"(0%)");}
                System.out.println("Hembras / Machos: "+hembrasTotal+"/"+machosTotal);
                if(fertilesTotal!=0&&vivosTotal!=0){
                    System.out.println("Fértiles: "+ fertilesTotal +"/"+ vivosTotal +"("+(int)(((double)fertilesTotal)/vivosTotal*100)+"%)");
                }else{System.out.println("Fértiles: "+ fertilesTotal +"/"+ vivosTotal +"(0%)");}
        } catch (ArithmeticException e) {
            ErrorWriter.writeInErrorLog("Error al intentar mostrar información de la piscifactoría " + this.nombre);
        }
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
        tanque.showCapacity();
    }

    /**
     * Método que muestre la cantidad comida actual de cada almacén en la piscifactoría.
     */
    public void showFood() {
        System.out.println(
            "Depósito de comida animal de la piscifactoría "+nombre+" al " + (this.comidaAnimal/this.comidaMax)*100 + "% de su capacidad. ["+this.comidaAnimal+"/"+this.comidaMax+"]\n" +
            "Depósito de comida vegetal de la piscifactoría "+nombre+" al " + (this.comidaVegetal/this.comidaMax)*100 + "% de su capacidad.["+this.comidaVegetal+"/"+this.comidaMax+"]"
        );
    }

    /**
     * Método que se encarga de hacer crecer y alimentar a todos los peces
     * 
     * @return  La cantidad de dinero conseguido por vender peces.
     */
    public int[] nextDay() {
        int pecesVendidos = 0;
        int dineroVendido = 0;
        int[] datos;
        for (Tanque tank : tanques) {
            datos = tank.nextDay(comidaAnimal,comidaVegetal);
            comidaAnimal = datos[2];
            comidaVegetal = datos[3];
            pecesVendidos += datos[0];
            dineroVendido += datos[1];

        }
        System.out.println("Piscifactoría "+nombre+": "+pecesVendidos+" peces vendidos por "+dineroVendido+" monedas");
        return new int[]{pecesVendidos,dineroVendido};
    }

    /**
     * Método que vende los peces óptimos en cada tanque.
     * 
     * @return  La cantidad de dinero ganado por vender a los peces
     */
    public int[] sellFish() {
        int dineroVendido = 0;
        int pecesVendidos = 0;
        for (Tanque tank : tanques) {
            try{
                for(int i=0;i<tank.getPeces().size();i++){
                    if (tank.getPeces().get(i)!=null&&tank.getPeces().get(i).isAdulto() && tank.getPeces().get(i).isVivo()) {
                        dineroVendido += tank.getPeces().get(i).getMonedas();
                        pecesVendidos++;
                        Simulador.instancia.orca.registrarVenta(tank.buscaNombre(), tank.getPeces().get(i).getMonedas());
                        ArrayList<Pez> newPeces = tank.getPeces();
                        newPeces.remove(i);
                        tank.setPeces(newPeces);
                    }
                }
            } catch(NullPointerException e) {
                ErrorWriter.writeInErrorLog("Error al intentar vender peces del tanque " + tank.getNumTanque() + " de la piscifactoría " + this.nombre);
            }
        }
        PremadeLogs.sellFish(pecesVendidos,this.nombre,dineroVendido);
        System.out.println("Piscifactoría "+nombre+": "+pecesVendidos+" peces vendidos por "+dineroVendido+" monedas");
        return new int[]{dineroVendido,pecesVendidos};
    }

    /**
     * Metodo que retira peces para su venta en un pedido de cliente
     * @param fishName Nombre del pez a retirar
     * @param maxAmount Maxima cantidad de peces a retirar
     * @return Numero de peces retirados
     */
    public int sendFish(String fishName,int maxAmount){
        int counter = 0;
        if (maxAmount!=-1) {
            System.out.println("Seleccione un tanque para retirar los peces de este pedido");
            int option = selectTank();
            if(tanques.get(option).getTipoPez().toLowerCase().trim().equals(fishName.toLowerCase().trim())){
                ArrayList<Pez> tanq = tanques.get(option).getPeces();
                for (int i = 0; i < tanq.size(); i++) {
                    if (tanq.get(i).isAdulto()&&maxAmount!=counter) {
                        tanq.remove(i);
                        tanques.get(option).setPeces(tanq);
                        counter++;
                    }
                }
            }else{
                System.out.println("El tanque seleccionado no posee el tipo de peces requeridos");
            }
        }
        return counter;
    }

    /**
     * Método que mejora los almacenes de comida.
     * 
     * @return  Si se ha mejorado o no.
     */
    public void upgradeFood() {
        if (this.tipo.equals("mar")) {
            this.comidaMax += 100;
        } else {
            this.comidaMax += 25;
        }
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
        System.out.println("Seleccione un tanque:");
        for(Tanque tanque : tanques){
            System.out.print(i+". Tanque "+tanque.getNumTanque());
            if(tanque.ocupacion()==0){
                System.out.println();
            }else{
                System.out.println(": "+tanque.getTipoPez());
            }
            i++;
        }
    }

    /**
     * Permite seleccionar un tanque y lo devuelve
     * @return el tanque seleccionado
     */
    public int selectTank(){
        menuTank();
        int opcion = Reader.readTheNumber(1,tanques.size());
        return opcion-1;
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
     * Añade un nuevo tanque a la piscifactoría
     */
    public void addTank(){
        this.tanques.add(new Tanque(this.tanques.size()+1, this.tipo,nombre));
        System.out.println("Nuevo tanque añadido a la piscifactoría "+this.nombre);
    }

    @Override
    public String toString() {
        return "Piscifactoria de "+tipo+" con "+tanques.size()+" tanques";
    }
}