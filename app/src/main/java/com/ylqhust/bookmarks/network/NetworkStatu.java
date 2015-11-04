package com.ylqhust.bookmarks.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by apple on 15/11/1.
 */
public class NetworkStatu {
    private Context context;
    private static NetworkStatu netWorkStatu;
    private static Object locked = new Object();
    private NetworkStatu(Context context){
        this.context = context;
    }

    public static NetworkStatu getInstance(Context context){
        synchronized (locked){
            if (netWorkStatu == null){
                netWorkStatu = new NetworkStatu(context);
            }
            return netWorkStatu;
        }
    }


    public boolean isNetworkConnected(){
        if (context != null){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null){
                return info.isAvailable();
            }
        }
        return false;
    }


    public int getConnectedType(){
        if (context != null){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null){
                return 0;
            }
            return info.getType();
        }
        return 0;
    }
}
