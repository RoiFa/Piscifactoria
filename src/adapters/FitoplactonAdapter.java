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

import granjas.Fitoplacton;

/**
 * Adapta las monedas para guardar solo su cantidad, es decir, como entero
 */
public class FitoplactonAdapter implements JsonSerializer<Fitoplacton>, JsonDeserializer<Fitoplacton> {
    
    @Override
    public Fitoplacton deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jobj = json.getAsJsonObject();
        Fitoplacton fito = new Fitoplacton();
        fito.setDisponible(jobj.get("disponible").getAsBoolean());
        fito.setTanques(jobj.get("tanques").getAsInt());
        fito.setCiclo(jobj.get("ciclo").getAsInt());
        
        return fito;
    }

    @Override
    public JsonElement serialize(Fitoplacton src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonobj = new JsonObject();
        jsonobj.add("disponible", new JsonPrimitive(src.isDisponible()));
        jsonobj.add("tanques", new JsonPrimitive(src.getTanques()));
        jsonobj.add("ciclo", new JsonPrimitive(src.getCiclo()));

        return jsonobj;
    }
}