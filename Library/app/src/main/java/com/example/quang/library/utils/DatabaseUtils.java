package com.example.quang.library.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.quang.library.model.ItemBook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DatabaseUtils {
    private static final String PATH = Environment.getDataDirectory().getPath()
            + "/data/com.example.quang.library/databases/";

    private static final String DB_NAME = "library.sqlite";
    private static final String TABLE_NAME_FAV = "TBLFAVORITE";
    private static final String TABLE_NAME_BM = "TBLBOOKMARK";
    private static final String ID = "ID";
    private static final String TITLE = "TITLE";
    private static final String AUTHOR = "AUTHOR";
    private static final String DESC = "DESC";
    private static final String SMALLIMAGE = "SMALLIMAGE";
    private static final String LARGEIMAGE = "LARGEIMAGE";
    private static final String SELFLINK = "SELFLINK";
    private static final String IMAGE = "IMAGE";

    private Context context;
    private SQLiteDatabase database;

    public DatabaseUtils(Context context) {
        this.context = context;
        copyFileToDevice();
    }

    private void copyFileToDevice() {
        File file = new File(PATH + DB_NAME);
        if (!file.exists()) {
            File parent = file.getParentFile();
            parent.mkdirs();
            try {
                InputStream inputStream = context.getAssets().open(DB_NAME);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int count = inputStream.read(b);
                while (count != -1) {
                    outputStream.write(b, 0, count);
                    count = inputStream.read(b);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openDatabase() {
        database = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    private void closeDatabase() {
        database.close();
    }

    public ArrayList<ItemBook> getFav() {
        ArrayList<ItemBook> arr = new ArrayList<>();
        openDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_NAME_FAV, null, null, null, null, null, null);
        cursor.moveToFirst();

        int indexTITLE = cursor.getColumnIndex(TITLE);
        int indexAUTHOR = cursor.getColumnIndex(AUTHOR);
        int indexDESC = cursor.getColumnIndex(DESC);
        int indexSMALLIMAGE = cursor.getColumnIndex(SMALLIMAGE);
        int indexLARGEIMAGE = cursor.getColumnIndex(LARGEIMAGE);
        int indexSELFLINK = cursor.getColumnIndex(SELFLINK);

        while (!cursor.isAfterLast()) {
            String title = cursor.getString(indexTITLE);
            String author = cursor.getString(indexAUTHOR);
            String desc = cursor.getString(indexDESC);
            String smallImage = cursor.getString(indexSMALLIMAGE);
            String largeImage = cursor.getString(indexLARGEIMAGE);
            String selfLink = cursor.getString(indexSELFLINK);
            ItemBook book = new ItemBook(title,author,desc,smallImage,largeImage,selfLink);
            arr.add(book);
            cursor.moveToNext();
        }
        closeDatabase();
        return arr;
    }

    public boolean checkExistsFav(ItemBook item){
        ArrayList<ItemBook> arrItem = getFav();
        boolean check = false;
        for (int i=0; i<arrItem.size(); i++){
            if (arrItem.get(i).getSelfLink().equals(item.getSelfLink())){
                check = true;
                break;
            }
        }
        return check;
    }

    public ArrayList<ItemBook> getBM() {
        ArrayList<ItemBook> arr = new ArrayList<>();
        openDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_NAME_BM, null, null, null, null, null, null);
        cursor.moveToFirst();
        int indexTITLE = cursor.getColumnIndex(TITLE);
        int indexAUTHOR = cursor.getColumnIndex(AUTHOR);
        int indexDESC = cursor.getColumnIndex(DESC);
        int indexIMAGE = cursor.getColumnIndex(IMAGE);

        while (!cursor.isAfterLast()) {
            String title = cursor.getString(indexTITLE);
            String author = cursor.getString(indexAUTHOR);
            String desc = cursor.getString(indexDESC);
            String image = cursor.getString(indexIMAGE);
            ItemBook book = new ItemBook(title,author,desc,image,image,"");
            arr.add(book);
            cursor.moveToNext();
        }
        closeDatabase();
        return arr;
    }

    public long insertFAV(ItemBook item) {
        ContentValues values = new ContentValues();
        values.put(TITLE, item.getTitle());
        values.put(AUTHOR, item.getAuthor());
        values.put(DESC, item.getDesc());
        values.put(SMALLIMAGE, item.getSmallImage());
        values.put(LARGEIMAGE, item.getLargeImage());
        values.put(SELFLINK, item.getSelfLink());
        openDatabase();
        long id = database.insert(TABLE_NAME_FAV, null, values);
        closeDatabase();
        return id;
    }

    public long insertBM(ItemBook item) {
        ContentValues values = new ContentValues();
        values.put(TITLE, item.getTitle());
        values.put(AUTHOR, item.getAuthor());
        values.put(DESC, item.getDesc());
        values.put(IMAGE, item.getSmallImage());
        openDatabase();
        long id = database.insert(TABLE_NAME_BM, null, values);
        closeDatabase();
        return id;
    }


    public int deleteFAV(String selfLink){
        openDatabase();
        String where = SELFLINK + " = ?";
        String[] whereAgrs = {selfLink + ""};
        int rows = database.delete(TABLE_NAME_FAV,where,whereAgrs);
        closeDatabase();
        return rows;
    }

    public int deleteBM(String image){
        openDatabase();
        String where = IMAGE + " = ?";
        String[] whereAgrs = {image + ""};
        int rows = database.delete(TABLE_NAME_BM,where,whereAgrs);
        closeDatabase();
        return rows;
    }
}
