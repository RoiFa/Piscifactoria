package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

public class Abadejo extends Pez{

    /**
     * Constructor de un abadejo con género definido.
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public Abadejo(boolean sexo) {
        super(
            AlmacenPropiedades.ABADEJO.getNombre(),
            AlmacenPropiedades.ABADEJO.getCientifico(),
            sexo,
            AlmacenPropiedades.ABADEJO.getCoste(),
            AlmacenPropiedades.ABADEJO.getMonedas(),
            AlmacenPropiedades.ABADEJO.getHuevos(),
            AlmacenPropiedades.ABADEJO.getCiclo(),
            AlmacenPropiedades.ABADEJO.getMadurez(),
            AlmacenPropiedades.ABADEJO.getOptimo(),
            AlmacenPropiedades.ABADEJO.getPiscifactoria(),
            AlmacenPropiedades.ABADEJO.getTipo(),
            AlmacenPropiedades.ABADEJO.getPropiedades()
            );
    }

    /**
     * Constructor de un abadejo con género aleatorio.
     */
    public Abadejo() {
        super(
            AlmacenPropiedades.ABADEJO.getNombre(),
            AlmacenPropiedades.ABADEJO.getCientifico(),
            AlmacenPropiedades.ABADEJO.getCoste(),
            AlmacenPropiedades.ABADEJO.getMonedas(),
            AlmacenPropiedades.ABADEJO.getHuevos(),
            AlmacenPropiedades.ABADEJO.getCiclo(),
            AlmacenPropiedades.ABADEJO.getMadurez(),
            AlmacenPropiedades.ABADEJO.getOptimo(),
            AlmacenPropiedades.ABADEJO.getPiscifactoria(),
            AlmacenPropiedades.ABADEJO.getTipo(),
            AlmacenPropiedades.ABADEJO.getPropiedades()
            );
    }

    @Override
    protected int[] comer(int a, int v) {
        if (RNG.RandomInt(100)<=75) {
            setAlimentado(true);
            return new int[]{0,0};
        }
        if (a <= 0) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        setAlimentado(true);
        return new int[]{1,0};
    }
    
}
