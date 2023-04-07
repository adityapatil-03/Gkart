package com.example.gkart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class cart_database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Cart.db";
    public static final String TABLE_NAME = "Product_Table";
    public static final String col_1 = "ID";
    public static final String col_2 = "NAME";
    public static final String col_3 = "PRICE";
    public static final String col_4 = "IMAGE";
    public static final String col_5 = "QUANTITY";
    public static final String col_6 = "CATEGORY";

    public cart_database(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sq) {
        sq.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PRICE TEXT,IMAGE TEXT,QUANTITY INTEGER,CATEGORY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sq, int i, int i1) {
        sq.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sq);

    }

    public boolean insert(String name,String price,String image,int quantity,String category){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_2,name);
        cv.put(col_3,price);
        cv.put(col_4,image);
        cv.put(col_5,quantity);
        cv.put(col_6,category);
        long x = sq.insert(TABLE_NAME,null,cv);
        if(x==-1){
            return false;
        }
        return true;
    }

    public Cursor getdata(){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor c = sq.rawQuery("select * from " + TABLE_NAME,null);
        return c;
    }

    public Cursor search(String id){
        SQLiteDatabase sq = this.getWritableDatabase();
        String query = "select * from " + TABLE_NAME +  " where ID=\""+ id + "\"";
        Cursor cs = sq.rawQuery(query, null);
        return cs;
    }

    public boolean search_name(String name,String d){
        SQLiteDatabase sq = this.getWritableDatabase();
        String query = "select * from " + TABLE_NAME +  " where NAME=\""+ name + "\"";
        Cursor cs = sq.rawQuery(query, null);
        String s = "";
        while (cs.moveToNext()){
            s = cs.getString(1);
        }
        if(s.length()==0){
            return false;
        }
        Log.d("pranav", "Cartclick: " + s);
        return true;
    }

    public Integer delete(String id){
        SQLiteDatabase sq = this.getWritableDatabase();
        return sq.delete(TABLE_NAME,"ID = ?",new String[] {id});
    }

    public boolean update(String id,int quantity){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put(col_2,name);
//        cv.put(col_3,phone);
//        cv.put(col_4,email);
        cv.put(col_5,quantity);

        sq.update(TABLE_NAME,cv,"ID = ?",new String[] {id});
        return true;
    }

    public Integer deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,null,null);
    }

}
