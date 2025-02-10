package peces.doble;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una dorada
 */
public class Dorada extends Pez{

    /**
     * Constructor de una dorada con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Dorada(boolean sexo) {
        super(AlmacenPropiedades.DORADA, sexo);
    }

    /**
     * Constructor de una dorada con género aleatorio.
     */
    public Dorada() {
        super(AlmacenPropiedades.DORADA, RNG.RandomBoolean());
    }

    @Override
    protected int[] comer(int a, int v, boolean enCria) {
        if (enCria) {
            if (a == 0 || v == 0) {
                setAlimentado(false);
                return new int[]{0,0};
            }
            setAlimentado(true);
            return new int[]{1,1};
        }
        if (a == 0 && v == 0) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        setAlimentado(true);
        if (a <= v) {
            return new int[]{0,1};
        } else {
            return new int[]{1,0};
        }
    }

    @Override
    public Dorada reprod() {
        return new Dorada();
    }

    @Override
    public Dorada reprod(boolean sexo) {
        return new Dorada(sexo);
    }
}
