package peces.rio;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una tilapia del nilo
 */
public class TilapiaDelNilo extends Pez{

    /**
     * Constructor de una tilapia del nilo con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public TilapiaDelNilo(boolean sexo) {
        super(AlmacenPropiedades.TILAPIA_NILO, sexo);
    }

    /**
     * Constructor de una tilapia del nilo con género aleatorio.
     */
    public TilapiaDelNilo() {
        super(AlmacenPropiedades.TILAPIA_NILO, RNG.RandomBoolean());
    }

    @Override
    protected int[] comer(int a, int v, boolean enCria) {
        if (enCria) {
            if (v < 2) {
                setAlimentado(false);
                return new int[]{0,0};
            }
            setAlimentado(true);
            return new int[]{0,2};
        } 
        if (RNG.RandomBoolean()) {
            setAlimentado(true);
            return new int[]{0,0};
        }
        if (v == 0) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        setAlimentado(true);
        return new int[]{0,1};
    }

    @Override
    public TilapiaDelNilo reprod() {
        return new TilapiaDelNilo();
    }

    @Override
    public TilapiaDelNilo reprod(boolean sexo) {
        return new TilapiaDelNilo(sexo);
    }
}
