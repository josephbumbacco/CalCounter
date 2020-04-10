package com.example.calcounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.calcounter.Javabean.Food;

import java.util.ArrayList;

/**
 * Class handles the database and its functionality
 *
 * @author Drew Brooks
 */
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

    /*
     * SQL string to create the food table
     */
    public static final String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NAME + " TEXT, "
            + COLUMN_BRAND + " TEXT, "
            + COLUMN_CALORIES + " DOUBLE)";


    /**
     * @param context
     *
     * Allows access to the db
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @param db
     *
     * On creation, create food table inside our DB
     */
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

    /**
     * CREATE FOOD
     *
     * Method used to create/add a food to the DB
     * @param food
     */
    public void addFood(Food food){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_BRAND, food.getBrand());
        values.put(COLUMN_CALORIES, food.getCalories());
        db.insert(TABLE_FOOD, null, values);
        db.close();
    }


    /**
     * READ FOOD
     *
     * Method used to return single food from DB
     * @param id
     * @return food item
     */
    public Food getFood(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Food food = null;
        Cursor cursor = db.query(TABLE_FOOD, new String[]{ COLUMN_ID,
                COLUMN_NAME, COLUMN_BRAND, COLUMN_CALORIES},COLUMN_ID + "= ?",
                new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst()){
            food = new Food(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3));
        }
        db.close();
        return food;
    }

    /**
     * READ ALL FOODS
     *
     * Method will return all foods from the DB
     * @return
     */
    public ArrayList<Food> getAllFoods(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOOD ,
                null);
        ArrayList<Food> foods = new ArrayList<>();
        while(cursor.moveToNext()) {
            foods.add(new Food(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3)));
        }
        db.close();
        return foods;
    }


    /**
     * UPDATE FOOD
     *
     * Method used to update food, will be used to manually adjust calories on specific food item, contains all values for now
     * @param food
     * @return
     */
    public int updateFood(Food food){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_BRAND, food.getBrand());
        values.put(COLUMN_CALORIES, food.getCalories());
        return db.update(TABLE_FOOD, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(food.getId())});
    }

    /**
     * DELETE FOOD
     *
     * Method used to delete a food item from the database
     * @param food
     */
    public void deleteFood(int food){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOOD, COLUMN_ID + " = ?",
                new String[]{String.valueOf(food)});
        db.close();
    }
}
