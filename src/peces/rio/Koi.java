package peces.rio;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Koi extends Pez{

    /**
     * Constructor de un koi con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Koi(boolean sexo) {
        super(
            AlmacenPropiedades.KOI.getNombre(),
            AlmacenPropiedades.KOI.getCientifico(),
            sexo,
            AlmacenPropiedades.KOI.getCoste(),
            AlmacenPropiedades.KOI.getMonedas(),
            AlmacenPropiedades.KOI.getHuevos(),
            AlmacenPropiedades.KOI.getCiclo(),
            AlmacenPropiedades.KOI.getMadurez(),
            AlmacenPropiedades.KOI.getOptimo(),
            AlmacenPropiedades.KOI.getPiscifactoria(),
            AlmacenPropiedades.KOI.getTipo(),
            AlmacenPropiedades.KOI.getPropiedades()
            );
    }

    /**
     * Constructor de un koi con género aleatorio.
     */
    public Koi() {
        super(
            AlmacenPropiedades.KOI.getNombre(),
            AlmacenPropiedades.KOI.getCientifico(),
            AlmacenPropiedades.KOI.getCoste(),
            AlmacenPropiedades.KOI.getMonedas(),
            AlmacenPropiedades.KOI.getHuevos(),
            AlmacenPropiedades.KOI.getCiclo(),
            AlmacenPropiedades.KOI.getMadurez(),
            AlmacenPropiedades.KOI.getOptimo(),
            AlmacenPropiedades.KOI.getPiscifactoria(),
            AlmacenPropiedades.KOI.getTipo(),
            AlmacenPropiedades.KOI.getPropiedades()
            );
    }

    @Override
    public void grow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'grow'");
    }
    
}
