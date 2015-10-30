package com.ylqhust.bookmarks.database;


import android.content.ContentValues;
import android.database.Cursor;

import com.ylqhust.bookmarks.model.dataModel.Node;

/**
 * Created by apple on 15/10/30.
 */
public class NodeHelper {
    private DatabaseHelper dbh;
    private NodeHelper(DatabaseHelper dbh){
        this.dbh = dbh;
    }
    private static NodeHelper nodeHelper = null;
    private static Object locked = new Object();
    private final String tableName = DatabaseModel.NODE.TABLE_NAME;
    String columns[] = {DatabaseModel.NODE.CN_ID,
            DatabaseModel.NODE.CN_USERID,
            DatabaseModel.NODE.CN_CREATETIME,
            DatabaseModel.NODE.CN_LASTMODIFYTIME,
            DatabaseModel.NODE.CN_NUM,
            DatabaseModel.NODE.CN_NODEDES,
            DatabaseModel.NODE.CN_NODENAME,
            DatabaseModel.NODE.CN_PRENODENUM};

    public static NodeHelper getInstance(DatabaseHelper dbh){
        synchronized (locked){
            if (nodeHelper == null){
                nodeHelper = new NodeHelper(dbh);
            }
            return nodeHelper;
        }
    }

    public boolean insert(Node node){
        String defaultValue = "null";
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.NODE.CN_NODENAME,node.nodeName);
        values.put(DatabaseModel.NODE.CN_USERID,node.userID);
        values.put(DatabaseModel.NODE.CN_CREATETIME,node.createTime);
        values.put(DatabaseModel.NODE.CN_LASTMODIFYTIME,node.lastModifyTime);
        values.put(DatabaseModel.NODE.CN_NUM,node.nodeNum);
        values.put(DatabaseModel.NODE.CN_PRENODENUM,node.preNodeNum);
        values.put(DatabaseModel.NODE.CN_NODEDES,node.nodeDes);
        return dbh.insert(tableName,defaultValue,values);
    }

    public boolean delete(Node node){
        String selections = DatabaseModel.NODE.CN_NUM+"=? AND "+ DatabaseModel.NODE.CN_USERID+"=?";
        String selectionArgs[] = {node.nodeNum+"",node.userID};
        return dbh.delete(tableName, selections, selectionArgs);
    }

    public boolean updateNodeName(String newName,Node node){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.NODE.CN_NODENAME,newName);
        String selections = DatabaseModel.NODE.CN_NUM+"=? AND "+ DatabaseModel.NODE.CN_USERID+"=?";
        String selectionArgs[] = {node.nodeNum+"",node.userID};
        return dbh.update(tableName, values, selections, selectionArgs);
    }

    public boolean updatePreNodeNum(int newNum,Node node){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.NODE.CN_NUM,newNum);
        String selections = DatabaseModel.NODE.CN_NUM+"=? AND "+ DatabaseModel.NODE.CN_USERID+"=?";
        String selectionArgs[] = {node.nodeNum+"",node.userID};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public boolean updateNodeDes(String newDes,Node node){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.NODE.CN_NODEDES,newDes);
        String selections = DatabaseModel.NODE.CN_NUM+"=? AND "+ DatabaseModel.NODE.CN_USERID+"=?";
        String selectionArgs[] = {node.nodeNum+"",node.userID};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public boolean updateLastModifyTime(long time,Node node){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.NODE.CN_LASTMODIFYTIME,time);
        String selections = DatabaseModel.NODE.CN_NUM+"=? AND "+ DatabaseModel.NODE.CN_USERID+"=?";
        String selectionArgs[] = {node.nodeNum+"",node.userID};
        return dbh.update(tableName,values,selections,selectionArgs);
    }

    public Cursor queryAll(String userID){
        String selections = DatabaseModel.NODE.CN_USERID+"=?";
        String selectionArgs[] = {userID};
        return dbh.query(tableName, columns, selections, selectionArgs);
    }

    public Node getNode(Cursor cs){
        Node node = new Node(cs.getString(cs.getColumnIndex(DatabaseModel.NODE.CN_USERID)),
                cs.getString(cs.getColumnIndex(DatabaseModel.NODE.CN_NODENAME)),
                cs.getInt(cs.getColumnIndex(DatabaseModel.NODE.CN_NUM)),
                cs.getLong(cs.getColumnIndex(DatabaseModel.NODE.CN_CREATETIME)),
                cs.getLong(cs.getColumnIndex(DatabaseModel.NODE.CN_LASTMODIFYTIME)),
                new String(cs.getBlob(cs.getColumnIndex(DatabaseModel.NODE.CN_NODEDES))),
                cs.getInt(cs.getColumnIndex(DatabaseModel.NODE.CN_PRENODENUM)));
        return node;
    }
}
