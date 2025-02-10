package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un arenque del atlántico
 */
public class ArenqueDelAtlantico extends Pez{

    /**
     * Constructor de un arenque del atlántico con género definido.
     * 
     * @param sexo  El sexo del pez (True = Macho, False = Hembra)
     */
    public ArenqueDelAtlantico(boolean sexo) {
        super(AlmacenPropiedades.ARENQUE_ATLANTICO, sexo);
    }

    /**
     * Constructor de un arenque del atlántico con género aleatorio.
     */
    public ArenqueDelAtlantico() {
        super(AlmacenPropiedades.ARENQUE_ATLANTICO, RNG.RandomBoolean());
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
        if (v <= 0) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        setAlimentado(true);
        return new int[]{0,1};
    }

    @Override
    public ArenqueDelAtlantico reprod() {
        return new ArenqueDelAtlantico();
    }

    @Override
    public ArenqueDelAtlantico reprod(boolean sexo) {
        return new ArenqueDelAtlantico(sexo);
    }
}
