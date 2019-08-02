package com.otemainc.foodfuzzapp.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "foodfuzz.db";
    private static final String SQL_CREATE_USERS_TABLE =  "CREATE TABLE tbl_users (" +
            "name varchar(100) NOT NULL," +
            "email varchar(60) NOT NULL);";
    private static final String SQL_CREATE_CART_TABLE =  "CREATE TABLE tbl_cart_items (" +
            "id int(11) NOT NULL," +
            "name varchar(100) NOT NULL," +
            "amount varchar(60) NOT NULL," +
            "supplier varchar(60) NOT NULL,"+
            "quantity int(11) NOT NULL);";
    private static final String SQL_CREATE_SAVED_TABLE =  "CREATE TABLE tbl_saved_items (" +
            "id int(11) NOT NULL," +
            "name varchar(100) NOT NULL," +
            "amount varchar(60) NOT NULL," +
            "supplier varchar(60) NOT NULL,"+
            "quantity int(11) NOT NULL);";

    private static final String SQL_DELETE_USERS_TABLE = "DROP TABLE IF EXISTS tbl_users";
    private static final String SQL_DELETE_CART_TABLE = "DROP TABLE IF EXISTS tbl_cart_items";
    private static final String SQL_DELETE_SAVED_TABLE = "DROP TABLE IF EXISTS tbl_saved_items";

    public Db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_CART_TABLE);
        db.execSQL(SQL_CREATE_SAVED_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_USERS_TABLE);
        db.execSQL(SQL_DELETE_CART_TABLE);
        db.execSQL(SQL_DELETE_SAVED_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public boolean addUser(String name, String email){
        boolean added;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
       contentValue.put("name",name);
       contentValue.put("email",email);
        long result = db.insert("tbl_users",null,contentValue);
        if(result==-1){
            added=false;
        }else{
            added=true;
        }
        return added;
    }
    public boolean addToCart(int id, String name, String amount, String supplier, int quantity){
        boolean addedCart;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put("id",id);
        contentValue.put("name",name);
        contentValue.put("amount",amount);
        contentValue.put("supplier",supplier);
        contentValue.put("quantity",quantity);
        long result = db.insert("tbl_users",null,contentValue);
        if(result==-1){
            addedCart=false;
        }else{
            addedCart=true;
        }
        return addedCart;
    }
        public boolean updateCart(int id, String name, String amount, String supplier, int quanity){
                boolean updated = false;
        return updated;
    }
}
