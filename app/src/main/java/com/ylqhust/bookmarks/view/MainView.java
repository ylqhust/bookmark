package com.ylqhust.bookmarks.view;

import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface MainView {
    public void selectTab(int index);
    public void onHomeTabFinished();
    public void onPublicTabFinished();
    public void onPeopleTabFinished();
    public void LoadFinished(List<Node> headNode,List<Bookmark> headBookmark);
    public void loading();

    public void showNodeInputDialog();
    public void showBookmarkInputDialog(String guessUrl,String guessTitle,String ShortCutUrl);
    public void choseAutoCompleteBookmark(String title,String shortcuturl);

    void showSearchView(List<String> searchHistory);

    void onClearOneHistoryFinished(int position, List<String> strings);

    void onClearAllFinished();
}
