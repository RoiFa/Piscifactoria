package tanque;
import helpers.RNG;
import helpers.Reader;
import main.Simulador;
import monedas.Monedas;
import peces.*;
import peces.mar.*;
import peces.rio.*;
import peces.doble.*;

/**
 * Clase que representa los tanques de agua
 */
public class Tanque {
    
    /** Identifica si el tanque es de agua salada o dulce */
    private String tipo;
    /** Indica el número del tanque */
    private int numTanque;
    /** Cuenta la creción de tanques */
    private static int contador=0;
    /** Lista de peces que avitan este tanque */
    public Pez[] peces;
    /** Capacidad maxima del tanque */
    private int maxSize;
    /** El tipo de pez que admite */
    private String tipoPez;

    public String getTipo() {
        return tipo;
    }

    public int getNumTanque() {
        return numTanque;
    }

    public int getMaxSize() {
        return maxSize;
    }
    public String getTipoPez(){
        return tipoPez;
    }
    public Pez[] getPeces() {
        return peces;
    }
    
    /**
     * Constructor de la clase tanque
     * @param numTanque Numero del tanque
     * @param pez Nombre del pez alojado en este tanque
     * @param nombrePiscifactoria Nombre de la piscifactoria a la que pertenece
     * @param tipo TIpo de tanque, mar o rio
     */
    public Tanque(int numTanque,String tipo){
        contador++;
        this.numTanque=contador;
        this.tipo=tipo;
        if(tipo.equals("rio")){
            this.peces=new Pez[25];
        } else{
            this.peces=new Pez[100];
        }
        this.maxSize=this.peces.length;
    }

    /**
     * Muestra el estado actual del tanque
     */
    public void showStatus(){
        System.out.println("================Tanque "+numTanque+"================"+"\n"+
        "Ocupación: "+ocupacion()+"/"+maxSize+"("+(ocupacion()/maxSize)+"%)");
        try {
            if(ocupacion()!=0){
                System.out.println("Peces vivos: "+vivos()+"/"+ocupacion()+"("+((int)(vivos()/ocupacion())*100)+"%)"+
                "\n"+"Peces alimentados: "+ocupacion()+"/"+ocupacion()+"("+((int)(alimentados()/ocupacion())*100)+"%)"+
                "\n"+"Peces adultos: "+adultos()+"/"+ocupacion()+"("+((int)(adultos()/ocupacion())*100)+"%)"+
                "\n"+"Hembras / machos: "+machos()+"/"+hembras());
            }
        } catch (Exception e) {
            //Skip en caso de error debido a divisiones con 0
        }
        
    }

    /**
     * Muestra el estado de todos los peces de este tanque
     */
    public void showFishStatus(){
        if(ocupacion()!=0){
            for(int i=0;i<peces.length;i++){
                if(peces[i]!=null){
                    peces[i].showStatus();
                }
            }
        }else{
            System.out.println("Este tanque no tiene ningun pez");
        }
    }

    /**
     * Muestra la capacidad del tanque y su ocupación actual
    */
    public void showCapacity(String nombrePiscifactoria){
        System.out.println("Tanque "+numTanque+" de la piscifactoría "+nombrePiscifactoria+" al "+((int)(ocupacion()/maxSize)*100)+"% de capacidad.["+ocupacion()+"/"+maxSize+"]");
    }

