package peces.doble;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un bagre de canal
 */
public class BagreDeCanal extends Pez{

    /**
     * Constructor de un bagre de canal con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public BagreDeCanal(boolean sexo) {
        super(AlmacenPropiedades.BAGRE_CANAL, sexo);
    }

    /**
     * Constructor de un bagre de canal con género aleatorio.
     */
    public BagreDeCanal() {
        super(AlmacenPropiedades.BAGRE_CANAL, RNG.RandomBoolean());
    }

    @Override
    protected int[] comer(int a, int v, boolean enCria) {
        if (a < 2) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        setAlimentado(true);
        return new int[]{2,0};
    }

    @Override
    public BagreDeCanal reprod() {
        return new BagreDeCanal();
    }

    @Override
    public BagreDeCanal reprod(boolean sexo) {
        return new BagreDeCanal(sexo);
    }
}
