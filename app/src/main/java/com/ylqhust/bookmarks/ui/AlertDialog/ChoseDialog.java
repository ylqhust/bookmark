package com.ylqhust.bookmarks.ui.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylqhust.bookmarks.R;
import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Impl.ChoseDialogPresenterImpl;
import com.ylqhust.bookmarks.mvp.presenter.Interface.ChoseDialogPresenter;
import com.ylqhust.bookmarks.mvp.view.ChoseDialogView;

import java.util.List;


/**
 * Created by apple on 15/11/3.
 */
public class ChoseDialog implements ChoseDialogView,ModifyDialog.MDCDBridge{
    private static ChoseDialog choseDialog;
    private Context context;
    private static Object lock = new Object();
    private AlertDialog choseAD;
    private ChoseDialogPresenter presenter;
    private CDHFBridge cdhfBridge;
    private ChoseDialog(Context context){
        this.context = context;
        this.presenter = new ChoseDialogPresenterImpl(this);
        createDialog();
    }

    private View choseView;
    private RelativeLayout rela_view;
    private RelativeLayout rela_send;
    private RelativeLayout rela_delete;
    private RelativeLayout rela_modify;
    private TextView tv_view;
    private TextView tv_send;
    private TextView tv_delete;
    private TextView tv_modify;

    private void createDialog() {
        choseView = LayoutInflater.from(context).inflate(R.layout.chose, null);
        choseAD = new AlertDialog.Builder(context).setView(choseView).create();
        initView();
        bindOnClickListener();
        bindOnTouchListener(rela_view, tv_view);
        bindOnTouchListener(rela_send, tv_send);
        bindOnTouchListener(rela_delete, tv_delete);
        bindOnTouchListener(rela_modify,tv_modify);
    }

    private void bindOnTouchListener(final RelativeLayout rela, final TextView tv) {
        rela.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    changeBackground(rela,tv);
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    resetBackground(rela,tv);
                }
                return false;
            }
        });
    }

    private void bindOnClickListener() {
        rela_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = bookmark.url;
                Uri uri = Uri.parse(url);
                Intent i_view = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(i_view);
            }
        });

        rela_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = bookmark.title;
                String url = bookmark.url;
                System.out.println("URL:" + url);
                Intent send = new Intent(Intent.ACTION_SEND);
                send.putExtra(Intent.EXTRA_TEXT, title+"\n"+url);
                send.setType("text/plain");
                context.startActivity(send);
            }
        });
        rela_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.delete(parentNode, bookmark, cdhfBridge.requestDbh(), headNode, headBookmark);
            }
        });
        rela_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.modify(parentNode, bookmark, headNode,headBookmark);
            }
        });
    }

    /**
     * 重置背景色
     * @param view
     * @param tv_view
     */
    private void resetBackground(RelativeLayout view, TextView tv_view) {
        view.setBackgroundColor(Color.WHITE);
        tv_view.setTextColor(Color.GRAY);
    }

    /**
     * 当用户点击relativelayout时改变背景
     * @param view
     */
    private void changeBackground(RelativeLayout view,TextView tv) {
        view.setBackgroundColor(Color.argb(0xff,0x03,0xa9,0xf4));
        tv.setTextColor(Color.argb(0xff,0xff,0xff,0xff));
    }

    private void initView() {
        rela_view = (RelativeLayout) choseView.findViewById(R.id.chose_rela_view);
        rela_send = (RelativeLayout) choseView.findViewById(R.id.chose_rela_send);
        rela_delete = (RelativeLayout) choseView.findViewById(R.id.chose_rela_delete);
        rela_modify = (RelativeLayout) choseView.findViewById(R.id.chose_rela_modify);
        tv_view = (TextView) rela_view.findViewById(R.id.chose_tv_view);
        tv_send = (TextView) rela_send.findViewById(R.id.chose_tv_send);
        tv_delete = (TextView) rela_delete.findViewById(R.id.chose_tv_delete);
        tv_modify = (TextView) rela_modify.findViewById(R.id.chose_tv_modify);
    }

    public static ChoseDialog getInstance(Context context){
        synchronized (lock){
            if (choseDialog == null){
                choseDialog = new ChoseDialog(context);
            }
            return choseDialog;
        }
    }


    private Node parentNode;
    private Bookmark bookmark;
    private List<Node> headNode;
    private List<Bookmark> headBookmark;

    public void setData(Node parentNode,Bookmark bookmark,List<Node> headNode,List<Bookmark> headBookmark){
        this.parentNode = parentNode;
        this.bookmark = bookmark;
        this.headBookmark = headBookmark;
        this.headNode = headNode;
    }

    @Override
    public void show(){
        choseAD.show();
    }

    @Override
    public void dismiss(){
        choseAD.dismiss();
    }

    /**
     * 书签删除完成
     * @param currentPageNode
     * @param currentPageBookmark
     */
    @Override
    public void onDeleteFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark) {
        cdhfBridge.onDeleteFinished(currentPageNode,currentPageBookmark);
    }

    /**
     * 显示
     * @param parentNode
     * @param bookmark
     * @param headBookmark
     * @param headNode
     */
    @Override
    public void showMoidfyDialog(Node parentNode, Bookmark bookmark, List<Bookmark> headBookmark, List<Node> headNode) {
        ModifyDialog modifyDialog = ModifyDialog.getInstance(context);
        modifyDialog.setData(parentNode,bookmark,headBookmark,headNode);
        modifyDialog.setMdcdBridge(this);
        modifyDialog.show();
    }

    @Override
    public DatabaseHelper requestDbh() {
        return cdhfBridge.requestDbh();
    }

    @Override
    public void onModifyFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark) {
        cdhfBridge.onModifyFinished(currentPageNode,currentPageBookmark);
    }

    public interface CDHFBridge{
        DatabaseHelper requestDbh();
        void onDeleteFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark);
        void onModifyFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark);
    }

    public void setCDHFBridge(CDHFBridge cdhfBridge){
        this.cdhfBridge = cdhfBridge;
    }
}
