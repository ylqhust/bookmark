package com.ylqhust.bookmarks.utils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by apple on 15/10/31.
 */
public class Utils {
    public static String getClipboardText(Context context){
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        ClipDescription cdes = clipboardManager.getPrimaryClipDescription();
        if (cdes != null && cdes.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
            ClipData cd = clipboardManager.getPrimaryClip();
            System.out.println(cd.getItemAt(0).toString());
            return cd.getItemAt(0).getText().toString();
        }
        return "";
    }

    public static void sort(Object[] objects,Compare compare){
        int n = objects.length;
        for (int i=0;i<n-1;i++)
            for (int j=0;j<n-1-i;j++){
                if (compare.compare(objects[j],objects[j+1])){
                    Object tmp = objects[j];
                    objects[j] = objects[j+1];
                    objects[j+1] = tmp;
                }
            }
        return;
    }
}
