package com.jphipps.rewatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class ImageUtility {
    byte[] compressImage(Bitmap bitmap){
        if(bitmap != null){
            ByteArrayOutputStream byteArrayOutputStream = null;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }finally {
                if(byteArrayOutputStream != null){
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                        Log.e(ImageUtility.class.getSimpleName(), "Stream was not closed");
                    }
                }
            }
        } else {
            return null;
        }
    }
    Bitmap uncompressImage(byte[] src){
        if(src != null) {
            return BitmapFactory.decodeByteArray(src, 0, src.length);
        } else {
            return null;
        }
    }
}
