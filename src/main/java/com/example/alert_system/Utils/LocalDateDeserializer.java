package com.example.alert_system.Utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
    static LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer();
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
    }
}