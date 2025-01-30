package main;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.annotations.JsonAdapter;

import estadisticas.Estadisticas;
import helpers.ErrorWriter;
import helpers.Guardado;
import helpers.LogWriter;
import helpers.Reader;
import helpers.TranscriptWriter;
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
        instancia.nombre = nombre;
        instancia.dia = dia;
        instancia.monedas = monedas;
        instancia.orca = estadisticas;
        instancia.almacen = almacen;
        instancia.piscis = piscis;
    }

    
    /** @param nombre el nombre de la partida */
    public void setNombre(String nombre) {
        instancia.nombre = nombre;
    }

    /** @param dia el dia actual */
    public void setDia(int dia) {
        instancia.dia = dia;
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
        instancia.piscis = piscis;
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
            LogWriter.startLog(instancia.nombre);
            LogWriter.writeInLog("Inicio de la simulación: "+instancia.nombre);
            LogWriter.writeInLog("Piscifactoría inicial: "+instancia.nombre);
            TranscriptWriter.transcriptInit(instancia.nombre);
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
            LogWriter.startLog(instancia.nombre);
            LogWriter.writeInLog("Inicio de la simulación: "+instancia.nombre);
            LogWriter.writeInLog("Piscifactoría inicial: "+instancia.nombre);
            TranscriptWriter.transcriptInit(instancia.nombre);
            TranscriptWriter.transcriptStart(instancia.nombre, instancia.implementados, instancia.piscis.get(0).getNombre());
        }

        ErrorWriter.startErrorLog();
        LogWriter.startLog(instancia.nombre);
        LogWriter.writeInLog("Inicio de la simulación " + instancia.nombre);
        TranscriptWriter.transcriptInit(instancia.nombre);
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
        
        System.out.println("---------- Seleccione un pez ----------");
        System.out.println("1. Abadejo\n2. Arenque del Atlántico\n3. Bagre de Canal\n4. Besugo\n5. Carpa"+
        "\n6. Cobia\n7. Dorada\n8. Koi\n9. Pejerrey\n10. Rodaballo\n11. Salmon Chinook\n12. Tilapia del Nilo\n13. Volver al menú"
        );
        while (pez<1 || pez>13) {
            System.out.println("Introduzca un entero entre 1 y 13");
            pez = Reader.readTheNumber(1,13);
        }
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
        System.out.println("Introduzca el tipo de comida:\n1.animal\n2.vegetal");
        int tipoComida = Reader.readTheNumber(1, 2);
        System.out.println("Introduzca la cantidad a añadir:\n1.- 5\n2.- 10\n3.- 25\n4.- llenar\nEl coste es 1 moneda por unidad y 5 monedas de descuento cada 25 unidades");
        int opcion = Reader.readTheNumber(1, 4);

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
                        TranscriptWriter.writeInTranscript(add + " de comida de tipo " + (tipoComida == 1 ? "animal" : "vegetal") + " por " + coste + " monedas. Se almacena en la piscifactoría " + instancia.piscis.get(piscifactoria).getNombre() + ".");
                        LogWriter.writeInLog(add + " de comida de tipo " + (tipoComida == 1 ? "animal" : "vegetal") + " por " + coste + " monedas. Se almacena en la piscifactoría " + instancia.piscis.get(piscifactoria).getNombre() + ".");
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
                    instancia.almacen.addFood(add, tipoComida == 1);
                    TranscriptWriter.writeInTranscript(add + " de comida de tipo " + (tipoComida == 1 ? "animal" : "vegetal") + " por " + coste + " monedas. Se almacena en el almacén central.");
                    LogWriter.writeInLog(add + " de comida de tipo " + (tipoComida == 1 ? "animal" : "vegetal") + " por " + coste + " monedas. Se almacena en el almacén central.");
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
            System.out.println("1. Piscifactoría");
            System.out.println("2. Almacén central: 2000 monedas. Disponible: "+instancia.monedas.getCantidad()+" monedas");
            System.out.println("3. Volver");

            opcion = Reader.readTheNumber(1,3);

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
                            TranscriptWriter.writeInTranscript("Comprado el almacén central.");
                            LogWriter.writeInLog("Comprado el almacén central.");
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
     * 
     * @return  Si se ha realizado la operación correctamente (0) o no (1)
     */
    private static void comparPisc() {
        System.out.println("Elige el tipo de piscifactoría a comprar:\n" +
        "1.- Río\n" +
        "2.- Mar\n" +
        "3.- Volver"
        );
        int buyPisc = Reader.readTheNumber(1, 3);

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
                TranscriptWriter.writeInTranscript("Comprada la piscifactoría de " + (buyPisc == 1 ? "rio" : "mar") + " por " + coste + " monedas.");
                LogWriter.writeInLog("Comprada la piscifactoría de " + (buyPisc == 1 ? "rio" : "mar") + " por " + coste + " monedas.");

            }
        }
    }

    /**
     * Se encarga de la opción mejorar del método upgrade
     */
    private static void mejorar(){

        int opcion = 0;

        while (opcion<1 || opcion>3) {
            System.out.println("1. Piscifactoria");
            System.out.println("2. Almacén central");
            System.out.println("3. Volver");

            int subOpcion = Reader.readTheNumber(1, 3);

            switch (subOpcion) {
                case 1:
                    System.out.println("1. Comprar tanque");
                    System.out.println("2. Aumentar almacén de comida");
                    System.out.println("3. Volver");

                    int mejora = Reader.readTheNumber(1,3);

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
        //TODO opcion para comprar tanque de cria (500 monedas, 3 por piscifactoria)
        int piscifactoria = selectPisc();
        if (piscifactoria != -1) {
            int numTanques = instancia.piscis.get(piscifactoria).tanques.size();
            if (numTanques < 10) {
                int costeTanque = instancia.piscis.get(piscifactoria).getTipo().equals("rio") ? 150 + 150 * numTanques : 600 + 600 * numTanques;
                if(instancia.monedas.comprar(costeTanque)){
                        instancia.piscis.get(piscifactoria).addTank();
                        TranscriptWriter.writeInTranscript("Comprado un tanque número " + instancia.piscis.get(piscifactoria).getTanques().getLast().getNumTanque() + " de la piscifactoría " + instancia.piscis.get(piscifactoria).getNombre());
                        LogWriter.writeInLog("Comprado un tanque para la piscifactoría " + instancia.piscis.get(piscifactoria).getNombre());  
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
                TranscriptWriter.writeInTranscript("Mejorada la piscifactoría " + instancia.piscis.get(piscifactoria).getNombre() + " aumentando su capacidad de comida hasta un total de " + instancia.piscis.get(piscifactoria).getComidaMax() + " por " + coste + " monedas.");
                LogWriter.writeInLog("Mejorada la piscifactoría " + instancia.piscis.get(piscifactoria).getNombre() + " aumentando su capacidad de comida hasta un total de " + instancia.piscis.get(piscifactoria).getComidaMax() + " por " + coste + " monedas.");
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
                op = Reader.readTheNumber(1,0);

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
        TranscriptWriter.writeInTranscript("Añadidas 1000 monedas mediante la opción oculta. Monedas actuales, "+instancia.monedas.getCantidad());
        LogWriter.writeInLog("Añadidas monedas mediante la opción oculta.");
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
        TranscriptWriter.writeInTranscript("Añadidos peces mediante la opción oculta a la piscifactoría "+instancia.piscis.get(opcion).getNombre());
        LogWriter.writeInLog("Añadidos peces mediante la opción oculta a la piscifactoría " + instancia.piscis.get(opcion).getNombre());
    }

}