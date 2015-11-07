package com.ylqhust.bookmarks.data.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;

/**
 * Created by apple on 15/10/30.
 */
public class BookmarkHelper {
    private DatabaseHelper dbh;
    private BookmarkHelper(DatabaseHelper dbh){
        this.dbh = dbh;
    }
    private static BookmarkHelper bookmarkHelper = null;
    private static Object locked = new Object();
    private final String tableName = DatabaseModel.BOOKMARK.TABLE_NAME;
    private final String columns[] = {DatabaseModel.BOOKMARK.CN_ID,
            DatabaseModel.BOOKMARK.CN_USERID,
            DatabaseModel.BOOKMARK.CN_BELONGNODENUM,
            DatabaseModel.BOOKMARK.CN_BOOKMARKNUM,
            DatabaseModel.BOOKMARK.CN_ISPRIVATE,
            DatabaseModel.BOOKMARK.CN_ISBACKUPED,
            DatabaseModel.BOOKMARK.CN_ISDELETED,
            DatabaseModel.BOOKMARK.CN_ISMODIFIED,
            DatabaseModel.BOOKMARK.CN_ISSYNCED,
            DatabaseModel.BOOKMARK.CN_URL,
            DatabaseModel.BOOKMARK.CN_BOOKMARKDES,
            DatabaseModel.BOOKMARK.CN_TITLE,
            DatabaseModel.BOOKMARK.CN_SHORTCUTURL,
            DatabaseModel.BOOKMARK.CN_CREATETIME,
            DatabaseModel.BOOKMARK.CN_LASTMODIFYTIME};

    public static BookmarkHelper getInstance(DatabaseHelper dbh){
        synchronized (locked){
            if (bookmarkHelper == null){
                bookmarkHelper = new BookmarkHelper(dbh);
            }
            return bookmarkHelper;
        }
    }

    public boolean insert(Bookmark bookmark){
        String defaultValue = "null";
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_URL,bookmark.url);
        values.put(DatabaseModel.BOOKMARK.CN_USERID,bookmark.userID);
        values.put(DatabaseModel.BOOKMARK.CN_TITLE,bookmark.title);
        values.put(DatabaseModel.BOOKMARK.CN_SHORTCUTURL,bookmark.shortCutUrl);
        values.put(DatabaseModel.BOOKMARK.CN_BELONGNODENUM,bookmark.belongNodeNum);
        values.put(DatabaseModel.BOOKMARK.CN_BOOKMARKDES,bookmark.bookmarkDes);
        values.put(DatabaseModel.BOOKMARK.CN_BOOKMARKNUM,bookmark.bookmarkNum);
        values.put(DatabaseModel.BOOKMARK.CN_CREATETIME,bookmark.createTime);
        values.put(DatabaseModel.BOOKMARK.CN_LASTMODIFYTIME,bookmark.lastModifyTime);
        values.put(DatabaseModel.BOOKMARK.CN_ISBACKUPED,bookmark.isBackuped);
        values.put(DatabaseModel.BOOKMARK.CN_ISDELETED,bookmark.isDeleted);
        values.put(DatabaseModel.BOOKMARK.CN_ISMODIFIED,bookmark.isModified);
        values.put(DatabaseModel.BOOKMARK.CN_ISPRIVATE,bookmark.isPrivate);
        values.put(DatabaseModel.BOOKMARK.CN_ISSYNCED,bookmark.isSynced);
        return dbh.insert(tableName,defaultValue,values);
    }

    public boolean delete(Bookmark bookmark){
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.delete(tableName, selections, selectionArgs);
    }

    public boolean updateTitle(String newTitle,Bookmark bookmark){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_TITLE,newTitle);
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.update(tableName, values, selections, selectionArgs);
    }

    public boolean updateBelongNodeNum(int newBelongNodeNum,Bookmark bookmark){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_BELONGNODENUM,newBelongNodeNum);
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.update(tableName, values, selections, selectionArgs);
    }

    public boolean updateDes(String newDes,Bookmark bookmark){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_BOOKMARKDES,newDes);
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.update(tableName, values, selections, selectionArgs);
    }

    public boolean updateLastModifyTime(long time,Bookmark bookmark){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_LASTMODIFYTIME,time);
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public boolean updateIsBackuped(boolean statu,Bookmark bookmark){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_ISBACKUPED,statu);
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public boolean updateIsDeleted(boolean statu,Bookmark bookmark){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_ISDELETED,statu);
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public boolean updateIsPrivate(boolean statu,Bookmark bookmark){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_ISPRIVATE,statu);
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public boolean updateIsModified(boolean statu,Bookmark bookmark){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_ISMODIFIED,statu);
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public boolean updateIsSynced(boolean statu,Bookmark bookmark){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.BOOKMARK.CN_ISSYNCED,statu);
        String selections = DatabaseModel.BOOKMARK.CN_BOOKMARKNUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {bookmark.bookmarkNum+"",bookmark.userID};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public Cursor queryAll(String userID){
        String selections = DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {userID};
        return dbh.query(tableName, columns, selections, selectionArgs);
    }

    public Cursor queryOneNode(int nodeNum,String userID){
        String selections = DatabaseModel.BOOKMARK.CN_BELONGNODENUM+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {nodeNum+"",userID};
        return dbh.query(tableName,columns,selections,selectionArgs);
    }

    public Cursor queryPrivate(String userID){
        String selections = DatabaseModel.BOOKMARK.CN_ISPRIVATE+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {"1",userID};
        return dbh.query(tableName,columns,selections,selectionArgs);
    }

    public Cursor queryNotBackuped(String userID){
        String selections = DatabaseModel.BOOKMARK.CN_ISBACKUPED+"=? AND "+ DatabaseModel.BOOKMARK.CN_USERID+"=?";
        String selectionArgs[] = {"0",userID};
        return dbh.query(tableName,columns,selections,selectionArgs);
    }

    public Bookmark getBookmark(Cursor cs){
        Bookmark bookmark = new Bookmark(cs.getInt(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_BELONGNODENUM)),
                new String(cs.getBlob(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_BOOKMARKDES))),
                cs.getInt(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_BOOKMARKNUM)),
                cs.getLong(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_CREATETIME)),
                cs.getInt(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_ISBACKUPED)) == 1 ? true:false,
                cs.getInt(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_ISDELETED)) == 1 ? true:false,
                cs.getInt(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_ISMODIFIED)) == 1 ? true:false,
                cs.getInt(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_ISPRIVATE)) == 1 ? true:false,
                cs.getInt(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_ISSYNCED)) == 1 ? true:false,
                cs.getLong(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_LASTMODIFYTIME)),
                cs.getString(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_SHORTCUTURL)),
                cs.getString(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_TITLE)),
                cs.getString(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_URL)),
                cs.getString(cs.getColumnIndex(DatabaseModel.BOOKMARK.CN_USERID)));
        return bookmark;
    }
}
