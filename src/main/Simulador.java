package main;
import java.util.ArrayList;
import estadisticas.Estadisticas;
import helpers.ErrorWriter;
import helpers.LogWriter;
import helpers.Reader;
import helpers.TranscripWriter;
import monedas.Monedas;
import peces.Pez;
import peces.mar.*;
import peces.doble.*;
import peces.rio.*;
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;

/**
 * Clase encargada de la lógica del simulador de la piscifactoría.
 */
public class Simulador {
    
    /** Los días que han pasado */
    private static int dias=0;
    /** Las piscifactorías que hay */
    private static ArrayList<Piscifactoria> piscis= new ArrayList<>();
    /** Las monedas */
    public static Monedas monedas = new Monedas();
    /** El almacén de comida */
    public static Almacen almacen = null;
    /**Recopila las estadisticas del programa */
    public static Estadisticas estadisticas;
    /**Nombre de la empresa */
    public static String nombre;

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

            
        
        System.out.println("Nombra a tu nueva empresa:");
        nombre = Reader.readTheLine();
        while (nombre.isBlank()) {
            System.out.println("Ese nombre no es válido. Inténtelo de nuevo:");
            nombre = Reader.readTheLine();
        }
        
        System.out.println("Nombra a tu primera piscifactoría de río:");
        String nomPisc = Reader.readTheLine();
        while (nomPisc.isBlank()) {
            System.out.println("Ese nombre no es válido. Inténtelo de nuevo:");
            nomPisc = Reader.readTheLine();
        }

        while (nomPisc.equals("")) {
            System.out.println("Introduzca un nombre valido");
            nomPisc = Reader.readTheLine();
        }
        piscis.add(new Piscifactoria("rio",nomPisc));
        piscis.get(0).addFood(25,25);
        Monedas.setCantidad(100);

        ErrorWriter.startErrorLog(nombre);
        LogWriter.startLog(nombre);
        TranscripWriter.transcriptInit(nombre);
        TranscripWriter.transcriptStart(nombre, nomPeces, nomPisc);
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
    public static void menuPisc(){
        
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
    public static int selectPisc(){
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
     * el día actual, las monedas disponibles y la información del
     * almacén si se dispone de el
     */
    private static void showGeneralStatus(){
        for(Piscifactoria p : piscis){
            p.showStatus();
            p.showFood();
            System.out.println();
        }
        System.out.println("Día actual: "+dias);
        System.out.println("Monedas disponibles: "+Monedas.getCantidad());
        System.out.println();
        if(almacen!=null){
            almacen.toString();
        }
    }

    /**
     * Muestra el estado de los tanques de una piscifactoría seleccionada
     */
    private static void showSpecificStatus(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            piscis.get(piscifactoria).showTankStatus();
        }
    }

    /**
     * Muestra la información de los peces de un tanque específico de una
     * piscifactoría seleccionada.
     */
    private static void showTankStatus(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            int tank = piscis.get(piscifactoria).selectTank();
            piscis.get(piscifactoria).tanques.get(tank).showFishStatus();
        }
    }

    /**
     * Muestra el nombre, el número de peces comprados, nacidos y vendidos 
     * y el dinero que se ha ganado durante la simulación
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
        System.out.println("Introduzca un entero entre 1 y 13");
        pez = escogeOpciones(1,13);
        if(pez!=13){
            peces[pez-1].toString();
        } else {
            System.out.println("Volviendo...");
        }
    }

    /**
     * Avanza un día haciendo crecer a todos los peces de todos los tanques
     * de todas las piscifactorías
     */
    private static void nextDay(){
        dias++;
        int ganancias=0;
        int totalMar=0;
        int totalRio=0;
        if(almacen!=null){
            Almacen.repartirComida();
        }
        for(Piscifactoria p : piscis){
            ganancias += p.nextDay();
            if(p.getTipo()=="mar"){
                totalMar += p.getTotalAlive();
            }else{
                totalRio += p.getTotalAlive();
            }
        }
        TranscripWriter.writeInTranscript("Fin del día "+dias+".\nPeces actuales: "+totalRio+" de río, "+totalMar+" de mar.\n"+ganancias+" monedas ganadas por un total de "+Monedas.getCantidad()+".\n------------------------------\n>>>Inicio del día "+dias+".");
    }

