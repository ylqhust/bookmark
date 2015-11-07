package com.ylqhust.bookmarks.ui.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ylqhust.bookmarks.R;
import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Impl.HomeFragPresenterImpl;
import com.ylqhust.bookmarks.mvp.presenter.Interface.HomeFragPresenter;
import com.ylqhust.bookmarks.ui.AlertDialog.ChoseDialog;
import com.ylqhust.bookmarks.ui.adapter.HomeFragRV2Adapter;
import com.ylqhust.bookmarks.ui.adapter.HomeFragRVAdapter;
import com.ylqhust.bookmarks.mvp.view.HomeFragView;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeFragView, ChoseDialog.CDHFBridge,HomeFragRVAdapter.HFRVAHFBridge,HomeFragRV2Adapter.HFRV2_HF_Bridge {

    private HomeFragPresenter presenter;
    private List<Node> headNode;
    private List<Bookmark> headBookmark;
    private Node parentNode = null;
    private Node parentNode2 = null;

    private View hmView;
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ProgressDialog progressDialog;
    private HMBridge hmBridge;

    private boolean isLongClicked = false;
    private Set<Integer> checkedNodeNum = new HashSet<Integer>();
    private Set<Integer> checkedBookmarkNum = new HashSet<Integer>();
    private HomeFragRVAdapter adapter;
    private HomeFragRV2Adapter adapter2;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (hmView == null){
            hmView = inflater.inflate(R.layout.fragment_home, container, false);
            initView(hmView);
            this.presenter = new HomeFragPresenterImpl(this);
        }
        if (parentNode == null)
            showData(headNode==null?hmBridge.RequestHeadNode():headNode,headBookmark==null?hmBridge.RequestHeadBookmark():headBookmark,parentNode );
        else
            showData(parentNode.containNode,parentNode.containBM, parentNode);
        return hmView;
    }

    private void initView(View view) {
        this.relativeLayout = (RelativeLayout) view.findViewById(R.id.fragment_home_relativelayout);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        this.recyclerView2 = (RecyclerView) view.findViewById(R.id.fragment_home_recyclerview2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setHasFixedSize(true);
        progressDialog = new ProgressDialog(getContext());
    }


    @Override
    public void showPD() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载数据...");
        progressDialog.show();
    }

    @Override
    public void dismissPD() {
        progressDialog.dismiss();
    }

    @Override
    public void showData(List<Node> currentPageNode, List<Bookmark> currentPageBK, Node parentnode) {
        if (currentPageBK.size() == 0 && currentPageNode.size() == 0){
            relativeLayout.setVisibility(View.VISIBLE);
        }
        else{
            relativeLayout.setVisibility(View.GONE);
        }
        adapter = new HomeFragRVAdapter(currentPageBK,
                currentPageNode,
                presenter,getContext(),parentnode);
        adapter.setHfrvahfBridge(this);
        recyclerView.setAdapter(adapter);
    }

    private void showData2(List<Node> containNode, List<Bookmark> containBM, Node nodeBeClicked) {
        if (containBM.size() == 0 && containNode.size() ==0){
            relativeLayout.setVisibility(View.VISIBLE);
        }
        else{
            relativeLayout.setVisibility(View.GONE);
        }
        adapter2 = new HomeFragRV2Adapter(getContext(),containBM,containNode,nodeBeClicked,this);
        recyclerView2.setAdapter(adapter2);
    }

    /**
     * 添加节点时的重名错误
     */
    @Override
    public void onNodeNameError(String errorMessage) {
        hmBridge.onNodeNameError(errorMessage);
    }

    /**
     * 节点添加成功
     * @param currentPageNode
     * @param currentPageBookmark
     * @param node
     */

    @Override
    public void onAddNodeFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark, Node node) {
        showData(currentPageNode, currentPageBookmark, parentNode);
        this.hmBridge.onAddNodeFinished();
    }

    /**
     * 书签url错误
     * @param errorMessage
     */
    @Override
    public void onBookmarkUrlError(String errorMessage) {
        this.hmBridge.onBookmarkUrlError(errorMessage);
    }

    /**
     * 存在相同URl的书签
     * @param sameUrlMessage
     * @param onClickListener
     */
    @Override
    public void onBookmarkSameUrl(String sameUrlMessage, View.OnClickListener onClickListener) {
        this.hmBridge.onBookmarkSameUrl(sameUrlMessage, onClickListener);
    }

    /**
     * 添加书签完成
     * @param currentPageNode
     * @param currentPageBookmark
     * @param bookmark
     */
    @Override
    public void onAddBookmarkFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark, Bookmark bookmark) {
        showData(currentPageNode, currentPageBookmark,parentNode );
        this.hmBridge.onAddBookmarkFinished();
    }

    @Override
    public void onStepInFinished(List<Node> containNode, List<Bookmark> containBM, Node preNode) {
        this.parentNode = preNode;
        showData(containNode, containBM,parentNode);
    }

    @Override
    public void onStepBackFinished(Node preNode) {
        this.parentNode = preNode;
        if (preNode == null)
            showData(headNode,headBookmark, parentNode);
        else
            showData(preNode.containNode,preNode.containBM,parentNode );
    }

    /**
     * 显示选项Dialog
     * @param parentNode
     * @param bookmark
     */
    @Override
    public void showChoseDialog(Node parentNode, Bookmark bookmark) {
        ChoseDialog choseDialog = ChoseDialog.getInstance(getContext());
        choseDialog.setData(parentNode, bookmark, headNode, headBookmark);
        choseDialog.setCDHFBridge(this);
        choseDialog.show();
    }

    /**
     * 隐藏第一个RecyclerView
     *
     */
    @Override
    public void hideRecyclerView1() {
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * 显示第二个RecyclerView
     */
    @Override
    public void showRecyclerView2() {
        recyclerView2.setVisibility(View.VISIBLE);
        parentNode2 = parentNode;
        if (parentNode2 == null)
            showData2(headNode,headBookmark,parentNode2);
        else
            showData2(parentNode2.containNode, parentNode2.containBM, parentNode2);
    }

    /**
     * 改变菜单
     * @param flag
     */
    /**
    @Override
    public void changeMenu(boolean flag) {
        hmBridge.
    }
    **/
    /**
     * 请求使用dbh,被ChoseDialog调用
     * @return
     */
    @Override
    public DatabaseHelper requestDbh() {
        return hmBridge.requestDbh();
    }

    /**
     * 书签删除完成
     * @param currentPageNode
     * @param currentPageBookmark
     */
    @Override
    public void onDeleteFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark) {
        showData(currentPageNode, currentPageBookmark,parentNode );
    }

    /**
     * 修改完成
     * @param currentPageNode
     * @param currentPageBookmark
     */
    @Override
    public void onModifyFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark) {
        showData(currentPageNode,currentPageBookmark,parentNode );
    }

    public void setData(List<Node> headNode, List<Bookmark> headBookmark) {
        this.headNode = headNode;
        this.headBookmark = headBookmark;
        this.parentNode = null;
    }


    @Override
    public void LongClickHappend() {
        isLongClicked = true;
        hmBridge.LongClickHappend();
    }

    @Override
    public boolean RequestIsLongClicked() {
        return isLongClicked;
    }

    @Override
    public boolean RequestThisBookmarkIfChecked(int bookmarkNum) {
        return checkedBookmarkNum==null?false:checkedBookmarkNum.contains(bookmarkNum);
    }

    /**
     * 当要移动时，选择节点时会触发此方法
     * @param nodeBeClicked
     */
    @Override
    public void NodeBeClicked(Node nodeBeClicked) {
        parentNode2 = nodeBeClicked;
        showData2(nodeBeClicked.containNode,nodeBeClicked.containBM,nodeBeClicked);
    }



    /**
     * 当要移动时，按返回按钮时会触发此方法
     */
    @Override
    public void BackBeClicked() {
        parentNode2 = parentNode2.preNode;
        if (parentNode2 == null){
            showData2(headNode,headBookmark,null);
        }
        else{
            showData2(parentNode2.containNode, parentNode2.containBM, parentNode2);
        }
    }

    @Override
    public boolean RequestThisNodeIfChecked(int nodeNum) {
        return checkedNodeNum==null?false:checkedNodeNum.contains(nodeNum);
    }

    /**
     * 通知Fragment一个bookmark的check状态改变了
     * @param bookmarkNum
     * @param isChecked
     */
    @Override
    public void OneBookmarkCheckChangeHappend(int bookmarkNum, boolean isChecked) {
        if(isChecked){
            checkedBookmarkNum.add(bookmarkNum);
        }
        else{
            checkedBookmarkNum.remove(new Integer(bookmarkNum));
        }

    }

    @Override
    public void OneNodeCheckChangeHappend(int nodeNum, boolean isChecked) {
        if (headBookmark == null)
            headBookmark = hmBridge.RequestHeadBookmark();
        if (headNode == null)
            headNode = hmBridge.RequestHeadNode();
        if (isChecked){
            checkedNodeNum.add(nodeNum);
            checkedNodeNum.addAll(getAllChildNode(findNode(headNode,nodeNum)));
            checkedBookmarkNum.addAll(getAllChildBookmark(findNode(headNode, nodeNum)));
        }
        else{
            checkedNodeNum.remove(new Integer(nodeNum));
            checkedNodeNum.removeAll(getAllChildNode(findNode(headNode,nodeNum)));
            checkedBookmarkNum.removeAll(getAllChildBookmark(findNode(headNode, nodeNum)));
        }
    }

    @Override
    public boolean RequestIsSelectAll() {
        return hmBridge.RequestIsSelectAll();
    }

    private Collection<Integer> getAllChildBookmark(Node thisNode) {
        Collection<Integer> integers = new HashSet<Integer>();
        if (thisNode == null)
            return integers;
        for (int i=0;i<thisNode.containBM.size();i++)
            integers.add(thisNode.containBM.get(i).bookmarkNum);
        for (int i=0;i<thisNode.containNode.size();i++)
            integers.addAll(getAllChildBookmark(thisNode.containNode.get(i)));
        return integers;
    }



    private Collection<Integer> getAllChildNode(Node thisNode) {
        Collection<Integer> integers = new HashSet<Integer>();
        if (thisNode == null)
            return integers;
        for (int i=0;i<thisNode.containNode.size();i++){
            integers.add(thisNode.containNode.get(i).nodeNum);
            integers.addAll(getAllChildNode(thisNode.containNode.get(i)));
        }
        return integers;
    }

    /**
     * 找到与编号对应的nodeNum;
     * @param nodeNum
     * @return
     */
    private Node findNode(List<Node> nodes,int nodeNum) {
        if (nodes == null || nodes.size() == 0)return null;
        for (int i=0;i<nodes.size();i++)
            if (nodes.get(i).nodeNum == nodeNum)
                return nodes.get(i);
        for (int i=0;i<nodes.size();i++){
            if (findNode(nodes.get(i).containNode,nodeNum) != null)
                return findNode(nodes.get(i).containNode,nodeNum);
        }
        return null;
    }

    /**
     * 重置Fragment，被MainActivity调用
     */
    public void resetPage() {
        this.isLongClicked = false;
        this.checkedNodeNum = new HashSet<Integer>();
        this.checkedBookmarkNum = new HashSet<Integer>();
        adapter.resetPage();
    }


    /**
     * 主界面的全选按钮的状态发生了改变，通知HomeFragment
     */
    public void IsSelectAllStatuChangeHappend() {
        if (parentNode == null){
            if (headNode == null || headBookmark == null)
                changeAll(hmBridge.RequestHeadNode(),hmBridge.RequestHeadBookmark());
            else
                changeAll(headNode,headBookmark);
        }
        else
            changeAll(parentNode.containNode,parentNode.containBM);
        adapter.changeCheckStatu();
    }

    /**
     * 改变当前界面中的所有checkBox的选择状态
     * @param containNode
     * @param containBM
     */
    private void changeAll(List<Node> containNode, List<Bookmark> containBM) {
        boolean isSelectAll = hmBridge.RequestIsSelectAll();
        if (isSelectAll){
            for (int i=0;i<containNode.size();i++){
                checkedNodeNum.add(containNode.get(i).nodeNum);
                checkedNodeNum.addAll(getAllChildNode(containNode.get(i)));
                checkedBookmarkNum.addAll(getAllChildBookmark(containNode.get(i)));
            }

            for (int i=0;i<containBM.size();i++)
                checkedBookmarkNum.add(containBM.get(i).bookmarkNum);
        }
        else{
            checkedBookmarkNum = new HashSet<Integer>();
            checkedNodeNum = new HashSet<Integer>();
        }

    }

    public Set<Integer> getCheckedNodeNum() {
        return checkedNodeNum;
    }

    public Set<Integer> getCheckedBookmarkNum(){
        return checkedBookmarkNum;
    }

    public Node getParentNode() {
        return parentNode;
    }

    /**
     * 被MainActivity调用，用来改变RecyclerView中元素的状态
     */
    public void ReadyMove() {
        presenter.ReadyMove();
    }

    /**
     * 被MainActivity调用，取消移动，用来改变RecyclerView 的显示状态
     */
    public void cancleMove() {
        recyclerView2.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * 被MainActivity调用，通知移动结束了
     */
    public void onMoveFinished() {
        checkedBookmarkNum = new HashSet<Integer>();
        checkedNodeNum = new HashSet<Integer>();
        isLongClicked = false;
        hideRecyclerView2();
        if (parentNode2 == null)
            showData(headNode,headBookmark,parentNode2);
        else
            showData(parentNode2.containNode,parentNode2.containBM,parentNode2);
        showRecyclerView1();
    }

    private void showRecyclerView1() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void hideRecyclerView2() {
        recyclerView2.setVisibility(View.GONE);
    }

    /**
     * 获取移动目的的节点
     */
    public Node getDistanceNode() {
        return parentNode2;
    }

    public interface HMBridge{
        public void onNodeNameError(String errorMessage);
        public void onAddNodeFinished();
        public void onBookmarkUrlError(String errorMessage);
        public void onBookmarkSameUrl(String sameUrlMessage, View.OnClickListener onClickListener);
        public void onAddBookmarkFinished();
        public DatabaseHelper requestDbh();

        List<Node> RequestHeadNode();

        List<Bookmark> RequestHeadBookmark();

        boolean RequestIsSelectAll();
        void LongClickHappend();
    }
    public void setBridge(HMBridge hmBridge){
        this.hmBridge = hmBridge;
    }

    /**
     * 被MainActivity调用，添加节点
     * @param nodeName
     * @param nodeDes
     * @param dbh
     */
    public void addNode(String nodeName, String nodeDes, DatabaseHelper dbh){
        presenter.addNode(nodeName, nodeDes, parentNode, headNode, headBookmark, dbh);
    }

    /**
     * 被MainActivity 调用，添加书签
     * @param url
     * @param title
     * @param shortcuturl
     */
    public void addBookmark(String url, String title, String shortcuturl, DatabaseHelper dbh) {
        presenter.addBookmark(url, title, shortcuturl, parentNode, headNode, headBookmark, dbh);
    }


}
