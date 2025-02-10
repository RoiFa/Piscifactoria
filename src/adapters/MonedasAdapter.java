package adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import monedas.Monedas;

/**
 * Adapta las monedas para guardar solo su cantidad, es decir, como entero
 */
public class MonedasAdapter implements JsonSerializer<Monedas>, JsonDeserializer<Monedas> {
    
    @Override
    public JsonElement serialize(Monedas src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getCantidad());
    }

    @Override
    public Monedas deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        int cant = json.getAsInt();
        Monedas mon = new Monedas();
        mon.setCantidad(cant);
        return mon;
    }
}