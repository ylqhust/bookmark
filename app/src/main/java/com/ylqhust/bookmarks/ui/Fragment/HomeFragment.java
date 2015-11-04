package com.ylqhust.bookmarks.ui.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ylqhust.bookmarks.R;
import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;
import com.ylqhust.bookmarks.presenter.Impl.HomeFragPresenterImpl;
import com.ylqhust.bookmarks.presenter.Interface.HomeFragPresenter;
import com.ylqhust.bookmarks.ui.AlertDialog.ChoseDialog;
import com.ylqhust.bookmarks.ui.Fragment.Interface.MHBridge;
import com.ylqhust.bookmarks.ui.adapter.HomeFragRVAdapter;
import com.ylqhust.bookmarks.view.HomeFragView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeFragView,MHBridge, ChoseDialog.CDHFBridge {

    private HomeFragPresenter presenter;
    private List<Node> headNode;
    private List<Bookmark> headBookmark;
    private Node parentNode;

    private RelativeLayout relativeLayout;
    private FrameLayout frameLayout;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private HMBridge hmBridge;

    public HomeFragment() {
        // Required empty public constructor
    }
    public void setData(List<Node> headNode,List<Bookmark> headBookmark){
        this.headNode = headNode;
        this.headBookmark = headBookmark;
        this.parentNode = null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        this.presenter = new HomeFragPresenterImpl(this);
        if (parentNode == null)
            showData(headNode,headBookmark);
        else
            showData(parentNode.containNode,parentNode.containBM);
        return view;
    }

    private void initView(View view) {
        this.relativeLayout = (RelativeLayout) view.findViewById(R.id.fragment_home_relativelayout);
        this.frameLayout = (FrameLayout) view.findViewById(R.id.fragment_home_container);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
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
    public void showData(List<Node> currentPageNode, List<Bookmark> currentPageBK) {
        if (currentPageBK.size() == 0 && currentPageNode.size() == 0){
            relativeLayout.setVisibility(View.VISIBLE);
        }
        else{
            relativeLayout.setVisibility(View.GONE);
        }
        HomeFragRVAdapter adapter = new HomeFragRVAdapter(currentPageBK,
                currentPageNode,
                presenter,getContext(),parentNode);
        recyclerView.setAdapter(adapter);
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
        showData(currentPageNode, currentPageBookmark);
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
        showData(currentPageNode, currentPageBookmark);
        this.hmBridge.onAddBookmarkFinished();
    }

    @Override
    public void onStepInFinished(List<Node> containNode, List<Bookmark> containBM, Node preNode) {
        this.parentNode = preNode;
        showData(containNode, containBM);
    }

    @Override
    public void onStepBackFinished(Node preNode) {
        this.parentNode = preNode;
        if (preNode == null)
            showData(headNode,headBookmark);
        else
            showData(preNode.containNode,preNode.containBM);
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
     * 改变菜单
     * @param flag
     */
    @Override
    public void changeMenu(boolean flag) {
        hmBridge.changeMenu(flag);

    }

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
        showData(currentPageNode,currentPageBookmark);
    }

    /**
     * 修改完成
     * @param currentPageNode
     * @param currentPageBookmark
     */
    @Override
    public void onModifyFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark) {
        showData(currentPageNode,currentPageBookmark);
    }

    public interface HMBridge{
        public void onNodeNameError(String errorMessage);
        public void onAddNodeFinished();
        public void onBookmarkUrlError(String errorMessage);
        public void onBookmarkSameUrl(String sameUrlMessage, View.OnClickListener onClickListener);
        public void onAddBookmarkFinished();
        public DatabaseHelper requestDbh();
        void changeMenu(boolean flag);
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
    @Override
    public void addNode(String nodeName, String nodeDes, DatabaseHelper dbh){
        presenter.addNode(nodeName,nodeDes,parentNode,headNode,headBookmark,dbh);
    }

    /**
     * 被MainActivity 调用，添加书签
     * @param url
     * @param title
     * @param shortcuturl
     */
    @Override
    public void addBookmark(String url, String title, String shortcuturl, DatabaseHelper dbh) {
        presenter.addBookmark(url, title, shortcuturl, parentNode, headNode, headBookmark, dbh);
    }
}
