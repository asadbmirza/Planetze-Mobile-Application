package com.example.plantezemobileapplication.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonParser {
    private Context context;

    public JsonParser() {}

    public JsonParser(Context context) {
        this.context = context;
    }

    public String loadJSON(String fileName) {
        String json = null;

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    public double getElement(String fileName, int row, int col) {
        String jsonString = loadJSON(fileName);
        double element = 0;

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONArray dataArray = jsonArray.getJSONArray(row);
            element = dataArray.getDouble(col);
        }
        catch(JSONException e) {
            e.printStackTrace();;
        }

        return element;
    }

    public double getEmissionByCountry(String fileName, String country) {
        String jsonString = loadJSON(fileName);

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject dataObj = jsonArray.getJSONObject(0);
            if (dataObj.has(country)) {
                return dataObj.getDouble(country);
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
