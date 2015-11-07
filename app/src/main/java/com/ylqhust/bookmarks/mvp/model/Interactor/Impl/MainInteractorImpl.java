package com.ylqhust.bookmarks.mvp.model.Interactor.Impl;

import android.content.Context;
import android.database.Cursor;

import com.ylqhust.bookmarks.utils.Utils;
import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.Interactor.Interface.MainInteractor;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.data.network.NetworkUtils;
import com.ylqhust.bookmarks.mvp.presenter.Listener.OnMainFinishListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by apple on 15/10/29.
 */
public class MainInteractorImpl implements MainInteractor {


    public MainInteractorImpl(MII_M_Bridge mii_m_bridge){
        this.mii_m_bridge = mii_m_bridge;
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
     * 发送被选择的书签
     * @param listener
     */

    @Override
    public void send(OnMainFinishListener listener) {
        List<Node> headNode = mii_m_bridge.RequestHeadNode();
        List<Bookmark> headBookmark = mii_m_bridge.RequestHeadBookmark();
        Set<Integer> checkBookmarkNum = mii_m_bridge.RequestCheckBookmark();

        List<Bookmark> bookmark = new ArrayList<Bookmark>();
        PaddingBookmark(bookmark, headNode, headBookmark, checkBookmarkNum);

        StringBuilder sb = new StringBuilder();
        for (Bookmark bookmark1 : bookmark){
            sb.append(bookmark1.title+"\n"+bookmark1.url+"\n\n\n");
        }

        listener.onSendParseFinished(sb.toString());
    }

    /**
     * 删除所有选中的文件
     * @param listener
     */
    @Override
    public void deleteAllSelect(OnMainFinishListener listener) {
        Node parentNode = mii_m_bridge.RequestParentNode();
        List<Node> headNode = mii_m_bridge.RequestHeadNode();
        List<Bookmark> headBookmark = mii_m_bridge.RequestHeadBookmark();
        Set<Integer> checkBookmarkNum = mii_m_bridge.RequestCheckBookmark();
        Set<Integer> checkNodeNum = mii_m_bridge.RequestCheckNodeNum();
        DatabaseHelper dbh = mii_m_bridge.RequestDbh();

        if (parentNode == null){
            deleteAllSelect(headNode, headBookmark, checkBookmarkNum, checkNodeNum, dbh);
            listener.onDeleteFinished(headNode, headBookmark);
        }
        else{
            deleteAllSelect(parentNode.containNode,parentNode.containBM,checkBookmarkNum,checkNodeNum,dbh);
            listener.onDeleteFinished(parentNode.containNode, parentNode.containBM);
        }
    }

    @Override
    public void move(OnMainFinishListener listener) {
        Node distanceNode = mii_m_bridge.RequestDistanceNode();
        Node sourceNode = mii_m_bridge.RequestParentNode();
        if (distanceNode == sourceNode){
            listener.onMoveFinished();
        }
        List<Node> headNode = mii_m_bridge.RequestHeadNode();
        List<Bookmark> headBookmark = mii_m_bridge.RequestHeadBookmark();
        Set<Integer> checkNodenum = mii_m_bridge.RequestCheckNodeNum();
        Set<Integer> checkBookmark = mii_m_bridge.RequestCheckBookmark();
        DatabaseHelper dbh = mii_m_bridge.RequestDbh();

        List<Node> beChosedNode = getBeChosedNode(sourceNode == null ? headNode : sourceNode.containNode, checkNodenum);
        List<Bookmark> beChosedBookmark = getBeChosedBookmark(sourceNode == null ? headBookmark : sourceNode.containBM, checkBookmark);

        for (Node node : beChosedNode){
            dbh.getNodeHelper().updatePreNodeNum(distanceNode==null?-1:distanceNode.nodeNum,node);
            node.preNodeNum = distanceNode==null?-1:distanceNode.nodeNum;
            node.preNode = distanceNode;
        }

        for (Bookmark bookmark : beChosedBookmark){
            dbh.getBookmarkHelper().updateBelongNodeNum(distanceNode==null?-1:distanceNode.nodeNum,bookmark);
            bookmark.belongNode = distanceNode;
            bookmark.belongNodeNum = distanceNode==null?-1:distanceNode.nodeNum;
        }
        /**
         * 更改在内存中的指向
         */
        List<Node> sourceNodeContainter = sourceNode==null?headNode:sourceNode.containNode;
        List<Bookmark> sourceBookmarkContainer = sourceNode==null?headBookmark:sourceNode.containBM;
        List<Node> distanceNodeContainer = distanceNode==null?headNode:distanceNode.containNode;
        List<Bookmark> distanceBookmarkContainer = distanceNode==null?headBookmark:distanceNode.containBM;

        for (Node node : beChosedNode){
            sourceNodeContainter.remove(node);
            distanceNodeContainer.add(node);
        }
        for (Bookmark bookmark : beChosedBookmark){
            sourceBookmarkContainer.remove(bookmark);
            distanceBookmarkContainer.add(bookmark);
        }
        listener.onMoveFinished();
    }

    private List<Bookmark> getBeChosedBookmark(List<Bookmark> bookmarks, Set<Integer> checkBookmark) {
        List<Bookmark> bookmarks1 = new ArrayList<Bookmark>();
        for (Bookmark bookmark : bookmarks)
            if (checkBookmark.contains(bookmark.bookmarkNum))
                bookmarks1.add(bookmark);
        return bookmarks1;
    }

    /**
     * 获取被选择节点的列表
     * @param nodes
     * @param checkNodenum
     * @return
     */
    private List<Node> getBeChosedNode(List<Node> nodes, Set<Integer> checkNodenum) {
        List<Node> node = new ArrayList<Node>();
        for (Node node1 : nodes){
            if (checkNodenum.contains(node1.nodeNum))
                node.add(node1);
        }
        return node;
    }

    @Override
    public void readyMove(OnMainFinishListener listener) {
        Set<Integer> checkNodenum = mii_m_bridge.RequestCheckNodeNum();
        Set<Integer> checkBookmark = mii_m_bridge.RequestCheckBookmark();

        if (checkBookmark.size() == 0 && checkNodenum.size() == 0)
            listener.onCheckNullError();
        else
            listener.onReadyMoveFinished();
    }

    private void deleteAllSelect(List<Node> nodes, List<Bookmark> bookmarks, Set<Integer> checkBookmarkNum, Set<Integer> checkNodeNum, DatabaseHelper dbh) {
        for (int i=0;i<bookmarks.size();i++){
            if (checkBookmarkNum.contains(bookmarks.get(i).bookmarkNum)){
                dbh.getBookmarkHelper().delete(bookmarks.get(i));
                bookmarks.remove(i);
                i--;
            }
        }

        for (int i=0;i<nodes.size();i++){
            if (checkNodeNum.contains(nodes.get(i).nodeNum)){
                deleteAllSelect(nodes.get(i).containNode,nodes.get(i).containBM,checkBookmarkNum,checkNodeNum,dbh);
                dbh.getNodeHelper().delete(nodes.get(i));
                nodes.remove(i);
                i--;
            }
        }

        return;
    }


    private void PaddingBookmark(List<Bookmark> bookmark, List<Node> headNode, List<Bookmark> headBookmark, Set<Integer> checkBookmarkNum) {
        for (int i=0;i<headBookmark.size();i++)
            if (checkBookmarkNum.contains(headBookmark.get(i).bookmarkNum))
                bookmark.add(headBookmark.get(i));
        for (int i=0;i<headNode.size();i++)
            PaddingBookmark(bookmark,headNode.get(i).containNode,headNode.get(i).containBM,checkBookmarkNum);
        return;
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

    private MII_M_Bridge mii_m_bridge;
    public interface MII_M_Bridge{
        List<Node> RequestHeadNode();
        List<Bookmark> RequestHeadBookmark();
        Set<Integer> RequestCheckNodeNum();
        Set<Integer> RequestCheckBookmark();

        Node RequestParentNode();

        DatabaseHelper RequestDbh();

        Node RequestDistanceNode();
    }

}
