package adapters;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class PiscifactoriaAdapter implements JsonSerializer<Piscifactoria>,JsonDeserializer<Piscifactoria>{
    
    @Override
    public JsonElement serialize(Piscifactoria src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("nombre", new JsonPrimitive(src.getNombre()));
        jsonObject.add("tipo", new JsonPrimitive(src.getTipo()));
        jsonObject.add("capacidad", new JsonPrimitive(src.getComidaMax()));
        JsonObject comida = new JsonObject();
        comida.add("vegetal", new JsonPrimitive(src.getComidaVegetal()));
        comida.add("animal", new JsonPrimitive(src.getComidaVegetal()));
        jsonObject.add("comida", comida);
        jsonObject.add("tanques", context.serialize(src.tanques));
        return jsonObject;
    }

    @Override
    public Piscifactoria deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Piscifactoria p = new Piscifactoria();
        p.setNombre(jsonObject.get("nombre").getAsString());
        p.setTipo(jsonObject.get("tipo").getAsString());
        p.setComidaMax(jsonObject.get("capacidad").getAsInt());
        JsonObject comida = jsonObject.getAsJsonObject("comida");
        p.setComidaVegetal(comida.get("vegetal").getAsInt());
        p.setComidaAnimal(comida.get("animal").getAsInt());
        Type tipo = new TypeToken<ArrayList<Tanque>>(){}.getType();
        p.tanques = context.deserialize(jsonObject.get("tanques"), tipo);
        for(int i = 0;i<p.tanques.size();i++){
            p.tanques.get(i).setNomPiscifactoria(p.getNombre());
            p.tanques.get(i).setNumTanque(i+1);
            p.tanques.get(i).setMaxSize(p.tanques.get(i).getPeces().length);
            p.tanques.get(i).setTipo(p.getTipo());
        }
        return p;
    }
}