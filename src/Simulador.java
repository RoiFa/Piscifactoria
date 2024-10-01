import java.util.ArrayList;
import helpers.Reader;

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

    /**
     * Inicializa el sistema desde cero
     */
    private void init(){

        dias = 0;
        System.out.println("Dame el nombre de la entidad:");
        System.out.println();
        piscis = new ArrayList<>();
        monedas = new Monedas();
        monedas.setCantidad(100);
        almacen = null;

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
        
        int i = 0;
        System.out.println("Seleccione una opción:");
        System.out.println("-------------------------- Piscifactorías --------------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        for(Piscifactoria p : piscis){
            System.out.println(i+".- "+p.getNombre()+" ["+p.getAlive()+"/"+p.getNum()+"/"+p.getTotal()+"]");
            i++;
        }
    }

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






    private void comprar(){
        System.out.println("1. Piscifactoría");
        System.out.println("2. Almacén central: 2000 monedas. Disponibles: "+monedas.getCantidad()+" monedas");
        System.out.println("3. Volver");

        int opcion = escogeTres();

        switch (opcion) {
            case 1:

                System.out.println("1. Río (");
                System.out.println("2. Mar (");
                break;
            case 2:
                if(monedas.getCantidad()>=2000){
                    monedas.gastar(2000);
                    almacen = new Almacen();
                    System.out.println("Monedas restantes: "+monedas.getCantidad());
                }else{
                    System.out.println("Monedas insuficientes");
                }
                break;
            case 3:
                upgrade();
                break;
        } 
    }

    private void mejorar(){

    }

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
     * Realiza toda la lógica
     * @param args
     */
    public static void main(String[] args) {
        
        init();

        int op = 0;

        try{
            while (op!=14) {
                menu();
            }
        }catch(Exception e){
            System.out.println("Ha ocurrido un error");
        }
    }
}


