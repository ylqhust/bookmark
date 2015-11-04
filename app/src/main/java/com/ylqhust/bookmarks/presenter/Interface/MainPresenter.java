package com.ylqhust.bookmarks.presenter.Interface;


import android.content.Context;

import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.ui.MainActivity;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface MainPresenter {
    public void HomeTab();
    public void PublicTab();
    public void PeopleTab();
    public void LoadDatabase(DatabaseHelper dbh);

    public void addBookmark(Context context);
    public void showSearchView(DatabaseHelper dbh);

    void clearOneHistory(int position, List<String> strings, DatabaseHelper dbh);

    void clearAllHistory(DatabaseHelper dbh);

}
