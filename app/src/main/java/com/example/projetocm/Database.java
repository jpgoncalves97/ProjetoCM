package com.example.projetocm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME ="projetocm.db";
    private static final String[] TABLE_NAMES = {"meals", "ingredients", "categories", "areas"};


    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists meals " +
                "( id integer, name text, category text, area text, instructions text, imageUrl text, tags text, youtubeUrl text," +
                " ingredients text, measurements text, sourceUrl text )");
        db.execSQL("create table if not exists ingredients " +
                "( id integer, ingredient text, included boolean )");
        db.execSQL("create table if not exists categories " +
                "( category text, included boolean )");
        db.execSQL("create table if not exists areas " +
                "( area text    , included boolean )");
        //db.execSQL(DICTIONARY_TABLE_CREATE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
/*
    public long newNote(String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note);
        contentValues.put("description", "");
        return db.insert(TABLE_NAMES[0], null, contentValues);
    }

    public void updateTitle(String id, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update notas set title = " + note + " where id = " + id);
    }

    public void updateDescription(String id, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("update notas set description = ? where id = ?");
        stmt.bindString(1, description);
        stmt.bindString(2, id);
        stmt.execute();
    }

    public void deleteNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAMES[0], "id = ? ", new String[]{id});
    }

    public String getNote(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select description from notas where id = ?", new String[]{id});
        res.moveToFirst();
        return res.getString(res.getColumnIndex("description"));
    }

    public Notas getNotas(){
        ArrayList<String> ids = new ArrayList<String>();
        ArrayList<String> titles = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select id, title from notas", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            ids.add(res.getString(res.getColumnIndex("id")));
            titles.add(res.getString(res.getColumnIndex("title")));
            res.moveToNext();
        }
        return new Notas(ids, titles);
    }

 */
}