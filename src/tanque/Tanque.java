package tanque;
import helpers.ErrorWriter;
import helpers.RNG;
import helpers.Reader;
import main.Almacen;
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

    /** @return El tipo de tanque (mar o río) */
    public String getTipo() {
        return tipo;
    }

    /** @return El número de tanque en la piscifactoría. */
    public int getNumTanque() {
        return numTanque;
    }

    /** @return La capacidad máxima del tanque. */
    public int getMaxSize() {
        return maxSize;
    }

    /** @return El tipo de pez que admite el tanque. */
    public String getTipoPez(){
        return tipoPez;
    }

    /** @return La lista de peces en el tanque. */
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
        } catch (ArithmeticException e) {
            //TODO Borrar el e.printStackTrace al teriminar el debugging.
            ErrorWriter.writeInErrorLog("Error al mostrar el estado actual del tanque.\n");
            e.printStackTrace();
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
        try {
            System.out.println("Tanque "+numTanque+" de la piscifactoría "+nombrePiscifactoria+" al "+((int)(ocupacion()/maxSize)*100)+"% de capacidad.["+ocupacion()+"/"+maxSize+"]");
        } catch (ArithmeticException e) {
            ErrorWriter.writeInErrorLog("Error al mostrar la capacidad de un tanque.\n");
            //TODO Borrar el e.printStackTrace al terminar el debugging.
            e.printStackTrace();
        }
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
                Almacen.repartirComida();
            }
        }
        return new int[]{pecesVendidos,carne,vegetal};
    }

    /**
     * Comprueba si hay un macho fértil y vivo en el tanque
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
     * Muestra las opciones y hace las comprobaciones a la hora de intentar añadir un pez al tanque
     */
    public void addFish(boolean enReproduccion){
        try {
            Pez[] especiesMar = {new Rodaballo(),new Besugo(),new ArenqueDelAtlantico(),new Abadejo(),new Cobia(), new Dorada(),new BagreDeCanal()};
                Pez[] especiesRio = {new Carpa(),new Koi(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new Dorada(),new BagreDeCanal()};
            if(enReproduccion){
                try {
                    if(tipo.equals("mar")){
                        for(int i=0;i<especiesMar.length;i++){
                            if(especiesMar[i].getNombre()==buscaNombre()){
                                if(peces.length!=ocupacion()){
                                    for(int k=0;k<especiesMar[i].getHuevos();k++){
                                        peces[findSpace()] = creadorEspecies(especiesMar[(i+1)],true);
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
                                            peces[findSpace()] = creadorEspecies(especiesRio[(i+1)],true);
                                            Simulador.estadisticas.registrarNacimiento(buscaNombre());
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    ErrorWriter.writeInErrorLog("Error en la reproducción de peces.");
                }
            }else{
                if(ocupacion()==maxSize){
                    System.out.println("No hay espacio suficiente en este tanque");
                }else if(ocupacion()==0){
                    if(tipo.equals("mar")){
                        menuEspeciesMar();
                        peces[0] = creadorEspecies(especiesMar[(Reader.readTheNumber()+1)],false);
                        if(peces[0]!=null){
                            tipoPez = peces[0].getNombre();
                        }
                    }else{
                        menuEspeciesRio();
                        peces[0] = creadorEspecies(especiesRio[(Reader.readTheNumber()+1)],false);
                        if(peces[0]!=null){
                            tipoPez = peces[0].getNombre();
                        }
                    }
                }else{
                    System.out.println("Quiere añadir un "+buscaNombre()+" mas al tanque?");
                    if(tipo=="mar"){
                        for(int i=0;i<especiesMar.length;i++){
                            if(especiesMar[i].getNombre()==buscaNombre()){
                                if(peces.length!=ocupacion()){
                                    peces[findSpace()] = creadorEspecies(especiesMar[(i+1)],false);
                                }
                            }
                        }
                    }else{
                        for(int i=0;i<especiesRio.length;i++){
                            if(especiesRio[i].getNombre()==buscaNombre()){
                                if(peces.length!=ocupacion()){
                                    peces[findSpace()] = creadorEspecies(especiesRio[(i+1)],false);
                                }
                            }
                        }
                    }
                    
                }
            }
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error en la reproducción de peces.");
            //TODO Borrar e.printStackTrace al terminar el debugging.
            e.printStackTrace();
        }
    }

    /**
     * Método que busca el nombre del tipo de peces que hay en el tanque.
     * 
     * @return  El nombre del tipo de peces
     */
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
     * Hace la logica para la creacion de un pez.
     * 
     * @param opcion Posicion en el switch del pez a querer crear
     * @param enReproduccion Informa si es a causa de reproduccion o por compra
     * @return Devuelve el nuevo pez
     */
    public Pez creadorEspecies(Pez pez,boolean enReproduccion){
        if(enReproduccion){
            if(ocupacion()==1||enReproduccion){
                return pez.reprod(!predominan());
            }
            return pez.reprod();
        }
        if(Monedas.comprar(pez.getCoste())){
            if(ocupacion()==1||enReproduccion){
                return pez.reprod(!predominan());
            }
            return pez.reprod();
        }
        return null;
    }
    //TODO Continuar comprobando errores aqui
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

    /**
     * Método que añade un pez aleatorio al tanque.
     */
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

    /**
     * Devuelve información del tanque
     */
    @Override
    public String toString() {
        return "Tanque de : "+tipo+" con "+peces.length+" peces de la especie "+peces[0].getNombre();
    }
}
