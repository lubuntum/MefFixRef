package database.web.jsondeserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.LinkedList;

import database.entities.Cell;
import database.entities.Kit;

public class KitDeserializer implements JsonDeserializer<Kit> {
    @Override
    public Kit deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject data = jsonObject.get("data").getAsJsonObject();

        JsonObject kitJsonObject = data.get("kit").getAsJsonObject();
        String name = kitJsonObject.get("kit_name").getAsString();
        float rating = kitJsonObject.get("rating").getAsFloat();
        Kit kit = new Kit(name,rating);

        JsonArray cellJsonArray = data.get("cells").getAsJsonArray();
        if (cellJsonArray != null) {
            kit.cells = new LinkedList<>();
            for (JsonElement cellJsonElement : cellJsonArray) {
                JsonObject cellJsonObject = cellJsonElement.getAsJsonObject();
                String key = cellJsonObject.get("key").getAsString();
                String value = cellJsonObject.get("value").getAsString();
                kit.cells.add(new Cell(key, value));
            }
        }
        return kit;
    }
}
