package helpers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase helper para la lectura de datos pedidos al usuario.
 */
public class Reader {
    
    /** Búfer para la lectura */
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Pide texto para poder devolverlo
     * @return Devuelve el texto dado
     * @throws IOException
     */
    public static String readTheLine(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        }catch(IOException e){
            ErrorWriter.writeInErrorLog("Error al pedirle una línea de texto al usuario.");
            return "";
        }
    }

    /**
     * Pide un numero para poder devolverlo
     * @return Devuelve el numero dado
     * @throws IOException
     */
    public static int readTheNumber(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input=0;
        try {
            input = Integer.parseInt(br.readLine());
            return input;
        }catch(IOException e){
            ErrorWriter.writeInErrorLog("Error al pedirle un número entero al usuario.");
            return -1;
        }
    }

    /**
     * Pide un numero para poder devolverlo
     * @return Devuelve el numero dado
     * @throws IOException
     */
    public static float readTheNumberF(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        float input=0;
        try {
            input = Float.parseFloat(br.readLine());
            return input;
        }catch(IOException e){
            ErrorWriter.writeInErrorLog("Error al pedirle un número decimal al usuario.");
            return 0;
        }
    }

    /**
     * Cierra el búfer
     */
    public static void closer(){
        try {
            br.close();
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al intentar cerrar el helper de Reader");
        }
    }
}