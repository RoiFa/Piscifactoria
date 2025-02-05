package main;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.LogManager;

import com.google.gson.annotations.JsonAdapter;

import estadisticas.Estadisticas;
import helpers.ErrorWriter;
import helpers.Guardado;
import helpers.LogWriter;
import helpers.Reader;
import helpers.TranscriptWriter;
import helpers.PremadeLogs;
import monedas.Monedas;
import peces.Pez;
import peces.mar.*;
import peces.doble.*;
import peces.rio.*;
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;
import tanque.Tanque;
import adapters.SimuladorAdapter;

@JsonAdapter(SimuladorAdapter.class)
public class Simulador {

    /** El estado del sistema */
    public static Simulador instancia;
    
    /** El array con los nombres de los peces implementados */
    private final String[] implementados = new String[]{AlmacenPropiedades.ABADEJO.getNombre(),AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
        AlmacenPropiedades.BAGRE_CANAL.getNombre(),AlmacenPropiedades.BESUGO.getNombre(),
        AlmacenPropiedades.CARPA.getNombre(),AlmacenPropiedades.COBIA.getNombre(),
        AlmacenPropiedades.DORADA.getNombre(),AlmacenPropiedades.KOI.getNombre(),
        AlmacenPropiedades.PEJERREY.getNombre(),AlmacenPropiedades.RODABALLO.getNombre(),
        AlmacenPropiedades.SALMON_CHINOOK.getNombre(),AlmacenPropiedades.TILAPIA_NILO.getNombre()};

    /** El nombre de la entidad */
    private static String nombre;
        /** El día actual */
        private int dia;
        /** Las monedas */
        public Monedas monedas;
        /** Recopila las estadisticas del programa */
        public Estadisticas orca;
        /** El almacén de comida */
        public Almacen almacen;
        /** Las piscifactorías que hay */
        private static ArrayList<Piscifactoria> piscis;
    
        /**
         * Constructor para la carga de datos
         */
        public Simulador(){}
    
        /**
         * 
         * @param nombre el nombre de la empresa/partida
         * @param dia el día actual
         * @param monedas
         * @param estadisticas
         * @param almacen
         * @param piscis
         */
        public Simulador(String nombre, int dia, Monedas monedas, Estadisticas estadisticas, Almacen almacen,
                ArrayList<Piscifactoria> piscis) {
            this.nombre = nombre;
            this.dia = dia;
            this.monedas = monedas;
            this.orca = estadisticas;
            this.almacen = almacen;
            this.piscis = piscis;
        }
    
        
        /** @param nombre el nombre de la partida */
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    
        /** @param dia el dia actual */
        public void setDia(int dia) {
            this.dia = dia;
        }
    
        /** @return el array con los peces implementados */
        public String[] getImplementados() {
            return implementados;
        }
    
        /** @return el dia actual */
        public int getDia() {
            return dia;
        }
    
        /**
         * @return el array con las piscifactorías
         */
        public ArrayList<Piscifactoria> getPiscis() {
            return piscis;
        }
    
        /**
         * @param piscis el nuevo array con las piscifactorías modificadas
         */
        public void setPiscis(ArrayList<Piscifactoria> piscis) {
            this.piscis = piscis;
        }
    
