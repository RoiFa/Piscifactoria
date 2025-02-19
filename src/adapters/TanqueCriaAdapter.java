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
import tanque.subtanque.TanqueCria;

public class TanqueCriaAdapter implements JsonSerializer<TanqueCria>,JsonDeserializer<TanqueCria>{
    
    @Override
    public JsonElement serialize(TanqueCria src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("pez", new JsonPrimitive(src.getTipoPez()));
        jsonObject.add("madurez", new JsonPrimitive(src.getPeces().get(0).getEdad()));
        jsonObject.add("ciclo", new JsonPrimitive(src.getCiclo()));
        return jsonObject;
    }

    @Override
    public TanqueCria deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String pezName = jsonObject.get("pez").getAsString();
        TanqueCria t = new TanqueCria();
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
        t.addFish(p.reprod(true)); //TODO setear ciclo
        t.addFish(p.reprod(false));
        t.getPeces().get(0).setEdad(jsonObject.get("madurez").getAsInt());
        t.getPeces().get(1).setEdad(jsonObject.get("madurez").getAsInt());
        t.setTipoPez(jsonObject.get("pez").getAsString());
        return t;
    }
}