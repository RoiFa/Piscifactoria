package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Clase helper encargada de escribir líneas en el log principal del simulador.
 */
public class LogWriter {
    /** La fecha y hora actuales. */
    private static String timeStamp;
    /** El writer encargado de escribir en el log. */
    private static BufferedWriter bw;
    /** El fichero en el que escribir. */
    private static File log;

    /**
     * Inicializa el fichero, creándolo en caso de que no exista, y el writer.
     * 
     * @param companyName   El nombre de la compañía que nombrará también al fichero.
     */
    public static void startLog(String companyName) {
        try {
            log = new File("logs/" + companyName + ".log");
            if (!log.exists()) {
                try {
                    log.createNewFile();
                } catch (IOException e) {
                    writeInLog("Error al inicializar el log principal.");
                }
            }
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(log, true)));
        } catch (FileNotFoundException e) {
            //TODO añadir al log de errores.
        }
    }

    /**
     * Escribe una nueva línea en el log, con el tiempo en el que se escribió.
     * 
     * @param logLine   Línea a escribir en el código.
     */
    public static void writeInLog(String logLine) {
        try {
            timeStamp = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(Calendar.getInstance().getTime());
            bw.append(timeStamp + " " + logLine);
            bw.flush();
        } catch (IOException e) {
            //TODO añadir al log de errores.
        }
    }

    /**
     * Cierra el writer
     */
    public static void closeLog() {
        try {
            bw.close();
        } catch (IOException e) {
            //TODO añadir al log de errores.
        }
    }
}