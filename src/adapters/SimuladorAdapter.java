
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

import estadisticas.Estadisticas;
import main.Almacen;
import main.Simulador;
import monedas.Monedas;
import piscifactoria.Piscifactoria;

/**
 * Adapta el simulador para poder ser guardado en el formato indicado
 */
public class SimuladorAdapter implements JsonSerializer<Simulador>,JsonDeserializer<Simulador>{

    @Override
    public JsonElement serialize(Simulador src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("implementados", context.serialize(src.getImplementados()));
        jsonObject.add("nombre", new JsonPrimitive(src.getNombre()));
        jsonObject.add("dia", new JsonPrimitive(src.getDia()));
        jsonObject.add("monedas", context.serialize(src.monedas));
        jsonObject.add("orca", new JsonPrimitive(src.orca.exportarDatos(src.getImplementados())));
        JsonObject edificios = new JsonObject();
        edificios.add("almacen", context.serialize(src.almacen));
        jsonObject.add("edificios", context.serialize(edificios));
        jsonObject.add("piscifactorias", context.serialize(src.getPiscis()));
        return jsonObject;
    }

    @Override
    public Simulador deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Simulador sim = new Simulador();
        sim.setNombre(jsonObject.get("nombre").getAsString());
        sim.setDia(jsonObject.get("dia").getAsInt());
        sim.monedas = context.deserialize(jsonObject.get("monedas"), Monedas.class);
        sim.orca = new Estadisticas(sim.getImplementados(), jsonObject.get("orca").getAsString());
        JsonObject edificios = jsonObject.getAsJsonObject("edificios");
        sim.almacen = context.deserialize(edificios.get("almacen"), Almacen.class);
        Type tipo = new TypeToken<ArrayList<Piscifactoria>>(){}.getType();
        sim.setPiscis(context.deserialize(jsonObject.get("piscifactorias"), tipo));
        return sim;
    }
}
