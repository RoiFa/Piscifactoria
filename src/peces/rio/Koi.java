package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

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
    protected void comer() {
        // TODO Comer
    }
}
