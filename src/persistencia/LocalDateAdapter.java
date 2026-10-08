package persistencia;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializationContext;

import java.lang.reflect.Type;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter
        implements JsonSerializer<LocalDate>,
                   JsonDeserializer<LocalDate>{

    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public JsonElement serialize(
            LocalDate data,
            Type typeOfSrc,
            JsonSerializationContext context) {

        if(data == null){
            return null;
        }

        return new JsonPrimitive(data.format(FORMATADOR));
    }

    @Override
    public LocalDate deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context)
            throws JsonParseException {

        if(json == null || json.getAsString().isBlank()){
            return null;
        }

        return LocalDate.parse(json.getAsString(), FORMATADOR);
    }

}