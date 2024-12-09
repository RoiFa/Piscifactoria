package tanque;
import com.google.gson.annotations.JsonAdapter;

import adapters.TanqueAdapter;
import helpers.ErrorWriter;
import helpers.LogWriter;
import helpers.RNG;
import helpers.Reader;
import helpers.TranscriptWriter;
import main.Simulador;
import peces.*;
import peces.mar.*;
import peces.rio.*;
import peces.doble.*;

/**
 * Clase que representa los tanques de agua
 */
@JsonAdapter(TanqueAdapter.class)
public class Tanque {
    
    /** Identifica si el tanque es de agua salada o dulce */
    private String tipo;
    /** Indica el número del tanque */
    private int numTanque;
    /** Lista de peces que avitan este tanque */
    public Pez[] peces;
    /** Capacidad maxima del tanque */
    private int maxSize;
    /** El nombre de la piscifactoría */
    private String nomPiscifactoria;
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

    /** @return El nombre de la piscfactoria. */
    public String getNomPiscifactoria(){
        return nomPiscifactoria;
    }

    /**
     * @param tipo si el tanque es de agua salada o dulce
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @param numTanque el número del tanque en la piscifactoría
     */
    public void setNumTanque(int numTanque) {
        this.numTanque = numTanque;
    }

    /**
     * @param peces el array con los peces
     */
    public void setPeces(Pez[] peces) {
        this.peces = peces;
    }

    /**
     * @param maxSize la capacidad máxima del tanque
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * @param nomPiscifactoria el nombre de la piscifactoría
     */
    public void setNomPiscifactoria(String nomPiscifactoria) {
        this.nomPiscifactoria = nomPiscifactoria;
    }

    /**
     * @param tipoPez el tipo de pes que admite
     */
    public void setTipoPez(String tipoPez) {
        this.tipoPez = tipoPez;
    }

    /** Constructor para la carga de datos */
    public Tanque(){}
    
