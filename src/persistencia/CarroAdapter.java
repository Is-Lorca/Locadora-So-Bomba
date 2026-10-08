package persistencia;

import carros.Carro;
import carros.Popular;
import carros.Suv;
import carros.Sedan;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CarroAdapter
        implements JsonSerializer<Carro>,
                   JsonDeserializer<Carro> {

    @Override
    public JsonElement serialize(
            Carro carro,
            Type tipo,
            JsonSerializationContext context) {

        return context.serialize(carro, carro.getClass());
    }

    @Override
    public Carro deserialize(JsonElement json, Type tipo, JsonDeserializationContext context) throws JsonParseException {
        JsonObject objeto = json.getAsJsonObject();

        String tipoCarro = objeto.get("tipo").getAsString();

        switch (tipoCarro) {
            case "Sedan":
                return context.deserialize(objeto, Sedan.class);

            case "SUV":
                return context.deserialize(objeto, Suv.class);

            case "Popular":
                return context.deserialize(objeto, Popular.class);

            default:
                throw new JsonParseException("Tipo de carro desconhecido: " + tipoCarro);
        }
    }
}