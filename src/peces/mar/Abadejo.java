package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

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
    protected void comer() {
        // TODO Comer
    }
}
