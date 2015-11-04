package com.ylqhust.bookmarks.presenter.Listener;

import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface OnMainFinishListener {
    public void LoadFinish(List<Node> headNode,List<Bookmark> headBookmark);
    public void onHomeTabFinished();
    public void onPublicTabFinished();
    public void onPeopleTabFinished();
    public void addBookmarkFinished(String guessUrl,String guessTitle,String shortcuturl);
    public void onTitleIconGetFinished(String title,String shortcuturl);

    void showSearchView(List<String> strings);

    void onClearOneHistoryFinished(int position, List<String> strings);

    void onClearAllFinished();

}
