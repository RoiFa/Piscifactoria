import java.util.ArrayList;

import almacen.Almacen;
import estadisticas.Estadisticas;
import helpers.Reader;
import monedas.Monedas;
import peces.Pez;
import peces.mar.*;
import peces.doble.*;
import peces.rio.*;
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;
import tanque.Tanque;

public class Simulador {
    
    /** Los días que han pasado */
    private int dias;
    /** Las piscifactorías que hay */
    private ArrayList<Piscifactoria> piscis;
    /** El nombre de la entidad */
    private String nombre;
    /** Las monedas */
    public Monedas monedas;
    /** El almacén de comida */
    private Almacen almacen;
    /** Las estadísticas */
    private Estadisticas estadisticas;

    /**
     * Constructor básico para la clase Simulador
     */
    private Simulador(){
        dias = 0;
        piscis = new ArrayList<>();
        nombre = "";
        monedas = new Monedas();
        almacen = null;
        String[] nomPeces = {AlmacenPropiedades.ABADEJO.getNombre(),AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
                             AlmacenPropiedades.BAGRE_CANAL.getNombre(),AlmacenPropiedades.BESUGO.getNombre(),
                             AlmacenPropiedades.CARPA.getNombre(),AlmacenPropiedades.COBIA.getNombre(),
                             AlmacenPropiedades.DORADA.getNombre(),AlmacenPropiedades.KOI.getNombre(),
                             AlmacenPropiedades.PEJERREY.getNombre(),AlmacenPropiedades.RODABALLO.getNombre(),
                             AlmacenPropiedades.SALMON_CHINOOK.getNombre(),AlmacenPropiedades.TILAPIA_NILO.getNombre()};
        estadisticas = new Estadisticas(nomPeces);
    }

