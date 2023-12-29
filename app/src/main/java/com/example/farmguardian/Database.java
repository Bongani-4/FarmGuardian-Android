package com.example.farmguardian;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class Database  extends SQLiteOpenHelper{
    LoginActivity login = new LoginActivity();
    public Database(@Nullable Context context,@Nullable String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
         @Override
         public  void onCreate(SQLiteDatabase sqLiteDatabase)
         {
             String qry = "create table users(username text, email text , password text,request text)";
              //AC means Animal Caretaker
             sqLiteDatabase.execSQL(qry);


                      }
    @Override
    public  void onUpgrade(SQLiteDatabase sqLiteDatabase,int i, int i1)
    {

    }
    public  void saveProfile(String username,String contacts, String location,String fullnames, String experience,int CBavailable)
    {
        username = login.getUsername();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("fullnames", fullnames);
        values.put("contacts", contacts);
        values.put("location", location);
        values.put("experience", experience);
        values.put("isAvailable", CBavailable);


        db.insert("ACprofile",null,values);
        db.close();

    }
    public  void register(String username,String email,String password, String request )
    {

        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("request", request);
        SQLiteDatabase db = getWritableDatabase();

        db.insert("users",null,cv);
        db.close();

    }
    public  int login(String username,String password)
    {
        int result =0;
         String str[] = new String[2];
         str[0] = username;
         str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username=?  and password=?",str);
        if(c.moveToFirst())
        {
            result =1;
        }
        return result;

    }
    // fetch ACprofile data
    public List<AcaretakerModel> getAcaretakerList() {
        List<AcaretakerModel> caretakerList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ACprofile", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") AcaretakerModel caretaker = new AcaretakerModel(
                        cursor.getString(cursor.getColumnIndex("fullnames")),
                        cursor.getString(cursor.getColumnIndex("location")),
                        cursor.getString(cursor.getColumnIndex("contacts")),
                        cursor.getString(cursor.getColumnIndex("experience")),
                        cursor.getInt(cursor.getColumnIndex("CBavailable"))
                );

                caretakerList.add(caretaker);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return caretakerList;
    }




}
