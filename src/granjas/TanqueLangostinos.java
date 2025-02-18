package granjas;

import com.google.gson.annotations.JsonAdapter;

import adapters.TanqueLangostinosAdapter;
import main.Simulador;

@JsonAdapter(TanqueLangostinosAdapter.class)
public class TanqueLangostinos {

    private int comida=0;

    private int descanso=0;

    public int getComida() {
        return comida;
    }

    public int getDescanso() {
        return descanso;
    }

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

    public void addFood(){
        comida += 1;
    }

    public void setComida(int comida) {
        this.comida = comida;
    }

    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }
    
}