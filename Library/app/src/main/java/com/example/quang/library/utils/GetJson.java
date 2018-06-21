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

public class GetJson extends AsyncTask<String,Void, ArrayBook> {

    private ArrayList<ItemBook> arrBook = new ArrayList<>();
    private OnEventListener<ArrayBook> mCallBack;
    private Context mContext;
    public Exception mException;

    public GetJson(Context context, OnEventListener callback) {
        mCallBack = callback;
        mContext = context;
    }

    @Override
    protected ArrayBook doInBackground(String... strings) {
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

            if (!builder.toString().isEmpty()){
                try {
                    JSONObject obj  = new JSONObject(builder.toString());
                    JSONArray arr = obj.getJSONArray("items");

                    for (int i=0; i<arr.length(); i++){

                        JSONObject objChild = arr.getJSONObject(i);

                        String selfLink = objChild.getString("selfLink");
                        JSONObject objVolume = objChild.getJSONObject("volumeInfo");
                        String title = objVolume.getString("title");

                        boolean checkAuthor = objVolume.has("authors");
                        String author;
                        if (checkAuthor){
                            JSONArray arrAuthor = objVolume.getJSONArray("authors");
                            author = arrAuthor.toString();
                        }else {
                            author = "";
                        }

                        boolean checkDesc = objVolume.has("description");
                        String desc;
                        if(checkDesc){
                            desc = objVolume.getString("description");
                        }else {
                            desc = "";
                        }

                        boolean checkImage = objVolume.has("imageLinks");
                        String image;
                        if(checkImage){
                            JSONObject objImage = objVolume.getJSONObject("imageLinks");
                            image = objImage.getString("smallThumbnail");
                        }else {
                            image = "";
                        }

                        ItemBook item = new ItemBook(title,author,desc,image,"",selfLink);
                        arrBook.add(item);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayBook arrayBook = new ArrayBook(arrBook);
                return arrayBook;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayBook arrayBook) {
        super.onPostExecute(arrayBook);
        if (mCallBack != null) {
            if (mException == null) {
                mCallBack.onSuccess(arrayBook);
            } else {
                mCallBack.onFailure(mException);
            }
        }
    }
}


