package com.ylqhust.bookmarks.mvp.presenter.Impl;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.Interactor.Impl.ModifyDialogInteractorImpl;
import com.ylqhust.bookmarks.mvp.model.Interactor.Interface.ModifyDialogInteractor;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Interface.ModifyDialogPresenter;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnMDFL;
import com.ylqhust.bookmarks.mvp.view.ModifyDialogView;

import java.util.List;

/**
 * Created by apple on 15/11/4.
 */
public class ModifyDialogPresenterImpl implements ModifyDialogPresenter,OnMDFL {
    private ModifyDialogView modifyDialogView;
    private ModifyDialogInteractor interactor;

    public ModifyDialogPresenterImpl(ModifyDialogView modifyDialogView){
        this.modifyDialogView = modifyDialogView;
        this.interactor = new ModifyDialogInteractorImpl();
    }

    @Override
    public void modify(String newname, Node parentNode, Bookmark bookmark, List<Node> headNode, List<Bookmark> headBookmark, DatabaseHelper databaseHelper) {
        interactor.modify(newname,parentNode,bookmark,headNode,headBookmark,databaseHelper,this);
    }


    @Override
    public void onModifyFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark) {
        modifyDialogView.dismiss();
        modifyDialogView.onModifyFinished(currentPageNode,currentPageBookmark);
    }
}
