package helpers;

import main.Simulador;

/**
 * Clase helper encargada de la geenración de lineas en LogWriter y TranscriptWriter
 */
public class PremadeLogs {

    /**
     * Genera lineas en Log y Transcripciones cuando se carga una partida
     */
    public static void loadGame(){
        LogWriter.startLog(Simulador.instancia.getNombre());
        LogWriter.writeInLog("Inicio de la simulación: "+Simulador.instancia.getNombre());
        LogWriter.writeInLog("Piscifactoría inicial: "+Simulador.instancia.getNombre());
        TranscriptWriter.transcriptInit(Simulador.instancia.getNombre());
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se inicia una partida nueva
     */
    public static void newGame(){
        LogWriter.startLog(Simulador.instancia.getNombre());
        LogWriter.writeInLog("Inicio de la simulación: "+Simulador.instancia.getNombre());
        LogWriter.writeInLog("Piscifactoría inicial: "+Simulador.instancia.getNombre());
        TranscriptWriter.transcriptInit(Simulador.instancia.getNombre());
        TranscriptWriter.transcriptStart(Simulador.instancia.getNombre(), Simulador.instancia.getImplementados(), Simulador.instancia.getPiscis().get(0).getNombre());
    }
    /**
     * Genera lineas en Log y Transcripciones cuando se inicia una partida
     */
    public static void startGame(){
        ErrorWriter.startErrorLog();
        LogWriter.startLog(Simulador.instancia.getNombre());
        LogWriter.writeInLog("Inicio de la simulación " + Simulador.instancia.getNombre());
        TranscriptWriter.transcriptInit(Simulador.instancia.getNombre());
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se añade comida
     */
    public static void addedFood(int add, String tipoComida,int coste,String pisciName){
        TranscriptWriter.writeInTranscript(add + " de comida de tipo " + tipoComida + " por " + coste + " monedas. Se almacena ens " + pisciName + ".");
        LogWriter.writeInLog(add + " de comida de tipo " + tipoComida + " por " + coste + " monedas. Se almacena en " + pisciName + ".");
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se compra el almacen
     */
    public static void pantryBuy(){
        TranscriptWriter.writeInTranscript("Comprado el almacén central.");
        LogWriter.writeInLog("Comprado el almacén central.");
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se compra una piscifactoría
     */
    public static void pisciBuy(String buyPisc,int coste){
        TranscriptWriter.writeInTranscript("Comprada piscifactoría de " + buyPisc + " por " + coste + " monedas.");
        LogWriter.writeInLog("Comprada piscifactoría de " + buyPisc + " por " + coste + " monedas.");
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se compra un tanque de peces
     */
    public static void tankBuy(int numtanques,String pisciName){
        TranscriptWriter.writeInTranscript("Comprado un tanque número " + numtanques + " de la piscifactoría " + pisciName);
        LogWriter.writeInLog("Comprado un tanque para la piscifactoría " + pisciName);  
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se mejora una piscifactoria
     */
    public static void pisciUpdate(String pisciName,int maxfood,int coste){
        TranscriptWriter.writeInTranscript("Mejorada la piscifactoría " + pisciName + " aumentando su capacidad de comida hasta un total de " + maxfood + " por " + coste + " monedas.");
        LogWriter.writeInLog("Mejorada la piscifactoría " + pisciName + " aumentando su capacidad de comida hasta un total de " + maxfood+ " por " + coste + " monedas.");
    
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se usa la opcion secreta de dinero
     */
    public static void secretMoney(int money){
        TranscriptWriter.writeInTranscript("Añadidas 1000 monedas mediante la opción oculta. Monedas actuales: "+money);
        LogWriter.writeInLog("Añadidas monedas mediante la opción oculta.");
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se usa la opcion secreta de generacion de peces
     */
    public static void secretFish(String fish){
        TranscriptWriter.writeInTranscript("Añadidos peces mediante la opción oculta a la piscifactoría "+fish);
        LogWriter.writeInLog("Añadidos peces mediante la opción oculta a la piscifactoría " + fish);
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se venden peces automaticamente
     */
    public static void sellFish(int pecesVendidos,String nombre, int dineroVendido){
        TranscriptWriter.writeInTranscript("Vendidos "+pecesVendidos+" peces de la piscifactoría "+nombre+" de forma manual por "+dineroVendido+" monedas.");
        LogWriter.writeInLog("Vendidos "+pecesVendidos+" peces de la piscifactoría "+nombre+" de forma manual por "+dineroVendido+" monedas.");
    }

    /**
     * Genera lineas en Log cuando se venden peces manualmente de una piscifactoria
     */
    public static void manualSellFish(int ventas,String pisci,int ventas2){
        TranscriptWriter.writeInTranscript("Vendidos "+ventas+" peces de la piscifactoría "+pisci+" de forma manual por "+ventas2+" monedas.");

    }

    /**
     * Genera lineas en Log y Transcripciones cuando se añade un pez
     */
    public static void addedFish(String fishName,String sex,int numTanque,String nomPiscifactoria){
        LogWriter.writeInLog(fishName+" ("+sex+") comprado. Añadido al tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se compra un pez
     */
    public static void buyFish(String fishName,String sex,int added,int numTanque,String nomPiscifactoria){
        TranscriptWriter.writeInTranscript(fishName + " (" + sex + ") comprado por " + added + " monedas. Añadido al tanque " + numTanque + " de la piscifactoría " + nomPiscifactoria);
        LogWriter.writeInLog(fishName+" ("+ sex + ") comprado por " + added + " monedas. Añadido al tanque " + numTanque + " de la piscifactoría "+nomPiscifactoria);
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se compra un pez
     */
    public static void buyTwoFish(String fishName,int added,int numTanque,String nomPiscifactoria){
        TranscriptWriter.writeInTranscript("Dos " +fishName + " (M y F) comprados por " + added + " monedas. Añadido al tanque de cría " + numTanque + " de la piscifactoría " + nomPiscifactoria);
        LogWriter.writeInLog("Dos " +fishName+" (M y F) comprados por " + added + " monedas. Añadido al tanque de cría " + numTanque + " de la piscifactoría "+nomPiscifactoria);
    }

    /**
     * Genera lineas en Log y Transcripciones cuando se limpia o vacia un tanque
     */
    public static void tankCleaning(String action,int numTanque,String nomPiscifactoria){
        TranscriptWriter.writeInTranscript(action+" el tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
        LogWriter.writeInLog(action+" el tanque "+numTanque+" de la piscifactoría "+nomPiscifactoria);
    }

    /**
     * Genera lineas en Trancripciones cuando se pasa de dia
     */
    public static void nextDay(int dia,int totalRio,int totalMar,int dineroVendido,int monedas){
        TranscriptWriter.writeInTranscript("Fin del día "+(dia-1)+".\nPeces actuales: "+totalRio+" de río, "+totalMar+" de mar.\n"+dineroVendido+" monedas ganadas por un total de "+monedas+".\n------------------------------\n>>>Inicio del día "+dia+".");
    }

    public static void buyFarm(String type){
        TranscriptWriter.writeInTranscript("Comprada la granja de "+type);
        LogWriter.writeInLog("Comprada la granja de "+type);
    }

    public static void upgradeFarm(String type,int total){
        TranscriptWriter.writeInTranscript("Mejorada la granja de "+type+" añadiendo un tanque por un total de "+total+" tanques");
        LogWriter.writeInLog("Mejorada la granja de "+type+" añadiendo un tanque por un total de "+total+" tanques");
    }
}