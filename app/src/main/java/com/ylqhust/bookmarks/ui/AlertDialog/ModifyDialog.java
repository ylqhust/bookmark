package com.ylqhust.bookmarks.ui.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ylqhust.bookmarks.R;
import com.ylqhust.bookmarks.data.database.DatabaseHelper;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;
import com.ylqhust.bookmarks.mvp.presenter.Impl.ModifyDialogPresenterImpl;
import com.ylqhust.bookmarks.mvp.presenter.Interface.ModifyDialogPresenter;
import com.ylqhust.bookmarks.ui.widget.WidgetUtils;
import com.ylqhust.bookmarks.mvp.view.ModifyDialogView;

import java.util.List;

/**
 * Created by apple on 15/11/4.
 */
public class ModifyDialog implements ModifyDialogView {
    private static ModifyDialog modifyDialog;
    private Context context;
    private static Object lock = new Object();
    private AlertDialog modifyAD;
    private ModifyDialogPresenter presenter;


    private ModifyDialog(Context context){
        this.context = context;
        this.presenter = new ModifyDialogPresenterImpl(this);
        createDialog();
    }

    private View modifyView;
    private void createDialog() {
        modifyView = LayoutInflater.from(context).inflate(R.layout.modify, null);
        modifyAD = new AlertDialog.Builder(context).setView(modifyView).
                setTitle(R.string.rename).setCancelable(false).create();
        modifyAD.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
                    dismiss();
                }
                return false;
            }
        });
        initView();
    }

    private EditText et_rename;
    private TextView tv_cancle;
    private TextView tv_ok;


    private void initView() {
        et_rename = (EditText) modifyView.findViewById(R.id.modify_et_rename);
        tv_cancle = (TextView) modifyView.findViewById(R.id.modify_tv_cancle);
        tv_ok = (TextView) modifyView.findViewById(R.id.modify_tv_ok);
        WidgetUtils.ClearEditText(et_rename);
        bindOnClickListener();
    }

    /**
     * 绑定点击事件
     */
    private void bindOnClickListener() {
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = et_rename.getText().toString();
                presenter.modify(newname,parentNode,bookmark,headNode,headBookmark,mdcdBridge.requestDbh());
            }
        });
    }

    public static ModifyDialog getInstance(Context context){
        synchronized (lock){
            if (modifyDialog == null){
                modifyDialog = new ModifyDialog(context);
            }
            return modifyDialog;
        }
    }




    public void show(){
        et_rename.setText(bookmark.title);
        modifyAD.show();
    }
    @Override
    public void dismiss(){
        modifyAD.dismiss();
    }

    /**
     * 修改完成
     * @param currentPageNode
     * @param currentPageBookmark
     */
    @Override
    public void onModifyFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark) {
        mdcdBridge.onModifyFinished(currentPageNode,currentPageBookmark);
    }

    private Node parentNode;
    private Bookmark bookmark;
    private List<Node> headNode;
    private List<Bookmark> headBookmark;
    public void setData(Node parentNode, Bookmark bookmark, List<Bookmark> headBookmark, List<Node> headNode) {
        this.parentNode = parentNode;
        this.bookmark = bookmark;
        this.headBookmark = headBookmark;
        this.headNode = headNode;
    }


    private MDCDBridge mdcdBridge;
    public interface MDCDBridge{
        public DatabaseHelper requestDbh();
        void onModifyFinished(List<Node> currentPageNode, List<Bookmark> currentPageBookmark);
    }
    public void setMdcdBridge(MDCDBridge mdcdBridge){
        this.mdcdBridge = mdcdBridge;
    }
}
