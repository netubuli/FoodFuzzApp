package com.otemainc.foodfuzzapp.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.otemainc.foodfuzzapp.R;

public class Db extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "foodfuzz.db";
    private static final String SQL_CREATE_USERS_TABLE =  "CREATE TABLE tbl_users (" +
            "id int(11) NOT NULL," +
            "name varchar(100) NOT NULL," +
            "tel varchar(12) NOT NULL," +
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
    public boolean addUser( int id, String name,String tel, String email){
        boolean added;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put("id",id);
       contentValue.put("name",name);
       contentValue.put("tel",tel);
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
        long result = db.insert("tbl_cart_items",null,contentValue);
        if(result==-1){
            addedCart=false;
        }else{
            addedCart=true;
        }
        return addedCart;
    }
    public boolean updateCart(String id, String name, double amount, String supplier, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put("id",id);
        contentValue.put("name",name);
        contentValue.put("amount",amount);
        contentValue.put("supplier",supplier);
        contentValue.put("quantity",quantity);
        db.update("tbl_cart_items",contentValue,"id=?",new String[]{id});
        return true;
    }
    public Cursor getAllCartItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from tbl_cart_items",null);
        return  res;

    }
    public String getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT id from tbl_users",null);
        String id = null;
        if (res.getCount() > 0) {
            res.moveToFirst();
            id = res.getString(1);
            res.close();
        }
        return id;
    }
    public int deleteItem(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tbl_cart_items","id=?",new String[]{id});
    }
    public void clearCart(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from tbl_cart_items");
    }
}
