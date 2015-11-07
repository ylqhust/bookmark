package com.ylqhust.bookmarks.mvp.model.Interactor.Interface;

import android.content.Context;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.presenter.Impl.MainPresenterImpl;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnMainFinishListener;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface MainInteractor {
    public void LoadDatabase(DatabaseHelper dbh,OnMainFinishListener listener);
    public void addBookmark(Context context,OnMainFinishListener listener);
    public void showSearchView(DatabaseHelper dbh, OnMainFinishListener listener);

    void clearOneHistory(int position, List<String> strings, DatabaseHelper dbh, OnMainFinishListener listener);

    void clearAllHistory(DatabaseHelper dbh, OnMainFinishListener listener);

    void send(OnMainFinishListener listener);

    void deleteAllSelect(OnMainFinishListener listener);

    void move(OnMainFinishListener listener);

    void readyMove(OnMainFinishListener listener);
}
