package com.ylqhust.bookmarks.mvp.view;

import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/11/3.
 */
public interface ChoseDialogView {
    void onDeleteFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark);

    void showMoidfyDialog(Node parentNode, Bookmark bookmark, List<Bookmark> headBookmark, List<Node> headNode);

    void dismiss();
    void show();
}
