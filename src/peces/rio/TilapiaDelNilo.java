package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class TilapiaDelNilo extends Pez{

    /**
     * Constructor de una tilapia del nilo con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public TilapiaDelNilo(boolean sexo) {
        super(AlmacenPropiedades.TILAPIA_NILO, sexo);
    }

    /**
     * Constructor de una tilapia del nilo con género aleatorio.
     */
    public TilapiaDelNilo() {
        super(AlmacenPropiedades.TILAPIA_NILO, RNG.RandomBoolean());
    }
    
}
