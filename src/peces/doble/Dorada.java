package peces.doble;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Dorada extends Pez{

    /**
     * Constructor de una dorada con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Dorada(boolean sexo) {
        super(
            AlmacenPropiedades.DORADA.getNombre(),
            AlmacenPropiedades.DORADA.getCientifico(),
            sexo,
            AlmacenPropiedades.DORADA.getCoste(),
            AlmacenPropiedades.DORADA.getMonedas(),
            AlmacenPropiedades.DORADA.getHuevos(),
            AlmacenPropiedades.DORADA.getCiclo(),
            AlmacenPropiedades.DORADA.getMadurez(),
            AlmacenPropiedades.DORADA.getOptimo(),
            AlmacenPropiedades.DORADA.getPiscifactoria(),
            AlmacenPropiedades.DORADA.getTipo(),
            AlmacenPropiedades.DORADA.getPropiedades()
            );
    }

    /**
     * Constructor de una dorada con género aleatorio.
     */
    public Dorada() {
        super(
            AlmacenPropiedades.DORADA.getNombre(),
            AlmacenPropiedades.DORADA.getCientifico(),
            AlmacenPropiedades.DORADA.getCoste(),
            AlmacenPropiedades.DORADA.getMonedas(),
            AlmacenPropiedades.DORADA.getHuevos(),
            AlmacenPropiedades.DORADA.getCiclo(),
            AlmacenPropiedades.DORADA.getMadurez(),
            AlmacenPropiedades.DORADA.getOptimo(),
            AlmacenPropiedades.DORADA.getPiscifactoria(),
            AlmacenPropiedades.DORADA.getTipo(),
            AlmacenPropiedades.DORADA.getPropiedades()
            );
    }

    @Override
    public void grow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'grow'");
    }
    
}
