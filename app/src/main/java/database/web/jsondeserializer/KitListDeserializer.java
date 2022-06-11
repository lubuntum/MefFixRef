package database.web.jsondeserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

import database.entities.Kit;

public class KitListDeserializer implements JsonDeserializer<List<Kit>> {
    @Override
    public List<Kit> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
