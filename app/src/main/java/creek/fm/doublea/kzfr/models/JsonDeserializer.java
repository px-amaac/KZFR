package creek.fm.doublea.kzfr.models;

import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Aaron on 6/23/2015.
 */
public class JsonDeserializer<T> implements com.google.gson.JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if(typeOfT != Boolean.class) {
            if (json.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
                if(jsonPrimitive.isBoolean()) {
                    return null;
                }
            }
        }
        Gson gson = new Gson();
        return gson.fromJson(json, typeOfT);
    }
}