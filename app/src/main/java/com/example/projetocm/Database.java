package com.example.projetocm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME ="projetocm.db";
    private static final String[] TABLE_NAMES = {"meals", "ingredients", "categories", "areas"};


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists meals " +
                "( status integer,id integer, name text, category text, area text, instructions text, image text, tags text, youtube text," +
                " ingredients text, measurements text, source text )"); //status, 0 = default, 1 = comido, -1 = nao gosta
        db.execSQL("create table if not exists ingredients " +
                "( id integer, ingredient text, included boolean )");
        db.execSQL("create table if not exists categories " +
                "( category text, included boolean )");
        db.execSQL("create table if not exists areas " +
                "( area text , included boolean )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(String i:TABLE_NAMES)
            db.execSQL("DROP TABLE IF EXISTS "+ i);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public ArrayList<Boolean> get_included_ingredients_asc(){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Boolean> result = new ArrayList<>();
        String sortOrder = "id" + " ASC";

        Cursor cursor = db.query(
                TABLE_NAMES[0],   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                  // don't filter by row groups
                sortOrder              // The sort order
        );
        while(cursor.moveToNext()) {
            if(cursor.getString(cursor.getColumnIndexOrThrow("included")).equals("True")){
                result.add(Boolean.TRUE);
            }else
                result.add(Boolean.FALSE);
        }
        return result;
    }
    public ArrayList<String> get_all_ingredients_asc(){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> result = new ArrayList<>();
        String sortOrder = "id" + " ASC";

        Cursor cursor = db.query(
                TABLE_NAMES[0],   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                  // don't filter by row groups
                sortOrder              // The sort order
        );
        while(cursor.moveToNext()) {
            result.add(cursor.getString(cursor.getColumnIndexOrThrow("ingredient")));
        }
        return result;
    }

    public Integer check_meal_status(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] projection = {"status", "id", "name"};

        String selection = "id" + " = ?";

        String[] selectionArgs = { id };

        Cursor cursor = db.query(
                TABLE_NAMES[0],   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                  // don't filter by row groups
                null              // The sort order
        );
        while(cursor.moveToNext()) {
            int item = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            if(item == Integer.valueOf(id)){
                return cursor.getInt(cursor.getColumnIndexOrThrow("status"));
            }
        }
        return 0;
    }

    public void get_meals(){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Meal> meals = new ArrayList<Meal>();

        Cursor cursor = db.query(
                TABLE_NAMES[0],   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                  // don't filter by row groups
                null              // The sort order
        );
        while(cursor.moveToNext()) {
            Log.d("db_debug", cursor.getString(cursor.getColumnIndexOrThrow("name")));
        }
        cursor.close();


    }

    public ArrayList<String> populateListView(){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> meals = new ArrayList<String>();

        Cursor cursor = db.query(
                TABLE_NAMES[0],   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                  // don't filter by row groups
                null              // The sort order
        );
        while(cursor.moveToNext()) {
            Log.d("db_debug", cursor.getString(cursor.getColumnIndexOrThrow("name")));
            meals.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        }
        return meals;

    }

    public long add_meal(Meal newMeal, int status){  //status, 0 = default, 1 = comido, -1 = nao gosta
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status",status);
        values.put("id",newMeal.id);
        values.put("name",newMeal.name);
        values.put("category",newMeal.category);
        values.put("area",newMeal.area);
        values.put("instructions",newMeal.instructions);
        values.put("image",newMeal.image);
        values.put("tags",newMeal.tag);
        values.put("youtube",newMeal.youtube);
        values.put("ingredients",newMeal.ingredients);
        values.put("measurements",newMeal.measurements);
        values.put("source",newMeal.source);

        return db.insert(TABLE_NAMES[0], null, values);
    }
    public void delete_meal(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "id" + " LIKE ?";
        String[] selectionArgs = new String[]{id};
        db.delete(TABLE_NAMES[0], selection, selectionArgs);
    }
    public long add_ingredient(Ingredient newIngredient){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id",newIngredient.id);
        values.put("ingredient",newIngredient.ingredient);
        values.put("included",newIngredient.included);

        return db.insert(TABLE_NAMES[1], null, values);
    }
    public void delete_ingredient(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "id" + " LIKE ?";
        String[] selectionArgs = new String[]{id};
        db.delete(TABLE_NAMES[1], selection, selectionArgs);
    }
    public long add_category(Category newCategory){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("category",newCategory.category);
        values.put("included",newCategory.included);

        return db.insert(TABLE_NAMES[2], null, values);
    }
    public void delete_category(String categoryname) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "category" + " LIKE ?";
        String[] selectionArgs = new String[]{categoryname};
        db.delete(TABLE_NAMES[1], selection, selectionArgs);
    }
    public long add_area(Area newArea){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("area",newArea.area);
        values.put("included",newArea.included);

        return db.insert(TABLE_NAMES[3], null, values);
    }
    public void delete_area(String areaname) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "area" + " LIKE ?";
        String[] selectionArgs = new String[]{areaname};
        db.delete(TABLE_NAMES[1], selection, selectionArgs);
    }

    public ArrayList<String> search_name(String in, String Table_name){
        ArrayList<String> res = new ArrayList<>();
        String search_coll = "";
        if(Table_name.equals(TABLE_NAMES[0]))
            search_coll = "name";//meals
        else if(Table_name.equals(TABLE_NAMES[1]))
            search_coll = "ingredient";
        else if(Table_name.equals(TABLE_NAMES[2]))
            search_coll = "category";
        else if(Table_name.equals(TABLE_NAMES[3]))
            search_coll = "area";
        else
            return res;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                Table_name,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                  // don't filter by row groups
                null              // The sort order
        );
        while(cursor.moveToNext()) {
            String item = cursor.getString(cursor.getColumnIndexOrThrow(search_coll));
            if(item.startsWith(in)){
                res.add(item);
            }
        }
        return res;
    }

}