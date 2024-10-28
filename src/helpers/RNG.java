package helpers;

import java.util.Random;

/**
 * Clase helper que ayda a generar números aleatorios.
 */
public class RNG {

    /** La variable de clase Random */
    static Random rm = new Random();

    /**
     * Método que devuelve un Int aleatorio
     * 
     * @param origin    El origen del número aleatorio.
     * @param bound El límite del número aleatorio.
     * @return  Un número aleatorio comprendico entre el origen y el límite.
     */
    public static int randomInt(int origin, int bound) {
        return rm.nextInt(origin, bound);
    }

    /**
     * Método que devuelve un Int aleatorio
     * 
     * @param bound El límite del número aleatorio.
     * @return  Un número aleatorio comprendido entre 0 y el límite
     */
    public static int randomInt(int bound) {
        return rm.nextInt(bound);
    }

    /**
     * Método que devuelve un Float aleatorio
     * 
     * @param origin    El origen del número aleatorio.
     * @param bound El límite del número aleatorio.
     * @return  Un número aleatorio comprendico entre el origen y el límite.
     */
    public static float randomFloat(float origin, float bound) {
        return rm.nextFloat(origin, bound);
    }

    /**
     * Método que devuelve un Float aleatorio
     * 
     * @param bound El límite del número aleatorio.
     * @return  Un número aleatorio comprendido entre 0 y el límite
     */
    public static float randomFloat(float bound) {
        return rm.nextFloat(bound);
    }

    /**
     * Método que devuelve un Double aleatorio
     * 
     * @param origin    El origen del número aleatorio.
     * @param bound El límite del número aleatorio.
     * @return  Un número aleatorio comprendico entre el origen y el límite.
     */
    public static double randomDouble(double origin, double bound) {
        return rm.nextDouble(origin, bound);
    }

    /**
     * Método que devuelve un Double aleatorio
     * 
     * @param bound El límite del número aleatorio.
     * @return  Un número aleatorio comprendido entre 0 y el límite
     */
    public static double randomDouble(double bound) {
        return rm.nextDouble(bound);
    }

    /**
     * Método que devuelve un booleano aletorio
     * @return  True o false, aleatoriamente.
     */
    public static boolean randomBoolean() {
        return rm.nextBoolean();
    }
}