        /**
         * @return el nombre de la partida/entidad
         */
        public String getNombre(){
            return nombre;
        }
    
    
        /**
         * Inicializa el sistema desde cero
         */
        private static void init(){
            File log = new File("logs");
            if(!log.exists()){
                log.mkdir();
            }
            File tr = new File("transcripts");
            if(!tr.exists()){
                tr.mkdir();
            }
            File rw = new File("rewards");
            if(!rw.exists()){
                rw.mkdir();
            }
            ErrorWriter.startErrorLog();
            int opcion = 0;
            String[] saves = Guardado.listarSaves();
            if(saves.length>0){
                System.out.println("¿Desea cargar una partida? (1. Si ; 2. No)");
                int cargar = Reader.readTheNumber(1, 2);
                if(cargar==1){
                    for(int i = 0;i<saves.length;i++){
                        System.out.println((i+1)+". "+saves[i].substring(0, saves[i].indexOf(".")));
                    }
                    opcion = Reader.readTheNumber(1, saves.length);
                }
            } 
            if(opcion>0){
                Guardado.load(saves[opcion-1]);
                PremadeLogs.loadGame();
            } else{
                System.out.println("Introduzca el nombre de la nueva empresa a crear:");
                String nombre = Reader.readTheLine();
                System.out.println("Introduzca el nombre de la primera piscifactoría (río)");
                String nomPisc = Reader.readTheLine();
                instancia = new Simulador(nombre, 0, new Monedas(), null, new Almacen(), new ArrayList<>());
                instancia.orca = new Estadisticas(instancia.implementados);
                instancia.piscis.add(new Piscifactoria("rio", nomPisc));
                instancia.piscis.get(0).addFood(25,25);
                instancia.monedas.setCantidad(100);
                Guardado.save();
                PremadeLogs.newGame();
            }

        PremadeLogs.startGame();
    }

    /**
     * Muestra el texto con las diferentes opciones a realizar 
     */
    private static void menu(){
        Reader.menuGenerator(new String[]{
        "Menu principal:",
        "1. Estado general",
        "2. Estado piscifactoría",
        "3. Estado tanques",
        "4. Informes",
        "5. Ictiopedia",
        "6. Pasar día",
        "7. Comprar comida",
        "8. Comprar peces",
        "9. Vender peces",
        "10. Limpiar tanques",
        "11. Vaciar tanque",
        "12. Mejorar",
        "13. Pasar varios días"});
        
    }

    /**
     * Muestra el texto para seleccionar una piscifactoría
     */
    private static void menuPisc(){
        
        int i = 1;
        System.out.println("Seleccione una piscifactoría:");
        System.out.println("-------------------------- Piscifactorías --------------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        for(Piscifactoria p : instancia.piscis){
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
        menuPisc();
        int opcion = Reader.readTheNumber(0,instancia.piscis.size());
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
        for(Piscifactoria p : instancia.piscis){
            p.showStatus();
            p.showFood();
            System.out.println();
        }
        System.out.println("Día actual: "+instancia.dia);
        System.out.println("Monedas disponibles: "+instancia.monedas.getCantidad());
        System.out.println();
        if(instancia.almacen.getDisponible()){
            instancia.almacen.toString();
        }
    }

    /**
     * Permite seleccionar una piscifactoría y mostrar el estado de sus tanques
     */
    private static void showSpecificStatus(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            instancia.piscis.get(piscifactoria).showTankStatus();
        }
    }

