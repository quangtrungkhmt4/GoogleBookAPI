package com.example.quang.library.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.quang.library.model.ArrayBook;
import com.example.quang.library.model.ItemBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class GetlargeImage extends AsyncTask<String,Void, String> {

    private OnEventListener<String> mCallBack;
    private Context mContext;
    public Exception mException;

    public GetlargeImage(Context context, OnEventListener callback) {
        mCallBack = callback;
        mContext = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String link = strings[0];

        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            InputStream inputStream = connection.getInputStream();
            StringBuilder builder = new StringBuilder();
            byte[] b = new byte[1024];
            int count = inputStream.read(b);
            while (count != -1) {
                builder.append(new String(b, 0, count, "utf-8"));
                count = inputStream.read(b);
            }
            inputStream.close();
            String medium = "";
            if (!builder.toString().isEmpty()) {
                try {
                    JSONObject obj = new JSONObject(builder.toString());

                    JSONObject objVolume = obj.getJSONObject("volumeInfo");
                    JSONObject objImage = objVolume.getJSONObject("imageLinks");

                    boolean checkDesc = objVolume.has("description");

                    if (checkDesc) {
                        medium = objImage.getString("medium");
                    } else {
                        medium = "";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return medium;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mCallBack != null) {
            if (mException == null) {
                mCallBack.onSuccess(s);
            } else {
                mCallBack.onFailure(mException);
            }
        }
    }
}
