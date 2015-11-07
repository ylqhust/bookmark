package com.ylqhust.bookmarks.mvp.model.Interactor.Impl;

import android.view.View;

import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.Interactor.Interface.HomeFragInteractor;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnHomeFragFinishListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 15/10/30.
 */
public class HomeFragInteractorImpl implements HomeFragInteractor {

    /**
     * 添加一个节点
     * @param nodeName
     * @param nodeDes
     * @param parentNode
     * @param headNode
     * @param headBookmark
     * @param listener
     * @param dbh
     */
    @Override
    public void addNode(String nodeName,
                        String nodeDes,
                        Node parentNode,
                        List<Node> headNode,
                        List<Bookmark> headBookmark,
                        OnHomeFragFinishListener listener,
                        DatabaseHelper dbh) {

        if (nodeName==null || nodeName.equals("")){
            listener.onNodeNameError("请输入节点名");
            return;
        }

        if (CheckSameNodeName(nodeName, parentNode != null ? parentNode.containNode : headNode)){
            listener.onNodeNameError("相同的节点名已经存在");
            return;
        }

        int nodeNum = getUniquNodeNum(headNode);
        int preNodeNum = parentNode==null?-1:parentNode.nodeNum;
        Node node = new Node(dbh.getUserHelper().getUserId(),nodeName,nodeNum,System.currentTimeMillis(),
                System.currentTimeMillis(),nodeDes,preNodeNum);
        node.preNode = parentNode;
        node.containNode = new ArrayList<Node>();
        node.containBM = new ArrayList<Bookmark>();

        dbh.getNodeHelper().insert(node);
        if (parentNode != null){
            parentNode.containNode.add(node);
            listener.onAddNodeFinished(parentNode.containNode,parentNode.containBM,node);
        }
        else{
            headNode.add(node);
            listener.onAddNodeFinished(headNode, headBookmark, node);
        }
    }

    /**
     * 添加一个书签
     * @param url
     * @param title
     * @param shortcuturl
     * @param parentNode
     * @param headNode
     * @param headBookmark
     * @param dbh
     * @param listener
     */
    @Override
    public void addBookmark(final String url,
                            final String title,
                            final String shortcuturl,
                            final Node parentNode,
                            final List<Node> headNode,
                            final List<Bookmark> headBookmark,
                            final DatabaseHelper dbh, final OnHomeFragFinishListener listener) {

        if (url == null || url.equals("")){
            listener.onBookmarkUrlError("URL不能为空");
            return;
        }

        String sameUrlMessage = CheckSameUrl(url,headBookmark,headNode);
        if ( sameUrlMessage != null){
            listener.onBookmarkSameUrl("/" + sameUrlMessage, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    addBookmark(listener, dbh, headNode, headBookmark, parentNode, shortcuturl, title, url);
                }
            });
            return;
        }
        addBookmark(listener,dbh,headNode,headBookmark,parentNode,shortcuturl,title,url);
    }

    /**
     * 跳回到父节点
     * @param parentNode
     * @param listener
     */
    @Override
    public void stepBack(Node parentNode, OnHomeFragFinishListener listener) {
        listener.onStepBackFinished(parentNode.preNode);
    }

    /**
     * 跳入到子节点
     * @param node
     * @param listener
     */
    @Override
    public void stepIn(Node node, OnHomeFragFinishListener listener) {
        listener.onStepInFinished(node.containNode,node.containBM,node);
    }

    /**
     * 检测是否已经保存过了相同的url
     * @param url
     * @param bookmarks
     * @param nodes
     * @return
     */
    private String CheckSameUrl(String url, List<Bookmark> bookmarks, List<Node> nodes) {
        for (int i=0;i<bookmarks.size();i++){
            if (bookmarks.get(i).url.equals(url))
                return url;
        }
        for(int i=0;i<nodes.size();i++){
            String check = CheckSameUrl(url,nodes.get(i).containBM,nodes.get(i).containNode);
            if ( check != null){
                return nodes.get(i).nodeName+"/"+check;
            }
        }
        return null;
    }
    private void addBookmark(OnHomeFragFinishListener listener,
                             DatabaseHelper dbh, List<Node> headNode, List<Bookmark> headBookmark,
                             Node parentNode, String shortcuturl,
                             String title, String url){
        int belongNodeNum = parentNode==null?-1:parentNode.nodeNum;
        int bookmarkNum = getUniquBookmarkNum(headBookmark,headNode);
        String userID = dbh.getUserHelper().getUserId();
        Bookmark bookmark = new Bookmark(belongNodeNum,"",bookmarkNum,System.currentTimeMillis(),
                false,false,false,false,false,System.currentTimeMillis(),shortcuturl,title,url,userID);

        bookmark.belongNode = parentNode;

        dbh.getBookmarkHelper().insert(bookmark);

        if (parentNode == null){
            headBookmark.add(bookmark);
            listener.onAddBookmarkFinished(headNode,headBookmark,bookmark);
        }
        else{
            parentNode.containBM.add(bookmark);
            listener.onAddBookmarkFinished(parentNode.containNode, parentNode.containBM, bookmark);
        }
    }

    /**
     * 获取唯一的bookmark编号
     * @param bookmarks
     * @param nodes
     * @return
     */
    private int getUniquBookmarkNum(List<Bookmark> bookmarks, List<Node> nodes) {
        int maxNum = 1;
        for (int i=0;i<nodes.size();i++){
            int thisMax = getUniquBookmarkNum(nodes.get(i).containBM,nodes.get(i).containNode);
            if (maxNum <= thisMax)
                maxNum = thisMax + 1;
        }

        for (Bookmark bookmark : bookmarks){
            if (maxNum <= bookmark.bookmarkNum)
                maxNum = bookmark.bookmarkNum + 1;
        }
        return maxNum;
    }

    /**
     * 判断是否重名
     * @param nodeName
     * @param nodes
     * @return
     */
    private boolean CheckSameNodeName(String nodeName, List<Node> nodes) {
        if (nodes == null)
            return false;
        for (Node node : nodes){
            if (node.nodeName.equals(nodeName))
                return true;
        }
        return false;
    }

    private int getUniquNodeNum(List<Node> headNode) {
        int unique = 1;
        int num;
        for(int i=0;i<headNode.size();i++){
            num = getUniquNodeNum(headNode.get(i).containNode);
            if (unique <= num )
                unique = num+1;
            if (unique <= headNode.get(i).nodeNum)
                unique = headNode.get(i).nodeNum + 1;
        }
        return unique;
    }
}
