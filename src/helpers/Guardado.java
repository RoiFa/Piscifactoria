package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import main.Simulador;

/**
 * Clase helper encargada del proceso de guardado
 */
public class Guardado {

    /** El fichero para realizar el guardado */
    private static File save;
    /** El writer para escribir en el fichero de guardado */
    private static BufferedWriter bw;

    /**
     * Si existe el directorio saves, devuelve un array con las partidas.
     * Si no existe lo crea y devuelve null
     * @return el array con las partidas o null
     */
    public static String[] listarSaves(){
        save = new File("saves");
        if(!save.exists()){
            save.mkdir();
        }
        return save.list();
    }

    /**
     * Guarda la partida en un fichero
     */
    public static void save(){
        save = new File("saves/"+Simulador.instancia.getNombre()+".save");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(Simulador.instancia);
        try{
            save.createNewFile();
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(save),"UTF-8"));
            bw.write(json);
            bw.flush();
        } catch(IOException e){
            System.out.println("Error en el guardado");
        }
    }

    /**
     * Carga una partida de un fichero
     * @param nomArchivo el nombre del fichero
     */
    public static void load(String nomArchivo){
        save = new File("saves/"+nomArchivo);
        JsonReader reader = null;
        try{
            reader = new JsonReader(new BufferedReader(new InputStreamReader(new FileInputStream(save),"UTF-8")));
            Simulador.instancia = (Simulador) new Gson().fromJson(reader, Simulador.class);
        } catch(IOException e){
            System.out.println("Error en la lectura del guardado");
        } finally{
            try{
                reader.close();
            } catch(IOException f){
                f.printStackTrace();
            }
        }
    }

    public static void close(){
        if(bw!=null){
            try{
                bw.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}