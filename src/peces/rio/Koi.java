package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un koi
 */
public class Koi extends Pez{

    /**
     * Constructor de un koi con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Koi(boolean sexo) {
        super(AlmacenPropiedades.KOI, sexo);
    }

    /**
     * Constructor de un koi con género aleatorio.
     */
    public Koi() {
        super(AlmacenPropiedades.KOI, RNG.RandomBoolean());
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
        if (a == 0) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        setAlimentado(true);
        return new int[]{1,0};
    }

    @Override
    public Koi reprod() {
        return new Koi();
    }

    @Override
    public Koi reprod(boolean sexo) {
        return new Koi(sexo);
    }
}