    /**
     * Añade comida a una piscifactoría seleccionada
     * o al almacén central si se dispone de el
     */
    private static void addFood(){
        System.out.println("Introduzca el tipo de comida:\n1.animal\n2.vegetal");
        int tipoComida = escogeOpciones(1, 2);
        System.out.println("Introduzca la cantidad a añadir:\n1.- 5\n2.- 10\n3.- 25\n4.- llenar\nEl coste es 1 moneda por unidad y 5 monedas de descuento cada 25 unidades");
        int opcion = escogeOpciones(1, 4);

        int espacio;
        int add = 0;
        if(almacen==null){
            int piscifactoria = selectPisc();
            if(piscifactoria !=-1){
                if(tipoComida==1){
                    espacio = piscis.get(piscifactoria).getComidaMax()-piscis.get(piscifactoria).getComidaAnimal();
                }else{
                    espacio = piscis.get(piscifactoria).getComidaMax()-piscis.get(piscifactoria).getComidaVegetal();
                }
                switch (opcion) {
                    case 1: add = 5; break;
                    case 2: add = 10; break;
                    case 3: add = 25; break;
                    case 4: add = espacio; break;
                }
                if(add<=espacio){
                    if(Monedas.comprar(add - (5*(int)(add/25)))){
                        if (tipoComida == 1) {
                            piscis.get(piscifactoria).addFood(add, 0);
                        } else {
                            piscis.get(piscifactoria).addFood(0, add);
                        }
                    }
                } else {
                    System.out.println("Cantidad a añadir mayor de lo posible");
                }
            }
        } else{

            if(tipoComida==1){
                espacio = Almacen.getMaxCapacidad()-Almacen.getCarne();
            }else{
                espacio = Almacen.getMaxCapacidad()-Almacen.getVegetal();
            }
            switch (opcion) {
                case 1: add = 5; break;
                case 2: add = 10; break;
                case 3: add = 25; break;
                case 4: add = espacio; break;
            }
            if(add<=espacio){
                if(Monedas.comprar(add - (5*(int) add/25))){
                    almacen.addFood(add, tipoComida == 1);
                }
            } else {
                System.out.println("Cantidad a añadir mayor de lo posible");
            }
        }
    }

    /**
     * Añade un pez a un tanque de una piscifactoría seleccionada
     */
    private static void addFish(){
        int opcion = selectPisc();
        int tankSelec = piscis.get(opcion).selectTank();
        piscis.get(opcion).tanques.get(tankSelec).addFish(false);
    }

