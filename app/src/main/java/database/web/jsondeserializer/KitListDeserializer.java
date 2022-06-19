package database.web.jsondeserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import database.entities.Kit;

public class KitListDeserializer implements JsonDeserializer<List<Kit>> {
    @Override
    public List<Kit> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Kit> kits = new LinkedList<>();
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray kitJsonArray = jsonObject.get("data").getAsJsonArray();
        for(JsonElement kitJsonElement:kitJsonArray){
            JsonObject kitJsonObject = kitJsonElement.getAsJsonObject();
            String name = kitJsonObject.get("kit_name").getAsString();
            float rating = kitJsonObject.get("rating").getAsFloat();
            kits.add(new Kit(name, rating));
        }
        return kits;
    }
}
