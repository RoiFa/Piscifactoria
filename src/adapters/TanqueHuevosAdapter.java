package adapters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

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
import tanque.subtanque.TanqueHuevos;

public class TanqueHuevosAdapter implements JsonSerializer<TanqueHuevos>,JsonDeserializer<TanqueHuevos>{
    
    @Override
    public JsonElement serialize(TanqueHuevos src, Type typeOfSrc, JsonSerializationContext context) { 
        JsonObject jsonObject = new JsonObject();
        ArrayList<Pez> peces = src.getPeces();
        String[] arrayPeces = new String[0];
        for (Pez pez : peces) {
            arrayPeces = Arrays.copyOf(arrayPeces, arrayPeces.length+1);
            arrayPeces[arrayPeces.length-1] = pez.getNombre();
        }
        jsonObject.add("peces", context.serialize(arrayPeces));
        return jsonObject;
    }

    @Override
    public TanqueHuevos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        TanqueHuevos th = new TanqueHuevos();
        Type tipo = new TypeToken<String[]>(){}.getType();
        String[] pecesName = context.deserialize(jsonObject.get("peces"), tipo);
        for (String pezName : pecesName) {
            Pez p = null;
            switch (pezName) {
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
            th.addFish(p);
        }
        
        return th;
    }
}