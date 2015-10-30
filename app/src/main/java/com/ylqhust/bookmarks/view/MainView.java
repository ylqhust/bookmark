package com.ylqhust.bookmarks.view;

import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface MainView {
    public void selectTab(int index);
    public void onFinished();
    public void onHomeTabFinished();
    public void LoadFinished(List<Node> headNode,List<Bookmark> headBookmark);
    public void loading();
}
