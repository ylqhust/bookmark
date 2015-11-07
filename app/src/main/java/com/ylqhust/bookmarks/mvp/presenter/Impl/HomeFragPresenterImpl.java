package com.ylqhust.bookmarks.mvp.presenter.Impl;

import android.view.View;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.Interactor.Impl.HomeFragInteractorImpl;
import com.ylqhust.bookmarks.mvp.model.Interactor.Interface.HomeFragInteractor;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Interface.HomeFragPresenter;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnHomeFragFinishListener;
import com.ylqhust.bookmarks.mvp.view.HomeFragView;

import java.util.List;

/**
 * Created by apple on 15/10/30.
 */
public class HomeFragPresenterImpl implements HomeFragPresenter,OnHomeFragFinishListener {
    private HomeFragView homeFragView;
    private HomeFragInteractor interactor;

    public HomeFragPresenterImpl(HomeFragView homeFragView){
        this.homeFragView = homeFragView;
        this.interactor = new HomeFragInteractorImpl();
    }


    /**
     *  @param nodeName
     * @param nodeDes
     */
    @Override
    public void addNode(String nodeName,
                        String nodeDes,
                        Node parentNode,
                        List<Node> headNode,
                        List<Bookmark> headBookmark,
                        DatabaseHelper dbh) {
        interactor.addNode(nodeName, nodeDes, parentNode, headNode, headBookmark, this, dbh);
    }

    @Override
    public void addBookmark(String url,
                            String title,
                            String shortcuturl,
                            Node parentNode,
                            List<Node> headNode,
                            List<Bookmark> headBookmark,
                            DatabaseHelper dbh) {
        interactor.addBookmark(url, title, shortcuturl, parentNode, headNode, headBookmark, dbh, this);
    }

    /**
     * 跳回到父节点
     * @param parentNode
     */
    @Override
    public void stepBack(Node parentNode) {
        interactor.stepBack(parentNode, this);
    }

    /**
     * 跳到下一个节点
     * @param node
     */
    @Override
    public void stepIn(Node node) {
        System.out.println(node.nodeName);
        interactor.stepIn(node, this);
    }

    /**
     * 显示选项dialog
     * @param parentNode
     * @param bookmark
     */
    @Override
    public void showChoseDailog(Node parentNode, Bookmark bookmark) {
        homeFragView.showChoseDialog(parentNode, bookmark);
    }

    /**
     * 准备移动，调整界面
     */
    @Override
    public void ReadyMove() {
        homeFragView.hideRecyclerView1();
        homeFragView.showRecyclerView2();
    }


    /**
     * 重名错误
     */
    @Override
    public void onNodeNameError(String errorMessage) {
        homeFragView.onNodeNameError(errorMessage);
    }

    @Override
    public void onAddNodeFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark,Node node) {
        homeFragView.onAddNodeFinished(currentPageNode,currentPageBookmark,node);
    }

    @Override
    public void onBookmarkUrlError(String errorMessage) {
        homeFragView.onBookmarkUrlError(errorMessage);
    }

    @Override
    public void onBookmarkSameUrl(String sameUrlMessage, View.OnClickListener onClickListener) {
        homeFragView.onBookmarkSameUrl(sameUrlMessage, onClickListener);
    }

    @Override
    public void onAddBookmarkFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark, Bookmark bookmark) {
        homeFragView.onAddBookmarkFinished(currentPageNode,currentPageBookmark,bookmark);
    }

    @Override
    public void onStepBackFinished(Node preNode) {
        homeFragView.onStepBackFinished(preNode);
    }

    @Override
    public void onStepInFinished(List<Node> containNode, List<Bookmark> containBM, Node node) {
        homeFragView.onStepInFinished(containNode, containBM, node);
    }


}
