package com.ylqhust.bookmarks.ui.other;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.ylqhust.bookmarks.R;

/**
 * Created by apple on 15/11/4.
 */
public class HMToolsBar {
    private Context context;
    private HMToolsBar(Context context){
        this.context = context;
        initView();
    }

    private static HMToolsBar hmToolsBar;
    private static Object lock = new Object();

    public static HMToolsBar getInstance(Context context){
        synchronized (lock) {
            if (hmToolsBar == null) {
                hmToolsBar = new HMToolsBar(context);
            }
            return hmToolsBar;
        }
    }

    private View toolsBarView;
    private RelativeLayout relaDelete;
    private RelativeLayout relaMove;
    private RelativeLayout relaSend;
    private RelativeLayout relaShare;
    private void initView() {
        toolsBarView = LayoutInflater.from(context).inflate(R.layout.tools, null);
        relaDelete = (RelativeLayout) toolsBarView.findViewById(R.id.tools_rela_delete);
        relaMove = (RelativeLayout) toolsBarView.findViewById(R.id.tools_rela_move);
        relaSend = (RelativeLayout) toolsBarView.findViewById(R.id.tools_rela_send);
        relaShare = (RelativeLayout) toolsBarView.findViewById(R.id.tools_rela_share);
        bindOnClickListener();
        bindOnTouchListener(relaMove);
        bindOnTouchListener(relaDelete);
        bindOnTouchListener(relaSend);
        bindOnTouchListener(relaShare);
    }

    private void bindOnTouchListener(final RelativeLayout rela) {
        rela.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    rela.setBackgroundColor(Color.RED);
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    rela.setBackgroundColor(Color.argb(0xff,0x03,0xa9,0xf4));
                }
                return false;
            }
        });
    }

    private void bindOnClickListener() {
        relaMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        relaShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        relaDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        relaSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public View getToolsBarView(){return toolsBarView;}
}
