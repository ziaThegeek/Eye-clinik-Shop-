package com.example.sirmubashar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class db_handler extends SQLiteOpenHelper {
    List<String> names, cnic, contact, image_url, value1, value2, value3, value4, value5, value6;
    Context context;

    public List<String> getNames() {
        return names;
    }

    public List<String> getCnic() {
        return cnic;
    }

    public List<String> getContact() {
        return contact;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public List<String> getValue1() {
        return value1;
    }

    public List<String> getValue2() {
        return value2;
    }

    public List<String> getValue3() {
        return value3;
    }

    public List<String> getValue4() {
        return value4;
    }

    public List<String> getValue5() {
        return value5;
    }

    public List<String> getValue6() {
        return value6;
    }

    public Context getContext() {
        return context;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public db_handler(Context context) {
        super(context, "farooqi", null, 1);
        names = new ArrayList<>();
        cnic = new ArrayList<>();
        contact = new ArrayList<>();
        value1 = new ArrayList<>();
        value2 = new ArrayList<>();
        value3 = new ArrayList<>();
        value4 = new ArrayList<>();
        value5 = new ArrayList<>();
        value6 = new ArrayList<>();
        image_url = new ArrayList<>();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + "details" + " (" +
                        "name" + " TEXT," +
                        "cnic" + " TEXT PRIMARY KEY," +
                        "contact" + " TEXT," +
                        "image_url" + " TEXT," +
                        "value1" + " TEXT," +
                        "value2" + " TEXT," +
                        "value3" + " TEXT," +
                        "value4" + " TEXT," +
                        "value5" + " TEXT," +
                        "value6" + " TEXT)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
        String SQL_CREATE_TABLE_LOGIN = "CREATE TABLE IF NOT EXISTS " + "LOGIN" + " (" +
                "Name" + " TEXT," +
                "Username" + " TEXT," +
                "Phone" + " TEXT," +
                "Remember" + " BOOLEAN," +
                "Password" + " TEXT)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_LOGIN);
//        Toast.makeText(context, "database table created", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert_data(String name, String cnic, String contact, String image_url, String value1, String value2

            , String value3, String value4, String value5, String value6
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("cnic", cnic);
        values.put("contact", contact);
        values.put("image_url", image_url);
        values.put("value1", value1);
        values.put("value2", value2);
        values.put("value3", value3);
        values.put("value4", value4);
        values.put("value5", value5);
        values.put("value6", value6);

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("details", null, values);
    }

    public void load_data() {
        SQLiteDatabase db = this.getReadableDatabase();
        String SEARCH_QUERY = "SELECT * FROM " + "details";
        Cursor cursor = db.rawQuery(SEARCH_QUERY, null);
//cursor.moveToFirst();
        while (cursor.moveToNext()) {
            names.add(cursor.getString(0));
            cnic.add(cursor.getString(1));
            contact.add(cursor.getString(2));
            image_url.add(cursor.getString(3));
            value1.add(cursor.getString(4));
            value2.add(cursor.getString(5));
            value3.add(cursor.getString(6));
            value4.add(cursor.getString(7));
            value5.add(cursor.getString(8));
            value6.add(cursor.getString(9));
        }
        cursor.close();
    }

    public void update(String name, String cnic, String contact, String image_url, String value1, String value2, String value3, String value4, String value5, String value6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("cnic", cnic);
        values.put("contact", contact);
        values.put("image_url", image_url);
        values.put("value1", value1);
        values.put("value2", value2);
        values.put("value3", value3);
        values.put("value4", value4);
        values.put("value5", value5);
        values.put("value6", value6);
        db.update("details", values, "cnic=?", new String[]{cnic});
    }

    public Cursor search(String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SEARCH_QUERY = "SELECT * FROM " + "details" + " WHERE " + "cnic" + " LIKE '%" + value + "%'" + " OR " + "name" + " LIKE '%" + value + "%'";
        Cursor cursor = db.rawQuery(SEARCH_QUERY, null);
        return cursor;
    }

    public Cursor search_fix(String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SEARCH_QUERY = "SELECT * FROM " + "details" + " WHERE " + "cnic" + "='" + value + "'";
        Cursor cursor = db.rawQuery(SEARCH_QUERY, null);
        return cursor;
    }

    public void delete(String cnic) {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete_query = "DELETE FROM " + "details" + " WHERE " + "cnic" + "='" + cnic + "'";
        db.execSQL(delete_query);
    }

    public void register(String name, String phone, String username, String password, boolean remember) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Phone", phone);
        contentValues.put("Username", username);
        contentValues.put("Password", password);
        contentValues.put("Remember", remember);
        long newRowId = db.insert("LOGIN", null, contentValues);

    }

    public void update_login(String username_old, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Password", password);
        values.put("Remember", false);
        db.update("LOGIN", values, "Username=?", new String[]{username_old});
    }

    public boolean validate_login() {
        SQLiteDatabase db = this.getReadableDatabase();
        String SEARCH_QUERY = "SELECT * FROM " + "LOGIN";
        Cursor cursor = db.rawQuery(SEARCH_QUERY, null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean find_duplicate(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SEARCH_QUERY = "SELECT * FROM " + "LOGIN" + " WHERE " + "Username" + "='" + username+ "' AND " + "Password" + "='" + password + "'";
        Cursor cursor = db.rawQuery(SEARCH_QUERY, null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public void set_remember(boolean remember, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Remember", remember);
        if (validate_login())
            db.update("LOGIN", values, "Username=?", new String[]{username});
    }

    public boolean get_remember() {
        SQLiteDatabase db = this.getReadableDatabase();
        String SEARCH_QUERY = "SELECT Remember FROM " + "LOGIN";
        Cursor cursor = db.rawQuery(SEARCH_QUERY, null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0)
            return false;
        else if (cursor.getInt(0) > 0)
            return true;
        else
            return false;
    }

    public String getUsername() {
        SQLiteDatabase db = this.getReadableDatabase();
        String SEARCH_QUERY = "SELECT Username FROM " + "LOGIN";
        Cursor cursor = db.rawQuery(SEARCH_QUERY, null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0)
            return " ";
        else
            return cursor.getString(0);
    }
    public  String getContact_no()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String SEARCH_QUERY = "SELECT Phone FROM " + "LOGIN";
        Cursor cursor = db.rawQuery(SEARCH_QUERY, null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0)
            return "";
        else
            return cursor.getString(0);

    }
}
