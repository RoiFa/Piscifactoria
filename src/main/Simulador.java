package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.annotations.JsonAdapter;

import estadisticas.Estadisticas;
import helpers.ErrorWriter;
import helpers.GestorXml;
import helpers.Guardado;
import helpers.LogWriter;
import helpers.Reader;
import mannagementBD.Conexion;
import mannagementBD.GeneradorBD;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOPedidos;

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
    private String nombre;
        /** El día actual */
        private int dia;
        /** Las monedas */
        public Monedas monedas;
        /** Recopila las estadisticas del programa */
        public Estadisticas orca;
        /** El almacén de comida */
        public Almacen almacen;
        /** Las piscifactorías que hay */
        private ArrayList<Piscifactoria> piscis;
        /** Conexion con la base de datos */
        public static Connection conn;
    
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
            conn = Conexion.getConect();
            GeneradorBD.generarTablas();
            GeneradorBD.anadirClientes();
            GeneradorBD.insertarPeces();
            ErrorWriter.startErrorLog();
            DAOPedidos.prepareStatements(conn);
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
        System.out.println("Menu principal:\n"+
        "1. Estado general\n"+
        "2. Estado piscifactoría\n"+
        "3. Estado tanques\n"+
        "4. Informes\n"+
        "5. Ictiopedia\n"+
        "6. Pasar día\n"+
        "7. Comprar comida\n"+
        "8. Comprar peces\n"+
        "9. Vender peces\n"+
        "10. Limpiar tanques\n"+
        "11. Vaciar tanque\n"+
        "12. Mejorar\n"+
        "13. Pasar varios días\n"+
        "14. Mostrar datos\n"+
        "15. Entregar peces\n"+
        "16. Reclamar Recompensa");
        
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
    public int selectPisc(){
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
        int piscifactoria = instancia.selectPisc();
        if(piscifactoria!=-1){
            instancia.piscis.get(piscifactoria).showTankStatus();
        }
    }

    /**
     * Permite seleccionar un tanque y muestra la información de sus peces
     */
    private static void showTankStatus(){
        int piscifactoria = instancia.selectPisc();
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
        if(instancia.dia%10==0){
            GeneradorBD.anadirPedido();
        }
        System.out.println(pecesVendidos+" peces vendidos por un total de "+dineroVendido+" monedas");
        PremadeLogs.nextDay(instancia.dia,totalRio,totalMar,dineroVendido,instancia.monedas.getCantidad());
    }

    /**
     * Añade comida a una piscifactoría seleccionada
     * o al almacén central si se dispone de el
     */
    private static void addFood(){
        System.out.println();

        int tipoComida = Reader.menuGenerator(new String[]{"Introduzca el tipo de comida:","animal","vegetal"});

        int opcion = Reader.menuGenerator(new String[]{"El coste es 1 moneda por unidad y 5 monedas de descuento cada 25 unidades\nIntroduzca la cantidad a añadir:","5","10","25","llenar"});

        int espacio;
        int add = 0;
        if(!instancia.almacen.getDisponible()){
            int piscifactoria = instancia.selectPisc();
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
        int opcion = instancia.selectPisc();
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
        int piscifactoria = instancia.selectPisc();
        if(piscifactoria!=-1){
            int[] datosVentas=instancia.piscis.get(piscifactoria).sellFish();
            instancia.monedas.anadir(datosVentas[0]);
            System.out.println("Se han conseguido "+datosVentas[0]+" monedas por la venta de peces adultos");
            PremadeLogs.manualSellFish(datosVentas[1],instancia.piscis.get(piscifactoria).getNombre(),datosVentas[0]);
        }
    }


    /**
     * Elimina todos los peces de un tanque de una piscifactoría
     * independientemente de su estado
     */
    private static void emptyTank(){
        int piscifactoria = instancia.selectPisc();
        if(piscifactoria!=-1){
            int option = instancia.piscis.get(piscifactoria).selectTank();
            instancia.piscis.get(piscifactoria).tanques.get(option).emptyTank();
            Tanque tanque = instancia.piscis.get(piscifactoria).tanques.get(option);
            PremadeLogs.tankCleaning("Vaciando", tanque.getNumTanque(), tanque.getNomPiscifactoria());
        }
    }


    /**
     * Elimina los peces muertos de una piscifactoría seleccionada
     */
    private static void cleanTank(){
        int piscifactoria = instancia.selectPisc();
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
        int piscifactoria = instancia.selectPisc();
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
        int piscifactoria = instancia.selectPisc();
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
     * Submenú para mostrar los datos almacenados en la base de datos.
     */
    private static void showData() {
        int op = -1;
        while (op != 0) {
            op = Reader.menuGenerator(new String[]{
                "Datos a mostrar:",
                "Mostrar todos los clientes",
                "Mostrar datos de un cliente",
                "Mostrar todos los peces",
                "Mostrar datos de un pez",
                "Mostrar todos los pedidos",
                "Mostrar datos de un pedido",
                "Mostrar todos los pedidos de un cliente",
                "Mostrar todos los pedidos donde se pidiesen un pez"});

            switch (op) {
                case 0:
                    break;
                case 1:
                    DAOPedidos.showTable(DAOPedidos.getAllInfoFromClients(true));
                    break;
                case 2:
                    System.out.println("Introduce el ID del cliente:");
                    DAOPedidos.showTable(DAOPedidos.getAllInfoFromClient(Reader.readTheNumber(1, 1000)));
                    break;
                case 3:
                    DAOPedidos.showTable(DAOPedidos.getAllInfoFromPeces(true));
                    break;
                case 4:
                    System.out.println("Introduce el ID del pez:");
                    DAOPedidos.showTable(DAOPedidos.getAllInfoFromPez(Reader.readTheNumber(1, 1000)));
                    break;
                case 5:
                    DAOPedidos.showTable(DAOPedidos.getAllInfoFromPedidos(true));
                    break;
                case 6:
                    System.out.println("Introduce el ID del pedido:");
                    DAOPedidos.showTable(DAOPedidos.getAllInfoFromPedido(Reader.readTheNumber(1, 1000)));
                    break;
                case 7:
                    System.out.println("Introduce el ID del cliente:");
                    DAOPedidos.showTable(DAOPedidos.getAllInfoFromClientePedidos(Reader.readTheNumber(1, 1000), true));
                    break;
                case 8:
                    System.out.println("Introduce el ID del pez:");
                    DAOPedidos.showTable(DAOPedidos.getAllInfoFromPezPedidos(Reader.readTheNumber(1, 1000), true));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Pide al usuario elegir un pedido.
     * 
     * @return  El ID del pedido elegido.
     */
    public static int selectPedido() {
        ResultSet pedidos = DAOPedidos.getAllInfoFromPedidos(false);
        String[] menuPedido = new String[]{"Selecciona un pedido:"};
        try {
            while (pedidos.next()) {
                String pedido = "[" + pedidos.getInt("ID") + "] " + pedidos.getString("Nombre del cliente") + ": " + pedidos.getString("Tipo de pez") + " " + pedidos.getInt("Cantidad entregada") + "/" + pedidos.getInt("Cantidad pedida") + "(" + ((int)(((double)pedidos.getInt("Cantidad entregada")/(double)pedidos.getInt("Cantidad pedida"))*100) + "%)");
                menuPedido = Arrays.copyOf(menuPedido, menuPedido.length+1);
                menuPedido[menuPedido.length-1] = pedido;
            } 
        }catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al elegir un pedido.");
        }
        return Reader.menuGenerator(menuPedido);
    }

    /**
     * Entrega peces a un pedido especificado.
     */
    public static void resPedido() {
        int idPedido = selectPedido();
        if (idPedido != 0) {
            ResultSet pedido = DAOPedidos.getAllInfoFromPedido(idPedido);
            String tipoPez = "";
            int pezCount = 0;
            
            try {
                pedido.next();
                tipoPez = pedido.getString("Tipo de pez").toString();
                pezCount = pedido.getInt("Cantidad pedida") - pedido.getInt("Cantidad entregada");
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al intentar recoger datos de un pedido.");
            }
            int pisci = instancia.selectPisc();
            int retirados = instancia.getPiscis().get(pisci).sendFish(tipoPez, pezCount);

            DAOPedidos.deliverFish(idPedido, retirados);
        } else {
            System.out.println("Cancelando...");
        }
    }

    /**
     * Realiza toda la lógica
     * @param args
     */
    public static void main(String[] args) {
        init();
        int op = -1;
        showGeneralStatus();
        while (op!=0) {
            try {
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
                    case 14:
                        showData();
                        break;
                    case 15:
                        resPedido();
                        break;
                    case 16:
                        GestorXml.claimReward();
                        break;
                    case 0:
                        System.out.println("Cerrando...");
                        Guardado.save();
                        System.out.println("Salida con éxito");
                        break;
                    case 97:
                        GestorXml.randomReward();
                        System.out.println("Se agrego una recompensa aleatoria");
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
            }catch(Exception e){
                ErrorWriter.writeInErrorLog("Error general en la simulación.");
            }
        }
        Reader.closer();
        Guardado.close();
        LogWriter.closeLog();
        DAOPedidos.close();
        ErrorWriter.closeErrorLog();
    }

    public static void cheat99(){
        instancia.monedas.anadir(1000);
        PremadeLogs.secretMoney(Simulador.instancia.monedas.getCantidad());
    }

    public static void cheat98(){
        int opcion = instancia.selectPisc();
        instancia.piscis.get(opcion).addTank();
        instancia.piscis.get(opcion).addTank();
        instancia.piscis.get(opcion).addTank();
        instancia.piscis.get(opcion).addTank();
        for(int i=instancia.piscis.get(opcion).tanques.size(),j=0;j<4;i--){
            instancia.piscis.get(opcion).tanques.get(i-1).randomFish();
            j++;
        }
        PremadeLogs.secretFish(instancia.piscis.get(opcion).getNombre());
    }

}