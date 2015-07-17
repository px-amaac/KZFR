package creek.fm.doublea.kzfr.models;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;

/**
 * This JsonDeserializer checks for boolean value type. If value is not supposed to be a boolean but it
 * is then the deserializer throws out the boolean value and replaces it with a null value. This is
 * used to handle "false" values in the json that should have been null.
 */
public class JsonDeserializer<T> implements com.google.gson.JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (typeOfT != Boolean.class) {
            if (json.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
                if (jsonPrimitive.isBoolean()) {
                    return null;
                }
            }
        }
        Gson gson = new Gson();
        return gson.fromJson(json, typeOfT);
    }
}