package com.ylqhust.bookmarks.presenter.Listener;

import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface OnMainFinishListener {
    public void HomeTabFinish();
    public void PublicTabFinish();
    public void PeopleTabFinish();
    public void LoadFinish(List<Node> headNode,List<Bookmark> headBookmark);
}
