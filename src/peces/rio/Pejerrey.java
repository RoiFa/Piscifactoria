package peces.rio;

import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Pejerrey extends Pez{

    /**
     * Constructor de un pejerrey con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Pejerrey(boolean sexo) {
        super(
            AlmacenPropiedades.PEJERREY.getNombre(),
            AlmacenPropiedades.PEJERREY.getCientifico(),
            sexo,
            AlmacenPropiedades.PEJERREY.getCoste(),
            AlmacenPropiedades.PEJERREY.getMonedas(),
            AlmacenPropiedades.PEJERREY.getHuevos(),
            AlmacenPropiedades.PEJERREY.getCiclo(),
            AlmacenPropiedades.PEJERREY.getMadurez(),
            AlmacenPropiedades.PEJERREY.getOptimo(),
            AlmacenPropiedades.PEJERREY.getPiscifactoria(),
            AlmacenPropiedades.PEJERREY.getTipo(),
            AlmacenPropiedades.PEJERREY.getPropiedades()
            );
    }

    /**
     * Constructor de una pejerrey con género aleatorio.
     */
    public Pejerrey() {
        super(
            AlmacenPropiedades.PEJERREY.getNombre(),
            AlmacenPropiedades.PEJERREY.getCientifico(),
            AlmacenPropiedades.PEJERREY.getCoste(),
            AlmacenPropiedades.PEJERREY.getMonedas(),
            AlmacenPropiedades.PEJERREY.getHuevos(),
            AlmacenPropiedades.PEJERREY.getCiclo(),
            AlmacenPropiedades.PEJERREY.getMadurez(),
            AlmacenPropiedades.PEJERREY.getOptimo(),
            AlmacenPropiedades.PEJERREY.getPiscifactoria(),
            AlmacenPropiedades.PEJERREY.getTipo(),
            AlmacenPropiedades.PEJERREY.getPropiedades()
            );
    }

    @Override
    public void grow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'grow'");
    }
    
}
