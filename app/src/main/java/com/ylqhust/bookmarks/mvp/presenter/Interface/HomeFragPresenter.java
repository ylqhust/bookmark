package com.ylqhust.bookmarks.mvp.presenter.Interface;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/10/30.
 */
public interface HomeFragPresenter {
    public void addNode(String nodeName,
                        String nodeDes,
                        Node parentNode,
                        List<Node> headNode,
                        List<Bookmark> headBookmark,
                        DatabaseHelper dbh);

    public void addBookmark(String url,
                            String title,
                            String shortcuturl,
                            Node parentNode,
                            List<Node> headNode,
                            List<Bookmark> headBookmark,
                            DatabaseHelper dbh);

    public void stepBack(Node parentNode);

    public void stepIn(Node node);

    void showChoseDailog(Node parentNode, Bookmark bookmark);


    void ReadyMove();
}
