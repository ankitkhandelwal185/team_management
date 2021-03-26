package com.example.alert_system.Utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Iterator;

public class JsonSerializerUtil implements JsonSerializer<JSONObject> {
    static JsonSerializerUtil jsonSerializerUtil = new JsonSerializerUtil();

    @Override
    public JsonElement serialize(JSONObject src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        Iterator<String> keys = src.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = src.opt(key);
            JsonElement jsonElement = context.serialize(value, value.getClass());
            jsonObject.add(key, jsonElement);
        }
        return jsonObject;
    }
}