    /**
     * Hace la logica al pasar de dia para el tanque y los peces dentro de este
     * @param carne Cantidad de comida para carnivoros en el almacen
     * @param vegetal Cantidad de comida para herbivoros en el almacen
     * @return Nuevas cantidades de alimento luego de alimentar a los peces
     */
    public int[] nextDay(int carne, int vegetal){
        int pecesVendidos=0;
        int[] cants;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null){
                if(!peces[i].isFertil()&&(peces[i].getEdad()-peces[i].getMadurez())%peces[i].getCiclo()==0&&peces[i].isAdulto()){
                    peces[i].setFertil(true);
                }
                if(peces[i].isVivo()&&!peces[i].isMale()&&peces[i].isFertil()&&peces[i].isAdulto()&&hayMacho()){
                    addFish(true);
                }
                cants = peces[i].grow(carne,vegetal);
                carne -= cants[0];
                vegetal -= cants[1];
                if (peces[i].getEdad() == peces[i].getOptimo()) {
                    if (peces[i] instanceof Koi && RNG.RandomInt(10) == 1) {
                        peces[i].setMonedas(peces[i].getMonedas()+5);
                    } else {
                        Monedas.anadir(peces[i].getMonedas());
                        pecesVendidos++;
                        Simulador.estadisticas.registrarVenta(peces[i].getNombre(), peces[i].getMonedas());
                        peces[i] = null;
                    }
                }
            }
            if (Simulador.almacen!=null&&(carne <= 0 || vegetal <= 0)) {
                Simulador.almacen.repartirComida(0,0);
            }
            
        }
        return new int[]{pecesVendidos,carne,vegetal};
    }

    /**
     * Comprueba si hay un macho en el tanque
     * @return true o false dependiendo de si hay o no un macho
     */
    public boolean hayMacho(){
        for(int i=0;i<peces.length;i++) {
            if(peces[i]!=null){
                if(peces[i].isMale()&&peces[i].isFertil()&&peces[i].isVivo()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Muestra las opciones y hace las comprobaciones a la hora de intentar añadir un pez manualmente al tanque
     */
    public void addFish(boolean enReproduccion){
        if(enReproduccion){
            Pez[] especiesMar = {new Rodaballo(),new Besugo(),new ArenqueDelAtlantico(),new Abadejo(),new Cobia(), new Dorada(),new BagreDeCanal()};
            Pez[] especiesRio = {new Carpa(),new Koi(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new Dorada(),new BagreDeCanal()};
            if(tipo.equals("mar")){
                for(int i=0;i<especiesMar.length;i++){
                    if(especiesMar[i].getNombre()==buscaNombre()){
                        if(peces.length!=ocupacion()){
                            for(int k=0;k<especiesMar[i].getHuevos();k++){
                                peces[findSpace()] = creadorEspeciesMar((i+1),true);
                                Simulador.estadisticas.registrarNacimiento(buscaNombre());
                            }
                        }
                    }
                }
            }else{
                for(int i=0;i<especiesRio.length;i++){
                    if(especiesRio[i].getNombre()==buscaNombre()){
                        for(int k=0;k<peces[0].getHuevos();k++){
                            for(int j=0;j<especiesMar[i].getHuevos();j++){
                                if(peces.length!=ocupacion()){
                                    peces[findSpace()] = creadorEspeciesRio((i+1),true);
                                    Simulador.estadisticas.registrarNacimiento(buscaNombre());
                                }
                            }
                        }
                    }
                }
            }
        }else{
            if(ocupacion()==maxSize){
                System.out.println("No hay espacio suficiente en este tanque");
            }else if(ocupacion()==0){
                if(tipo.equals("mar")){
                    menuEspeciesMar();
                    peces[0] = creadorEspeciesMar(Reader.readTheNumber(),false);
                    if(peces[0]!=null){
                        tipoPez = peces[0].getNombre();
                    }
                }else{
                    menuEspeciesRio();
                    peces[0] = creadorEspeciesRio(Reader.readTheNumber(),false);
                    if(peces[0]!=null){
                        tipoPez = peces[0].getNombre();
                    }
                }
            }else{
                System.out.println("Quiere añadir un "+buscaNombre()+" mas al tanque?");
                Pez[] especiesMar = {new Rodaballo(),new Besugo(),new ArenqueDelAtlantico(),new Abadejo(),new Cobia(), new Dorada(),new BagreDeCanal()};
                Pez[] especiesRio = {new Carpa(),new Koi(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new Dorada(),new BagreDeCanal()};
                if(tipo=="mar"){
                    for(int i=0;i<especiesMar.length;i++){
                        if(especiesMar[i].getNombre()==buscaNombre()){
                            if(peces.length!=ocupacion()){
                                peces[findSpace()] = creadorEspeciesMar((i+1),false);
                            }
                        }
                    }
                }else{
                    for(int i=0;i<especiesRio.length;i++){
                        if(especiesRio[i].getNombre()==buscaNombre()){
                            if(peces.length!=ocupacion()){
                                peces[findSpace()] = creadorEspeciesRio((i+1),false);
                            }
                        }
                    }
                }
                
            }
        }
    }

    public String buscaNombre(){
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null){
                return peces[i].getNombre();
            }
        }
        return "";
    }

    /**Menu de texto para peces de Rio */
    public void menuEspeciesRio(){
        System.out.println("Seleccione una de estas especies a añadir:");
        System.out.println(
        "1.-Carpa\n" +
        "2.-Koi\n" +
        "3.-Salmón chinook\n" +
        "4.-Tilapia del Nilo\n" +
        "5.-Pejerrey \n" +
        "6.-Dorada\n" +
        "7.-Bagre de canal\n" +
        "0.-Cancelar");
    }
    
    /**Menu de texto para peces de Mar */
    public void menuEspeciesMar(){
        System.out.println(
        "1.-Rodaballo\n" +
        "2.-Besugo\n" +
        "3.-Arenque del Atlántico\n" +
        "4.-Abadejo\n" +
        "5.-Cobia\n" +
        "6.-Dorada\n" +
        "7.-Bagre de canal\n" +
        "0.-Cancelar");
    }

    /**
     * Encuentra un hueco dentro del array del Tanque
     * @return Devuelve la posicion del hueco
     */
    public int findSpace(){
        int result=-1;
        for(int i=0;i<peces.length||result==-1;i++){
            if(peces[i]==null){
                result=i;
                break;
            }
        }
        return result;
    }

    /**
     * Hace la logica para la creacion de un pez de Rio
     * @param opcion Posicion en el switch del pez a querer crear
     * @param enReproduccion Informa si es a causa de reproduccion o por compra
     * @return Devuelve el nuevo pez
     */
    public Pez creadorEspeciesRio(int opcion,boolean enReproduccion){
        switch (opcion) {
            default:
                return null;
            case 0:
                System.out.println("Cancelando...");
                break;
            case 1:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Carpa(false);
                        }else {
                            return new Carpa(true);
                        }
                    }else{
                        return new Carpa();
                    }
            }else{
            if(Monedas.comprar(new Carpa().getCoste())){
                    if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Carpa(false);
                        }else {
                            return new Carpa(true);
                        }
                    }else{
                        return new Carpa();
                    }
                }else{
                    return null;
                }
            }
            case 2:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Koi(false);
                        }else {
                            return new Koi(true);
                        }
                    }else{
                        return new Koi();
                    }
            }else{
            if(Monedas.comprar(new Koi().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new Koi(false);
                    }else {
                        return new Koi(true);
                    }
                }else{
                    return new Koi();
                }
            }else{
                return null;
            }
        }

            case 3:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new SalmonChinook(false);
                        }else {
                            return new SalmonChinook(true);
                        }
                    }else{
                        return new SalmonChinook();
                    }
            }else{
            if(Monedas.comprar(new SalmonChinook().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new SalmonChinook(false);
                    }else {
                        return new SalmonChinook(true);
                    }
                }else{
                    return new SalmonChinook();
                }
            }else{
                return null;
            }
        }
            case 4:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new TilapiaDelNilo(false);
                        }else {
                            return new TilapiaDelNilo(true);
                        }
                    }else{
                        return new TilapiaDelNilo();
                    }
            }else{
            if(Monedas.comprar(new TilapiaDelNilo().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new TilapiaDelNilo(false);
                    }else {
                        return new TilapiaDelNilo(true);
                    }
                }else{
                    return new TilapiaDelNilo();
                }
            }else{
                return null;
            }
        }    
            case 5:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Pejerrey(false);
                        }else {
                            return new Pejerrey(true);
                        }
                    }else{
                        return new Pejerrey();
                    }
            }else{
            if(Monedas.comprar(new Pejerrey().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new Pejerrey(false);
                    }else {
                        return new Pejerrey(true);
                    }
                }else{
                    return new Pejerrey();
                }
            }else{
                return null;
            }
        }
            case 6:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Dorada(false);
                        }else {
                            return new Dorada(true);
                        }
                    }else{
                        return new Dorada();
                    }
            }else{
            if(Monedas.comprar(new Dorada().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new Dorada(false);
                    }else {
                        return new Dorada(true);
                    }
                }else{
                    return new Dorada();
                }
            }else{
                return null;
            }
        }

            case 7:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new BagreDeCanal(false);
                        }else {
                            return new BagreDeCanal(true);
                        }
                    }else{
                        return new BagreDeCanal();
                    }
            }else{
            if(Monedas.comprar(new BagreDeCanal().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new BagreDeCanal(false);
                    }else {
                        return new BagreDeCanal(true);
                    }
                }else{
                    return new BagreDeCanal();
                }
            }else{
                return null;
            }
        }
    }
                return null;
    }
    /**
     * Hace la logica para la creacion de un pez de Mar
     * @param opcion Posicion en el switch del pez a querer crear
     * @param enReproduccion Informa si es a causa de reproduccion o por compra
     * @return Devuelve el nuevo pez
     */
    public Pez creadorEspeciesMar(int opcion,boolean enReproduccion){
        switch (opcion) {
            default:
                return null;
            case 0:
            System.out.println("Cancelando...");
                break;
                case 1:
                if(enReproduccion){
                    if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Rodaballo(false);
                        }else {
                            return new Rodaballo(true);
                        }
                    }else{
                        return new Rodaballo();
                    }
                }else{
                if(Monedas.comprar(new Rodaballo().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new Rodaballo(false);
                    }else {
                        return new Rodaballo(true);
                    }
                }else{
                    return new Rodaballo();
                }
            }else{
                return null;
            }
        }

            case 2:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Besugo(false);
                        }else {
                            return new Besugo(true);
                        }
                    }else{
                        return new Besugo();
                    }
            }else{
            if(Monedas.comprar(new Besugo().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new Besugo(false);
                    }else {
                        return new Besugo(true);
                    }
                }else{
                    return new Besugo();
                }
            }else{
                return null;
            }
        }

            case 3:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new ArenqueDelAtlantico(false);
                        }else {
                            return new ArenqueDelAtlantico(true);
                        }
                    }else{
                        return new ArenqueDelAtlantico();
                    }
            }else{
            if(Monedas.comprar(new ArenqueDelAtlantico().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new ArenqueDelAtlantico(false);
                    }else {
                        return new ArenqueDelAtlantico(true);
                    }
                }else{
                    return new ArenqueDelAtlantico();
                }
            }else{
                return null;
            }
        }

            case 4:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Abadejo(false);
                        }else {
                            return new Abadejo(true);
                        }
                    }else{
                        return new Abadejo();
                    }
            }else{
            if(Monedas.comprar(new Abadejo().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new Abadejo(false);
                    }else {
                        return new Abadejo(true);
                    }
                }else{
                    return new Abadejo();
                }
            }else{
                return null;
            }
        }

            case 5:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Cobia(false);
                        }else {
                            return new Cobia(true);
                        }
                    }else{
                        return new Cobia();
                    }
            }else{
            if(Monedas.comprar(new Cobia().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new Cobia(false);
                    }else {
                        return new Cobia(true);
                    }
                }else{
                    return new Cobia();
                }
            }else{
                return null;
            }
        }
                case 6:
                if(enReproduccion){
                    if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new Dorada(false);
                        }else {
                            return new Dorada(true);
                        }
                    }else{
                        return new Dorada();
                    }
                }else{
                if(Monedas.comprar(new Dorada().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new Dorada(false);
                    }else {
                        return new Dorada(true);
                    }
                }else{
                    return new Dorada();
                }
            }else{
                return null;
            }
        }
            case 7:
            if(enReproduccion){
                if(ocupacion()==1||enReproduccion){
                        if(predominan()){
                            return new BagreDeCanal(false);
                        }else {
                            return new BagreDeCanal(true);
                        }
                    }else{
                        return new BagreDeCanal();
                    }
            }else{
            if(Monedas.comprar(new BagreDeCanal().getCoste())){
                if(ocupacion()==1||enReproduccion){
                    if(predominan()){
                        return new BagreDeCanal(false);
                    }else {
                        return new BagreDeCanal(true);
                    }
                }else{
                    return new BagreDeCanal();
                }
            }else{
                return null;
            }
        }
    }
        return null;
    }
    
    /**
     * Identifica que genero predomina en el tanque
     * @return Devuelve true o false dependiendo si macho o hembra
     */
    public boolean predominan(){
        int m=0;
        int w=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null){
                if(peces[i].isMale()&&peces[i].isVivo()){
                    m++;
                }else{
                    w++;
                }
            }
        }
        if(m<w){
            return false;
        }else {
            return true;
        }
    }

    /**
     * Devuelve la cantidad ocupada en el tanque
     * @return Numero de espacios ocupados
     */
    public int ocupacion(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null){
                count++;
            }
        }
        return count;
    }

    /**
     * Devuelve la cantidad de peces vivos en el tanque
     * @return Número de peces vivos
     */
    public int vivos(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null&&peces[i].isVivo()){
                count++;
            }
        }
        return count;
    }

    /**
     * Devuelve la cantidad de peces alimentados
     * @return Numero de peces alimentados
     */
    public int alimentados(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null&&peces[i].isAlimentado()&&peces[i].isVivo()){
                count++;
            }
        }
        return count;
    }

    /**
     * Devuelve la cantidad de peces adultos
     * @return Número de peces adultos
     */
    public int adultos(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null&&peces[i].isAdulto()&&peces[i].isVivo()){
                count++;
            }
        }
        return count;
    }

    /**
     * Devuelve la cantidad de peces hembra
     * @return Numero de peces hembra
     */
    public int hembras(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null&&peces[i].getSexo()=="Hembra"&&peces[i].isVivo()){
                count++;
            }
        }
        return count;
    }

    /**
     * Devuelve la cantidad de peces macho
     * @return Numero de peces macho
     */
    public int machos(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null&&peces[i].getSexo()=="Macho"&&peces[i].isVivo()){
                count++;
            }
        }
        return count;
    }

    /**
     * Devuelve la cantidad de peces fertiles
     * @return Numero de peces fertiles
     */
    public int fertiles(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null&&peces[i].isFertil()&&peces[i].isVivo()){
                count++;
            }
        }
        return count;
    }
    /**
     * Elimina los peces muertos del tanque
     */
    public void cleanTank(){
        for(int i = 0;i<maxSize;i++){
            if(peces[i]!=null && !peces[i].isVivo()){
                peces[i] = null;
            }
        }
    }

    /**
     * Elimina todos los peces del tanque independientemente de su estado
     */
    public void emptyTank(){
        for(int i = 0;i<maxSize;i++){
            if(peces[i]!=null){
                peces[i] = null;
            }
        }
        System.out.println("El tanque se ah vaciado por completo");
    }

    public void randomFish(){
        if(tipo.equals("mar")){
            switch (RNG.RandomInt(7)) {
                case 0:
                    peces[0] = new Abadejo();
                    break;
                case 1:
                peces[0] = new ArenqueDelAtlantico();
                    break;
                case 2:
                peces[0] = new Besugo();
                    break;
                case 3:
                peces[0] = new Cobia();
                    break;
                case 4:
                peces[0] = new Rodaballo();
                    break;
                case 5:
                peces[0] = new BagreDeCanal();
                    break;
                case 6:
                peces[0] = new Dorada();
                    break;
            
                default:
                    break;
            }
           
        }else{
            switch (RNG.RandomInt(7)) {
                case 0:
                    peces[0] = new Carpa();
                    break;
                case 1:
                peces[0] = new Koi();
                    break;
                case 2:
                peces[0] = new Pejerrey();
                    break;
                case 3:
                peces[0] = new SalmonChinook();
                    break;
                case 4:
                peces[0] = new TilapiaDelNilo();
                    break;
                case 5:
                peces[0] = new BagreDeCanal();
                    break;
                case 6:
                peces[0] = new Dorada();
                    break;
            
                default:
                    break;
            }
        }
        if(peces[0]!=null){
            tipoPez = peces[0].getNombre();
        }            
    }

    @Override
    public String toString() {
        return "Tanque de : "+tipo+" con "+peces.length+" peces de la especie "+peces[0].getNombre();
    }
}
