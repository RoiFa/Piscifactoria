package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un abadejo
 */
public class Abadejo extends Pez{

    /**
     * Constructor de un abadejo con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Abadejo(boolean sexo) {
        super(AlmacenPropiedades.ABADEJO, sexo);
    }

    /**
     * Constructor de un abadejo con género aleatorio.
     */
    public Abadejo() {
        super(AlmacenPropiedades.ABADEJO, RNG.RandomBoolean());
    }

    @Override
    protected int[] comer(int a, int v) {
        if (RNG.RandomInt(100)<=75) {
            setAlimentado(true);
            return new int[]{0,0};
        }
        if (a <= 0) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        setAlimentado(true);
        return new int[]{1,0};
    }

    @Override
    public Abadejo reprod() {
        return new Abadejo();
    }

    @Override
    public Abadejo reprod(boolean sexo) {
        return new Abadejo(sexo);
    }
}
