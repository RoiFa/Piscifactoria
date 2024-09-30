package peces.doble;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class BagreDeCanal extends Pez{

    /**
     * Constructor de un bagre de canal con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public BagreDeCanal(boolean sexo) {
        super(
            AlmacenPropiedades.BAGRE_CANAL.getNombre(),
            AlmacenPropiedades.BAGRE_CANAL.getCientifico(),
            sexo,
            AlmacenPropiedades.BAGRE_CANAL.getCoste(),
            AlmacenPropiedades.BAGRE_CANAL.getMonedas(),
            AlmacenPropiedades.BAGRE_CANAL.getHuevos(),
            AlmacenPropiedades.BAGRE_CANAL.getCiclo(),
            AlmacenPropiedades.BAGRE_CANAL.getMadurez(),
            AlmacenPropiedades.BAGRE_CANAL.getOptimo(),
            AlmacenPropiedades.BAGRE_CANAL.getPiscifactoria(),
            AlmacenPropiedades.BAGRE_CANAL.getTipo(),
            AlmacenPropiedades.BAGRE_CANAL.getPropiedades()
            );
    }

    /**
     * Constructor de un bagre de canal con género aleatorio.
     */
    public BagreDeCanal() {
        super(
            AlmacenPropiedades.BAGRE_CANAL.getNombre(),
            AlmacenPropiedades.BAGRE_CANAL.getCientifico(),
            AlmacenPropiedades.BAGRE_CANAL.getCoste(),
            AlmacenPropiedades.BAGRE_CANAL.getMonedas(),
            AlmacenPropiedades.BAGRE_CANAL.getHuevos(),
            AlmacenPropiedades.BAGRE_CANAL.getCiclo(),
            AlmacenPropiedades.BAGRE_CANAL.getMadurez(),
            AlmacenPropiedades.BAGRE_CANAL.getOptimo(),
            AlmacenPropiedades.BAGRE_CANAL.getPiscifactoria(),
            AlmacenPropiedades.BAGRE_CANAL.getTipo(),
            AlmacenPropiedades.BAGRE_CANAL.getPropiedades()
            );
    }
}
