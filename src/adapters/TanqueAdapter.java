package adapters;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import peces.Pez;
import tanque.Tanque;

public class TanqueAdapter implements JsonSerializer<Tanque>,JsonDeserializer<Tanque>{
    
    @Override
    public JsonElement serialize(Tanque src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("pez", new JsonPrimitive(src.getTipoPez()));
        jsonObject.add("num", new JsonPrimitive(src.ocupacion()));
        JsonObject datos = new JsonObject();
        datos.add("vivos", new JsonPrimitive(src.vivos()));
        datos.add("maduros", new JsonPrimitive(src.adultos()));
        datos.add("fertiles", new JsonPrimitive(src.fertiles()));
        jsonObject.add("datos", datos);
        jsonObject.add("peces", context.serialize(src.peces));
        return jsonObject;
    }

    @Override
    public Tanque deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("peces");
        Tanque t = new Tanque();
        Type tipo = new TypeToken<ArrayList<Pez>>(){}.getType();
        t.setPeces(context.deserialize(jsonArray, tipo));
        t.setMaxSize(t.getPeces().size());
        t.setTipoPez(jsonObject.get("pez").getAsString());
        return t;
    }
}