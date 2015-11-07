package com.ylqhust.bookmarks.mvp.presenter.Interface;


import android.content.Context;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface MainPresenter {
    public void LoadDatabase(DatabaseHelper dbh);

    public void addBookmark(Context context);
    public void showSearchView(DatabaseHelper dbh);

    void clearOneHistory(int position, List<String> strings, DatabaseHelper dbh);

    void clearAllHistory(DatabaseHelper dbh);

    void cancle(boolean isReadyMove);

    void HomeTab();

    void PublicTab();

    void PeopleTab();


    void send();

    void deleteAllSelect();

    void move();

    void readyMove();

    void LongClickHappend();

}
