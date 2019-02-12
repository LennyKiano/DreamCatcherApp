package com.leonkianoapps.leonk.dreamcatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "dream";
    private static final String COL1 = "ID";   //0
    private static final String COL2 = "label";
    private static final String COL3= "dream";



    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable="CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL2 + " VARCHAR, " + COL3 + " VARCHAR)";

        db.execSQL(createTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public  boolean addData(String name, String email){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(COL2,name);
        contentValues.put(COL3,email);


        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + email + " to " + TABLE_NAME);

        long result= db.insert(TABLE_NAME,null,contentValues);

        //if data as inserted incorrectly it will return -1


        if (result == -1) {

            return false;

        } else

        {
            return true;
        }


    }

    public Cursor getData(){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getLabel(String label){

        SQLiteDatabase db=this.getWritableDatabase();
        String query= "SELECT * FROM "+TABLE_NAME+ " WHERE "+COL2 +" = '" +label +"'";
        Cursor data= db.rawQuery(query,null);

        return data;
    }

    public Cursor getDream(String label){

        SQLiteDatabase db=this.getWritableDatabase();
        String query= "SELECT * FROM "+COL3+ " WHERE "+COL2 +" = '" +label +"'";
        Cursor data= db.rawQuery(query,null);

        return data;
    }


}