    /**
     * Permite seleccionar un tanque y muestra la información de sus peces
     */
    private static void showTankStatus(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            int tank = instancia.piscis.get(piscifactoria).selectTank();
            instancia.piscis.get(piscifactoria).tanques.get(tank).showFishStatus();
        }
    }

    /**
     * Muestra el nombre, el número de comprados y nacidos, el número de vendidos 
     * y el dinero que se ha ganado con los peces del sistema
     */
    private static void showStats(){
        instancia.orca.mostrar();
    }

    /**
     * Muestra la información de un pez a seleccionar entre los implementados
     */
    private static void showIctio(){

        Pez[] peces = {new Abadejo(),new ArenqueDelAtlantico(),new BagreDeCanal(),new Besugo(),new Carpa(),
                       new Cobia(),new Dorada(),new Koi(),new Pejerrey(),new Rodaballo(),
                       new SalmonChinook(),new TilapiaDelNilo()};
        int pez = 0;
            pez = Reader.menuGenerator(new String[]{"---------- Seleccione un pez ----------","1. Abadejo","2. Arenque del Atlántico","3. Bagre de Canal","4. Besugo","5. Carpa",
                "6. Cobia","7. Dorada","8. Koi","9. Pejerrey","10. Rodaballo","11. Salmon Chinook","12. Tilapia del Nilo"});
        if(pez==0){
            System.out.println("Vuelta con éxito");
        } else if(pez!=-1){
            System.out.println(peces[(pez-1)].toString());
        }
    
        
    }

    /**
     * Avanza un día haciendo la lógica necesaria y muestra un mensaje con los
     * peces vendidos por piscifactoría y despues en general
     */
    private static void nextDay(){
        LogWriter.writeInLog("Fin del día " + (instancia.dia));
        instancia.dia++;
        int pecesVendidos = 0;
        int dineroVendido = 0;
        int totalMar=0;
        int totalRio=0;
        if(instancia.almacen.getDisponible()){
            instancia.almacen.repartirComida();
        }
        for(Piscifactoria p : instancia.piscis){
            int[] venta = p.nextDay();
            pecesVendidos+=venta[0];
            dineroVendido+=venta[1];
            if(p.getTipo()=="mar"){
                totalMar += p.getTotalAlive();
            }else{
                totalRio += p.getTotalAlive();
            }
        }
        System.out.println(pecesVendidos+" peces vendidos por un total de "+dineroVendido+" monedas");
        TranscriptWriter.writeInTranscript("Fin del día "+(instancia.dia-1)+".\nPeces actuales: "+totalRio+" de río, "+totalMar+" de mar.\n"+dineroVendido+" monedas ganadas por un total de "+instancia.monedas.getCantidad()+".\n------------------------------\n>>>Inicio del día "+instancia.dia+".");
    }

    /**
     * Añade comida a una piscifactoría seleccionada
     * o al almacén central si se dispone de el
     */
    private static void addFood(){
        System.out.println();

        int tipoComida = Reader.menuGenerator(new String[]{"Introduzca el tipo de comida:","1.animal","2.vegetal"});

        int opcion = Reader.menuGenerator(new String[]{"El coste es 1 moneda por unidad y 5 monedas de descuento cada 25 unidades\nIntroduzca la cantidad a añadir:","1.- 5","2.- 10","3.- 25","4.- llenar"});

        int espacio;
        int add = 0;
        if(!instancia.almacen.getDisponible()){
            int piscifactoria = selectPisc();
            if(piscifactoria !=-1){
                if(tipoComida==1){
                    espacio = instancia.piscis.get(piscifactoria).getComidaMax()-instancia.piscis.get(piscifactoria).getComidaAnimal();
                }else{
                    espacio = instancia.piscis.get(piscifactoria).getComidaMax()-instancia.piscis.get(piscifactoria).getComidaVegetal();
                }
                switch (opcion) {
                    case 1: add = 5; break;
                    case 2: add = 10; break;
                    case 3: add = 25; break;
                    case 4: add = espacio; break;
                }
                if(add>espacio){
                    add = espacio;
                }
                int coste = add - (5*(int)(add/25));
                if(add<=espacio){
                    if(instancia.monedas.comprar(coste)){
                        if (tipoComida == 1) {
                           instancia.piscis.get(piscifactoria).addFood(add, 0);
                        } else {
                           instancia.piscis.get(piscifactoria).addFood(0, add);
                        }
                        PremadeLogs.addedFood(add,(tipoComida == 1 ? "animal" : "vegetal"),coste,instancia.piscis.get(piscifactoria).getNombre());
                    }
                 }
            }
        } else{

            if(tipoComida==1){
                espacio = instancia.almacen.getMaxCapacidad()-instancia.almacen.getCarne();
            }else{
                espacio = instancia.almacen.getMaxCapacidad()-instancia.almacen.getVegetal();
            }
            switch (opcion) {
                case 1: add = 5; break;
                case 2: add = 10; break;
                case 3: add = 25; break;
                case 4: add = espacio; break;
            }
            if(add>espacio){
                add = espacio;
            }
            int coste = add - (5*(int)(add/25));
            if(add<=espacio){
                if(instancia.monedas.comprar(coste)){
                    instancia.almacen.addFood(add, tipoComida == 1);PremadeLogs.addedFood(add,(tipoComida == 1 ? "animal" : "vegetal"),coste,"el almacén");
                }
            }else{
              System.out.println("Cantidad a añadir mayor de lo posible");
            }
        }
    }
    

        


    private static void addFish(){
        int opcion = selectPisc();
        if(opcion!=-1){
            int tankSelec = instancia.piscis.get(opcion).selectTank();
            instancia.piscis.get(opcion).tanques.get(tankSelec).addFish(false);
        }
    }

    /**
     * Vende todos los peces adultos vivos de una piscifactoría
     * a la mitad de dinero de lo normal
     */
    private static void sell(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            int[] datosVentas=instancia.piscis.get(piscifactoria).sellFish();
            instancia.monedas.anadir(datosVentas[0]);
            TranscriptWriter.writeInTranscript("Vendidos "+datosVentas[1]+" peces de la piscifactoría "+instancia.piscis.get(piscifactoria).getNombre()+" de forma manual por "+datosVentas[0]+" monedas.");
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
            int option = instancia.piscis.get(piscifactoria).selectTank();
            instancia.piscis.get(piscifactoria).tanques.get(option).emptyTank();
            Tanque tanque = instancia.piscis.get(piscifactoria).tanques.get(option);
            TranscriptWriter.writeInTranscript("Vaciando el tanque "+tanque.getNumTanque()+" de la piscifactoría "+tanque.getNomPiscifactoria());
        }
    }


    /**
     * Elimina los peces muertos de una piscifactoría seleccionada
     */
    private static void cleanTank(){
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            instancia.piscis.get(piscifactoria).cleanTank();
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
            opcion = Reader.readTheNumber(1, 3);

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
     * 
     * @return  Si se ha realizado la operación correctamente (0) o no (1)
     */

    private static void comprar(){

        int opcion = 0;

        while (opcion<1 || opcion>3) {

            opcion = Reader.menuGenerator(new String[]{"Menu de compra: ","Piscifactoría","Almacén central: 2000 monedas. Disponible: "+instancia.monedas.getCantidad()+" monedas"});

            switch (opcion) {
                case 1:
                    comparPisc();
                    break;
                case 2:
                    if(!instancia.almacen.getDisponible()){
                        if(instancia.monedas.comprar(2000)){
                            instancia.monedas.gastar(2000);
                            instancia.almacen.setDisponible(true);
                            System.out.println("Monedas restantes: "+instancia.monedas.getCantidad());
                            PremadeLogs.pantryBuy();
                        }
                    } else{
                        System.out.println("Ya se dispone del almacén");
                    }
                    break;

                case 0:
                    System.out.println("Cancelando...");
            }
        }
    }

    /**
     * Método que se encarga de la lógica de comprar piscifactorías.
     * 
     * @return  Si se ha realizado la operación correctamente (0) o no (1)
     */
    private static void comparPisc() {

        int buyPisc = Reader.menuGenerator(new String[]{"Elige el tipo de piscifactoría a comprar:","Río","Mar"});

        if (buyPisc != 3) {
            int numPisc = 0;
            for (Piscifactoria p : instancia.piscis) {
                if (p.getTipo().equals(buyPisc == 1 ? "rio" : "mar")) {
                    numPisc++;
                }
            }
            int coste= buyPisc == 1 ? 500 + 500 * numPisc : 2000 + 2000 * numPisc;
            if(instancia.monedas.comprar(coste)) {
                System.out.println("Introduzca el nombre de la nueva piscifactoría");
                String nombre = Reader.readTheLine();
                instancia.piscis.add(new Piscifactoria(buyPisc == 1 ? "rio" : "mar",nombre));
                PremadeLogs.pisciBuy(buyPisc == 1 ? "rio" : "mar",coste);

            }
        }
    }

    /**
     * Se encarga de la opción mejorar del método upgrade
     */
    private static void mejorar(){

        int opcion = 0;

        while (opcion<1 || opcion>3) {

            opcion = Reader.menuGenerator(new String[]{"Escoja que mejorar: ","Piscifactoria","Almacén central"});

            switch (opcion) {
                case 1:

                    int mejora = Reader.menuGenerator(new String[]{"Que tipo de mejora?","Comprar tanque","Aumentar almacén de comida"});

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
                    break;
                case 2:

                    upgradeCentral();

                    break;

                case 3:
                System.out.println("Volviendo...");
            }
        }
    }

    /**
     * Métdo encargado de la lógica de mejorar una piscifactoría.
     * 
     * @return  Si se ha completado (0) o no (1)
     */

    private static void upgradePisc() {
        int piscifactoria = selectPisc();
        if (piscifactoria != -1) {
            int numTanques = instancia.piscis.get(piscifactoria).tanques.size();
            if (numTanques < 10) {
                int costeTanque = instancia.piscis.get(piscifactoria).getTipo().equals("rio") ? 150 + 150 * numTanques : 600 + 600 * numTanques;
                if(instancia.monedas.comprar(costeTanque)){
                        instancia.piscis.get(piscifactoria).addTank();
                        PremadeLogs.tankBuy(instancia.piscis.get(piscifactoria).getTanques().getLast().getNumTanque(),instancia.piscis.get(piscifactoria).getNombre());
                }
            } else {
                System.out.println("Ya no se admiten más tanques en la piscifactoría");
            }
        }
    }

    /**
     * Métdo encargado de la lógica de mejorar un almacén de una piscifactoría. 
     * 
     * @return  Si se ha completado (0) o no (1)
     */
    private static void upgradeAlmacen() {
        int piscifactoria = selectPisc();
        if(piscifactoria!=-1){
            int coste = instancia.piscis.get(piscifactoria).getTipo().equals("rio") ? 50 : 200;
            if (instancia.monedas.comprar(coste)) {
                instancia.piscis.get(piscifactoria).upgradeFood();
                PremadeLogs.pisciUpdate(instancia.piscis.get(piscifactoria).getNombre(),instancia.piscis.get(piscifactoria).getComidaMax(),coste);
            }
        }
    }

    /**
     * Método encargado de la lógica de mejorar el almacén central.
     * 
     * @return  Si se ha completado (0) o no (1)
     */
    private static void upgradeCentral() {
        if(instancia.almacen.getDisponible()){
            if(instancia.monedas.comprar(200)){
                instancia.almacen.upgrade();
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
        int numDias = Reader.readTheNumber(1,5);
        for(int i = 0;i<numDias;i++){
            nextDay();
        }
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
                op = Reader.readTheNumber(0,100);

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
                        Guardado.save();
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
                    case 0:
                        System.out.println("Cerrando...");
                        Guardado.save();
                        System.out.println("Salida con éxito");
                        break;
                    case 98:
                        cheat98();
                        break;
                    case 99:
                        cheat99();
                        break;
                    default:
                    System.out.println("Opción no valida");
                        break;
                }
            }

        }catch(Exception e){
            ErrorWriter.writeInErrorLog("Error general en la simulación.");            
        } finally{
            Reader.closer();
            Guardado.close();
            LogWriter.closeLog();
            ErrorWriter.closeErrorLog();
        }
    }

    public static void cheat99(){
        instancia.monedas.anadir(1000);
        PremadeLogs.secretMoney();
    }

    public static void cheat98(){
        int opcion = selectPisc();
        instancia.piscis.get(opcion).addTank();
        instancia.piscis.get(opcion).addTank();
        instancia.piscis.get(opcion).addTank();
        instancia.piscis.get(opcion).addTank();
        for(int i=instancia.piscis.get(opcion).tanques.size(),j=0;j<4;i--){
            instancia.piscis.get(opcion).tanques.get(i-1).randomFish();
            j++;
        }
        PremadeLogs.secretFish(piscis.get(opcion).getNombre());
    }

}