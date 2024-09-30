package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Carpa extends Pez{

    /**
     * Constructor de una carpa con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Carpa(boolean sexo) {
        super(AlmacenPropiedades.CARPA, sexo);
    }

    /**
     * Constructor de una carpa con género aleatorio.
     */
    public Carpa() {
        super(AlmacenPropiedades.CARPA, RNG.RandomBoolean());
    }
}
