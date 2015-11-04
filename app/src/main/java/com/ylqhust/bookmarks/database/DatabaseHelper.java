package com.ylqhust.bookmarks.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by apple on 15/10/30.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public final String TAG = "DatabaseHelper";
    private Object locked = new Object();
    private SQLiteDatabase db;
    private NodeHelper nodeHelper;
    private BookmarkHelper bookmarkHelper;
    private UserHelper userHelper;
    private SearchHisHelper searchHisHelper;

    public DatabaseHelper(Context context,int Version)
    {
        super(context, DatabaseModel.DB_NAME, null, Version);
        db = this.getWritableDatabase();
        nodeHelper = NodeHelper.getInstance(this);
        bookmarkHelper = BookmarkHelper.getInstance(this);
        userHelper = UserHelper.getInstance(this);
        searchHisHelper = SearchHisHelper.getInstance(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseModel.NODE.SQL_CREATE_TABLE);
        db.execSQL(DatabaseModel.BOOKMARK.SQL_CREATE_TABLE);
        db.execSQL(DatabaseModel.USER.SQL_CREATE_TABLE);
        db.execSQL(DatabaseModel.SEARCHHISTORY.SQL_CREATE_TABLE);
        Log.i(TAG, "TABLE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insert(String tableName,
                          String defaultValue,
                          ContentValues values) {
        synchronized (locked){
            try{
                db.insert(tableName, defaultValue, values);
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

    }

    public boolean delete(String tableName,
                          String selections,
                          String selectionArgs[]){
        synchronized (locked){
            try{
                db.delete(tableName, selections, selectionArgs);
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

    }

    public boolean update(String tabelName,
                          ContentValues values,
                          String selections,
                          String selectionArgs[]){
        synchronized (locked){
            try{
                db.update(tabelName, values, selections, selectionArgs);
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

    }

    public Cursor query(String tableName,
                        String columns[],
                        String selections,
                        String selectionArgs[]) {
        synchronized (locked){
            try {
                return db.query(tableName,columns,selections,selectionArgs,null,null,null);
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

    }

    public NodeHelper getNodeHelper(){return nodeHelper;}
    public BookmarkHelper getBookmarkHelper(){return bookmarkHelper;}
    public UserHelper getUserHelper(){return userHelper;}
    public SearchHisHelper getSearchHisHelper(){return searchHisHelper;}
}
