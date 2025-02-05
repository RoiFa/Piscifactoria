package helpers;

import main.Simulador;

public class PremadeLogs {

    public static void loadGame(){
        LogWriter.startLog(Simulador.instancia.getNombre());
        LogWriter.writeInLog("Inicio de la simulación: "+Simulador.instancia.getNombre());
        LogWriter.writeInLog("Piscifactoría inicial: "+Simulador.instancia.getNombre());
        TranscriptWriter.transcriptInit(Simulador.instancia.getNombre());
    }

    public static void newGame(){
        LogWriter.startLog(Simulador.instancia.getNombre());
        LogWriter.writeInLog("Inicio de la simulación: "+Simulador.instancia.getNombre());
        LogWriter.writeInLog("Piscifactoría inicial: "+Simulador.instancia.getNombre());
        TranscriptWriter.transcriptInit(Simulador.instancia.getNombre());
        TranscriptWriter.transcriptStart(Simulador.instancia.getNombre(), Simulador.instancia.getImplementados(), Simulador.instancia.getPiscis().get(0).getNombre());
    }

    public static void startGame(){
        ErrorWriter.startErrorLog();
        LogWriter.startLog(Simulador.instancia.getNombre());
        LogWriter.writeInLog("Inicio de la simulación " + Simulador.instancia.getNombre());
        TranscriptWriter.transcriptInit(Simulador.instancia.getNombre());
    }

    public static void addedFood(int add, String tipoComida,int coste,String pisciName){
        TranscriptWriter.writeInTranscript(add + " de comida de tipo " + tipoComida + " por " + coste + " monedas. Se almacena ens " + pisciName + ".");
        LogWriter.writeInLog(add + " de comida de tipo " + tipoComida + " por " + coste + " monedas. Se almacena en " + pisciName + ".");
    }

    public static void pantryBuy(){
        TranscriptWriter.writeInTranscript("Comprado el almacén central.");
        LogWriter.writeInLog("Comprado el almacén central.");
    }

    public static void pisciBuy(String buyPisc,int coste){
        TranscriptWriter.writeInTranscript("Comprada piscifactoría de " + buyPisc + " por " + coste + " monedas.");
        LogWriter.writeInLog("Comprada piscifactoría de " + buyPisc + " por " + coste + " monedas.");
    }

    public static void tankBuy(int numtanques,String pisciName){
        TranscriptWriter.writeInTranscript("Comprado un tanque número " + numtanques + " de la piscifactoría " + pisciName);
        LogWriter.writeInLog("Comprado un tanque para la piscifactoría " + pisciName);  
    }

    public static void pisciUpdate(String pisciName,int maxfood,int coste){
        TranscriptWriter.writeInTranscript("Mejorada la piscifactoría " + pisciName + " aumentando su capacidad de comida hasta un total de " + maxfood + " por " + coste + " monedas.");
        LogWriter.writeInLog("Mejorada la piscifactoría " + pisciName + " aumentando su capacidad de comida hasta un total de " + maxfood+ " por " + coste + " monedas.");
    
    }

    public static void secretMoney(){
        TranscriptWriter.writeInTranscript("Añadidas 1000 monedas mediante la opción oculta. Monedas actuales: "+Simulador.instancia.monedas.getCantidad());
        LogWriter.writeInLog("Añadidas monedas mediante la opción oculta.");
    }

    public static void secretFish(String fish){
        TranscriptWriter.writeInTranscript("Añadidos peces mediante la opción oculta a la piscifactoría "+fish);
        LogWriter.writeInLog("Añadidos peces mediante la opción oculta a la piscifactoría " + fish);
    }

    public static void sellFish(int pecesVendidos,String nombre, int dineroVendido){
        TranscriptWriter.writeInTranscript("Vendidos "+pecesVendidos+" peces de la piscifactoría "+nombre+" de forma manual por "+dineroVendido+" monedas.");
        LogWriter.writeInLog("Vendidos "+pecesVendidos+" peces de la piscifactoría "+nombre+" de forma manual por "+dineroVendido+" monedas.");
    }

    public static void addedFish(String fishName,String sex,int numTanque,String nomPiscifactoria){
        LogWriter.writeInLog(fishName+" ("+sex+") comprado. Añadido al tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
    }

    public static void buyFish(String fishName,String sex,int added,int numTanque,String nomPiscifactoria){
        TranscriptWriter.writeInTranscript(fishName + " (" + sex + ") comprado por " + added + " monedas. Añadido al tanque " + numTanque + " de la piscifactoría " + nomPiscifactoria);
        LogWriter.writeInLog(fishName+" ("+ sex + ") comprado por " + added + " monedas. Añadido al tanque " + numTanque + " de la piscifactoría "+nomPiscifactoria);
    }

    public static void tankCleaning(String action,int numTanque,String nomPiscifactoria){
        TranscriptWriter.writeInTranscript(action+" el tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
        LogWriter.writeInLog(action+" el tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
    }
}