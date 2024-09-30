package peces.doble;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Dorada extends Pez{

    /**
     * Constructor de una dorada con género definido.
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
}
