package com.ylqhust.bookmarks.mvp.model.dataModel;


/**
 * Created by apple on 15/11/2.
 */
public class SearchHistory {
    public String userid;
    public String string;
    public long searchTime;

    public int count;
    /**
     * 创建时使用
     * @param userid
     * @param string
     * @param searchTime
     */
    public SearchHistory(String userid,
                           String string,
                           long searchTime){
        this.userid = userid;
        this.string = string;
        this.searchTime = searchTime;
    }

    /**
     * 检索排序时使用
     * @param string
     * @param count
     */
    public SearchHistory(String string,int count){
        this.string = string;
        this.count = count;
    }

}
