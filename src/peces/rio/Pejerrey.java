package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un pejerrey
 */
public class Pejerrey extends Pez{

    /**
     * Constructor de un pejerrey con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Pejerrey(boolean sexo) {
        super(AlmacenPropiedades.PEJERREY, sexo);
    }

    /**
     * Constructor de una pejerrey con género aleatorio.
     */
    public Pejerrey() {
        super(AlmacenPropiedades.PEJERREY, RNG.RandomBoolean());
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
    public Pejerrey reprod() {
        return new Pejerrey();
    }

    @Override
    public Pejerrey reprod(boolean sexo) {
        return new Pejerrey(sexo);
    }
}
