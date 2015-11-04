package com.ylqhust.bookmarks.ui.widget;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by apple on 15/10/31.
 */
public class WidgetUtils {
    public static void ClearEditText(final EditText editText){

        final int DRAWABLE_RIGHT = 2;

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                        Drawable drawableRight = editText.getCompoundDrawables()[DRAWABLE_RIGHT] ;
                        if (drawableRight != null && event.getRawX() >= (editText.getRight() - drawableRight.getBounds().width())) {
                            editText.setText("");
                        }
                }
                return editText.onTouchEvent(event);
            }
        });
    }

    public static void ADKL(final AlertDialog alertDialog){
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    alertDialog.dismiss();
                    return true;
                }
                return false;
            }
        });
    }
}
