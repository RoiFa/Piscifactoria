package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import monedas.Monedas;

public class TranscripWriter {
    
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
            bw.append(transcriptLine);
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
            "Dinero: "+Monedas.getCantidad());
            bw.append("Peces:\nRio:");
            for(int i=0;i<nomPeces.length;i++){
                bw.append("-"+nomPeces[i]);
                if(i==4){
                    bw.append("Mar:");
                }else if(i==9){
                    bw.append("Doble:");
                }
            }
            bw.append("------------------------------------------------\nPiscifactoría inicial: "+nomPisc);
            bw.flush();
        } catch (IOException e) {
            ErrorWriter.writeInErrorLog("Error al agregar linea al documento de transcripcion");
        }
    }


    /**
     * Cierra el escritor de transcripciones
     */
    public void closer(){
        try {
            bw.close();
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al cerrar el transciptor");
        }
    }
}
