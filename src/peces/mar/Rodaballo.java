package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Rodaballo extends Pez{

    /**
     * Constructor de un rodaballo con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Rodaballo(boolean sexo) {
        super(AlmacenPropiedades.RODABALLO, sexo);
    }

    /**
     * Constructor de un rodaballo con género aleatorio.
     */
    public Rodaballo() {
        super(AlmacenPropiedades.RODABALLO, RNG.RandomBoolean());
    }

    @Override
    protected int[] comer(int a, int v) {
        if (a <= 2) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        return new int[]{2,0};
    }
}
