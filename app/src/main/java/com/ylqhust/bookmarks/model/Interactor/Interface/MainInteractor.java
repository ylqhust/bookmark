package com.ylqhust.bookmarks.model.Interactor.Interface;

import android.content.Context;

import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.presenter.Impl.MainPresenterImpl;
import com.ylqhust.bookmarks.presenter.Listener.OnMainFinishListener;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface MainInteractor {
    public void LoadDatabase(DatabaseHelper dbh,OnMainFinishListener listener);
    public void onHomeTab(OnMainFinishListener listener);
    public void onPublicTab(OnMainFinishListener listener);
    public void onPeopleTab(OnMainFinishListener listener);
    public void addBookmark(Context context,OnMainFinishListener listener);
    public void showSearchView(DatabaseHelper dbh, OnMainFinishListener listener);

    void clearOneHistory(int position, List<String> strings, DatabaseHelper dbh, OnMainFinishListener listener);

    void clearAllHistory(DatabaseHelper dbh, OnMainFinishListener listener);

}
