package com.ylqhust.bookmarks.mvp.model.Interactor.Impl;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.Interactor.Interface.ModifyDialogInteractor;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnMDFL;

import java.util.List;

/**
 * Created by apple on 15/11/4.
 */
public class ModifyDialogInteractorImpl implements ModifyDialogInteractor {
    @Override
    public void modify(String newname, Node parentNode, Bookmark bookmark, List<Node> headNode, List<Bookmark> headBookmark, DatabaseHelper databaseHelper, OnMDFL listener) {
        if (newname == null || newname.equals(""))
            newname = "未命名";
        databaseHelper.getBookmarkHelper().updateTitle(newname,bookmark);
        databaseHelper.getBookmarkHelper().updateIsBackuped(false,bookmark);
        databaseHelper.getBookmarkHelper().updateIsModified(true,bookmark);
        bookmark.title = newname;
        if (parentNode == null){
            listener.onModifyFinished(headNode,headBookmark);
        }
        else{
            listener.onModifyFinished(parentNode.containNode,parentNode.containBM);
        }
    }
}
