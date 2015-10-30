package com.ylqhust.bookmarks.model.Interactor.Interface;

import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.presenter.Listener.OnMainFinishListener;

/**
 * Created by apple on 15/10/29.
 */
public interface MainInteractor {
    public void HomeTabEvent(OnMainFinishListener listener);
    public void PublicTabEvent(OnMainFinishListener listener);
    public void PeopleTabEvent(OnMainFinishListener listener);
    public void LoadDatabase(DatabaseHelper dbh,OnMainFinishListener listener);
}
