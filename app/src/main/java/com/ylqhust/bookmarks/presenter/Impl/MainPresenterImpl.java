package com.ylqhust.bookmarks.presenter.Impl;

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
        interactor.HomeTabEvent(this);
    }

    @Override
    public void PublicTab() {
        mainView.selectTab(1);
        interactor.PublicTabEvent(this);
    }

    @Override
    public void PeopleTab() {
        mainView.selectTab(2);
        interactor.PeopleTabEvent(this);
    }

    /**
     * 加载数据库
     * @param dbh
     */
    @Override
    public void LoadDatabase(DatabaseHelper dbh) {
        mainView.loading();
        interactor.LoadDatabase(dbh,this);
    }


    @Override
    public void HomeTabFinish() {
        mainView.onHomeTabFinished();
    }

    @Override
    public void PublicTabFinish() {
        mainView.onFinished();
    }

    @Override
    public void PeopleTabFinish() {
        mainView.onFinished();
    }

    @Override
    public void LoadFinish(List<Node> headNode, List<Bookmark> headBookmark) {
        mainView.LoadFinished(headNode,headBookmark);
    }

}
