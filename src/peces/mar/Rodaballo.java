package peces.mar;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Rodaballo extends Pez{

    /**
     * Constructor de un rodaballo con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Rodaballo(boolean sexo) {
        super(
            AlmacenPropiedades.RODABALLO.getNombre(),
            AlmacenPropiedades.RODABALLO.getCientifico(),
            sexo,
            AlmacenPropiedades.RODABALLO.getCoste(),
            AlmacenPropiedades.RODABALLO.getMonedas(),
            AlmacenPropiedades.RODABALLO.getHuevos(),
            AlmacenPropiedades.RODABALLO.getCiclo(),
            AlmacenPropiedades.RODABALLO.getMadurez(),
            AlmacenPropiedades.RODABALLO.getOptimo(),
            AlmacenPropiedades.RODABALLO.getPiscifactoria(),
            AlmacenPropiedades.RODABALLO.getTipo(),
            AlmacenPropiedades.RODABALLO.getPropiedades()
            );
    }

    /**
     * Constructor de un rodaballo con género aleatorio.
     */
    public Rodaballo() {
        super(
            AlmacenPropiedades.RODABALLO.getNombre(),
            AlmacenPropiedades.RODABALLO.getCientifico(),
            AlmacenPropiedades.RODABALLO.getCoste(),
            AlmacenPropiedades.RODABALLO.getMonedas(),
            AlmacenPropiedades.RODABALLO.getHuevos(),
            AlmacenPropiedades.RODABALLO.getCiclo(),
            AlmacenPropiedades.RODABALLO.getMadurez(),
            AlmacenPropiedades.RODABALLO.getOptimo(),
            AlmacenPropiedades.RODABALLO.getPiscifactoria(),
            AlmacenPropiedades.RODABALLO.getTipo(),
            AlmacenPropiedades.RODABALLO.getPropiedades()
            );
    }
}
