package peces.rio;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class SalmonChinook extends Pez{

    /**
     * Constructor de un salmón chinook con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public SalmonChinook(boolean sexo) {
        super(
            AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
            AlmacenPropiedades.SALMON_CHINOOK.getCientifico(),
            sexo,
            AlmacenPropiedades.SALMON_CHINOOK.getCoste(),
            AlmacenPropiedades.SALMON_CHINOOK.getMonedas(),
            AlmacenPropiedades.SALMON_CHINOOK.getHuevos(),
            AlmacenPropiedades.SALMON_CHINOOK.getCiclo(),
            AlmacenPropiedades.SALMON_CHINOOK.getMadurez(),
            AlmacenPropiedades.SALMON_CHINOOK.getOptimo(),
            AlmacenPropiedades.SALMON_CHINOOK.getPiscifactoria(),
            AlmacenPropiedades.SALMON_CHINOOK.getTipo(),
            AlmacenPropiedades.SALMON_CHINOOK.getPropiedades()
            );
    }

    /**
     * Constructor de un salmón chinook con género aleatorio.
     */
    public SalmonChinook() {
        super(
            AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
            AlmacenPropiedades.SALMON_CHINOOK.getCientifico(),
            AlmacenPropiedades.SALMON_CHINOOK.getCoste(),
            AlmacenPropiedades.SALMON_CHINOOK.getMonedas(),
            AlmacenPropiedades.SALMON_CHINOOK.getHuevos(),
            AlmacenPropiedades.SALMON_CHINOOK.getCiclo(),
            AlmacenPropiedades.SALMON_CHINOOK.getMadurez(),
            AlmacenPropiedades.SALMON_CHINOOK.getOptimo(),
            AlmacenPropiedades.SALMON_CHINOOK.getPiscifactoria(),
            AlmacenPropiedades.SALMON_CHINOOK.getTipo(),
            AlmacenPropiedades.SALMON_CHINOOK.getPropiedades()
            );
    }

    @Override
    public void grow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'grow'");
    }
    
}
