package com.ylqhust.bookmarks.presenter.Interface;


import com.ylqhust.bookmarks.database.DatabaseHelper;

/**
 * Created by apple on 15/10/29.
 */
public interface MainPresenter {
    public void HomeTab();
    public void PublicTab();
    public void PeopleTab();
    public void LoadDatabase(DatabaseHelper dbh);
}
