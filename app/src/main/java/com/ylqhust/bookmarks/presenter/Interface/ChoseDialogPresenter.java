package com.ylqhust.bookmarks.presenter.Interface;

import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/11/3.
 */
public interface ChoseDialogPresenter {
    void delete(Node parentNode, Bookmark bookmark, DatabaseHelper dbh, List<Node> headNode, List<Bookmark> headBookmark);

    void modify(Node parentNode, Bookmark bookmark, List<Node> headNode, List<Bookmark> headBookmark);

}
