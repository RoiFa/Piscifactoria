package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class SalmonChinook extends Pez{

    /**
     * Constructor de un salmón chinook con género definido.
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
}
