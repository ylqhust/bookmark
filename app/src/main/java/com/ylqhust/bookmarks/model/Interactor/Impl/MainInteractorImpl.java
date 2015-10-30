package com.ylqhust.bookmarks.model.Interactor.Impl;

import android.database.Cursor;

import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.database.DatabaseModel;
import com.ylqhust.bookmarks.model.Interactor.Interface.MainInteractor;
import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;
import com.ylqhust.bookmarks.presenter.Listener.OnMainFinishListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public class MainInteractorImpl implements MainInteractor {
    @Override
    public void HomeTabEvent(OnMainFinishListener listener) {
        listener.HomeTabFinish();
    }

    @Override
    public void PublicTabEvent(OnMainFinishListener listener) {
        listener.PublicTabFinish();
    }

    @Override
    public void PeopleTabEvent(OnMainFinishListener listener) {
        listener.PeopleTabFinish();
    }

    /**
     * 加载数据库文件
     * @param listener
     */
    @Override
    public void LoadDatabase(DatabaseHelper dbh,OnMainFinishListener listener) {
        String userid = getUserId(dbh);
        List<Node> allNode = getNodeList(dbh, userid);
        List<Bookmark> allBookmark = getBookmarkList(dbh, userid);

        List<Bookmark> headBookmark = new ArrayList<Bookmark>();
        for(int i=0;i<allBookmark.size();i++){
            if (allBookmark.get(i).belongNodeNum == 0){
                headBookmark.add(allBookmark.get(i));
                allBookmark.remove(i);
                i--;
            }
        }

        Bind(allBookmark, allNode);
        List<Node> headNode = Bind(allNode);

        listener.LoadFinish(headNode,headBookmark);
        return;
    }

    /**
     * 将节点根据所属关系归好,并返回头节点
     * @param allNode
     */
    private List<Node> Bind(List<Node> allNode) {
        List<Node> headNode = new ArrayList<Node>();
        for (int i=0;i<allNode.size();i++){
            if (allNode.get(i).nodeNum == 0){
                headNode.add(allNode.get(i));
            }
            for(int j=0;j<allNode.size();j++){
                if (i != j && allNode.get(i).nodeNum == allNode.get(j).preNodeNum){
                    allNode.get(i).containNode.add(allNode.get(j));
                    allNode.get(j).preNode = allNode.get(i);
                }
            }
        }
        return headNode;
    }

    /**
     * 将书签和节点根据所属关系归好
     * @param allBookmark
     * @param allNode
     */
    private void Bind(List<Bookmark> allBookmark, List<Node> allNode) {
        for(int i=0;i<allBookmark.size();i++)
            for(int j=0;j<allNode.size();j++){
                if (allBookmark.get(i).belongNodeNum == allNode.get(j).nodeNum){
                    allBookmark.get(i).belongNode = allNode.get(j);
                    allNode.get(j).containBM.add(allBookmark.get(i));
                    break;
                }
            }
        return;
    }

    /**
     * 根据用户id获得所有书签
     * @param dbh
     * @param userid
     * @return
     */
    private List<Bookmark> getBookmarkList(DatabaseHelper dbh, String userid) {
        List<Bookmark> allBookmark = new ArrayList<Bookmark>();

        Cursor cs = dbh.getBookmarkHelper().queryAll(userid);
        while(cs.moveToNext()){
            allBookmark.add(dbh.getBookmarkHelper().getBookmark(cs));
        }
        return allBookmark;
    }

    /**
     * 根据用户id获得所有节点
     * @param dbh
     * @param userid
     * @return
     */
    private List<Node> getNodeList(DatabaseHelper dbh, String userid) {
        List<Node> allNode = new ArrayList<Node>();

        Cursor cs = dbh.getNodeHelper().queryAll(userid);
        while(cs.moveToNext()){
            Node node = dbh.getNodeHelper().getNode(cs);
            node.containNode = new ArrayList<Node>();
            node.containBM = new ArrayList<Bookmark>();
            allNode.add(node);
        }
        return allNode;
    }


    /**
     * 获取数据库中的userid，如果没有用户的话，那么创建一个userid为STRANGER的用户，没有密码，相当于未登录用户
     * @param dbh
     * @return
     */
    private String getUserId(DatabaseHelper dbh) {
        String userId = "STRANGER";
        Cursor cs = dbh.getUserHelper().query();
        if (cs.moveToNext()){
            userId = cs.getString(cs.getColumnIndex(DatabaseModel.USER.CN_USERID));
        }
        return userId;
    }
}
