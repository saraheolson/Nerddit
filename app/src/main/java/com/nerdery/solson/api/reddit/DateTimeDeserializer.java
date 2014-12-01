package com.nerdery.solson.api.reddit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;

import java.lang.reflect.Type;

/**
 * To save time implenting the Reddit API (which has TERRIBLE documentation), I took this code from:
 * https://github.com/jacobtabak/droidcon/
 */
public class DateTimeDeserializer implements JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return new DateTime(json.getAsLong() * 1000);
    }
}
