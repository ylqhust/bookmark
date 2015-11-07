package com.ylqhust.bookmarks.data.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ylqhust.bookmarks.utils.Utils;
import com.ylqhust.bookmarks.mvp.model.dataModel.SearchHistory;
import com.ylqhust.bookmarks.utils.Compare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 15/11/2.
 */
public class SearchHisHelper {
    private DatabaseHelper dbh;
    private SearchHisHelper(DatabaseHelper dbh){
        this.dbh = dbh;
    }
    private static SearchHisHelper searchHisHelper = null;
    private static Object locked = new Object();
    private final String tableName = DatabaseModel.SEARCHHISTORY.TABLE_NAME;
    String columns[] = {DatabaseModel.SEARCHHISTORY.CN_ID,
            DatabaseModel.SEARCHHISTORY.CN_STRING,
            DatabaseModel.SEARCHHISTORY.CN_USERID,
            DatabaseModel.SEARCHHISTORY.CN_FIRSTSEARCHTIME,
            DatabaseModel.SEARCHHISTORY.CN_LASTSEARCHTIME,
            DatabaseModel.SEARCHHISTORY.CN_COUNT};

    public static SearchHisHelper getInstance(DatabaseHelper dbh){
        synchronized (locked){
            if (searchHisHelper == null){
                searchHisHelper = new SearchHisHelper(dbh);
            }
            return searchHisHelper;
        }
    }

    private boolean insert(SearchHistory sh){
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.SEARCHHISTORY.CN_STRING,sh.string);
        values.put(DatabaseModel.SEARCHHISTORY.CN_USERID,sh.userid);
        values.put(DatabaseModel.SEARCHHISTORY.CN_FIRSTSEARCHTIME,sh.searchTime);
        values.put(DatabaseModel.SEARCHHISTORY.CN_LASTSEARCHTIME,sh.searchTime);
        values.put(DatabaseModel.SEARCHHISTORY.CN_COUNT, 1);
        return dbh.insert(tableName, null, values);
    }

    /**
     * 根据给定的userid和string，删除此用户的搜索数据
     * @param userid
     * @param string
     * @return
     */
    public boolean delete(String userid, String string){
        String selections = DatabaseModel.SEARCHHISTORY.CN_USERID+"=? AND "+ DatabaseModel.SEARCHHISTORY.CN_STRING+"=?";
        String selectionArgs[] = {userid,string};
        return dbh.delete(tableName, selections, selectionArgs);
    }

    /**
     * 清空此用户的所有搜索数据
     * @param userid
     * @return
     */
    public boolean deleteAll(String userid){
        String selections = DatabaseModel.SEARCHHISTORY.CN_USERID+"=?";
        String selectionArgs[] = {userid};
        return dbh.delete(tableName, selections, selectionArgs);
    }

    /**
     * 当数据库存在相同的搜索数据时，更新搜索数据，将数据被搜索次数家1，最后搜索时间改变
     * 否则插入数据
     * @param sh
     * @return
     */
    public boolean updateEntity(SearchHistory sh){
        int count = getCount(sh);
        if (count == 0){
            return insert(sh);
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.SEARCHHISTORY.CN_COUNT,count+1);
        values.put(DatabaseModel.SEARCHHISTORY.CN_LASTSEARCHTIME,sh.searchTime);
        String selections = DatabaseModel.SEARCHHISTORY.CN_USERID+"=? AND "+ DatabaseModel.SEARCHHISTORY.CN_STRING+"=?";
        String selectionArgs[] = {sh.userid,sh.string};
        return dbh.update(tableName, values, selections, selectionArgs);
    }


    private int getCount(SearchHistory sh){
        Cursor cs = queryCount(sh);
        if (cs.moveToNext()){
            return cs.getInt(cs.getColumnIndex(DatabaseModel.SEARCHHISTORY.CN_COUNT));
        }
        return 0;
    }
    private Cursor queryCount(SearchHistory sh){
        String selections = DatabaseModel.SEARCHHISTORY.CN_USERID+"=? AND "+ DatabaseModel.SEARCHHISTORY.CN_STRING+"=?";
        String selectionArgs[] = {sh.userid,sh.string};
        return dbh.query(tableName, columns, selections, selectionArgs);
    }

    /**
     * 根据给定的userId,返回此用户的所有搜索结果
     * @param userid
     * @return
     */
    private Cursor query(String userid){
        String selections = DatabaseModel.SEARCHHISTORY.CN_USERID+"=?";
        String selectionArgs[] = {userid};
        return dbh.query(tableName, columns, selections, selectionArgs);
    }

    public List<String> getHistory(String userid){
        Cursor cs = query(userid);
        //List<SearchHistory> searchHistories = new ArrayList<SearchHistory>();
        SearchHistory[] searchHistories = new SearchHistory[cs.getCount()];
        int i=0;
        while(cs.moveToNext()){
            searchHistories[i] = getSH(cs);
            i++;
        }
        return sortByCount(searchHistories);
    }

    private List<String> sortByCount(SearchHistory[] searchHistories) {
        Utils.sort(searchHistories, new Compare() {
            @Override
            public boolean compare(Object o1, Object o2) {
                return ((SearchHistory)o1).count < ((SearchHistory)o2).count;
            }
        });

        List<String> string = new ArrayList<String>();
        for (SearchHistory sh : searchHistories){
            string.add(sh.string);
        }
        return string;
    }

    private SearchHistory getSH(Cursor cs){
        SearchHistory sh = new SearchHistory(cs.getString(
                cs.getColumnIndex(DatabaseModel.SEARCHHISTORY.CN_STRING)
        ),cs.getInt(
                cs.getColumnIndex(DatabaseModel.SEARCHHISTORY.CN_COUNT)
        ));
        return sh;
    }


}
