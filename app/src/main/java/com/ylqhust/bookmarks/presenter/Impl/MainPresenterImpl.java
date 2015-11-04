package com.ylqhust.bookmarks.presenter.Impl;

import android.content.Context;

import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.model.Interactor.Interface.MainInteractor;
import com.ylqhust.bookmarks.model.Interactor.Impl.MainInteractorImpl;
import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;
import com.ylqhust.bookmarks.presenter.Interface.MainPresenter;
import com.ylqhust.bookmarks.presenter.Listener.OnMainFinishListener;
import com.ylqhust.bookmarks.view.MainView;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public class MainPresenterImpl implements MainPresenter,OnMainFinishListener {



    private MainView mainView;
    private MainInteractor interactor;

    public MainPresenterImpl(MainView mainView)
    {
        this.mainView = mainView;
        this.interactor = new MainInteractorImpl();
    }
    @Override
    public void HomeTab() {
        mainView.selectTab(0);
        interactor.onHomeTab(this);
    }

    @Override
    public void PublicTab() {
        mainView.selectTab(1);
        interactor.onPublicTab(this);

    }

    @Override
    public void PeopleTab() {
        mainView.selectTab(2);
        interactor.onPeopleTab(this);
    }

    /**
     * 加载数据库
     * @param dbh
     */
    @Override
    public void LoadDatabase(DatabaseHelper dbh) {
        mainView.loading();
        interactor.LoadDatabase(dbh, this);
    }

    /**
     *
     */
    @Override
    public void addBookmark(Context context) {
        interactor.addBookmark(context,this);
    }

    /**
     * 显示搜索dialog
     * @param dbh
     */
    @Override
    public void showSearchView(DatabaseHelper dbh) {
        interactor.showSearchView(dbh, this);
    }

    /**
     * 清除一条历史记录
     * @param position
     * @param strings
     * @param dbh
     */
    @Override
    public void clearOneHistory(int position, List<String> strings, DatabaseHelper dbh) {
        interactor.clearOneHistory(position,strings, dbh, this);
    }

    /**
     * 清除所有历史记录
     * @param dbh
     */
    @Override
    public void clearAllHistory(DatabaseHelper dbh) {
        interactor.clearAllHistory(dbh,this);
    }


    @Override
    public void LoadFinish(List<Node> headNode, List<Bookmark> headBookmark) {
        mainView.LoadFinished(headNode,headBookmark);
    }

    @Override
    public void onHomeTabFinished() {
        mainView.onHomeTabFinished();
    }

    @Override
    public void onPublicTabFinished() {
        mainView.onPublicTabFinished();
    }

    @Override
    public void onPeopleTabFinished() {
        mainView.onPeopleTabFinished();
    }

    @Override
    public void addBookmarkFinished(String guessUrl, String guessTitle, String shortcuturl) {
        mainView.showBookmarkInputDialog(guessUrl,guessTitle,shortcuturl);
    }

    @Override
    public void onTitleIconGetFinished(String title, String shortcuturl) {
        mainView.choseAutoCompleteBookmark(title,shortcuturl);
    }

    @Override
    public void showSearchView(List<String> strings) {
        mainView.showSearchView(strings);
    }

    @Override
    public void onClearOneHistoryFinished(int position, List<String> strings) {
        mainView.onClearOneHistoryFinished(position,strings);
    }

    @Override
    public void onClearAllFinished() {
        mainView.onClearAllFinished();
    }
}
