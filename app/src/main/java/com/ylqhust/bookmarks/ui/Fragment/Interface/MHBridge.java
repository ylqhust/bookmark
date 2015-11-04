package com.ylqhust.bookmarks.ui.Fragment.Interface;

import com.ylqhust.bookmarks.database.DatabaseHelper;

/**
 * Created by apple on 15/11/1.
 */
public interface MHBridge{
    public void addNode(String nodeName, String nodeDes, DatabaseHelper dbh);
    public void addBookmark(String url, String title, String shortcuturl, DatabaseHelper dbh);
}