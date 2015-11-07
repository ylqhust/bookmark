package com.ylqhust.bookmarks.mvp.presenter.Listener;

import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/11/4.
 */
public interface OnMDFL {
    void onModifyFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark);
}
