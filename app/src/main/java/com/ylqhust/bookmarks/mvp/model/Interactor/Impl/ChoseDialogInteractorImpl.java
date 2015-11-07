package com.ylqhust.bookmarks.mvp.model.Interactor.Impl;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.Interactor.Interface.ChoseDialogInteractor;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnCDFL;

import java.util.List;

/**
 * Created by apple on 15/11/3.
 */
public class ChoseDialogInteractorImpl implements ChoseDialogInteractor {
    /**
     * 删除一个书签
     * @param parentNode
     * @param bookmark
     * @param dbh
     * @param listener
     * @param headNode
     * @param headBookmark
     */
    @Override
    public void delete(Node parentNode, Bookmark bookmark, DatabaseHelper dbh, OnCDFL listener, List<Node> headNode, List<Bookmark> headBookmark) {
        dbh.getBookmarkHelper().delete(bookmark);
        if (parentNode != null){
            parentNode.containBM.remove(bookmark);
            listener.onDeleteFinished(parentNode.containNode,parentNode.containBM);
        }
        else{
            headBookmark.remove(bookmark);
            listener.onDeleteFinished(headNode,headBookmark);
        }

    }
}
