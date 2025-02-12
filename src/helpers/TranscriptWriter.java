
package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import main.Simulador;

public class TranscriptWriter {
    
    /**Bufer del transcriptor */
    private static BufferedWriter bw;

    /**Documento guardado donde se escriben las transcripciones */
    private static File transcript;

    /**
     * Inicia el trancriptor y lo deja listo para su funcionamiento
     * @param companyName Nombre de la partida iniciada
     */
    public static void transcriptInit(String companyName) {
        try {
            File transcriptFolder = new File("transcripts");
            if (!transcriptFolder.exists()) {
                transcriptFolder.mkdir();
            }
            transcript = new File("transcripts/" + companyName + ".transcript");
            if (!transcript.exists()) {
                try {
                    transcript.createNewFile();
                } catch (IOException e) {
                    ErrorWriter.writeInErrorLog("Fallo al iniciar el documento de transcripcion");
                }
            }
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(transcript, true)));
        } catch (FileNotFoundException e) {
            ErrorWriter.writeInErrorLog("Fallo al iniciar el documento de transcripcion");
        }
    }

    /**
     * Escribe el texto dado en el documento de transcripciones
     * @param transcriptLine Linea a escribir
     */
    public static void writeInTranscript(String transcriptLine) {
        try {
            bw.append(transcriptLine + "\n");
            bw.flush();
        } catch (IOException e) {
            ErrorWriter.writeInErrorLog("Error al agregar linea al documento de transcripcion");
        }
    }

    /**
     * Pone todo el texto necesario al iniciar una partida en el documento de transcripciones
     * @param nombrePartida Nombre de la partida iniciada
     * @param nomPeces Nombre de todos los peces implementados
     * @param nomPisc Nombre de la primera piscifactoria
     */
    public static void transcriptStart(String nombrePartida ,String[] nomPeces ,String nomPisc) {
        try {
            bw.append("Inicio de la simulación "+nombrePartida+"\n"+
            "Dinero: "+Simulador.instancia.monedas.getCantidad() + "\n");
            bw.append("Peces:\nRio:\n");
            for(int i=0;i<nomPeces.length;i++){
                bw.append("-"+nomPeces[i] + "\n");
                if(i==4){
                    bw.append("Mar:\n");
                }else if(i==9){
                    bw.append("Doble:\n");
                }
            }
            bw.append("------------------------------------------------\nPiscifactoría inicial: "+nomPisc + "\n");
            bw.flush();
        } catch (IOException e) {
            ErrorWriter.writeInErrorLog("Error al agregar linea al documento de transcripcion");
        }
    }

    /**
     * Cierra el escritor de transcripciones
     */
    public static void closer(){
        try {
            bw.close();
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al cerrar el transciptor");
        }
    }
}
