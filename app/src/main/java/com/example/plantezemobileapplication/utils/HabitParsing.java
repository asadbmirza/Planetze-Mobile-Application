package com.example.plantezemobileapplication.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HabitParsing {
    public static String loadJSONFromAsset(Context context) throws IOException {
        String json = null;
        InputStream is = context.getAssets().open("habitList.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);  // Read the file
        is.close();
        json = new String(buffer, StandardCharsets.UTF_8);  // Convert byte array to String
        return json;
    }

    public static JSONArray parseJSON(String json, String jsonName) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray habitsArray = jsonObject.getJSONArray(jsonName);
            return habitsArray;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
