package helpers;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Reader {
    
static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

/**
     * Pide texto para poder devolverlo
     * @return Devuelve el texto dado
     */
    public static String readTheLine(){
        boolean check = false;
        String input = "";
        while (!check) {
            try {
                input = br.readLine();
                while (input==null || input.equals("")) {
                    System.out.println("Introduzca al menos un caracter");
                    input = br.readLine();
                }
                check = true;
            }catch(Exception e){
                System.out.println("Introduzca un caracter válido, por favor");
                ErrorWriter.writeInErrorLog("Error al pedirle una línea de texto al usuario.");
            }
        }
        return input;
    }

    /**
     * Pide un numero para poder devolverlo
     * @return Devuelve el numero dado
     */
    public static int readTheNumber(int min, int max){
        boolean check = false;
        int input = 0;
        while (!check) {
            try {
                input=Integer.parseInt(br.readLine());
                if(max>min){
                    while (input<min || input>max) {
                        System.out.println("Introduzca un entero entre "+min+" y "+max);
                        input = Integer.parseInt(br.readLine()); 
                    }
                }
                check = true;
            }catch(Exception e){
                System.out.println("Introduzca un número entero, por favor");
                ErrorWriter.writeInErrorLog("Error al pedirle un número entero al usuario.");
            } 
        }
        return input;
    }

    /**
     * Pide un numero para poder devolverlo
     * @return Devuelve el numero dado
     */
    public static float readTheNumberF(float min, float max){
        boolean check = false;
        float input = 0;
        while (!check) {
            try {
                input=Float.parseFloat(br.readLine());
                if(max>min){
                    while (input<min || input>max) {
                        System.out.println("Introduzca un decimal entre "+min+" y "+max);
                        input = Float.parseFloat(br.readLine()); 
                    }
                }
                check = true;
            }catch(Exception e){
                System.out.println("Introduzca un número decimal, por favor");
                ErrorWriter.writeInErrorLog("Error al pedirle un número decimal al usuario.");
            } 
        }
        return input;
    }

    public static void closer(){
        try {
            br.close();
        } catch (Exception e) {
            ErrorWriter.writeInErrorLog("Error al intentar cerrar el helper de Reader");
        }
    }
}