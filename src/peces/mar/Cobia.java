package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una cobia
 */
public class Cobia extends Pez{

    /**
     * Constructor de una cobia con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Cobia(boolean sexo) {
        super(AlmacenPropiedades.COBIA, sexo);
    }

    /**
     * Constructor de una cobia con género aleatorio.
     */
    public Cobia() {
        super(AlmacenPropiedades.COBIA, RNG.RandomBoolean());
    }

    @Override
    protected int[] comer(int a, int v, boolean enCria) {
        if (a < 2) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        setAlimentado(true);
        return new int[]{2,0};
    }

    @Override
    public Cobia reprod() {
        return new Cobia();
    }

    @Override
    public Cobia reprod(boolean sexo) {
        return new Cobia(sexo);
    }
}
