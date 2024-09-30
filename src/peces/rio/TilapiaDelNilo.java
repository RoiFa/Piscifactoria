package peces.rio;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class TilapiaDelNilo extends Pez{

    /**
     * Constructor de una tilapia del nilo con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public TilapiaDelNilo(boolean sexo) {
        super(
            AlmacenPropiedades.TILAPIA_NILO.getNombre(),
            AlmacenPropiedades.TILAPIA_NILO.getCientifico(),
            sexo,
            AlmacenPropiedades.TILAPIA_NILO.getCoste(),
            AlmacenPropiedades.TILAPIA_NILO.getMonedas(),
            AlmacenPropiedades.TILAPIA_NILO.getHuevos(),
            AlmacenPropiedades.TILAPIA_NILO.getCiclo(),
            AlmacenPropiedades.TILAPIA_NILO.getMadurez(),
            AlmacenPropiedades.TILAPIA_NILO.getOptimo(),
            AlmacenPropiedades.TILAPIA_NILO.getPiscifactoria(),
            AlmacenPropiedades.TILAPIA_NILO.getTipo(),
            AlmacenPropiedades.TILAPIA_NILO.getPropiedades()
            );
    }

    /**
     * Constructor de una tilapia del nilo con género aleatorio.
     */
    public TilapiaDelNilo() {
        super(
            AlmacenPropiedades.TILAPIA_NILO.getNombre(),
            AlmacenPropiedades.TILAPIA_NILO.getCientifico(),
            AlmacenPropiedades.TILAPIA_NILO.getCoste(),
            AlmacenPropiedades.TILAPIA_NILO.getMonedas(),
            AlmacenPropiedades.TILAPIA_NILO.getHuevos(),
            AlmacenPropiedades.TILAPIA_NILO.getCiclo(),
            AlmacenPropiedades.TILAPIA_NILO.getMadurez(),
            AlmacenPropiedades.TILAPIA_NILO.getOptimo(),
            AlmacenPropiedades.TILAPIA_NILO.getPiscifactoria(),
            AlmacenPropiedades.TILAPIA_NILO.getTipo(),
            AlmacenPropiedades.TILAPIA_NILO.getPropiedades()
            );
    }
    
}
