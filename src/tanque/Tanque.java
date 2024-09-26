package tanque;
import helpers.Reader;
import peces.*;
import peces.mar.*;
import peces.rio.*;
import peces.doble.*;

public abstract class Tanque {
    
    protected String tipo;
    protected String nombrePiscifactoria;
    protected int numTanque;
    protected String pez="";
    protected Pez[] peces;
    protected int maxSize=20;
    
    public Tanque(int numTanque,String pez,String nombrePiscifactoria,String tipo){
        this.pez=pez;
        this.peces=new Pez[20];
        this.numTanque=numTanque;
        this.nombrePiscifactoria=nombrePiscifactoria;
        this.tipo=tipo;
    }

    public void showStatus(){
        System.out.println("================Tanque"+numTanque+"================"+"\n"+
        "Ocupación: "+ocupacion()+"/"+maxSize+"("+(ocupacion()/maxSize)+"%)");
        if(ocupacion()==0){
            System.out.println("Peces vivos: "+vivos()+"/"+ocupacion()+"("+(vivos()/ocupacion())+"%)"+
            "\n"+"Peces alimentados: "+ocupacion()+"/"+ocupacion()+"("+(alimentados()/ocupacion())+"%)"+
            "\n"+"Peces adultos: "+adultos()+"/"+ocupacion()+"("+(adultos()/ocupacion())+"%)"+
            "\n"+"Hembras / machos: "+machos()+"/"+hembras());
        }
    }

    public void showFishStatus(){
        for(int i=0;i<peces.length;i++){
            peces[i].showStatus();
        }
    }

    public void showCapacity(){
        System.out.println("Tanque "+numTanque+" de la piscifactoría x al y% de capacidad.[peces/espacios]");
    }

    public void addFish(){
        if(ocupacion()==maxSize){
            System.out.println("No hay espacio suficiente en este tanque");
        }else if(ocupacion()==0){
            if(tipo=="Mar"){
                menuEspeciesMar();
                creadorEspeciesMar(Reader.readTheNumber());
            }else{
                menuEspeciesRio();
                creadorEspeciesRio(Reader.readTheNumber());
            }
        }else{
            System.out.println("Quiere añadir un "+peces[0].getNombre()+" mas al tanque?"+"\n"+"1.Si"+"\n"+"2.No");
            int opcion = Reader.readTheNumber();
            if(opcion==1){
                Pez[] especiesMar = {new Rodaballo(),new Besugo(),new ArenqueDelAtlantico(),new Abadejo(),new Cobia(), new Dorada(),new BagreDeCanal()};
                Pez[] especiesRio = {new Carpa(),new Koi(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new Dorada(),new BagreDeCanal()};
                if(tipo=="Mar"){
                    for(int i=0;i<especiesMar.length;i++){
                        if(especiesMar[i].getNombre()==peces[0].getNombre()){
                            peces[findSpace()] = creadorEspeciesMar(i);
                        }
                    }
                }else{
                    for(int i=0;i<especiesRio.length;i++){
                        if(especiesRio[i].getNombre()==peces[0].getNombre()){
                            peces[findSpace()] = creadorEspeciesRio(i);
                        }
                    }
                }
            }else if(opcion==2){
                System.out.println("Opción cancelada");
            }else{
                System.out.println("Opción no valida");
            }
            
        }
    }

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

    public int findSpace(){
        int result=-1;
        for(int i=0;i<peces.length||result==-1;i++){
            if(peces[i]==null){
                result=i;
            }
        }
        return result;
    }

    public Pez creadorEspeciesRio(int opcion){
        switch (opcion) {
            default:
                return null;
            case 0:
                System.out.println("Cancelando...");
                break;
            case 1:
                    if(ocupacion()==1){
                        if(peces[0].isMale()){
                            return new Carpa(false);
                        }else {
                            return new Carpa(true);
                        }
                    }else{
                        return new Carpa();
                    }

            case 2:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new Koi(false);
                    }else {
                        return new Koi(true);
                    }
                }else{
                    return new Koi();
                }

            case 3:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new SalmonChinook(false);
                    }else {
                        return new SalmonChinook(true);
                    }
                }else{
                    return new SalmonChinook();
                }

            case 4:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new TilapiaDelNilo(false);
                    }else {
                        return new TilapiaDelNilo(true);
                    }
                }else{
                    return new TilapiaDelNilo();
                }
    
            case 5:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new Pejerrey(false);
                    }else {
                        return new Pejerrey(true);
                    }
                }else{
                    return new Pejerrey();
                }

            case 6:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new Dorada(false);
                    }else {
                        return new Dorada(true);
                    }
                }else{
                    return new Dorada();
                }

            case 7:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new BagreDeCanal(false);
                    }else {
                        return new BagreDeCanal(true);
                    }
                }else{
                    return new BagreDeCanal();
                }
        }
                return null;
    }

    public Pez creadorEspeciesMar(int opcion){
        switch (opcion) {
            default:
                return null;
            case 0:
            System.out.println("Cancelando...");
                break;
                case 1:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new Rodaballo(false);
                    }else {
                        return new Rodaballo(true);
                    }
                }else{
                    return new Rodaballo();
                }

            case 2:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new Besugo(false);
                    }else {
                        return new Besugo(true);
                    }
                }else{
                    return new Besugo();
                }

            case 3:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new ArenqueDelAtlantico(false);
                    }else {
                        return new ArenqueDelAtlantico(true);
                    }
                }else{
                    return new ArenqueDelAtlantico();
                }

            case 4:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new Abadejo(false);
                    }else {
                        return new Abadejo(true);
                    }
                }else{
                    return new Abadejo();
                }

            case 5:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new Cobia(false);
                    }else {
                        return new Cobia(true);
                    }
                }else{
                    return new Cobia();
                }

                case 6:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new Dorada(false);
                    }else {
                        return new Dorada(true);
                    }
                }else{
                    return new Dorada();
                }

            case 7:
                if(ocupacion()==1){
                    if(peces[0].isMale()){
                        return new BagreDeCanal(false);
                    }else {
                        return new BagreDeCanal(true);
                    }
                }else{
                    return new BagreDeCanal();
                }
        }
        return null;
    }
            

    public int ocupacion(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i]!=null){
                count++;
            }
        }
        return count;
    }

    public int vivos(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i].isVivo()){
                count++;
            }
        }
        return count;
    }
    public int alimentados(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i].isAlimentado()){
                count++;
            }
        }
        return count;
    }
    public int adultos(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i].isAdulto()){
                count++;
            }
        }
        return count;
    }
    public int hembras(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i].getSexo()=="Hembra"){
                count++;
            }
        }
        return count;
    }
    public int machos(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i].getSexo()=="Macho"){
                count++;
            }
        }
        return count;
    }
    public int fertiles(){
        int count=0;
        for(int i=0;i<peces.length;i++){
            if(peces[i].isFertil()){
                count++;
            }
        }
        return count;
    }
}
