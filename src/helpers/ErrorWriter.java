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
 * Clase helper encargada de escribir líneas en el log de errores del simulador.
 */
public class ErrorWriter {
    /** La fecha y hora actuales. */
    private static String timeStamp;
    /** El writer encargado de escribir en el log. */
    private static BufferedWriter bw;
    /** El fichero de errores en el que escribir. */
    private static File errLog;

    /**
     * Inicializa el fichero, creándolo en caso de que no exista, y el writer.
     */
    public static void startErrorLog() {
        try {
            File errFolder = new File("err");
            if (!errFolder.exists()) {
                errFolder.mkdir();
            }
            errLog = new File("err/0_errors.log");
            if (!errLog.exists()) {
                try {
                    errLog.createNewFile();
                } catch (IOException e) {
                    System.out.println("Se ha producido un error al inicializar el documento de errores.");
                    e.printStackTrace();
                }
            }
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(errLog, true)));
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo de errores.");
            e.printStackTrace();
        }
    }

    /**
     * Escribe una nueva línea en el log de errores, con el tiempo en el que se escribió.
     * 
     * @param logLine   Línea a escribir en el código.
     */
    public static void writeInErrorLog(String ErrLine) {
        try {
            timeStamp = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(Calendar.getInstance().getTime());
            bw.append(timeStamp + " " + ErrLine + "\n");
            bw.flush();
        } catch (IOException e) {
            System.out.println("Fallo al documentar un error.");
        }
    }

    /**
     * Cierra el writer
     */
    public static void closeErrorLog() {
        try {
            bw.close();
        } catch (IOException e) {
            System.out.println(("Fallo al cerrar el escritor de errores."));
        }
    }
}
