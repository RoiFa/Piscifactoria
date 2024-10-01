package peces.doble;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class BagreDeCanal extends Pez{

    /**
     * Constructor de un bagre de canal con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public BagreDeCanal(boolean sexo) {
        super(AlmacenPropiedades.BAGRE_CANAL, sexo);
    }

    /**
     * Constructor de un bagre de canal con género aleatorio.
     */
    public BagreDeCanal() {
        super(AlmacenPropiedades.BAGRE_CANAL, RNG.RandomBoolean());
    }

    @Override
    protected void comer() {
        // TODO Comer
    }
}
