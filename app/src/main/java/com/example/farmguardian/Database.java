package com.example.farmguardian;

import com.github.javafaker.Faker;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private Context context;

    public Database(@Nullable Context context, @Nullable String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);

        // For debugging
        Toast.makeText(context, "onCreate method invoked", Toast.LENGTH_SHORT).show();
        addAnimalCaretakers(sqLiteDatabase);
    }

    @Override
    // No implementation for now
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean isDataExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ACUser WHERE username =?", new String[]{username});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    //Some caretakers to find at first app launch
    public void addAnimalCaretakers(SQLiteDatabase sqLiteDatabase) {
        SQLiteDatabase db = getWritableDatabase();
        Faker faker = new Faker();

        try {
            for (int i = 0; i < 20; i++) {
                ContentValues values = new ContentValues();
                values.put("username", faker.name().username());
                values.put("contacts", faker.phoneNumber().cellPhone());
                values.put("location", faker.address().city());
                values.put("fullnames", faker.name().fullName());
                values.put("experience", "Experience" + i);
                values.put("CBavailable", i % 2);

                db.insert("ACUser", null, values);
            }


        } catch (Exception e) {
            // Handle SQLiteException
            Log.e("Database", "Error adding First caretakers: " + e.getMessage());
        }
    }



    public void createTables(SQLiteDatabase sqLiteDatabase) {
        // AC means Animal Caretaker
        String qry = "CREATE TABLE IF NOT EXISTS user (username TEXT, email TEXT, password TEXT, request INT)";
        sqLiteDatabase.execSQL(qry);

        String qryACprofile = "CREATE TABLE IF NOT EXISTS ACUser (username TEXT, contacts TEXT, location TEXT, fullnames TEXT, experience TEXT, CBavailable INTEGER)";
        sqLiteDatabase.execSQL(qryACprofile);
    }

    public void saveProfile(String username, String contacts, String location, String fullnames, String experience, int CBavailable) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("fullnames", fullnames);
        values.put("contacts", contacts);
        values.put("location", location);
        values.put("experience", experience);
        values.put("CBavailable", CBavailable);

        db.insert("ACUser", null, values);
    }

    public void register(String username, String email, String password, String request) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("request", request);

        SQLiteDatabase db = getWritableDatabase();

        try {
            long result = db.insertOrThrow("user", null, cv);

            if (result != -1) {
                // Successful registration
                Toast.makeText(context, "Registration success", Toast.LENGTH_SHORT).show();
            } else {
                // Failed to insert data
                Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // Handle SQLiteException
            Log.e("Database", "Error during registration: " + e.getMessage());
        }
    }

    public int login(String username, String password) {
        int result = 0;
        String str[] = new String[]{username, password};
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor c = db.rawQuery("SELECT * FROM user WHERE username=? AND password=?", str)) {
            if (c.moveToFirst()) {
                result = 1; // Login successful
            }
        } catch (Exception e) {
            // Handle SQLiteException
            Log.e("Database", "Error during login: " + e.getMessage());
        }

        return result;
    }

    // Fetch AnimalCaretaker profile data
    public List<AcaretakerModel> getAcaretakerList() {
        List<AcaretakerModel> caretakerList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT * FROM ACUser", null)) {
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
        } catch (Exception e) {
            // Handle SQLiteException
            Log.e("Database", "Error fetching Animal caretaker list: " + e.getMessage());
        }

        return caretakerList;
    }
}
