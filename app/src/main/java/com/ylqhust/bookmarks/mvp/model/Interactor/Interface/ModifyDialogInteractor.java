package com.ylqhust.bookmarks.mvp.model.Interactor.Interface;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnMDFL;

import java.util.List;

/**
 * Created by apple on 15/11/4.
 */
public interface ModifyDialogInteractor {
    void modify(String newname, Node parentNode, Bookmark bookmark, List<Node> headNode, List<Bookmark> headBookmark, DatabaseHelper databaseHelper, OnMDFL listener);
}
