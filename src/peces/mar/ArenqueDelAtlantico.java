package peces.mar;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class ArenqueDelAtlantico extends Pez{

    /**
     * Constructor de un arenque del atlántico con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public ArenqueDelAtlantico(boolean sexo) {
        super(
            AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getCientifico(),
            sexo,
            AlmacenPropiedades.ARENQUE_ATLANTICO.getCoste(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getMonedas(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getHuevos(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getCiclo(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getOptimo(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getPiscifactoria(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getTipo(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getPropiedades()
            );
    }

    /**
     * Constructor de un arenque del atlántico con género aleatorio.
     */
    public ArenqueDelAtlantico() {
        super(
            AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getCientifico(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getCoste(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getMonedas(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getHuevos(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getCiclo(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getMadurez(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getOptimo(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getPiscifactoria(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getTipo(),
            AlmacenPropiedades.ARENQUE_ATLANTICO.getPropiedades()
            );
    }
}
