package peces.mar;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Cobia extends Pez{

    /**
     * Constructor de una cobia con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Cobia(boolean sexo) {
        super(
            AlmacenPropiedades.COBIA.getNombre(),
            AlmacenPropiedades.COBIA.getCientifico(),
            sexo,
            AlmacenPropiedades.COBIA.getCoste(),
            AlmacenPropiedades.COBIA.getMonedas(),
            AlmacenPropiedades.COBIA.getHuevos(),
            AlmacenPropiedades.COBIA.getCiclo(),
            AlmacenPropiedades.COBIA.getMadurez(),
            AlmacenPropiedades.COBIA.getOptimo(),
            AlmacenPropiedades.COBIA.getPiscifactoria(),
            AlmacenPropiedades.COBIA.getTipo(),
            AlmacenPropiedades.COBIA.getPropiedades()
            );
    }

    /**
     * Constructor de una cobia con género aleatorio.
     */
    public Cobia() {
        super(
            AlmacenPropiedades.COBIA.getNombre(),
            AlmacenPropiedades.COBIA.getCientifico(),
            AlmacenPropiedades.COBIA.getCoste(),
            AlmacenPropiedades.COBIA.getMonedas(),
            AlmacenPropiedades.COBIA.getHuevos(),
            AlmacenPropiedades.COBIA.getCiclo(),
            AlmacenPropiedades.COBIA.getMadurez(),
            AlmacenPropiedades.COBIA.getOptimo(),
            AlmacenPropiedades.COBIA.getPiscifactoria(),
            AlmacenPropiedades.COBIA.getTipo(),
            AlmacenPropiedades.COBIA.getPropiedades()
            );
    }
}
