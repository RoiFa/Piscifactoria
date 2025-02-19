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
import tanque.subtanque.TanqueCria;
import tanque.subtanque.TanqueHuevos;

public class PiscifactoriaAdapter implements JsonSerializer<Piscifactoria>,JsonDeserializer<Piscifactoria>{
    
    @Override
    public JsonElement serialize(Piscifactoria src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("nombre", new JsonPrimitive(src.getNombre()));
        jsonObject.add("tipo", new JsonPrimitive(src.getTipo()));
        jsonObject.add("capacidad", new JsonPrimitive(src.getComidaMax()));
        JsonObject comida = new JsonObject();
        comida.add("vegetal", new JsonPrimitive(src.getComidaVegetal()));
        comida.add("animal", new JsonPrimitive(src.getComidaAnimal()));
        jsonObject.add("comida", comida);
        jsonObject.add("tanques", context.serialize(src.getTanques()));
        JsonObject mejoras = new JsonObject();
        mejoras.add("cria", context.serialize(src.getTanquesCria()));
        mejoras.add("huevos", context.serialize(src.getTanquesHuevos()));
        jsonObject.add("mejoras", mejoras);
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
        Type tipoTanque = new TypeToken<ArrayList<Tanque>>(){}.getType();
        p.setTanques(context.deserialize(jsonObject.get("tanques"), tipoTanque));
        for(int i = 0;i<p.getTanques().size();i++){
            p.getTanques().get(i).setNomPiscifactoria(p.getNombre());
            p.getTanques().get(i).setNumTanque(i+1);
            p.getTanques().get(i).setMaxSize(p.getTanques().get(i).getPeces().size());
            p.getTanques().get(i).setTipo(p.getTipo());
        }
        Type tipoTanqueCria = new TypeToken<ArrayList<TanqueCria>>(){}.getType();
        JsonObject mejoras = jsonObject.getAsJsonObject("mejoras");
        p.setTanquesCria(context.deserialize(mejoras.get("cria"), tipoTanqueCria));
        Type tipoTanqueHuevos = new TypeToken<ArrayList<TanqueHuevos>>(){}.getType();
        p.setTanquesHuevos(context.deserialize(mejoras.get("huevos"), tipoTanqueHuevos));
        return p;
    }
}