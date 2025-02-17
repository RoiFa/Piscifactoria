package tanque.subtanque;

import java.util.ArrayList;
import java.util.Map;

import main.Simulador;
import peces.Pez;
import tanque.Tanque;

public class TanqueHuevos extends Tanque{
    
    /**
     * Constructor de un tanque de huevos.
     * 
     * @param numTanque El número de tanque de huevos.
     * @param tipo  El tipo de peces que puede guardar (mar o rio)
     * @param nomPiscifactoria  El nombre de la piscifactoría.
     */
    public TanqueHuevos(int numTanque, String tipo, String nomPiscifactoria) {
        super(numTanque, tipo, nomPiscifactoria);
        this.maxSize = 25;
    }

    /**
     * Busca si hay espacios en algún tanque de la piscifactoría y añade los peces que puedan ser añadidos.
     */
    @Override
    public int[] nextDay(int carne, int vegetal) {
        for (Pez fish : new ArrayList<>(peces)) {
            if (checkPiscifactoriaSpace(fish)) {
                this.peces.remove(fish);
            }
        }
        return null;
    }

    /**
     * Busca si hay algún tanque con espacio en la piscifactoría con el mismo tipo de pez que el pasado y lo añade.
     * 
     * @param fish  El pez a añadir en otro tanque
     * @return  Si se ha añadido o no.
     */
    private boolean checkPiscifactoriaSpace(Pez fish) {
        int length = Simulador.instancia.getPiscis().size();
        for (int i = 0; i < length; i++) {
            if (Simulador.instancia.getPiscis().get(i).getNombre().equals(this.nomPiscifactoria)) {
                int length2 = Simulador.instancia.getPiscis().get(i).getTanques().size();
                for (int j = 0; j < length2; j++) {
                    if (Simulador.instancia.getPiscis().get(i).getTanques().get(j).getTipoPez().equals(fish.getNombre()) && Simulador.instancia.getPiscis().get(i).getTanques().get(j).ocupacion() < length2) {
                        Simulador.instancia.getPiscis().get(i).getTanques().get(j).addFish(fish);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Añade a una lista el número de cada tipo de pez.
     * 
     * @param map   La lista a actualizar
     * @return  La lista actualizada.
     */
    public Map<String, Integer> listFish(Map<String, Integer> map) {
        for (Pez pez : peces) {
            int newInt = map.get(pez.getNombre())+1;
            map.put(pez.getNombre(), newInt);
        }
        return map;
    }
    
}
