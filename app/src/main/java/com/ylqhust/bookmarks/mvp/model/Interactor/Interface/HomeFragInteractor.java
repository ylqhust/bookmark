package com.ylqhust.bookmarks.mvp.model.Interactor.Interface;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnHomeFragFinishListener;

import java.util.List;

/**
 * Created by apple on 15/10/30.
 */
public interface HomeFragInteractor {
    public void addNode(String nodeName,
                        String nodeDes,
                        Node parentNode,
                        List<Node> headNode,
                        List<Bookmark> headBookmark,
                        OnHomeFragFinishListener listener,
                        DatabaseHelper dbh);
    public void addBookmark(String url,
                            String title,
                            String shortcuturl,
                            Node parentNode,
                            List<Node> headNode,
                            List<Bookmark> headBookmark,
                            DatabaseHelper dbh, OnHomeFragFinishListener listener);

    public void stepBack(Node parentNode, OnHomeFragFinishListener listener);

    public void stepIn(Node node, OnHomeFragFinishListener listener);
}
