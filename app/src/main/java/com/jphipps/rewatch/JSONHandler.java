package com.jphipps.rewatch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSONHandler extends NetworkHandler {

    protected ArrayList<Entry> parseFile(InputStream inputStream) throws Exception {
        String jsonString = getJSON(inputStream);
        return decodeJSON(jsonString);
    }

    private String getJSON(InputStream stream) throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();

    }

    private ArrayList<Entry> decodeJSON(String jsonString) throws Exception {
        ArrayList<Entry> entryDetails = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for (int index = 0; index < jsonArray.length(); index++){
            Entry entry = new Entry();
            JSONObject entryJSON = jsonArray.getJSONObject(index);

            if(entryJSON.has("title")) { //for movies
                entry.setName(entryJSON.getString("title"));
            }
            else if(entryJSON.has("name")) { //for tv
                entry.setName(entryJSON.getString("name"));
            }


            if(entryJSON.has("release_date")) { //for movies
                String date = entryJSON.getString("release_date");
                if(!date.equals(""))entry.setYear(date.substring(0,4));
                else entry.setYear("Date Unknown");
            }
            else if(entryJSON.has("first_air_date")) { //for tv
                String date = entryJSON.getString("first_air_date");
                if(!date.equals(""))entry.setYear(date.substring(0,4));
                else entry.setYear("Date Unknown");
            }

            if(entryJSON.has("poster_path")) {
                String url = entryJSON.getString("poster_path");
                if(!url.equals("null"))entry.setImageURL("http://image.tmdb.org/t/p/w200"+url);
                else entry.setImageURL("null");
            }
            entryDetails.add(entry);
        }
        return entryDetails;
    }
}
