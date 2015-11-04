package com.ylqhust.bookmarks.presenter.Listener;

import android.view.View;

import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/10/30.
 */
public interface OnHomeFragFinishListener {
    public void onNodeNameError(String errorMessage);
    public void onAddNodeFinished(List<Node> currentPageNode,List<Bookmark> currentPageBookmark,Node node);
    public void onBookmarkUrlError(String errorMessage);
    public void onBookmarkSameUrl(String sameUrlMessage, View.OnClickListener onClickListener);

    public void onAddBookmarkFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark, Bookmark bookmark);

    public void onStepBackFinished(Node preNode);

    public void onStepInFinished(List<Node> containNode, List<Bookmark> containBM, Node node);
}
