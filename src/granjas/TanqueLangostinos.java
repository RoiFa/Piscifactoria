package granjas;

import main.Simulador;

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
        }else if(comida>50){
            if(descanso!=3){
                descanso++;
            }
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
        comida += 50;
    }
}