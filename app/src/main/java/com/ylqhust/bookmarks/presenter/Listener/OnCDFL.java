package com.ylqhust.bookmarks.presenter.Listener;

import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/11/3.
 */
public interface OnCDFL {
    void onDeleteFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark);
}
