package peces.mar;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Besugo extends Pez{

    /**
     * Constructor de un besugo con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Besugo(boolean sexo) {
        super(
            AlmacenPropiedades.BESUGO.getNombre(),
            AlmacenPropiedades.BESUGO.getCientifico(),
            sexo,
            AlmacenPropiedades.BESUGO.getCoste(),
            AlmacenPropiedades.BESUGO.getMonedas(),
            AlmacenPropiedades.BESUGO.getHuevos(),
            AlmacenPropiedades.BESUGO.getCiclo(),
            AlmacenPropiedades.BESUGO.getMadurez(),
            AlmacenPropiedades.BESUGO.getOptimo(),
            AlmacenPropiedades.BESUGO.getPiscifactoria(),
            AlmacenPropiedades.BESUGO.getTipo(),
            AlmacenPropiedades.BESUGO.getPropiedades()
            );
    }

    /**
     * Constructor de un besugo con género aleatorio.
     */
    public Besugo() {
        super(
            AlmacenPropiedades.BESUGO.getNombre(),
            AlmacenPropiedades.BESUGO.getCientifico(),
            AlmacenPropiedades.BESUGO.getCoste(),
            AlmacenPropiedades.BESUGO.getMonedas(),
            AlmacenPropiedades.BESUGO.getHuevos(),
            AlmacenPropiedades.BESUGO.getCiclo(),
            AlmacenPropiedades.BESUGO.getMadurez(),
            AlmacenPropiedades.BESUGO.getOptimo(),
            AlmacenPropiedades.BESUGO.getPiscifactoria(),
            AlmacenPropiedades.BESUGO.getTipo(),
            AlmacenPropiedades.BESUGO.getPropiedades()
            );
    }

    @Override
    public void grow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'grow'");
    }
    
}
