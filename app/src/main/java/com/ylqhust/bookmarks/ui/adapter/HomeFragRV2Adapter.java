package com.ylqhust.bookmarks.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylqhust.bookmarks.R;
import com.ylqhust.bookmarks.mvp.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.mvp.model.dataModel.Node;

import java.util.List;

/**
 * Created by apple on 15/11/7.
 */
public class HomeFragRV2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Node> currentPageNode;
    private List<Bookmark> currentPageBookmark;
    private Node parentNode;
    private Context context;
    private HFRV2_HF_Bridge hfrv2_hf_bridge;

    private final int BOOKMARK = 0x1;
    private final int NODE = 0x2;
    private final int BACK = 0x3;

    public HomeFragRV2Adapter(Context context, List<Bookmark> currentPageBookmark, List<Node> currentPageNode, Node parentNode,
    HFRV2_HF_Bridge hfrv2_hf_bridge) {
        this.context = context;
        this.currentPageBookmark = currentPageBookmark;
        this.currentPageNode = currentPageNode;
        this.parentNode = parentNode;
        this.hfrv2_hf_bridge = hfrv2_hf_bridge;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NODE)
            return new NodeHolder(parent.inflate(context,R.layout.node,null));
        else if (viewType == BACK)
            return new BackHolder(parent.inflate(context,R.layout.back,null));
        return new BookmarkHolder(parent.inflate(context,R.layout.bookmark,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NodeHolder){
            bindNodeHolder((NodeHolder)holder,position);
        }
        else if (holder instanceof BookmarkHolder){
            bindBookmarkHolder((BookmarkHolder)holder,position);
        }
        else{
            bindBackHolder((BackHolder)holder,position);
        }
        return;
    }

    private void bindBackHolder(BackHolder holder, int position) {
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hfrv2_hf_bridge.BackBeClicked();
            }
        });
    }

    private void bindBookmarkHolder(BookmarkHolder holder, int position) {
        if (currentPageBookmark.size() == 0)
            return;
        final Bookmark bookmark = currentPageBookmark.get(position-currentPageNode.size()-(parentNode==null?0:1));
        final String url = bookmark.url;
        String title = bookmark.title;
        holder.bookmarkUrl.setText(url);
        holder.bookmarkTitle.setText(title);

        boolean isCheckd = hfrv2_hf_bridge.RequestThisBookmarkIfChecked(bookmark.bookmarkNum);
        if (isCheckd){
            holder.linearlayout.setBackgroundColor(Color.RED);
        }
    }

    private void bindNodeHolder(NodeHolder holder, int position) {
        if (currentPageNode.size() == 0)
            return;
        final Node node = currentPageNode.get(position-(parentNode==null?0:1));

        final String nodeName = node.nodeName;
        String nodeDes = node.nodeDes;
        String bookmarkCount = getBookmarkCount(node)+"";

        holder.nodeName.setText(nodeName);
        holder.nodeDes.setText(nodeDes);
        holder.bookmarkCount.setText(bookmarkCount);

        boolean isChecked = hfrv2_hf_bridge.RequestThisNodeIfChecked(node.nodeNum);
        if (isChecked){
            holder.relativeLayout.setBackgroundColor(Color.RED);
        }
        else{
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hfrv2_hf_bridge.NodeBeClicked(node);
                }
            });
        }
    }

    private int getBookmarkCount(Node node) {
        if (node.containNode.size() == 0)
            return node.containBM.size();
        else{
            int bookmarks = node.containBM.size();
            int childs = 0;
            for (int i=0;i<node.containNode.size();i++)
                childs += getBookmarkCount(node.containNode.get(i));
            return bookmarks + childs;
        }
    }


    @Override
    public int getItemCount() {
        return currentPageNode.size()+currentPageBookmark.size()+(parentNode==null?0:1);
    }

    @Override
    public int getItemViewType(int position){
        if (position == 0 && parentNode != null)
            return BACK;
        if (position<(currentPageNode.size()+(parentNode==null?0:1)))
            return NODE;
        return BOOKMARK;
    }



    public class NodeHolder extends RecyclerView.ViewHolder{
        TextView nodeName;
        TextView nodeDes;
        TextView bookmarkCount;
        RelativeLayout relativeLayout;
        CheckBox checkBox;
        public NodeHolder(View itemView) {
            super(itemView);
            nodeName = (TextView) itemView.findViewById(R.id.node_nodeName);
            nodeDes = (TextView) itemView.findViewById(R.id.node_nodeDes);
            bookmarkCount = (TextView) itemView.findViewById(R.id.node_bookmarkCount);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.node_relativelayout);
            checkBox = (CheckBox) itemView.findViewById(R.id.node_check);
        }
    }

    public class BookmarkHolder extends RecyclerView.ViewHolder{
        ImageView bookmarkImage;
        TextView bookmarkTitle;
        TextView bookmarkUrl;
        LinearLayout linearlayout;
        CheckBox checkBox;
        public BookmarkHolder(View itemView) {
            super(itemView);
            bookmarkImage = (ImageView) itemView.findViewById(R.id.bookmark_image);
            bookmarkTitle = (TextView) itemView.findViewById(R.id.bookmark_title);
            bookmarkUrl = (TextView) itemView.findViewById(R.id.bookmark_url);
            linearlayout = (LinearLayout) itemView.findViewById(R.id.bookmark_linearlayout);
            checkBox = (CheckBox) itemView.findViewById(R.id.bookmark_check);
        }
    }

    public class BackHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        public BackHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.back_relativelayout);
        }
    }

    public interface HFRV2_HF_Bridge{
        public boolean RequestThisNodeIfChecked(int nodeNum);
        public boolean RequestThisBookmarkIfChecked(int bookmarkNum);

        void NodeBeClicked(Node nodeBeClicked);

        void BackBeClicked();

    }
}
