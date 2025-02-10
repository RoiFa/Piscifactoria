package tanque.subtanque;

import java.util.ArrayList;

import helpers.ErrorWriter;
import helpers.PremadeLogs;
import helpers.Reader;
import main.Simulador;
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
import piscifactoria.Piscifactoria;
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

    /**
     * Comprueba si hay un macho fertil y alimentado en el tanque de cria
     */
    @Override
    public boolean hayMacho() {
        for(Pez pez : this.peces) {
            if(pez!=null && pez.isMale() && pez.isFertil() && pez.isVivo() && pez.isAlimentado()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addFish(boolean enReproduccion) {
        Pez[] especiesMar = {new Rodaballo(),new Besugo(),new ArenqueDelAtlantico(),new Abadejo(),new Cobia(), new Dorada(),new BagreDeCanal()};
        Pez[] especiesRio = {new Carpa(),new Koi(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new Dorada(),new BagreDeCanal()};
        if (enReproduccion) {
            try {
                if(tipo.equals("mar")){
                    for(int i=0;i<especiesMar.length;i++){
                        if(especiesMar[i].getNombre().equals(this.tipoPez) && peces.size()!=ocupacion()){
                            for(int j=0;j<especiesMar[i].getHuevos()*2;j++){
                                peces.set(findSpace(), creadorEspecies(especiesMar[(i+1)],true));
                                Simulador.instancia.orca.registrarNacimiento(this.tipoPez);
                            }
                        }
                    }
                }else{
                    for(int i=0;i<especiesRio.length;i++){
                        if(especiesRio[i].getNombre().equals(this.tipoPez) && peces.size() != ocupacion()){
                            for(int j=0;j<especiesRio[i].getHuevos()*2;j++){
                                peces.set(findSpace(), creadorEspecies(especiesRio[(i+1)],true));
                                Simulador.instancia.orca.registrarNacimiento(this.tipoPez);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                ErrorWriter.writeInErrorLog("Error en la reproducción de peces.");
            }
        } else {
            if (this.tipo.equals("mar")) {
                menuEspeciesMar();
                creadorEspecies(especiesMar[(Reader.readTheNumber(1, 7)-1)], false);
            } else {
                menuEspeciesRio();
                creadorEspecies(especiesRio[(Reader.readTheNumber(1, 7)-1)], false);
            }
        }
    }

    @Override
    public Pez creadorEspecies(Pez pez, boolean enReproduccion) {
        if (enReproduccion) {
            ArrayList<Piscifactoria> piscis = Simulador.instancia.getPiscis();
            ArrayList<Tanque> tanques = null;
            for (Piscifactoria pisci : piscis) {
                if (pisci.getNombre().equals(this.nomPiscifactoria)) {
                    tanques = pisci.getTanques();
                }
            }

            for (Tanque tanque : tanques) {
                if (tanque != null && tanque.ocupacion() > 0 && tanque.getTipoPez().equals(pez.getNombre())) {
                    ArrayList<Pez> newPeces = tanque.getPeces();
                    newPeces.add(pez);
                    tanque.setPeces(newPeces);
                    break;
                }
            }
        } else {
            if(Simulador.instancia.monedas.comprar(pez.getCoste()*2)){
                this.peces.set(0, pez.reprod());
                this.peces.set(1, pez.reprod(!predominan()));
                PremadeLogs.buyTwoFish(pez.getNombre(), pez.getCoste()*2, numTanque, nomPiscifactoria);
            }
        }
        return null;
    }

    @Override
    public int[] nextDay(int carne, int vegetal) {
        int[] cants;

        for (Pez pez : this.peces) {
            if(!pez.isFertil()&&(pez.getEdad()-pez.getMadurez())%pez.getCiclo()==0&&pez.isAdulto()){
                pez.setFertil(true);
            }
            if (!pez.isMale() && pez.isFertil() && hayMacho() && pez.isAlimentado()) {
                addFish(true);
            }

            cants = pez.grow(carne, vegetal, true);
            carne -= cants[0];
            vegetal -= cants[1];
            if (Simulador.instancia.almacen.getDisponible()&&(carne <= 0 || vegetal <= 0)) {
                Simulador.instancia.almacen.repartirComida();
            }
        }
        
        return new int[]{0,0,carne,vegetal};
    }

    @Override
    public void showStatus() {
        System.out.println("Tanque de cría Nº " + this.numTanque + ":");
        if (ocupacion() != 0) {
            System.out.println(
                "Tipo de pez: " + this.tipoPez + "\n" +
                "Días hasta la siguiente reproducción: " + (peces.get(0).getEdad()-peces.get(0).getMadurez()%peces.get(0).getCiclo())
            );
        } else {
            System.out.println("Este tanque de cría está vacío.");
        }
    }

    @Override
    public void emptyTank() {
        System.out.println("Estás seguro de querer vaciar este tanque de cría? (1.-Si/2.-No)");
        if (Reader.readTheNumber(1, 2) == 1) {
            System.out.println("Vaciando tanque de cría...");
            for (int i = 0; i < peces.size(); i++) {
                if (peces.get(i) != null) {
                    peces.set(i, null);
                }
            }
            this.tipoPez = null;
            PremadeLogs.tankCleaning("Vaciado", numTanque, nomPiscifactoria);
            System.out.println("Tanque de cría vaciado.");
        } else {
            System.out.println("Cancelado.");
        }
    }
}