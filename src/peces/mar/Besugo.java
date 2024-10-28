package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Besugo extends Pez{

    /**
     * Constructor de un besugo con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Besugo(boolean sexo) {
        super(AlmacenPropiedades.BESUGO, sexo);
    }

    /**
     * Constructor de un besugo con género aleatorio.
     */
    public Besugo() {
        super(AlmacenPropiedades.BESUGO, RNG.randomBoolean());
    }

    @Override
    protected int[] comer(int a, int v) {
        if (a <= 0) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        return new int[]{1,0};
    }
}
