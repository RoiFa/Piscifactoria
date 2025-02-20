package granjas;

import com.google.gson.annotations.JsonAdapter;

import adapters.TanqueLangostinosAdapter;
import main.Simulador;

/** Esta clase se encarga de la logica relacionada con los tanques de langostinos. */
@JsonAdapter(TanqueLangostinosAdapter.class)
public class TanqueLangostinos {

    /** Representa la cantidad de comida almacenada en este tanque, por cada unidad son 50 de comida vegetal */
    private int comida=0;

    /** Representa los dias necesarios para producir, siendo 3 cuando empiezan a producir */
    private int descanso=0;

    /**
     * Devuelve la cantidad de comida almacenada
     * @return Cantidad de comida
     */
    public int getComida() {
        return comida;
    }

    /**
     * Devuelve los dias necesarios para producir actuales
     * @return Dias pasados hasta llegar al maximo
     */
    public int getDescanso() {
        return descanso;
    }

    /**
     * Se encarga de la logica para pasar en un dia este tanque de langostinos
     * @param dead Numero de peces muertos almacenados en la granja
     * @return Nuevo numero de peces muertos almacenados en la granja
     */
    public int nextDay(int dead){
        if(dead!=0){
            if(descanso!=3){
                descanso++;
            }
            dead--;
        }else if(comida>=1){
            if(descanso!=3){
                descanso++;
            }
            comida--;
        }else{
            if (descanso!=0) {
                descanso--;
            }
        }
        if(descanso==3){
            Simulador.instancia.almacen.addFood((int)((Math.random()*100)+100), true);
        }
        
        return dead;
    }

    /**
     * Añade una unidad de comida al tanque
     */
    public void addFood(){
        comida += 1;
    }

    /**
     * Fija la cantidad de comida actual en el tanque
     * @param comida Cantidad de comida a fijar
     */
    public void setComida(int comida) {
        this.comida = comida;
    }

    /**
     * Fija los dias de descanso a la cantidad dada
     * @param descanso Nuevo numero de dias de descanso
     */
    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }
    
}