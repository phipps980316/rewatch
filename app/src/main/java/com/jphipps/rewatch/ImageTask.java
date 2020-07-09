package com.jphipps.rewatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

class ImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageResponseDelegate imageResponseDelegate;

    ImageTask(ImageResponseDelegate delegate){
        imageResponseDelegate = delegate;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bitmap = null;
        if(!url.equals("null")){
            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                bitmap = null;
            }
        }
        return bitmap;
    }
    protected void onPostExecute(Bitmap result) {
        imageResponseDelegate.imageTaskFinished(result);
    }

    public interface ImageResponseDelegate {
        void imageTaskFinished(Bitmap bitmap);
    }
}
