package tanque.subtanque;

import helpers.Reader;
import peces.Pez;
import peces.doble.BagreDeCanal;
import peces.doble.Dorada;
import peces.mar.Abadejo;
import peces.mar.ArenqueDelAtlantico;
import peces.mar.Besugo;
import peces.mar.Cobia;
import peces.mar.Rodaballo;
import peces.rio.Carpa;
import peces.rio.Koi;
import peces.rio.Pejerrey;
import peces.rio.SalmonChinook;
import peces.rio.TilapiaDelNilo;
import tanque.Tanque;

public class TanqueCria extends Tanque{
    
    public TanqueCria(int numTanqueCria, String tipo, String nomPiscifactoria, String tipoPez) {
        super(numTanqueCria, tipo, nomPiscifactoria);
        this.maxSize = 2;
        this.tipoPez = tipoPez;
        
    }

    public void tankCriaMenu() {

        System.out.println(
            "Escoge una opción:\n" +
            "1.-\tEstado de los peces."
        );
        if (ocupacion() != 0) {
            System.out.println("2.-\tComprar peces.");
        } else {
            System.out.println("2.-\tVaciar tanque.");
        }
        
    }

    @Override
    public void addFish(boolean enReproduccion) {
        Pez[] especiesMar = {new Rodaballo(),new Besugo(),new ArenqueDelAtlantico(),new Abadejo(),new Cobia(), new Dorada(),new BagreDeCanal()};
        Pez[] especiesRio = {new Carpa(),new Koi(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new Dorada(),new BagreDeCanal()};
        if (this.tipo.equals("mar")) {
            menuEspeciesMar();
            this.peces[0] = creadorEspecies(especiesMar[(Reader.readTheNumber(1,7)-1)], false);
            //TODO terminar
        } else {
            menuEspeciesRio();
        }
    }

    @Override
    public Pez creadorEspecies(Pez pez, boolean enReproduccion) {
        return null;
        //TODO terminar
    }

}
