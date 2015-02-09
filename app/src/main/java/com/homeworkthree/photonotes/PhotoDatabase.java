package com.homeworkthree.photonotes;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class PhotoDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "imagedatabase";
    private static final int DATABASE_VERSION = 1;

    public PhotoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE savedimages(_id INTEGER PRIMARY KEY, image_name TEXT, image_path TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Photos getValues(int key) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM savedimages WHERE _id=" + key+ "", null);

        if (cursor != null && cursor.getCount() > 0)
            cursor.moveToFirst();
            Photos photos = new Photos(cursor.getString(cursor.getColumnIndex("image_name")),cursor.getString(cursor.getColumnIndex("image_path")));
        Log.e("image name ", cursor.getString(cursor.getColumnIndex("image_name")));
        Log.e("image path ", cursor.getString(cursor.getColumnIndex("image_path")));

        cursor.close();
        db.close();

        return photos;

    }

    public List<String> getAllImages() {
        List<String> names = new LinkedList<String>();
        String selectQuery = "SELECT  * FROM savedimages" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("image_name"));
                names.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return names;
    }
}