package com.ylqhust.bookmarks.model.Interactor.Impl;

import android.content.Context;
import android.database.Cursor;

import com.ylqhust.bookmarks.utils.Utils;
import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.model.Interactor.Interface.MainInteractor;
import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;
import com.ylqhust.bookmarks.network.NetworkUtils;
import com.ylqhust.bookmarks.presenter.Listener.OnMainFinishListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public class MainInteractorImpl implements MainInteractor {


    /**
     * 处理HomeTab按钮的点击事件
     */
    @Override
    public void onHomeTab(OnMainFinishListener listener) {
        listener.onHomeTabFinished();
    }

    @Override
    public void onPublicTab(OnMainFinishListener listener) {
        listener.onPublicTabFinished();
    }

    @Override
    public void onPeopleTab(OnMainFinishListener listener) {
        listener.onPeopleTabFinished();
    }

    /**
     * 获取剪切板上的数据，判断是否是url类型的数据
     * 如果是，获取url
     * 如果有网络，获取url所表示的title，和shortcuturl
     * @param listener
     */
    @Override
    public void addBookmark(Context context,OnMainFinishListener listener) {
        String url = Utils.getClipboardText(context);
        if (url.contains("http")){
            listener.addBookmarkFinished(url,"","");
            NetworkUtils.getInstance().getTS(listener,url);
        }
        else{
            listener.addBookmarkFinished("","","");
        }
    }

    /**
     *获取搜索历史列表
     * @param dbh
     * @param listener*/
    @Override
    public void showSearchView(DatabaseHelper dbh, OnMainFinishListener listener) {
        String userid = dbh.getUserHelper().getUserId();
        List<String> strings = dbh.getSearchHisHelper().getHistory(userid);
        listener.showSearchView(strings);
    }

    /**
     * 清除一条历史记录
     * @param position
     * @param strings
     * @param dbh
     * @param listener
     */
    @Override
    public void clearOneHistory(int position, List<String> strings, DatabaseHelper dbh, OnMainFinishListener listener) {
        String userid = dbh.getUserHelper().getUserId();
        dbh.getSearchHisHelper().delete(userid,strings.get(position));
        strings.remove(position);
        listener.onClearOneHistoryFinished(position, strings);
    }

    /**
     * 清除所有历史记录
     * @param dbh
     * @param listener
     */
    @Override
    public void clearAllHistory(DatabaseHelper dbh, OnMainFinishListener listener) {
        dbh.getSearchHisHelper().deleteAll(dbh.getUserHelper().getUserId());
        listener.onClearAllFinished();
    }


    /**
     * 加载数据库文件
     * @param listener
     */
    @Override
    public void LoadDatabase(DatabaseHelper dbh,OnMainFinishListener listener) {
        String userid = dbh.getUserHelper().getUserId();
        List<Node> allNode = getNodeList(dbh, userid);
        List<Bookmark> allBookmark = getBookmarkList(dbh, userid);

        List<Bookmark> headBookmark = new ArrayList<Bookmark>();
        for(int i=0;i<allBookmark.size();i++){
            if (allBookmark.get(i).belongNodeNum == -1){
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
            if (allNode.get(i).preNodeNum == -1){
                allNode.get(i).preNode = null;
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


}
