package Tanques;
import Helpers.Reader;
import peces.Pez;

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
                Pez[] especiesMar = (new Rodaballo(),new Besugo(),new Robalo(), new LenguadoEuropeo(),new Corvina(),new Sargo(),new LubinaRayada(),new ArenqueDelAtlantico(),new Caballa(),new Abadejo(),new Cobia(),new TruchaArcoiris(),new SalmonAtlantico(), new Dorada(),new LubinaEuropea(),new BagreDeCanal());new Carpa(),new Koi(),new CarpaPlateada(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new SiluroEuropeo(), new PercaEuropea(),new LucioDelNorte(),new CarpinDeTresEspinas(),new TruchaArcoiris(),new SalmonAtlantico(), new Dorada(),new LubinaEuropea(),new BagreDeCanal());
                Pez[] especiesRio = (new Carpa(),new Koi(),new CarpaPlateada(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new SiluroEuropeo(), new PercaEuropea(),new LucioDelNorte(),new CarpinDeTresEspinas(),new TruchaArcoiris(),new SalmonAtlantico(), new Dorada(),new LubinaEuropea(),new BagreDeCanal());
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
        "3.-Carpa plateada\n" +
        "4.-Salmón chinook\n" +
        "5.-Tilapia del Nilo\n" +
        "6.-Pejerrey \n" +
        "7.-Siluro europeo\n" +
        "8.-Perca europea\n" +
        "9.-Lucio del norte\n" +
        "10.-Carpín de tres espinas\n" +
        "12.-Trucha arcoíris\n" +
        "13.-Salmón atlántico\n" +
        "14.-Dorada\n" +
        "15.-Lubina europea\n" +
        "16.-Bagre de canal\n" +
        "0.-Cancelar");
    }
    
    public void menuEspeciesMar(){
        System.out.println(
        "1.-Rodaballo\n" +
        "2.-Besugo\n" +
        "3.-Róbalo\n" +
        "4.-Lenguado europeo\n" +
        "5.-Corvina\n" +
        "6.-Sargo\n" +
        "7.-Lubina rayada\n" +
        "8.-Arenque del Atlántico\n" +
        "9.-Caballa\n" +
        "10.-Abadejo\n" +
        "11.-Cobia\n" +
        "12.-Trucha arcoíris\n" +
        "13.-Salmón atlántico\n" +
        "14.-Dorada\n" +
        "15.-Lubina europea\n" +
        "16.-Bagre de canal\n" +
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
            case 0:
                
                break;
            case 1:
                
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
                
                break;
            case 14:
                
                break;
            case 15:
                
                break;
            case 16:
        }
    }

    public Pez creadorEspeciesMar(int opcion){
        switch (opcion) {
            case 0:
                
                break;
            case 1:
                
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
                
                break;
            case 14:
                
                break;
            case 15:
                
                break;
            case 16:
        }
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
