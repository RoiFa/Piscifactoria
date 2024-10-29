package main;
import java.util.ArrayList;
import estadisticas.Estadisticas;
import helpers.Reader;
import monedas.Monedas;
import peces.Pez;
import peces.mar.*;
import peces.doble.*;
import peces.rio.*;
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;

public class Simulador {
    
    /** Los días que han pasado */
    private static int dias=0;
    /** Las piscifactorías que hay */
    private static ArrayList<Piscifactoria> piscis= new ArrayList<>();
    /** El nombre de la entidad */
    private static String nombre;
    /** Las monedas */
    public static Monedas monedas = new Monedas();
    /** El almacén de comida */
    private static Almacen almacen = null;
    /**Recopila las estadisticas del programa */
    public static Estadisticas estadisticas;

    /**
     * Inicializa el sistema desde cero
     */
    private static void init(){
        String[] nomPeces = {AlmacenPropiedades.ABADEJO.getNombre(),AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
            AlmacenPropiedades.BAGRE_CANAL.getNombre(),AlmacenPropiedades.BESUGO.getNombre(),
            AlmacenPropiedades.CARPA.getNombre(),AlmacenPropiedades.COBIA.getNombre(),
            AlmacenPropiedades.DORADA.getNombre(),AlmacenPropiedades.KOI.getNombre(),
            AlmacenPropiedades.PEJERREY.getNombre(),AlmacenPropiedades.RODABALLO.getNombre(),
            AlmacenPropiedades.SALMON_CHINOOK.getNombre(),AlmacenPropiedades.TILAPIA_NILO.getNombre()};
            estadisticas = new Estadisticas(nomPeces);
        
        System.out.println("Deme el nombre de la nueva empresa:");
        nombre = Reader.readTheLine();
        
        System.out.println("Deme el nombre de la primera piscifactoría (río)");
        String nomPisc = Reader.readTheLine();
        while (nomPisc.equals("")) {
            System.out.println("Introduzca un nombre valido");
            nomPisc = Reader.readTheLine();
        }
        piscis.add(new Piscifactoria("rio",nomPisc));
        piscis.get(0).addFood(25,25);
        monedas.setCantidad(100);
    }

    /**
     * Muestra el texto con las diferentes opciones a realizar 
     */
    private static void menu(){
        System.out.println("1. Estado general");
        System.out.println("2. Estado piscifactoría");
        System.out.println("3. Estado tanques");
        System.out.println("4. Informes");
        System.out.println("5. Ictiopedia");
        System.out.println("6. Pasar día");
        System.out.println("7. Comprar comida");
        System.out.println("8. Comprar peces");
        System.out.println("9. Vender peces");
        System.out.println("10. Limpiar tanques");
        System.out.println("11. Vaciar tanque");
        System.out.println("12. Mejorar");
        System.out.println("13. Pasar varios días");
        System.out.println("14. Salir");
    }

    /**
     * Muestra el texto para seleccionar una piscifactoría
     */
    private static void menuPisc(){
        
        int i = 1;
        System.out.println("Seleccione una piscifactoría:");
        System.out.println("-------------------------- Piscifactorías --------------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        for(Piscifactoria p : piscis){
            System.out.println(i+".- "+p.getNombre()+" ["+p.getTotalAlive()+"/"+p.getNum()+"/"+p.getTotal()+"]");
            i++;
        }
        System.out.println("0. Cancelar");
    }

    /**
     * Permite seleccionar una piscifactoría
     * @return un entero con la opción seleccionada
     */
    private static int selectPisc(){
        int opcion = -1;
        while (opcion<0 || opcion>piscis.size()) {
            menuPisc();
            opcion = Reader.readTheNumber();
        }
        if(opcion==0){
            System.out.println("Operación cancelada");
        }
        return opcion-1;
    }

    /**
     * Muestra el estado de las piscifactorías, su comida disponible, 
     * el día actual y las monedas disponibles y la info del almacén si se dispone de el
     */
    private static void showGeneralStatus(){
        for(Piscifactoria p : piscis){
            p.showStatus();
            p.showFood();
            System.out.println();
        }
        System.out.println("Día actual: "+dias);
        System.out.println("Monedas disponibles: "+monedas.getCantidad());
        System.out.println();
        if(almacen!=null){
            almacen.toString();
        }
    }

