package com.example.alert_system.Utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

@Log4j2
public class JsonDeSerializerUtil implements JsonDeserializer<JSONObject> {
    static JsonDeSerializerUtil jsonDeSerializerUtil = new JsonDeSerializerUtil();

    @Override
    public JSONObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json == null) {
            return null;
        }
        try {
            return new JSONObject(json.toString());
        } catch (JSONException e) {
            log.error("Error: {}", e);
            e.printStackTrace();
            throw new JsonParseException(e);
        }
    }
}