    /**
     * Constructor de la clase tanque
     * @param numTanque Numero del tanque
     * @param pez Nombre del pez alojado en este tanque
     * @param nombrePiscifactoria Nombre de la piscifactoria a la que pertenece
     * @param tipo TIpo de tanque, mar o rio
     */
    public Tanque(int numTanque,String tipo,String nomPiscifactoria){
        this.numTanque=numTanque;
        this.tipo=tipo;
        if(tipo.equals("rio")){
            this.peces=new Pez[25];
        } else{
            this.peces=new Pez[100];
        }
        this.maxSize=this.peces.length;
        this.nomPiscifactoria=nomPiscifactoria;
        this.tipoPez = "";
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
    public void showCapacity(){
        try {
            System.out.println("Tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria+" al "+((int)(ocupacion()/maxSize)*100)+"% de capacidad.["+ocupacion()+"/"+maxSize+"]");
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
        int dineroVendido=0;
        int[] cants;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null&&peces[i].isVivo()){
                if(!peces[i].isFertil()&&(peces[i].getEdad()-peces[i].getMadurez())%peces[i].getCiclo()==0&&peces[i].isAdulto()){
                    peces[i].setFertil(true);
                }
                if(!peces[i].isMale()&&peces[i].isFertil()&&peces[i].isAdulto()&&hayMacho()){
                    addFish(true);
                }
                cants = peces[i].grow(carne,vegetal);
                carne -= cants[0];
                vegetal -= cants[1];
                if (peces[i].getEdad() >= peces[i].getOptimo()) {
                    if ((peces[i] instanceof Koi) && RNG.RandomInt(10) == 1) {
                        peces[i].setMonedas(peces[i].getMonedas()+5);
                    } else {
                        pecesVendidos++;
                        dineroVendido+=peces[i].getMonedas();
                        Simulador.instancia.monedas.anadir(peces[i].getMonedas());
                        Simulador.instancia.orca.registrarVenta(peces[i].getNombre(), peces[i].getMonedas());
                        peces[i] = null;
                    }
                }
            }
            if (Simulador.instancia.almacen.getDisponible()&&(carne <= 0 || vegetal <= 0)) {
                Simulador.instancia.almacen.repartirComida();
            }
        }
        return new int[]{pecesVendidos,dineroVendido,carne,vegetal};
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
        Pez[] especiesMar = {new Rodaballo(),new Besugo(),new ArenqueDelAtlantico(),new Abadejo(),new Cobia(), new Dorada(),new BagreDeCanal()};
                Pez[] especiesRio = {new Carpa(),new Koi(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new Dorada(),new BagreDeCanal()};
            if(enReproduccion){
                try {
                    if(tipo.equals("mar")){
                        for(int i=0;i<especiesMar.length;i++){
                            if(especiesMar[i].getNombre().equals(this.tipoPez)){
                                if(peces.length!=ocupacion()){
                                    for(int k=0;k<especiesMar[i].getHuevos();k++){
                                        peces[findSpace()] = creadorEspecies(especiesMar[(i+1)],true);
                                        Simulador.instancia.orca.registrarNacimiento(this.tipoPez);
                                    }
                                }
                            }
                        }
                    }else{
                        for(int i=0;i<especiesRio.length;i++){
                            if(especiesRio[i].getNombre().equals(this.tipoPez)){
                                for(int k=0;k<peces[0].getHuevos();k++){
                                    for(int j=0;j<especiesMar[i].getHuevos();j++){
                                        if(peces.length!=ocupacion()){
                                            peces[findSpace()] = creadorEspecies(especiesRio[(i+1)],true);
                                            Simulador.instancia.orca.registrarNacimiento(this.tipoPez);
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
                try {
                    if(ocupacion()==maxSize){
                        System.out.println("No hay espacio suficiente en este tanque");
                    }else if(ocupacion()==0){
                        if(tipo.equals("mar")){
                            menuEspeciesMar();
                            peces[0] = creadorEspecies(especiesMar[(Reader.readTheNumber(1,7)-1)],false);
                            if(peces[0]!=null){
                                tipoPez = peces[0].getNombre();
                                LogWriter.writeInLog(peces[0].getNombre()+" ("+((peces[0].getSexo())?"M":"F")+") comprado. Añadido al tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
                            }
                        }else{
                            menuEspeciesRio();
                            peces[0] = creadorEspecies(especiesRio[(Reader.readTheNumber(1,7)-1)],false);
                            if(peces[0]!=null){
                                tipoPez = peces[0].getNombre();
                                LogWriter.writeInLog(peces[0].getNombre()+" ("+((peces[0].getSexo())?"M":"F")+") comprado. Añadido al tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
                            }
                        }
                    }else{
                        int opcion = 0;
                        while (opcion!=2) {
                            System.out.println("Quiere añadir un "+this.tipoPez+" mas al tanque?");
                            opcion = Reader.readTheNumber(1, 2);
                            if(opcion==1){
                                if(tipo=="mar"){
                                    for(int i=0;i<especiesMar.length;i++){
                                        if(especiesMar[i].getNombre().equals(this.tipoPez)){
                                            if(peces.length!=ocupacion()){
                                                int espacio = findSpace();
                                                peces[espacio] = creadorEspecies(especiesMar[(i+1)],false);
                                                LogWriter.writeInLog(peces[espacio].getNombre()+" ("+((peces[espacio].getSexo())?"M":"F")+") comprado. Añadido al tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
                                            }
                                        }
                                    }
                                }else{
                                    for(int i=0;i<especiesRio.length;i++){
                                        if(especiesRio[i].getNombre().equals(this.tipoPez)){
                                            if(peces.length!=ocupacion()){
                                                int espacio = findSpace();
                                                peces[espacio] = creadorEspecies(especiesRio[(i+1)],false);
                                                LogWriter.writeInLog(peces[espacio].getNombre()+" ("+((peces[espacio].getSexo())?"M":"F")+") comprado. Añadido al tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                } catch (Exception e) {
                    ErrorWriter.writeInErrorLog("Error al intentar añadir un pez.");
                    //TODO Borrar e.printStackTrace al terminar el debugging.
                    e.printStackTrace();
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
     * Hace la logica para la creacion de un pez.
     * 
     * @param opcion Posicion en el switch del pez a querer crear
     * @param enReproduccion Informa si es a causa de reproduccion o por compra
     * @return Devuelve el nuevo pez
     */
    public Pez creadorEspecies(Pez pez,boolean enReproduccion){
        Pez added;
        if(enReproduccion){
            added = pez.reprod(!predominan());
            return added;
        } else {
            if(Simulador.instancia.monedas.comprar(pez.getCoste())){
                if(ocupacion()==1){
                    added = pez.reprod(!predominan());
                } else {
                    added = pez.reprod();
                }
                TranscriptWriter.writeInTranscript(added.getNombre() + " (" + ((added.getSexo())?"M":"F") + ") comprado por " + added.getCoste() + " monedas. Añadido al tanque " + this.numTanque + " de la piscifactoría " + this.nomPiscifactoria);
                return added;
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
            if(peces[i]!=null&&!peces[i].getSexo()&&peces[i].isVivo()){
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
            if(peces[i]!=null&&peces[i].getSexo()&&peces[i].isVivo()){
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
        System.out.println("El tanque se ha vaciado por completo");
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
        return "Tanque de : "+tipo+" con "+peces.length+" peces de la especie "+this.tipoPez;
    }
}