    /**
     * Permite seleccionar una piscifactoría y mostrar el estado de sus tanques
     */
    private static void showSpecificStatus(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            piscis.get(piscifactoria).showTankStatus();
        }
    }

    /**
     * Permite seleccionar un tanque y muestra la información de sus peces
     */
    private static void showTankStatus(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            int tank = piscis.get(piscifactoria).selectTank();
            piscis.get(piscifactoria).tanques.get(tank).showFishStatus();
        }
    }

    /**
     * Muestra el nombre, el número de comprados y nacidos, el número de vendidos 
     * y el dinero que se ha ganado con los peces del sistema
     */
    private static void showStats(){
        estadisticas.mostrar();
    }

    /**
     * Muestra la información de un pez a seleccionar entre los implementados
     */
    private static void showIctio(){

        Pez[] peces = {new Abadejo(),new ArenqueDelAtlantico(),new BagreDeCanal(),new Besugo(),new Carpa(),
                       new Cobia(),new Dorada(),new Koi(),new Pejerrey(),new Rodaballo(),
                       new SalmonChinook(),new TilapiaDelNilo()};
        int pez = 0;
        
        System.out.println("---------- Seleccione un pez ----------");
        System.out.println("1. Abadejo\n2. Arenque del Atlántico\n3. Bagre de Canal\n4. Besugo\n5. Carpa"+
        "\n6. Cobia\n7. Dorada\n8. Koi\n9. Pejerrey\n10. Rodaballo\n11. Salmon Chinook\n12. Tilapia del Nilo\n13. Volver al menú"
        );
        pez = Reader.readTheNumber();
        while (pez<1 || pez>13 || pez!=13) {
            System.out.println("Introduzca un entero entre 1 y 13");
            pez = Reader.readTheNumber();
        }
        if(pez==13){
            System.out.println("Vuelta con éxito");
        } else{
            peces[pez-1].toString();
        }
    
        
    }

    /**
     * Avanza un día haciendo la lógica necesaria y muestra un mensaje con los
     * peces vendidos por piscifactoría y despues en general
     */
    private static void nextDay(){
        dias++;
        for(Piscifactoria p : piscis){
            p.nextDay();
        }
    }

    /**
     * Añade comida a una piscifactoría seleccionada
     * o al almacén central si se dispone de el
     */
    private static void addFood(){
        System.out.println("Introduzca el tipo de comida:\n1.animal\n2.vegetal");
        int tipoComida = Reader.readTheNumber()+1;
        while (tipoComida!=1&&tipoComida!=2) {
            System.out.println("Especifique animal o vegetal");
            tipoComida = Reader.readTheNumber();
        }
        System.out.println("Introduzca la cantidad (5, 10, 25 o llenar). El coste es 1 moneda por unidad y 5 monedas de descuento cada 25 unidades");
        String cantidad = Reader.readTheLine();
        while (!cantidad.equals("5") && !cantidad.equals("10") && !cantidad.equals("25") && !cantidad.equalsIgnoreCase("llenar")) {
            System.out.println("Especifique 5, 10, 25 o llenar");
            cantidad = Reader.readTheLine();
        }

        int espacio;
        int add = 0;
        int coste=0;
        if(almacen==null){
            int piscifactoria = selectPisc();
            if(piscifactoria !=-1){
                if(cantidad!="llenar"){
                    add = Integer.parseInt(cantidad);
                }
                if(tipoComida!=0){
                    espacio = piscis.get(piscifactoria).getComidaMax()-piscis.get(piscifactoria).getComidaAnimal();
                    if(add==0){
                        add = espacio - (5*((int) espacio/25));
                    }
                    if(add<=espacio){
                        coste = add - (5*((int) add/25));
                    }

                    if(add > espacio){
                        System.out.println("Cantidad a añadir mayor de lo posible");
                    }else{
                        System.out.println("Coste: "+coste+" monedas. Disponible: "+monedas.getCantidad()+" monedas");
                        System.out.println("Confirmar compra?");
                        System.out.println("1. Si    2. No");
                        if(Reader.readTheNumber()==1){
                            if(monedas.getCantidad()>=coste){
                                Monedas.gastar(coste);
                                if(tipoComida==1){
                                    piscis.get(piscifactoria).addFood(add, 0);
                                   
                                }else{
                                    piscis.get(piscifactoria).addFood(0,add);
                                }
                                
                            } else{
                                System.out.println("Monedas insuficientes");
                            }
                        }
                    }
                    
                }
            }

        } else{
            
            if(cantidad!="llenar"){
                add = Integer.parseInt(cantidad);
            }
            if(tipoComida!=0){
                espacio = Almacen.getMaxCapacidad()-Almacen.getCarne();
                if(add==0){
                    add = espacio - (5*((int) espacio/25));
                }
                if(add<=espacio){
                    coste = add - (5*((int) add/25));
                }
                System.out.println("Coste: "+coste+" monedas. Disponible: "+monedas.getCantidad()+" monedas");
                System.out.println("Confirma la compra?(1.Si/2.No)");
                int cont = escogeOpciones(1,2);
                if(cont==1){
                    if(monedas.getCantidad()>=coste){
                        monedas.gastar(coste);
                        almacen.addFood(add, true);
                    } else{
                        System.out.println("Monedas insuficientes");
                    }
                }
            } else{
                espacio = Almacen.getMaxCapacidad()-Almacen.getVegetal();
                if(add==0){
                    add = espacio;
                }
                if(add<=espacio){
                    coste = add - (5*((int) add/25));
                } else{
                    coste = espacio;
                }
                System.out.println("Coste: "+coste+" monedas. Disponible: "+monedas.getCantidad()+" monedas");
                int cont = escogeOpciones(1,2);
                if(cont==1){
                    if(monedas.getCantidad()>=coste){
                        monedas.gastar(coste);
                        almacen.addFood(add, false);
                    } else{
                        System.out.println("Monedas insuficientes");
                    }
                }
            }
            piscis = Almacen.repartirComida(0,0);

                if(add > espacio){
                    System.out.println("Cantidad a añadir mayor de lo posible");
                }else{
                    System.out.println("Coste: "+coste+" monedas. Disponible: "+monedas.getCantidad()+" monedas");
                    System.out.println("Confirmar compra?");
                    System.out.println("1. Si    2. No");
                    if(Reader.readTheNumber()==1){
                        if(monedas.getCantidad()>=coste){
                            Monedas.gastar(coste);
                            if(tipoComida==1){
                                almacen.addFood(add, true);
                            }else{
                                almacen.addFood(add, false);
                            }
                            
                        } else{
                            System.out.println("Monedas insuficientes");
                        }
                    }
                }
                
            }
        }
    

        


    private static void addFish(){
        int opcion = selectPisc();
        int tankSelec = piscis.get(opcion).selectTank();
        piscis.get(opcion).tanques.get(tankSelec).addFish(false);
    }

    /**
     * Vende todos los peces adultos vivos de una piscifactoría
     * a la mitad de dinero de lo normal
     */
    private static void sell(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            Monedas.setCantidad(piscis.get(piscifactoria).sellFish());
        }
    }


    /**
     * Elimina todos los peces de un tanque de una piscifactoría
     * independientemente de su estado
     */
    private static void emptyTank(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            int option = piscis.get(piscifactoria).selectTank();
            piscis.get(piscifactoria).tanques.get(option).emptyTank();
        }
    }


    /**
     * Elimina los peces muertos de un tanque seleccionado
     */
    private static void cleanTank(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            piscis.get(piscifactoria).cleanTank();
        }
    }
    

    /**
     * Permite hacer mejoras o comprar nuevas estructuras
     */
    private static void upgrade(){
        
        int opcion = 0;
        while (opcion<1 || opcion>3) {
            System.out.println("1. Comprar edificios");
            System.out.println("2. Mejorar edificios");
            System.out.println("3. Cancelar");
            opcion = escogeOpciones(1,3);

            switch (opcion) {
                case 1:
                    opcion = comprar();
                    break;
                case 2:
                    opcion = mejorar();
                    break;
                case 3:
                    System.out.println("Operación cancelada");
                    break;
            }
        }

    }

    /**
     * Se encarga de la opción comprar del método upgrade
     * 
     * @return  Si se ha realizado la operación correctamente (0) o no (1)
     */

    private static int comprar(){

        int opcion = 0;

        while (opcion<1 || opcion>3) {
            System.out.println("1. Piscifactoría");
            System.out.println("2. Almacén central: 2000 monedas. Disponible: "+monedas.getCantidad()+" monedas");
            System.out.println("3. Volver");

            opcion = Reader.readTheNumber();

            switch (opcion) {
                case 1:
                    opcion = comparPisc();
                    break;
                case 2:
                    if(almacen==null){
                        if(monedas.getCantidad()>=2000){
                            Monedas.gastar(2000);
                            almacen = new Almacen();
                            System.out.println("Monedas restantes: "+monedas.getCantidad());
                        } else{
                            System.out.println("Monedas insuficientes");
                        }
                    } else{
                        System.out.println("Ya se dispone del almacén");
                    }
                    break;

                case 3:
                    System.out.println("Cancelando...");
                    opcion = 0;
                    return opcion;
            }
        }
        return 1;
    }

    /**
     * Método que se encarga de la lógica de comprar piscifactorías.
     * 
     * @return  Si se ha realizado la operación correctamente (0) o no (1)
     */
    private static int comparPisc() {
        System.out.println("Elige el tipo de piscifactoría a comprar:\n" +
        "1.- Río\n" +
        "2.- Mar\n" +
        "3.- Volver"
        );
        int buyPisc = escogeOpciones(1,3);

        if (buyPisc != 3) {
            int numPisc = 0;
            for (Piscifactoria p : piscis) {
                if (p.getTipo().equals(buyPisc == 1 ? "rio" : "mar")) {
                    numPisc++;
                }
            }
            int coste = buyPisc == 1 ? 500 + 500 * numPisc : 2000 + 2000 * numPisc;
            System.out.println("Coste: " + coste + " monedas. Disponibles: " + monedas.getCantidad() + " monedas.");
            if (coste < monedas.getCantidad()) {
                System.out.println("Monedas insuficientes. Cancelando operación.");
            } else {
                System.out.println("Desea proseguir con la operación? (1: sí; 2: no)");
                int cont = escogeOpciones(1,2);
                if (cont == 1) {
                    Monedas.gastar(coste);
                    System.out.println("Introduzca el nombre de la nueva piscifactoría");
                    String nombre = Reader.readTheLine();
                    while (nombre.equals("")) {
                        System.out.println("Nombre no válido. Vuelva a introducir un nombre");
                        nombre = Reader.readTheLine();
                    }
                    piscis.add(new Piscifactoria(buyPisc == 1 ? "rio" : "mar",nombre));
                    return 0;
                } else {
                    System.out.println("Cancelando operación.");
                }
            }
        }
        return 1;
    }

    /**
     * Se encarga de la opción mejorar del método upgrade
     */
    private static int mejorar(){

        int opcion = 0;

        while (opcion<1 || opcion>3) {
            System.out.println("1. Piscifactoria");
            System.out.println("2. Almacén central");
            System.out.println("3. Volver");

            int subOpcion = escogeOpciones(1, 3);

            switch (subOpcion) {
                case 1:
                    System.out.println("1. Comprar tanque");
                    System.out.println("2. Aumentar almacén de comida");
                    System.out.println("3. Volver");

                    int mejora = escogeOpciones(1,3);

                    switch (mejora) {
                        case 1:
                            opcion = upgradePisc();
                            break;
                        case 2:
                            opcion = upgradeAlmacen();
                            break;
                        case 3:
                            System.out.println("Cancelando...");
                            return 0;
                    }
                case 2:

                    opcion = upgradeCentral();

                    break;

                case 3:
                    return 0;
            }
        }
        return 2;

    }

    /**
     * Métdo encargado de la lógica de mejorar una piscifactoría.
     * 
     * @return  Si se ha completado (0) o no (1)
     */

    private static int upgradePisc() {
        int piscifactoria = selectPisc();
        if (piscifactoria != -1) {
            int numTanques = piscis.get(piscifactoria).getTanques().size();
            if (numTanques < 10) {
                int costeTanque = piscis.get(piscifactoria).getTipo().equals("rio") ? 150 + 150 * numTanques : 600 + 600 * numTanques;
                System.out.println("Coste del nuevo tanque: "+costeTanque+" monedas. Disponible: "+monedas.getCantidad()+" monedas");
                System.out.println("Desea proseguir con la operación? (1: sí; 2: no)");
                int cont = escogeOpciones(1,2);
                if(cont==1){
                    if(monedas.getCantidad()>=costeTanque){
                        monedas.gastar(costeTanque);
                        piscis.get(piscifactoria).addTank();
                    } else{
                        System.out.println("Monedas insuficientes");
                        return 1;
                    }
                }
            } else {
                System.out.println("Ya no se admiten más tanques en la piscifactoría");
                return 1;
            }
        }
        return 0;
    }

    /**
     * Métdo encargado de la lógica de mejorar un almacén de una piscifactoría. 
     * 
     * @return  Si se ha completado (0) o no (1)
     */
    private static int upgradeAlmacen() {
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            int coste = piscis.get(piscifactoria).getTipo().equals("rio") ? 50 : 200;
            if (monedas.getCantidad() >= coste) {
                System.out.println("Coste mejora almacén de comida: " + coste + " monedas. Disponibles: "+monedas.getCantidad()+" monedas");
                System.out.println("Desea proseguir con la operación? (1: sí; 2: no)");
                int cont = escogeOpciones(1,2);
                if (cont == 1) {
                    if (piscis.get(piscifactoria).upgradeFood()) {
                        monedas.gastar(coste);
                    } else {
                        System.out.println("Ha habido un error. Cancelando.");
                        return 1;
                    }
                } else {
                    System.out.println("Cancelando.");
                    return 1;
                }
            } else {
                System.out.println("Monedas insuficientes. Cancelando.");
                return 1;
            }
        }
        return 0;
    }

    /**
     * Método encargado de la lógica de mejorar el almacén central.
     * 
     * @return  Si se ha completado (0) o no (1)
     */
    private static int upgradeCentral() {
        if(almacen!=null){
            System.out.println("Aumentar capacidad: 200 monedas. Disponibles: "+monedas.getCantidad()+" monedas");
            System.out.println("Desea proseguir con la operación? (1: sí; 2: no)");
            int cont = escogeOpciones(1,2);
            if(cont==1){
                if(monedas.getCantidad()>=200){
                    monedas.gastar(200);
                    almacen.upgrade();
                } else{
                    System.out.println("Monedas insuficientes");
                    return 1;
                }
            } else{
                System.out.println("Operación cancelada");
                return 1;
            }
        } else{
            System.out.println("No se dispone de almacén central");
            return 1;
        }
        return 0;
    }

    /**
     * Permite pasar entre 1 y 5 días de golpe con sus consecuencias
     */

    private static void forwardDays(){
        System.out.println("Indique entre 1 y 5 cuántos días desea pasar");
        int numDias = Reader.readTheNumber();
        while (numDias<1 || numDias>5) {
            System.out.println("Introduzca un entero entre 1 y 5");
            numDias = Reader.readTheNumber();
        }
        for(int i = 0;i<numDias;i++){
            nextDay();
        }
    }

    /**
     * Permite escoger un número entre un mínimo y un máximo pasado
     * 
     * @param min   El número mínimo a seleccionar
     * @param max   El número máximo a seleccionar
     * @return  El número escogido.
     */
    private static int escogeOpciones(int min, int max) {
        System.out.println("Escoge una opción entre " + min + " y " + max);
        int opcion = Reader.readTheNumber();
        while (opcion<min || opcion>max) {
            System.out.println("Por favor introduzca una opción válida (entre " + min + " y " + max + ")" );
            opcion = Reader.readTheNumber();
        }
        return opcion;
    }

    /**
     * Realiza toda la lógica
     * @param args
     */
    public static void main(String[] args) {
        init();
        int op = 0;
        try{
            showGeneralStatus();
            while (op!=14) {
                menu();
                op = Reader.readTheNumber();

                switch (op) {
                    case 1:
                        showGeneralStatus();
                        break;
                    case 2:
                        showSpecificStatus();
                        break;
                    case 3:
                        showTankStatus();
                        break;
                    case 4:
                        showStats();
                        break;
                    case 5:
                        showIctio();
                        break;
                    case 6:
                        nextDay();
                        showGeneralStatus();
                        break;
                    case 7:
                        addFood();
                        break;
                    case 8:
                        addFish();
                        break;
                    case 9:
                        sell();
                        break;
                    case 10:
                        cleanTank();
                        break;
                    case 11:
                        emptyTank();
                        break;
                    case 12:
                        upgrade();
                        break;
                    case 13:
                        forwardDays();
                        break;
                    case 14:
                        System.out.println("Salida con éxito");
                        break;
                    case 98:
                        break;
                    case 99:
                        monedas.anadir(1000);
                        break;
                    default:
                        break;
                }
            }

        }catch(Exception e){
            System.out.println("Ha ocurrido un error");
        } finally{
            Reader.closer();
        }
    }

    public static ArrayList<Piscifactoria> getPiscis() {
        return piscis;
    }
}