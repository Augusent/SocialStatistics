package com.serovr.vkspy.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;


import com.vk.sdk.api.model.VKUsersArray;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImageManager extends AsyncTask<Void, Void, Void> {
    private String path;
    private ArrayList<String> links = new ArrayList<String>();


    public ImageManager(String path,ArrayList<String> links) {
        this.path = path;
        this.links = links;
    }

    public ImageManager() {

    }

    public void setParams(String path,ArrayList<String> links){
        this.path = path;
        this.links = links;
    }

    @Override
    protected Void doInBackground(Void... params) {

        OutputStream outputFile = null;

        for (int i=0; i<links.size(); i++ ) {
            try {
                String[] tokens = links.get(i).split("/");
                String fileName = tokens[tokens.length - 1];
                File file = new File(path, fileName);
//                Log.i("File", fileName);
                if (!file.exists()) {
//                    Log.i("file not ", "exists");
                    outputFile = new FileOutputStream(file);
                    Bitmap bitmap = null;
                    HttpURLConnection httpURLConnection = null;
                    BufferedInputStream bufferedInputStream = null;
                    try {
//                        Log.v("ImageManager", "Starting loading image by URL: " + links.get(i));
                        httpURLConnection = (HttpURLConnection) new URL(links.get(i)).openConnection();
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpURLConnection.connect();
                        bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream(), 8192);
                        bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                        bufferedInputStream.close();
                        httpURLConnection.disconnect();
                        bufferedInputStream = null;
                        httpURLConnection = null;
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
//                        Log.e("ImageManager", "Url parsing was failed: " + links.get(i));
                    } catch (IOException ex) {
                        ex.printStackTrace();
//                        Log.d("ImageManager", links.get(i) + " does not exists");
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
//                        Log.w("ImageManager", "Out of memory!!!");
                        return null;
                    } finally {
                        if (bufferedInputStream != null)
                            try {
                                bufferedInputStream.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        if (httpURLConnection != null)
                            httpURLConnection.disconnect();
                    }
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputFile); // сохранять картинку в jpeg-формате с 20% сжатия.
                    }catch (NullPointerException npException){
                        npException.printStackTrace();
                    }
                    outputFile.flush();
                    outputFile.close();
                }
            } catch (Exception e) // TODO:обработать реальные исключения
            {
                e.printStackTrace();
            }
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {
    }

}
