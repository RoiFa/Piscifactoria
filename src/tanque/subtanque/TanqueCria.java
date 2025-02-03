package tanque.subtanque;

import helpers.ErrorWriter;
import helpers.LogWriter;
import helpers.Reader;
import helpers.TranscriptWriter;
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
        //TODO cambiar para que lleve las crias a otro tanque.
        Pez[] especiesMar = {new Rodaballo(),new Besugo(),new ArenqueDelAtlantico(),new Abadejo(),new Cobia(), new Dorada(),new BagreDeCanal()};
        Pez[] especiesRio = {new Carpa(),new Koi(),new SalmonChinook(),new TilapiaDelNilo(), new Pejerrey(), new Dorada(),new BagreDeCanal()};
        if (enReproduccion) {
            try {
                if(tipo.equals("mar")){
                    for(int i=0;i<especiesMar.length;i++){
                        if(especiesMar[i].getNombre().equals(this.tipoPez) && peces.length!=ocupacion()){
                            for(int j=0;j<especiesMar[i].getHuevos()*2;j++){
                                peces[findSpace()] = creadorEspecies(especiesMar[(i+1)],true);
                                Simulador.instancia.orca.registrarNacimiento(this.tipoPez);
                            }
                        }
                    }
                }else{
                    for(int i=0;i<especiesRio.length;i++){
                        if(especiesRio[i].getNombre().equals(this.tipoPez) && peces.length != ocupacion()){
                            for(int j=0;j<especiesRio[i].getHuevos()*2;j++){
                                peces[findSpace()] = creadorEspecies(especiesRio[(i+1)],true);
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
            
        } else {
            if(Simulador.instancia.monedas.comprar(pez.getCoste()*2)){
                this.peces[0] = pez.reprod();
                this.peces[1] = pez.reprod(!predominan());
                TranscriptWriter.writeInTranscript("Dos " + this.peces[0].getNombre() + " (M y F) comprados por " + this.peces[0].getCoste()*2 + " monedas. Añadido al tanque de cría " + this.numTanque + " de la piscifactoría " + this.nomPiscifactoria);
                LogWriter.writeInLog(this.peces[0].getNombre()+" (M y F) comprado por " + this.peces[0].getCoste() + " monedas. Añadido al tanque de cría " + this.numTanque + " de la piscifactoría "+this.nomPiscifactoria);
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
                "Días hasta la siguiente reproducción: " + (peces[0].getEdad()-peces[0].getMadurez()%peces[0].getCiclo())
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
            for (int i = 0; i < peces.length; i++) {
                if (peces[i] != null) {
                    peces[i] = null;
                }
            }
            this.tipoPez = null;
            //TODO log y transcript
            System.out.println("Tanque de cría vaciado.");
        } else {
            System.out.println("Cancelado.");
        }
    }
}