
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

import main.Almacen;

/**
 * Adapta el almacén para ser guardado con el formato indicado
 */
public class AlmacenAdapter implements JsonSerializer<Almacen>,JsonDeserializer<Almacen>{
    
    @Override
    public JsonElement serialize(Almacen src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("disponible", new JsonPrimitive(src.getDisponible()));
        jsonObject.add("capacidad", new JsonPrimitive(src.getMaxCapacidad()));
        JsonObject comida = new JsonObject();
        comida.add("vegetal", new JsonPrimitive(src.getVegetal()));
        comida.add("animal", new JsonPrimitive(src.getCarne()));
        jsonObject.add("comida", comida);
        return jsonObject;
    }

    @Override
    public Almacen deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Almacen al = new Almacen();
        al.setDisponible(jsonObject.get("disponible").getAsBoolean());
        al.setMaxCapacidad(jsonObject.get("capacidad").getAsInt());
        JsonObject comida = jsonObject.getAsJsonObject("comida");
        al.setVegetal(comida.get("vegetal").getAsInt());
        al.setCarne(comida.get("animal").getAsInt());
        return al;
    }
}
