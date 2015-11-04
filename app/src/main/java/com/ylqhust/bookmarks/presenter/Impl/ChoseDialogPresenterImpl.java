package com.ylqhust.bookmarks.presenter.Impl;

import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.model.Interactor.Impl.ChoseDialogInteractorImpl;
import com.ylqhust.bookmarks.model.Interactor.Interface.ChoseDialogInteractor;
import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;
import com.ylqhust.bookmarks.presenter.Interface.ChoseDialogPresenter;
import com.ylqhust.bookmarks.presenter.Listener.OnCDFL;
import com.ylqhust.bookmarks.view.ChoseDialogView;

import java.util.List;

/**
 * Created by apple on 15/11/3.
 */
public class ChoseDialogPresenterImpl implements ChoseDialogPresenter ,OnCDFL{
    private ChoseDialogView choseDialogView;
    private ChoseDialogInteractor interactor;

    public ChoseDialogPresenterImpl(ChoseDialogView choseDialogView){
        this.choseDialogView = choseDialogView;
        this.interactor = new ChoseDialogInteractorImpl();
    }

    /**
     * 删除一个书签
     * @param parentNode
     * @param bookmark
     * @param dbh
     * @param headNode
     * @param headBookmark
     */

    @Override
    public void delete(Node parentNode, Bookmark bookmark, DatabaseHelper dbh, List<Node> headNode, List<Bookmark> headBookmark) {
        interactor.delete(parentNode,bookmark,dbh,this,headNode , headBookmark);
    }

    /**
     *  @param parentNode
     * @param bookmark
     * @param headNode
     * @param headBookmark
     */

    @Override
    public void modify(Node parentNode, Bookmark bookmark, List<Node> headNode, List<Bookmark> headBookmark) {
        choseDialogView.dismiss();
        choseDialogView.showMoidfyDialog(parentNode,bookmark, headBookmark,headNode);
    }

    /**
     * 书签删除完成
     * @param currentPageNode
     * @param currentPageBookmark
     */
    @Override
    public void onDeleteFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark) {
        choseDialogView.dismiss();
        choseDialogView.onDeleteFinished(currentPageNode,currentPageBookmark);
    }
}