    /**
     * Vende todos los peces adultos vivos de una piscifactoría
     * a la mitad de dinero normal
     */
    private static void sell(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            int[] datosVentas = {0,0};
            datosVentas =piscis.get(piscifactoria).sellFish();
            Monedas.setCantidad(datosVentas[0]+Monedas.getCantidad());
            TranscripWriter.writeInTranscript("Vendidos "+datosVentas[1]+" peces de la piscifactoría "+piscis.get(piscifactoria).getNombre()+" de forma manual por "+datosVentas[0]+" monedas.");
            System.out.println("Se han conseguido "+datosVentas[0]+" monedas por la venta de peces adultos");
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
            TranscripWriter.writeInTranscript("Vaciando el tanque "+piscis.get(piscifactoria).tanques.get(option).getNumTanque()+" de la piscifactoría "+piscis.get(piscifactoria).getNombre());
        }
    }

    /**
     * Elimina los peces muertos de una piscifactoría seleccionada
     */
    private static void cleanTank(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            piscis.get(piscifactoria).cleanTank(piscis.get(piscifactoria).getNombre());
        }
    }
    
    /**
     * Permite hacer mejoras o comprar nuevas estructuras
     */
    private static void upgrade(){
        
        int opcion = 0;
        while (opcion != 3) {
            System.out.println("1. Comprar edificios");
            System.out.println("2. Mejorar edificios");
            System.out.println("3. Cancelar");
            opcion = escogeOpciones(1,3);

            switch (opcion) {
                case 1:
                    comprar();
                    break;
                case 2:
                    mejorar();
                    break;
                case 3:
                    System.out.println("Operación cancelada");
                    break;
            }
        }
    }

    /**
     * Se encarga de la opción comprar del método upgrade
     */

    private static void comprar(){

        int opcion = 0;

        while (opcion != 3) {
            System.out.println("1. Piscifactoría");
            System.out.println("2. Almacén central");
            System.out.println("3. Volver");

            opcion = escogeOpciones(1, 3);

            switch (opcion) {
                case 1:
                    comparPisc();
                    break;
                case 2:
                    if(almacen==null){
                        if(Monedas.comprar(2000)){
                            almacen = new Almacen();
                            System.out.println("Monedas restantes: "+Monedas.getCantidad());
                        }
                    } else{
                        System.out.println("Ya se dispone del almacén");
                    }
                    break;

                case 3:
                    System.out.println("Cancelando...");
            }
        }
    }

    /**
     * Método que se encarga de la lógica de comprar piscifactorías.
     */
    private static void comparPisc() {
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
            int coste= buyPisc == 1 ? 500 + 500 * numPisc : 2000 + 2000 * numPisc;
            if (Monedas.comprar(coste)) {
                System.out.println("Introduzca el nombre de la nueva piscifactoría");
                String nombre = Reader.readTheLine();
                while (nombre.equals("")) {
                    System.out.println("Nombre no válido. Vuelva a introducir un nombre");
                    nombre = Reader.readTheLine();
                }
                piscis.add(new Piscifactoria(buyPisc == 1 ? "rio" : "mar",nombre));
            }
        } else {
            System.out.println("Volviendo...");
        }
    }

    /**
     * Se encarga de la opción mejorar del método upgrade
     */
    private static void mejorar(){
        int opcion = 0;
        while (opcion != 3) {
            System.out.println("1. Piscifactoria");
            System.out.println("2. Almacén central");
            System.out.println("3. Volver");

            int subOpcion = escogeOpciones(1, 3);

            switch (subOpcion) {
                case 1:
                    int mejora = 0;
                    while (mejora != 3) {
                        System.out.println("1. Comprar tanque");
                        System.out.println("2. Aumentar almacén de comida");
                        System.out.println("3. Cancelar");

                        mejora = escogeOpciones(1,3);
                        switch (mejora) {
                            case 1:
                                upgradePisc();
                                break;
                            case 2:
                                upgradeAlmacen();
                                break;
                            case 3:
                                System.out.println("Cancelando...");
                                break;
                        }
                    }
                    break;
                case 2:
                    upgradeCentral();
                    break;
                case 3:
                    System.out.println("Volviendo...");
                    break;
            }
        }

    }

    /**
     * Métdo encargado de la lógica de mejorar una piscifactoría.
     */

    private static void upgradePisc() {
        int piscifactoria = selectPisc();
        if (piscifactoria != -1) {
            int numTanques = piscis.get(piscifactoria).getTanques().size();
            if (numTanques < 10) {
                int costeTanque = piscis.get(piscifactoria).getTipo().equals("rio") ? 150 + 150 * numTanques : 600 + 600 * numTanques;
                if(Monedas.comprar(costeTanque)){
                    piscis.get(piscifactoria).addTank();
                }
            } else {
                System.out.println("Ya no se admiten más tanques en la piscifactoría");
            }
        }
    }

    /**
     * Métdo encargado de la lógica de mejorar un almacén de una piscifactoría.
     */
    private static void upgradeAlmacen() {
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            int coste = piscis.get(piscifactoria).getTipo().equals("rio") ? 50 : 200;
            if (Monedas.comprar(coste)) {
                piscis.get(piscifactoria).upgradeFood();
            }
        }
    }

    /**
     * Método encargado de la lógica de mejorar el almacén central.
     */
    private static void upgradeCentral() {
        if(almacen!=null){
            if(Monedas.comprar(200)){
                almacen.upgrade();
            }
        } else{
            System.out.println("No se dispone de almacén central");
        }
    }

    /**
     * Permite pasar entre 1 y 5 días de golpe con sus consecuencias
     */
    private static void forwardDays(){
        System.out.println("Indique entre 1 y 5 cuántos días desea pasar");
        int numDias = escogeOpciones(1, 5);
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
     * Inicializa la simulación. Después, muestra el menú y pide al usuario
     * una opción en bucle hasta que el usuario decida acabar el programa.
     * 
     * @param args
     */
    public static void main(String[] args) {
        init();
        int op = 0;
            showGeneralStatus();
            while (op!=14) {
                try {
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
                            showGeneralStatus();
                            break;
                        case 14:
                            System.out.println("Salida con éxito");
                            break;
                        case 98:
                            cheat98();
                            break;
                        case 99:
                            cheat99();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    ErrorWriter.writeInErrorLog("Error general en la simulación.");
                }
            }
        Reader.closer();
    }

    /**
     * Método oculto que añade 1000 monedas.
     */
    public static void cheat99(){
        Monedas.anadir(1000);
        TranscripWriter.writeInTranscript("Añadidas 1000 monedas mediante la opción oculta. Monedas actuales, "+Monedas.getCantidad());
    }

    /**
     * Método oculto que añade 4 tanques con 1 pez aleatorio cada uno
     * a una piscifactoría seleccionada
     */
    public static void cheat98(){
        int opcion = selectPisc();
        piscis.get(opcion).addTank();
        piscis.get(opcion).addTank();
        piscis.get(opcion).addTank();
        piscis.get(opcion).addTank();
        for(int i=piscis.get(opcion).tanques.size(),j=0;j<4;i--){
            piscis.get(opcion).tanques.get(i-1).randomFish();
            j++;
        }
        TranscripWriter.writeInTranscript("Añadidos peces mediante la opción oculta a la piscifactoría "+piscis.get(opcion).getNombre());
    }

    /** @return La lista de piscifactorías en la simulación */
    public static ArrayList<Piscifactoria> getPiscis() {
        return piscis;
    }

    /** @param piscis La nueva lista de piscifactorías. */
    public static void setPiscis(ArrayList<Piscifactoria> piscis) {
        Simulador.piscis = piscis;
    }

    /** @return El nombre de la partida*/
    public static String getNombre() {
        return nombre;
    }
}