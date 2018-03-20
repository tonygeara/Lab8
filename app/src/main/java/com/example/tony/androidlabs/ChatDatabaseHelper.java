package com.example.tony.androidlabs;

/**
 * Created by Tony on 2018-02-27.
 */

//public  class ChatDatabaseHelper extends SQLiteOpenHelper {

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "Messages.db" ;
    private static int VERSION_NUM =3;
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "msgs";
    public static String TABLE_NAME = "addMsg";
    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_MESSAGE+" TEXT );");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}