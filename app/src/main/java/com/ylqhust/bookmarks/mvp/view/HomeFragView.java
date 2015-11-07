package com.ylqhust.bookmarks.mvp.view;

import android.view.View;

import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/10/31.
 */
public interface HomeFragView {
    public void showPD();
    public void dismissPD();

    public void showData(List<Node> currentPageNode, List<Bookmark> currentPageBK, Node parentNode2);
    public void onNodeNameError(String errorMessage);
    public void onAddNodeFinished(List<Node> currentPageNode,List<Bookmark> currentPageBookmark,Node node);
    public void onBookmarkUrlError(String errorMessage);

    public void onBookmarkSameUrl(String sameUrlMessage, View.OnClickListener onClickListener);

    public void onAddBookmarkFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark, Bookmark bookmark);

    public void onStepInFinished(List<Node> containNode, List<Bookmark> containBM, Node preNode);

    public void onStepBackFinished(Node preNode);

    void showChoseDialog(Node parentNode, Bookmark bookmark);


    void hideRecyclerView1();

    void showRecyclerView2();
}
