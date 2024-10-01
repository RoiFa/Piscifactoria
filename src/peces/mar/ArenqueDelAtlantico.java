package peces.mar;

import helpers.RNG;
import peces.Pez;
import propiedades.AlmacenPropiedades;

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
        super(
            AlmacenPropiedades.ARENQUE_ATLANTICO, RNG.RandomBoolean());
    }

    @Override
    protected int[] comer(int a, int v) {
        if (RNG.RandomBoolean()) {
            setAlimentado(true);
            return new int[]{0,0};
        }
        if (v <= 0) {
            setAlimentado(false);
            return new int[]{0,0};
        }
        return new int[]{0,1};
        }
}
