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

import granjas.Langostinos;
import granjas.TanqueLangostinos;

public class LangostinosAdapter implements JsonSerializer<Langostinos>,JsonDeserializer<Langostinos>{
    
    @Override
    public Langostinos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("tanques");
        Langostinos lang = new Langostinos();
        Type tipo = new TypeToken<ArrayList<TanqueLangostinos>>(){}.getType();
        lang.setDisponible(jsonObject.get("disponible").getAsBoolean());
        lang.setMuertos(jsonObject.get("muertos").getAsInt());
        lang.setTanques(context.deserialize(jsonArray, tipo));
        
        return lang;
    }

    @Override
    public JsonElement serialize(Langostinos arg0, Type arg1, JsonSerializationContext arg2) {
        JsonObject jsomObject = new JsonObject();
        jsomObject.add("disponible", new JsonPrimitive(Langostinos.isDisponible()));
        jsomObject.add("muertos", new JsonPrimitive(Langostinos.getMuertos()));
        jsomObject.add("tanques", arg2.serialize(Langostinos.getTanques()));

        return jsomObject;
    }
}