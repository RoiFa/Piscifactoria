package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un besugo
 */
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
        super(AlmacenPropiedades.BESUGO, RNG.RandomBoolean());
    }

    @Override
    protected int[] comer(int a, int v, boolean enCria) {
        if (enCria) {
            if (a < 2) {
                setAlimentado(false);
                return new int[]{0,0};
            }
            setAlimentado(true);
            return new int[]{2,0};
        }
        if (a <= 0) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        setAlimentado(true);
        return new int[]{1,0};
    }

    @Override
    public Besugo reprod() {
        return new Besugo();
    }

    @Override
    public Besugo reprod(boolean sexo) {
        return new Besugo(sexo);
    }
}
