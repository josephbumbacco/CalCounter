package com.example.calcounter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "calcounter";

    // Table Name
    public static final String TABLE_FOOD = "food";

    // Column Name
    public static final String COLUMN_ID = "id";

    /*
     * Food Table
     */

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_CALORIES = "calories";

    public static final String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NAME + " TEXT, "
            + COLUMN_BRAND + " TEXT, "
            + COLUMN_CALORIES + " INTEGER)";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    /*
     *      DATABASE METHODS
     */


}
