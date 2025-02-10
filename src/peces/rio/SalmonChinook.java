package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un salmón chinook
 */
public class SalmonChinook extends Pez{

    /**
     * Constructor de un salmón chinook con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public SalmonChinook(boolean sexo) {
        super(AlmacenPropiedades.SALMON_CHINOOK, sexo);
    }

    /**
     * Constructor de un salmón chinook con género aleatorio.
     */
    public SalmonChinook() {
        super(AlmacenPropiedades.SALMON_CHINOOK, RNG.RandomBoolean());
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
    public SalmonChinook reprod() {
        return new SalmonChinook();
    }

    @Override
    public SalmonChinook reprod(boolean sexo) {
        return new SalmonChinook(sexo);
    }
}
