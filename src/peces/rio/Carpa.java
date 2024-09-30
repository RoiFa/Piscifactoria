package peces.rio;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Carpa extends Pez{

    /**
     * Constructor de una carpa con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Carpa(boolean sexo) {
        super(
            AlmacenPropiedades.CARPA.getNombre(),
            AlmacenPropiedades.CARPA.getCientifico(),
            sexo,
            AlmacenPropiedades.CARPA.getCoste(),
            AlmacenPropiedades.CARPA.getMonedas(),
            AlmacenPropiedades.CARPA.getHuevos(),
            AlmacenPropiedades.CARPA.getCiclo(),
            AlmacenPropiedades.CARPA.getMadurez(),
            AlmacenPropiedades.CARPA.getOptimo(),
            AlmacenPropiedades.CARPA.getPiscifactoria(),
            AlmacenPropiedades.CARPA.getTipo(),
            AlmacenPropiedades.CARPA.getPropiedades()
            );
    }

    /**
     * Constructor de una carpa con género aleatorio.
     */
    public Carpa() {
        super(
            AlmacenPropiedades.CARPA.getNombre(),
            AlmacenPropiedades.CARPA.getCientifico(),
            AlmacenPropiedades.CARPA.getCoste(),
            AlmacenPropiedades.CARPA.getMonedas(),
            AlmacenPropiedades.CARPA.getHuevos(),
            AlmacenPropiedades.CARPA.getCiclo(),
            AlmacenPropiedades.CARPA.getMadurez(),
            AlmacenPropiedades.CARPA.getOptimo(),
            AlmacenPropiedades.CARPA.getPiscifactoria(),
            AlmacenPropiedades.CARPA.getTipo(),
            AlmacenPropiedades.CARPA.getPropiedades()
            );
    }
}
