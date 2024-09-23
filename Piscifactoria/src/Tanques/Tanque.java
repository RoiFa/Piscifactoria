package Tanques;
import Helpers.Reader;

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
        for(int i=0;i<peces.lenght;i++){
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
            menuEspecies();
            creadorEspecies(Reader.readTheNumber());
        }else{
            System.out.println("Quiere añadir un "+peces[0].getNombre()+" mas al tanque?"+"\n"+"1.Si"+"\n"+"2.No");
            int opcion = Reader.readTheNumber();
            if(opcion==1){
                Pez[] especiesPeces = (new Carpa());
                for(int i=0;i<peces.lenght;i++){
                    if(especiesPeces[i].getNombre==peces[0].getNombre){
                        peces[findSpace()] = creadorEspecies(i);
                    }
                }
            }else if(opcion==2){
                System.out.println("Opción cancelada");
            }else{
            System.out.println("Opción no valida");}
            
        }
    }

    public void menuEspecies(){

    }

    public int findSpace(){
        int result=-1;
        for(int i=0;i<peces.lenght||result==-1;i++){
            if(peces[i]==null){
                result=i;
            }
        }
        return result;
    }

    public Pez creadorEspecies(int opcion){
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
                
                break;
            case 17:
                
                break;
            case 18:
                
                break;
            case 19:
                
                break;
            case 20:
                
                break;
            case 21:
                
                break;
            case 22:
                
                break;
            case 23:
                
                break;
            case 24:
                
                break;
            case 25:
                
                break;
            case 26:
                
                break;
            case -1:
                
                break;
            default:
                break;
        }
    }

    public int ocupacion(){
        int count=0;
        for(int i=0;i<peces.lenght;i++){
            if(peces[i]!=null){
                count++;
            }
        }
        return count;
    }

    public int vivos(){
        int count=0;
        for(int i=0;i<peces.lenght;i++){
            if(peces[i].isVivo()){
                count++;
            }
        }
        return count;
    }
    public int alimentados(){
        int count=0;
        for(int i=0;i<peces.lenght;i++){
            if(peces[i].isAlimentado()){
                count++;
            }
        }
        return count;
    }
    public int adultos(){
        int count=0;
        for(int i=0;i<peces.lenght;i++){
            if(peces[i].isAdult()){
                count++;
            }
        }
        return count;
    }
    public int hembras(){
        int count=0;
        for(int i=0;i<peces.lenght;i++){
            if(!peces[i].getSexo()){
                count++;
            }
        }
        return count;
    }
    public int machos(){
        int count=0;
        for(int i=0;i<peces.lenght;i++){
            if(peces[i].getSexo()){
                count++;
            }
        }
        return count;
    }
    public int fertiles(){
        int count=0;
        for(int i=0;i<peces.lenght;i++){
            if(peces[i].isFertil()){
                count++;
            }
        }
        return count;
    }
}
