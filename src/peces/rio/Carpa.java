package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Carpa extends Pez{

    /**
     * Constructor de una carpa con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Carpa(boolean sexo) {
        super(AlmacenPropiedades.CARPA, sexo);
    }

    /**
     * Constructor de una carpa con género aleatorio.
     */
    public Carpa() {
        super(AlmacenPropiedades.CARPA, RNG.RandomBoolean());
    }

    @Override
    protected int[] comer(int a, int v) {
        if (a+v < 2) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        if (a >= 1 && v >= 1 && RNG.RandomBoolean()) {
            setAlimentado(true);
            return new int[]{1,1};
        }
        setAlimentado(true);
        if (a <= v) {
            return new int[]{0,2};
        } else {
            return new int[]{2,0};
        }
    }
}
