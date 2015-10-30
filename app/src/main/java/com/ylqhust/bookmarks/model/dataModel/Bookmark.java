package com.ylqhust.bookmarks.model.dataModel;

/**
 * Created by apple on 15/10/29.
 */
public class Bookmark {
    public String userID;//用户id

    public String url;//书签url
    public String title;//书签标题
    public int bookmarkNum;//书签编号
    public String shortCutUrl;//书签小图标
    public String bookmarkDes;//书签描述
    public long createTime;//创建时间
    public long lastModifyTime;//最后修改时间
    public int belongNodeNum;//所属节点编号

    public boolean isModified;//是否被修改
    public boolean isDeleted;//是否被删除
    public boolean isBackuped;//是否被备份过
    public boolean isSynced;//是否被同步过
    public boolean isPrivate;//是否属于私人书签

    public Node belongNode;//所属节点

    public Bookmark(int belongNodeNum,
                    String bookmarkDes,
                    int bookmarkNum,
                    long createTime,
                    boolean isBackuped,
                    boolean isDeleted,
                    boolean isModified,
                    boolean isPrivate,
                    boolean isSynced,
                    long lastModifyTime,
                    String shortCutUrl,
                    String title,
                    String url,
                    String userID) {
        this.belongNodeNum = belongNodeNum;
        this.bookmarkDes = bookmarkDes;
        this.bookmarkNum = bookmarkNum;
        this.createTime = createTime;
        this.isBackuped = isBackuped;
        this.isDeleted = isDeleted;
        this.isModified = isModified;
        this.isPrivate = isPrivate;
        this.isSynced = isSynced;
        this.lastModifyTime = lastModifyTime;
        this.shortCutUrl = shortCutUrl;
        this.title = title;
        this.url = url;
        this.userID = userID;
    }

    public static Bookmark testBuilder(int bookmarkNum){
        return new Bookmark(bookmarkNum+1,"null",bookmarkNum,System.currentTimeMillis(),
                false,false,false,false,false,System.currentTimeMillis(),null,"null",null,"STRANGER");
    }
}
