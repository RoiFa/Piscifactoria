package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un rodaballo
 */
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

    @Override
    public Rodaballo reprod() {
        return new Rodaballo();
    }

    @Override
    public Rodaballo reprod(boolean sexo) {
        return new Rodaballo(sexo);
    }
}
