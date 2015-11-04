package com.ylqhust.bookmarks.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylqhust.bookmarks.R;

import java.util.List;

/**
 * Created by apple on 15/11/3.
 */
public class SearchHistoryAdapter extends BaseAdapter{

    List<String> searchHistory;
    LayoutInflater inflater;
    private SHAMBridge shamBridge;
    public void setShamBridge(SHAMBridge shamBridge){
        this.shamBridge = shamBridge;
    }

    public SearchHistoryAdapter(LayoutInflater inflater, List<String> searchHistory) {
        this.inflater = inflater;
        this.searchHistory = searchHistory;
    }


    @Override
    public int getCount() {
        return searchHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.search_lv_item,null);
        final TextView string = (TextView) view.findViewById(R.id.search_lv_item_tv_string);
        string.setText(searchHistory.get(position));
        TextView clear = (TextView) view.findViewById(R.id.search_lv_item_tv_clear);
        string.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shamBridge.choseHistory(searchHistory.get(position));
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shamBridge.clearHistory(position,searchHistory);
            }
        });
        return view;
    }

    public interface SHAMBridge{
        public void choseHistory(String beChosedString);
        public void clearHistory(int position,List<String> strings);
    }
}
