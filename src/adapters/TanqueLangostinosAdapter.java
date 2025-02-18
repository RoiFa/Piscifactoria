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

import granjas.TanqueLangostinos;

public class TanqueLangostinosAdapter implements JsonSerializer<TanqueLangostinos>,JsonDeserializer<TanqueLangostinos>{
    
    @Override
    public TanqueLangostinos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        TanqueLangostinos t = new TanqueLangostinos();
        t.setComida(jsonObject.get("comida").getAsInt());
        t.setDescanso(jsonObject.get("descanso").getAsInt());
        return t;
    }

    @Override
    public JsonElement serialize(TanqueLangostinos src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject langs = new JsonObject();
        langs.add("comida", new JsonPrimitive(src.getComida()));
        langs.add("descanso", new JsonPrimitive(src.getDescanso()));
        return langs;
    }
}