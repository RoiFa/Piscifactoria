package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Pejerrey extends Pez{

    /**
     * Constructor de un pejerrey con género definido.
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
}
