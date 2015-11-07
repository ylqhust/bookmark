package com.ylqhust.bookmarks.mvp.presenter.Listener;

import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface OnMainFinishListener {
    public void LoadFinish(List<Node> headNode,List<Bookmark> headBookmark);
    public void addBookmarkFinished(String guessUrl,String guessTitle,String shortcuturl);
    public void onTitleIconGetFinished(String title,String shortcuturl);

    void showSearchView(List<String> strings);

    void onClearOneHistoryFinished(int position, List<String> strings);

    void onClearAllFinished();

    void onSendParseFinished(String s);

    void onDeleteFinished(List<Node> nodes, List<Bookmark> bookmarks);

    void onMoveFinished();

    void onCheckNullError();

    void onReadyMoveFinished();

}
