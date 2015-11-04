package com.ylqhust.bookmarks.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ylqhust.bookmarks.model.dataModel.User;

/**
 * Created by apple on 15/10/30.
 */
public class UserHelper {
    private DatabaseHelper dbh;
    private UserHelper(DatabaseHelper dbh){
        this.dbh = dbh;
    }
    private static UserHelper userHelper = null;
    private static Object locked = new Object();
    private final String tableName = DatabaseModel.USER.TABLE_NAME;
    String columns[] = {DatabaseModel.USER.CN_ID,
            DatabaseModel.USER.CN_USERID,
            DatabaseModel.USER.CN_PASSWORD,
            DatabaseModel.USER.CN_KEY};

    public static UserHelper getInstance(DatabaseHelper dbh){
        synchronized (locked){
            if (userHelper == null){
                userHelper = new UserHelper(dbh);
            }
            return userHelper;
        }
    }


    public boolean insert(User user){
        String defaultValue = "null";
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.USER.CN_USERID,user.userId);
        values.put(DatabaseModel.USER.CN_PASSWORD,user.password);
        values.put(DatabaseModel.USER.CN_KEY,user.key);
        return dbh.insert(tableName,defaultValue,values);
    }

    public boolean delete(User user){
        String selections = DatabaseModel.USER.CN_USERID+"=?";
        String selectionArgs[] = {user.userId};
        return dbh.delete(tableName, selections, selectionArgs);
    }

    public boolean updateKey(String newKey,User user){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.USER.CN_KEY,newKey);
        String selections = DatabaseModel.USER.CN_USERID+"=?";
        String selectionArgs[] = {user.userId};
        return dbh.update(tableName, values, selections, selectionArgs);
    }

    public boolean updatePassword(String newPassword,User user){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.USER.CN_PASSWORD,newPassword);
        String selections = DatabaseModel.USER.CN_PASSWORD+"=?";
        String selectionArgs[] = {user.userId};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public Cursor query(){
        return dbh.query(tableName, columns, null, null);
    }
    public String getUserId(){
        String userId = "STRANGER";
        Cursor cs = dbh.getUserHelper().query();
        if (cs.moveToNext()){
            userId = cs.getString(cs.getColumnIndex(DatabaseModel.USER.CN_USERID));
        }
        return userId;
    }
}