    /**
     * Inicializa el sistema desde cero
     */
    private void init(){

        while (nombre.equals("")) {
            System.out.println("Deme el nombre de la entidad:");
            nombre = Reader.readTheLine();
        }
        System.out.println("Deme el nombre de la primera piscifactoría (río)");
        String nomPisc = Reader.readTheLine();
        while (nomPisc.equals("")) {
            System.out.println("Vuelva a introducir un nombre");
            nomPisc = Reader.readTheLine();
        }
        piscis.add(new Piscifactoria("rio",nomPisc));
        piscis.get(0).addFood(25,true);
        piscis.get(0).addFood(25, false);
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
    private void menuPisc(){
        
        int i = 1;
        System.out.println("Seleccione una opción:");
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
    private int selectPisc(){
        int opcion = 0;
        while (opcion == 0) {
            menuPisc();
            opcion = Reader.readTheNumber();
        }
        return opcion-1;
    }

    /**
     * Permite seleccionar un tanque
     * @return el tanque seleccionado
     */
    private Tanque selectTank(Piscifactoria pisc){
        return pisc.selectTank();
    }

    /**
     * Muestra el estado de las piscifactorías, su comida disponible, 
     * el día actual y las monedas disponibles y la info del almacén si se dispone de el
     */
    private void showGeneralStatus(){
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
    private void showSpecificStatus(){
        int pisc = selectPisc();
        piscis.get(pisc).showTankStatus();
    }

    /**
     * Permite seleccionar un tanque y muestra la información de sus peces
     */
    private void showTankStatus(){
        int pisc = selectPisc();
        Tanque tank = selectTank(piscis.get(pisc));
        tank.showFishStatus();
    }

    /**
     * Muestra el nombre, el número de comprados y nacidos, el número de vendidos 
     * y el dinero que se ha ganado con los peces del sistema
     */
    private void showStats(){
        estadisticas.mostrar();
    }

    /**
     * Muestra la información de un pez a seleccionar entre los implementados
     */
    private void showIctio(){

        Pez[] peces = {new Abadejo(),new ArenqueDelAtlantico(),new BagreDeCanal(),new Besugo(),new Carpa(),
                       new Cobia(),new Dorada(),new Koi(),new Pejerrey(),new Rodaballo(),
                       new SalmonChinook(),new TilapiaDelNilo()};
        int pez = 0;
        while (pez!=13) {
            System.out.println("---------- Seleccione un pez ----------");
            System.out.println("1. Abadejo\n2. Arenque del Atlántico\n3. Bagre de Canal\n4. Besugo\n5. Carpa"+
            "\n6. Cobia\n7. Dorada\n8. Koi\n9. Pejerrey\n10. Rodaballo\n11. Salmon Chinook\n12. Tilapia del Nilo\n13. Volver al menú"
            );
            pez = Reader.readTheNumber();
            while (pez<1 || pez>13) {
                System.out.println("Introduzca un entero entre 1 y 13");
                pez = Reader.readTheNumber();
            }
            if(pez==13){
                System.out.println("Vuelta con éxito");
            } else{
                peces[pez-1].toString();
            }
        }
        
    }

    /**
     * Avanza un día haciendo la lógica necesaria y muestra un mensaje con los
     * peces vendidos por piscifactoría y despues en general
     */
    private void nextDay(){
        dias++;
        for(Piscifactoria p : piscis){
            p.nextDay();
        }
        //TODO terminar
    }

    /**
     * Añade comida a una piscifactoría seleccionada
     * o al almacén central si se dispone de el
     */
    private void addFood(){
        if(almacen==null){
            int piscifactoria = selectPisc();
            
        } else{
            
        }
        //TODO terminar
    }

    /**
     * Añade un pez a una piscifactoría si hay sitio
     */
    private void addFish(){
        System.out.println("----- Escoja un pez a comprar. El coste está en monedas. Disponibles: "+monedas.getCantidad()+" monedas -----");
            System.out.println("1. Abadejo: "+AlmacenPropiedades.ABADEJO.getCoste()+
            "\n2. Arenque del Atlántico: "+AlmacenPropiedades.ARENQUE_ATLANTICO.getCoste()+
            "\n3. Bagre de Canal"+AlmacenPropiedades.BAGRE_CANAL.getCoste()+
            "\n4. Besugo"+AlmacenPropiedades.BESUGO.getCoste()+
            "\n5. Carpa"+AlmacenPropiedades.CARPA.getCoste()+
            "\n6. Cobia"+AlmacenPropiedades.COBIA.getCoste()+
            "\n7. Dorada"+AlmacenPropiedades.DORADA.getCoste()+
            "\n8. Koi"+AlmacenPropiedades.KOI.getCoste()+
            "\n9. Pejerrey"+AlmacenPropiedades.PEJERREY.getCoste()+
            "\n10. Rodaballo"+AlmacenPropiedades.RODABALLO.getCoste()+
            "\n11. Salmon Chinook"+AlmacenPropiedades.SALMON_CHINOOK.getCoste()+
            "\n12. Tilapia del Nilo"+AlmacenPropiedades.TILAPIA_NILO.getCoste()+
            "\n13. Volver al menú"
        );
        int pez = Reader.readTheNumber();
        while (pez<1 || pez>13) {
            System.out.println("Introduzca un entero entre 1 y 13");
            pez = Reader.readTheNumber();
        }
        
        int coste;
        boolean anadido = false;
        switch (pez) { //TODO terminar
            case 1:
                coste = AlmacenPropiedades.ABADEJO.getCoste();
                if(this.monedas.getCantidad()>=coste){
                    for(Piscifactoria p : piscis){
                        
                    }
                    if(!anadido){
                        System.out.println("No ha habido un tanque o espacio disponible para el pez");
                    }
                } else{
                    System.out.println("Monedas insuficientes");
                }
                break;
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
                
                break;
            case 5:
                
                break;
            case 6:
                
                break;
            case 7:
                
                break;
            case 8:
                
                break;
            case 9:
                
                break;
            case 10:
                
                break;
            case 11:
                
                break;
            case 12:
                
                break;
            case 13:
                System.out.println("Vuelta con éxito");
                break;
        }
    }

    /**
     * Vende todos los peces adultos vivos de una piscifactoría
     * a la mitad de dinero de lo normal
     */
    private void sell(){
        int piscifactoria = selectPisc();
        piscis.get(piscifactoria).sellFish();
    }

    /**
     * Elimina los peces muertos de un tanque seleccionado
     */
    private void cleanTank(){
        int piscifactoria = selectPisc();
        piscis.get(piscifactoria).cleanTank();
    }

    /**
     * Elimina todos los peces de un tanque de una piscifactoría
     * independientemente de su estado
     */
    private void emptyTank(){
        int piscifactoria = selectPisc();
        piscis.get(piscifactoria).emptyTank(piscis.get(piscifactoria).selectTank());
    }

    /**
     * Permite hacer mejoras o comprar nuevas estructuras
     */
    private void upgrade(){
        
        System.out.println("1. Comprar edificios");
        System.out.println("2. Mejorar edificios");
        System.out.println("3. Cancelar");

        int opcion = escogeTres();

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





    /**
     * Se encarga de la opción comprar del método upgrade
     */
    private void comprar(){
        System.out.println("1. Piscifactoría");
        System.out.println("2. Almacén central: 2000 monedas. Disponible: "+monedas.getCantidad()+" monedas");
        System.out.println("3. Volver");

        int opcion = escogeTres();

        switch (opcion) {
            case 1:
                System.out.println("1. Río");
                System.out.println("2. Mar");
                System.out.println("3. Volver");
                int buyPisc = escogeTres();
                
                if(buyPisc == 3){
                    comprar();
                } else{
                    
                    if(buyPisc == 1){
                        int piscRio = 0;
                        for(Piscifactoria p : piscis){
                            if(p.getTipo().equals("rio")){
                                piscRio++;
                            }
                        }
                        int costeRio = (500+500*piscRio);
                        System.out.println("Coste: "+costeRio+" monedas. Disponibles: "+monedas.getCantidad()+" monedas.");
                        int cont = escogeDos();
                        if(cont==1){
                            if(monedas.getCantidad()>=costeRio){
                                monedas.gastar(costeRio);
                                System.out.println("Introduzca el nombre de la nueva piscifactoría");
                                String nomRio = Reader.readTheLine();
                                while (nomRio.equals("")) {
                                    System.out.println("Vuelva a introducir un nombre");
                                    nomRio = Reader.readTheLine();
                                }
                                piscis.add(new Piscifactoria("rio",nomRio));
                            } else{
                                System.out.println("Monedas insuficientes");
                            }
                        } else{
                            System.out.println("Operación cancelada");
                        }

                    } else{
                        int piscMar = 0;
                        for(Piscifactoria p : piscis){
                            if(p.getTipo().equals("mar")){
                                piscMar++;
                            }
                        }
                        int costeMar = (2000+2000*piscMar);
                        System.out.println("Coste: "+costeMar+" monedas. Disponibles: "+monedas.getCantidad()+" monedas.");
                        int cont = escogeDos();
                        if(cont==1){
                            if(monedas.getCantidad()>=costeMar){
                                monedas.gastar(costeMar);
                                System.out.println("Introduzca el nombre de la nueva piscifactoría");
                                String nomMar = Reader.readTheLine();
                                while (nomMar.equals("")) {
                                    System.out.println("Vuelva a introducir un nombre");
                                    nomMar = Reader.readTheLine();
                                }
                                piscis.add(new Piscifactoria("mar",nomMar));
                            } else{
                                System.out.println("Monedas insuficientes");
                            }
                        } else{
                            System.out.println("Operación cancelada");
                        }
                    }

                }
                break;

            case 2:
                if(almacen==null){
                    if(monedas.getCantidad()>=2000){
                        monedas.gastar(2000);
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
                upgrade();
                break;
        } 
    }

    /**
     * Se encarga de la opción mejorar del método upgrade
     */
    private void mejorar(){
        System.out.println("1. Piscifactoria");
        System.out.println("2. Almacén central");
        System.out.println("3. Volver");

        int opcion = escogeTres();

        switch (opcion) {
            case 1:
                System.out.println("1. Comprar tanque");
                System.out.println("2. Aumentar almacén de comida");
                System.out.println("3. Volver");
                int mejora = escogeTres();

                if(mejora==3){
                    mejorar();
                } else{
                    
                    if(mejora==1){
                        int piscifactoria = selectPisc();
                        int numTanques = piscis.get(piscifactoria).getTanques().size();
                        if(numTanques<10){
                            int costeTanque;
                            if(piscis.get(piscifactoria).getTipo().equals("rio")){
                                costeTanque = 150+(150*numTanques);
                            } else{
                                costeTanque = 600+(600*numTanques);
                            }
                            System.out.println("Coste del nuevo tanque: "+costeTanque+" monedas. Disponible: "+monedas.getCantidad()+" monedas");
                            int cont = escogeDos();
                            if(cont==1){
                                if(monedas.getCantidad()>=costeTanque){
                                    monedas.gastar(costeTanque);
                                    piscis.get(piscifactoria).addTank();
                                } else{
                                    System.out.println("Monedas insuficientes");
                                }
                            }
                        } else{
                            System.out.println("Ya no se admiten más tanques en la piscifactoría");
                        }
                    
                    } else{
                        int piscifactoria = selectPisc();
                        if(piscis.get(piscifactoria).getTipo().equals("rio")){
                            if(piscis.get(piscifactoria).getComidaMax()<250){
                                System.out.println("Coste mejora almacén de comida: 50 monedas. Disponibles: "+monedas.getCantidad()+" monedas");
                                int cont = escogeDos();
                                if(cont==1){
                                    if(monedas.getCantidad()>=50){
                                        monedas.gastar(50);
                                        piscis.get(piscifactoria).upgradeFood();
                                    }
                                }
                            } else{
                                System.out.println("El almacén de la piscifactoría ya no se puede mejorar más");
                            }
                        } else{
                            if(piscis.get(piscifactoria).getComidaMax()<1000){
                                System.out.println("Coste mejora almacén de comida: 200 monedas. Disponibles: "+monedas.getCantidad()+" monedas");
                                int cont = escogeDos();
                                if(cont==1){
                                    if(monedas.getCantidad()>=200){
                                        monedas.gastar(200);
                                        piscis.get(piscifactoria).upgradeFood();
                                    }
                                }
                            } else{
                                System.out.println("El almacén de la piscifactoría ya no se puede mejorar más");
                            }
                        }
                    }
                }
                break;
        
            case 2:
                if(almacen!=null){
                    System.out.println("Aumentar capacidad: 200 monedas. Disponibles: "+monedas.getCantidad()+" monedas");
                    int cont = escogeDos();
                    if(cont==1){
                        if(monedas.getCantidad()>=200){
                            monedas.gastar(200);
                            almacen.upgrade();
                        } else{
                            System.out.println("Monedas insudicientes");
                        }
                    } else{
                        System.out.println("Operación cancelada");
                    }
                } else{
                    System.out.println("No se dispone de almacén central");
                    mejorar();
                }
                break;

            case 3:
                upgrade();
                break;
        }
    }


    /**
     * Permite pasar entre 1 y 5 días de golpe con sus consecuencias
     */
    private void forwardDays(){
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
     * Permite escoger una opción entera entre 1 y 3
     * @return el número seleccionado
     */
    private int escogeTres(){
        int opcion = Reader.readTheNumber();
        while (opcion==0) {
            System.out.println("Por favor introduzca un número entero entre 1 y 3");
            opcion = Reader.readTheNumber();
            if(opcion<0 || opcion>3){
                opcion = 0;
            }
        }
        return opcion;
    }
   
    /**
     * Permite escoger una opción entera entre 1 y 2
     * @return el número seleccionado
     */
    private int escogeDos(){
        System.out.println("Introduzca 1 para continuar o 2 para cancelar");
        int cont = Reader.readTheNumber();
        while (cont<1 || cont>2) {
            System.out.println("Por favor, introduzca 1 o 2");
            cont = Reader.readTheNumber();
        }
        return cont;
    }

    /**
     * Realiza toda la lógica
     * @param args
     */
    public static void main(String[] args) {
        
        Simulador sim = new Simulador();

        sim.init();

        int op = 0;

        try{
            sim.showGeneralStatus();
            while (op!=14) {
                menu();
                op = Reader.readTheNumber();

                switch (op) {
                    case 1:
                        sim.showGeneralStatus();
                        break;
                    case 2:
                        sim.showSpecificStatus();
                        break;
                    case 3:
                        sim.showTankStatus();
                        break;
                    case 4:
                        sim.showStats();
                        break;
                    case 5:
                        sim.showIctio();
                        break;
                    case 6:
                        sim.nextDay();
                        sim.showGeneralStatus();
                        break;
                    case 7:
                        sim.addFood();
                        break;
                    case 8:
                        sim.addFish();
                        break;
                    case 9:
                        sim.sell();
                        break;
                    case 10:
                        sim.cleanTank();
                        break;
                    case 11:
                        sim.emptyTank();
                        break;
                    case 12:
                        sim.upgrade();
                        break;
                    case 13:
                        sim.forwardDays();
                        break;
                    case 14:
                        System.out.println("Salida con éxito");
                        break;
                    case 98:
                        break;
                    case 99:
                        sim.monedas.anadir(1000);
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
}


