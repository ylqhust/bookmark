package com.ylqhust.bookmarks.mvp.view;

import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public interface MainView {
    void selectTab(int index);

    void onHomeTabFinished();

    void onPublicTabFinished();

    void onPeopleTabFinished();

    public void LoadFinished(List<Node> headNode,List<Bookmark> headBookmark);

    public void showNodeInputDialog();
    public void showBookmarkInputDialog(String guessUrl,String guessTitle,String ShortCutUrl);
    public void choseAutoCompleteBookmark(String title,String shortcuturl);

    void showSearchView(List<String> searchHistory);

    void onClearOneHistoryFinished(int position, List<String> strings);

    void onClearAllFinished();

    void onSendParseFinished(String s);

    void onDeleteFinished(List<Node> nodes, List<Bookmark> bookmarks);

    void hideToolsBar();

    void showMoveBar();

    void hideMoveBar();

    void hideThreeMenu();

    void showCancleMenu();

    void showSelectAllMenu();

    void hideTabBar();

    void showToolsBar();

    void hideSelectAllMenu();

    void resetSelectMenuText();

    void showTabBar();

    void showThreeMenu();

    void hideCancleMenu();

    void cancleMoveComplete();

    void cancleComplete();

    void HMReadyMove();

    void onMoveFinished();

    void showToast(int checknullerror);

    void showSnackBar(int checknullerror);
}
