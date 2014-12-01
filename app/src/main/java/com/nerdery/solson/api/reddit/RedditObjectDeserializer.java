package com.nerdery.solson.api.reddit;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import com.nerdery.solson.model.RedditObject;
import com.nerdery.solson.model.RedditObjectWrapper;

import android.util.Log;

import java.lang.reflect.Type;

/**
 * To save time implenting the Reddit API (which has TERRIBLE documentation), I took this code from:
 * https://github.com/jacobtabak/droidcon/
 */
public class RedditObjectDeserializer implements JsonDeserializer<RedditObject> {

    public static final String TAG = RedditObjectDeserializer.class.getSimpleName();

    public static final String KIND = "kind";

    public RedditObject deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        if (!json.isJsonObject()) {
            // if there are no replies, we're given a String rather than an object
            return null;
        }
        try {
            RedditObjectWrapper wrapper = new Gson().fromJson(json, RedditObjectWrapper.class);
            return context.deserialize(wrapper.getData(), wrapper.getKind().getDerivedClass());
        } catch (JsonParseException e) {
            Log.e(TAG, "Failed to deserialize", e);
            return null;
        }
    }
}
