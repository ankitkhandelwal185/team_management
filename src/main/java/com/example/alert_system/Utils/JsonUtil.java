package com.example.alert_system.Utils;

import com.google.gson.*;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

@Log4j2
public class JsonUtil {
    public static final String DATE_TIME_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static Gson gsonSnakeCase =
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setDateFormat(DATE_TIME_UTC_FORMAT)
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(JSONObject.class, JsonSerializerUtil.jsonSerializerUtil)
                    .registerTypeAdapter(LocalDate.class, LocalDateSerializer.localDateSerializer)
                    .registerTypeAdapter(LocalDate.class, LocalDateDeserializer.localDateDeserializer)
                    .registerTypeAdapter(JSONObject.class, JsonDeSerializerUtil.jsonDeSerializerUtil)
                    .disableHtmlEscaping()
                    .create();

    public static Gson gsonCamelCase =
            new GsonBuilder()
                    .setDateFormat(DATE_TIME_UTC_FORMAT)
                    .registerTypeAdapter(JSONObject.class, JsonSerializerUtil.jsonSerializerUtil)
                    .registerTypeAdapter(JSONObject.class, JsonDeSerializerUtil.jsonDeSerializerUtil)
                    .disableHtmlEscaping()
                    .create();


    public static <T> String toSnakeCaseJsonString(T obj) {
        return gsonSnakeCase.toJson(obj);
    }

    public static <T> T fromJsonStringToCamelCaseObject(String string, Class<T> toClass)
            throws JsonSyntaxException {
        return gsonSnakeCase.fromJson(string, toClass);
    }

    public static JSONObject getValidJSON(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return json;
        } catch (JSONException e) {
            log.error("Error: {}", e);
            return null;
        }
    }

    public static <T> String toCamelCaseJsonString(T obj) {
        return gsonCamelCase.toJson(obj);
    }

    public static <T> T fromCamelCaseJsonStringToObject(String string, Class<T> toClass)
            throws JsonSyntaxException {
        return gsonCamelCase.fromJson(string, toClass);
    }

    public static <T> T fromCamelCaseObjectToPojo(Object data, Class<T> toClass) throws JsonSyntaxException{
        JsonElement jsonElement = gsonCamelCase.toJsonTree(data);
        return gsonCamelCase.fromJson(jsonElement, toClass);
    }
}
