package com.ylqhust.bookmarks.ui.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.ylqhust.bookmarks.R;

/**
 * Created by apple on 15/11/6.
 */
public class DeleteDialog {
    private Context context;
    private static DeleteDialog deleteDialog;
    private static Object lock = new Object();
    private AlertDialog deleteAD;

    private DeleteDialog(Context context,DD_ANYNOE_Bridge dd_anynoe_bridge){
        this.context = context;
        this.dd_anynoe_bridge = dd_anynoe_bridge;
        createDialog();
    }



    public static DeleteDialog getInstance(Context context,DD_ANYNOE_Bridge dd_anynoe_bridge){
        synchronized (lock){
            if (deleteDialog == null)
                deleteDialog = new DeleteDialog(context,dd_anynoe_bridge);
            return deleteDialog;
        }
    }

    private void createDialog() {
        if (deleteAD == null)
            deleteAD = new AlertDialog.Builder(context).setTitle(R.string.warnning)
                    .setMessage(R.string.warnningMessage).
                    setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DeleteDialog.this.dismiss();
                            dd_anynoe_bridge.DeleteYes();
                        }
                    }).
                    setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setCancelable(false).create();
    }

    private DD_ANYNOE_Bridge dd_anynoe_bridge;

    public interface DD_ANYNOE_Bridge{
        public void DeleteYes();
    }

    public void show(){deleteAD.show();}
    public void dismiss(){deleteAD.dismiss();}
}
