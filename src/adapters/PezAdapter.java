package adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import peces.Pez;
import peces.doble.BagreDeCanal;
import peces.doble.Dorada;
import peces.mar.Abadejo;
import peces.mar.ArenqueDelAtlantico;
import peces.mar.Besugo;
import peces.mar.Cobia;
import peces.mar.Rodaballo;
import peces.rio.Carpa;
import peces.rio.Koi;
import peces.rio.Pejerrey;
import peces.rio.SalmonChinook;
import peces.rio.TilapiaDelNilo;

/**
 * Adapta los peces para poder ser guardados con el formato indicado
 */
public class PezAdapter implements JsonSerializer<Pez>,JsonDeserializer<Pez>{
    
    @Override
    public JsonElement serialize(Pez src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("edad", new JsonPrimitive(src.getEdad()));
        jsonObject.add("sexo", new JsonPrimitive(src.getSexo()));
        jsonObject.add("vivo", new JsonPrimitive(src.isVivo()));
        jsonObject.add("maduro", new JsonPrimitive(src.isAdulto()));
        jsonObject.add("fertil", new JsonPrimitive(src.isFertil()));
        jsonObject.add("ciclo", new JsonPrimitive(src.getCiclo()));
        JsonObject extras = new JsonObject();
        extras.add("alimentado", new JsonPrimitive(src.isAlimentado()));
        jsonObject.add("extras", extras);
        return jsonObject;
    }

    @Override
    public Pez deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if(json!=null){
            JsonObject jsonObject = json.getAsJsonObject();
            JsonObject extras = jsonObject.getAsJsonObject("extras");
            Pez p = null;
            switch (extras.get("nombre").getAsString()) {
                case "Carpa":
                    p = new Carpa();
                    break;
                case "Koi":
                    p = new Koi();
                    break;
                case "Pejerrey ":
                    p = new Pejerrey();
                    break;
                case "Salmón chinook":
                    p = new SalmonChinook();
                    break;
                case "Tilapia del Nilo":
                    p = new TilapiaDelNilo();
                    break;
                case "Abadejo":
                    p = new Abadejo();
                    break;
                case "Arenque del Atlántico":
                    p = new ArenqueDelAtlantico();
                    break;
                case "Besugo":
                    p = new Besugo();
                    break;
                case "Cobia":
                    p = new Cobia();
                    break;
                case "Rodaballo":
                    p = new Rodaballo();
                    break;
                case "Bagre de canal":
                    p = new BagreDeCanal();
                    break;
                case "Dorada":
                    p = new Dorada();
                    break;
                default:
                    break;
            }
            p.setEdad(jsonObject.get("edad").getAsInt());
            p.setAlimentado(extras.get("alimentado").getAsBoolean());
            p.setSexo(jsonObject.get("sexo").getAsBoolean());
            p.setVivo(jsonObject.get("vivo").getAsBoolean());
            p.setFertil(jsonObject.get("fertil").getAsBoolean());
            return p;
        } else{
            return null;
        }
    }
}