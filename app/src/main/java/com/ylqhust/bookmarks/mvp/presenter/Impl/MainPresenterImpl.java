package com.ylqhust.bookmarks.mvp.presenter.Impl;

import android.content.Context;

import com.ylqhust.bookmarks.R;
import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.Interactor.Interface.MainInteractor;
import com.ylqhust.bookmarks.mvp.model.Interactor.Impl.MainInteractorImpl;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Interface.MainPresenter;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnMainFinishListener;
import com.ylqhust.bookmarks.mvp.view.MainView;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public class MainPresenterImpl implements MainPresenter,OnMainFinishListener {



    private MainView mainView;
    private MainInteractor interactor;

    public MainPresenterImpl(MainView mainView,MainInteractorImpl.MII_M_Bridge mii_m_bridge)
    {
        this.mainView = mainView;
        this.interactor = new MainInteractorImpl(mii_m_bridge);
    }

    /**
     * 加载数据库
     * @param dbh
     */
    @Override
    public void LoadDatabase(DatabaseHelper dbh) {
        interactor.LoadDatabase(dbh, this);
    }

    /**
     *
     */
    @Override
    public void addBookmark(Context context) {
        interactor.addBookmark(context, this);
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
        interactor.clearOneHistory(position, strings, dbh, this);
    }

    /**
     * 清除所有历史记录
     * @param dbh
     */
    @Override
    public void clearAllHistory(DatabaseHelper dbh) {
        interactor.clearAllHistory(dbh, this);
    }

    @Override
    public void cancle(boolean isReadyMove) {
        if (isReadyMove){
            mainView.showSelectAllMenu();
            mainView.hideMoveBar();
            mainView.showToolsBar();
            mainView.cancleMoveComplete();
        }
        else{
            mainView.hideSelectAllMenu();
            mainView.hideCancleMenu();
            mainView.showThreeMenu();
            mainView.hideToolsBar();
            mainView.showTabBar();
            mainView.resetSelectMenuText();
            mainView.cancleComplete();
        }
    }

    @Override
    public void HomeTab() {
        mainView.selectTab(0);
        mainView.onHomeTabFinished();
    }

    @Override
    public void PublicTab() {
        mainView.selectTab(1);
        mainView.onPublicTabFinished();
    }

    @Override
    public void PeopleTab() {
        mainView.selectTab(2);
        mainView.onPeopleTabFinished();
    }

    @Override
    public void send() {
        interactor.send(this);
    }

    @Override
    public void deleteAllSelect() {
        interactor.deleteAllSelect(this);
    }

    @Override
    public void move() {
        interactor.move(this);
    }

    @Override
    public void readyMove() {
        interactor.readyMove(this);

    }

    @Override
    public void LongClickHappend() {
        mainView.hideThreeMenu();
        mainView.showCancleMenu();
        mainView.showSelectAllMenu();
        mainView.hideTabBar();
        mainView.showToolsBar();
    }


    @Override
    public void LoadFinish(List<Node> headNode, List<Bookmark> headBookmark) {
        mainView.LoadFinished(headNode, headBookmark);
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

    @Override
    public void onSendParseFinished(String s) {
        mainView.onSendParseFinished(s);
    }

    @Override
    public void onDeleteFinished(List<Node> nodes, List<Bookmark> bookmarks) {
        mainView.onDeleteFinished(nodes,bookmarks);
    }

    @Override
    public void onMoveFinished() {
        mainView.onMoveFinished();
        mainView.hideMoveBar();
        mainView.hideCancleMenu();
        mainView.hideSelectAllMenu();
        mainView.showTabBar();
        mainView.showThreeMenu();
        mainView.resetSelectMenuText();
        mainView.showSnackBar(R.string.movecomplete);
    }

    /**
     * 用户点击移动按钮，但是没有选择任何东西
     */
    @Override
    public void onCheckNullError() {
        mainView.showSnackBar(R.string.checknullerror);
    }

    /**
     * 准备移动完毕
     */
    @Override
    public void onReadyMoveFinished() {
        mainView.hideToolsBar();
        mainView.showMoveBar();
        mainView.hideSelectAllMenu();
        mainView.HMReadyMove();
    }
}
