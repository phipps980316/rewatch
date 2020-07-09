package com.jphipps.rewatch;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public abstract class NetworkHandler extends AsyncTask<String, Void, ArrayList<Entry>> {
    SearchResponseDelegate delegate = null;

    protected ArrayList<Entry> doInBackground(String... urls){
        ArrayList<Entry> searchResults;
        try {
            searchResults = loadFileFromNetwork(urls[0]);
        } catch (Exception e) {
            searchResults = new ArrayList<>();
        }
        return searchResults;
    }

    private ArrayList<Entry> loadFileFromNetwork(String urlString) throws Exception {

        ArrayList<Entry> searchResults;
        try (InputStream stream = downloadUrl(urlString)) {
            searchResults = parseFile(stream);
        }
        return searchResults;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        return connection.getInputStream();
    }

    protected abstract ArrayList<Entry> parseFile(InputStream inputStream) throws Exception;

    protected void onPostExecute(ArrayList<Entry> fetchedData) {
        delegate.searchTaskFinished(fetchedData);
    }

    public interface SearchResponseDelegate {
        void searchTaskFinished(ArrayList<Entry> fetchedData);
    }
}
