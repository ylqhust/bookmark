package com.ylqhust.bookmarks;

import com.ylqhust.bookmarks.data.database.BookmarkHelper;
import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.data.database.NodeHelper;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;

/**
 * Created by apple on 15/10/31.
 */
public class TestData {
    public static void addData(DatabaseHelper dbh){
        NodeHelper nodeHelper = dbh.getNodeHelper();
        BookmarkHelper bookmarkHelper = dbh.getBookmarkHelper();

        nodeHelper.insert(new Node("STRANGER","计算机",0,System.currentTimeMillis(),
                System.currentTimeMillis(), "science",-1));
        nodeHelper.insert(new Node("STRANGER","数学",1,System.currentTimeMillis(),
                System.currentTimeMillis(), "science",-1));
        nodeHelper.insert(new Node("STRANGER","语文",2,System.currentTimeMillis(),
                System.currentTimeMillis(), "science",-1));
        nodeHelper.insert(new Node("STRANGER","英语",3,System.currentTimeMillis(),
                System.currentTimeMillis(), "science",0));
        nodeHelper.insert(new Node("STRANGER","物理",4,System.currentTimeMillis(),
                System.currentTimeMillis(), "science",1));
        nodeHelper.insert(new Node("STRANGER","化学",5,System.currentTimeMillis(),
                System.currentTimeMillis(), "science",2));


        bookmarkHelper.insert(new Bookmark(-1,"null",0,System.currentTimeMillis(),
                    false,false,false,false,false,System.currentTimeMillis(),null,"Android基础入门教程——8.3.12 Paint API之—— PathEffect(路径效果)","http://blog.csdn.net/coder_pig/article/details/49512115","STRANGER"));
        bookmarkHelper.insert(new Bookmark(-1,"null",1,System.currentTimeMillis(),
                false,false,false,false,false,System.currentTimeMillis(),null,"Android实战简易教程-第七十枪（自定义实用控制之-邮箱验证EditText)","http://blog.csdn.net/yayun0516/article/details/49489367","STRANGER"));
        bookmarkHelper.insert(new Bookmark(-1,"null",2,System.currentTimeMillis(),
                false,false,false,false,false,System.currentTimeMillis(),null," Android实战简易教程-第六十九枪（自定义控件实现雪花飘落效果） ","http://blog.csdn.net/yayun0516/article/details/49488701","STRANGER"));
        bookmarkHelper.insert(new Bookmark(0,"null",3,System.currentTimeMillis(),
                false,false,false,false,false,System.currentTimeMillis(),null," Android实战简易教程-第六十九枪（自定义控件实现雪花飘落效果） ","http://blog.csdn.net/yayun0516/article/details/49488701","STRANGER"));
        bookmarkHelper.insert(new Bookmark(1,"null",4,System.currentTimeMillis(),
                false,false,false,false,false,System.currentTimeMillis(),null," Android实战简易教程-第六十九枪（自定义控件实现雪花飘落效果） ","http://blog.csdn.net/yayun0516/article/details/49488701","STRANGER"));
        bookmarkHelper.insert(new Bookmark(2,"null",5,System.currentTimeMillis(),
                false,false,false,false,false,System.currentTimeMillis(),null," Android实战简易教程-第六十九枪（自定义控件实现雪花飘落效果） ","http://blog.csdn.net/yayun0516/article/details/49488701","STRANGER"));
        bookmarkHelper.insert(new Bookmark(3,"null",6,System.currentTimeMillis(),
                false,false,false,false,false,System.currentTimeMillis(),null," Android实战简易教程-第六十九枪（自定义控件实现雪花飘落效果） ","http://blog.csdn.net/yayun0516/article/details/49488701","STRANGER"));
    }
}
