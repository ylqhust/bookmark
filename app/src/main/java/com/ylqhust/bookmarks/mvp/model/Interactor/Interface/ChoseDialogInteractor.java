package com.ylqhust.bookmarks.mvp.model.Interactor.Interface;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnCDFL;

import java.util.List;

/**
 * Created by apple on 15/11/3.
 */
public interface ChoseDialogInteractor {
    void delete(Node parentNode, Bookmark bookmark, DatabaseHelper dbh, OnCDFL listener, List<Node> headNode, List<Bookmark> headBookmark);
}
