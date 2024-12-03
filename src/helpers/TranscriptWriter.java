package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import monedas.Monedas;

public class TranscriptWriter {
    
    private static BufferedWriter bw;

    private static File transcript;

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

    public static void writeInTranscript(String transcriptLine) {
        try {
            bw.append(transcriptLine);
            bw.flush();
        } catch (IOException e) {
            ErrorWriter.writeInErrorLog("Error al agregar linea al documento de transcripcion");
        }
    }

    public static void transcriptStart(String nombrePartida ,String[] nomPeces ,String nomPisc) {
        try {
            bw.append("Inicio de la simulación "+nombrePartida+"\n"+
            "Dinero: "+Monedas.getCantidad());
            bw.append("Peces:\nRio:");
            for(int i=0;i<nomPeces.length;i++){
                bw.append("-"+nomPeces[i] );
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

}
