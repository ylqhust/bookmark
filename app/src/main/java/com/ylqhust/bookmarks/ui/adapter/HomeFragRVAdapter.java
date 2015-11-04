package com.ylqhust.bookmarks.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylqhust.bookmarks.R;
import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;
import com.ylqhust.bookmarks.presenter.Interface.HomeFragPresenter;

import java.util.List;

/**
 * Created by apple on 15/10/31.
 */
public class HomeFragRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Node> currentPageNode;
    private List<Bookmark> currentPageBookmark;
    private Node parentNode;
    private Context context;
    private HomeFragPresenter presenter;

    private final int BOOKMARK = 0x1;
    private final int NODE = 0x2;
    private final int BACK = 0x3;

    public HomeFragRVAdapter(List<Bookmark> currentPageBookmark,
                             List<Node> currentPageNode,
                             HomeFragPresenter presenter,
                             Context context,
                             Node parentNode) {
        this.currentPageBookmark = currentPageBookmark;
        this.currentPageNode = currentPageNode;
        this.context = context;
        this.presenter = presenter;
        this.parentNode = parentNode;
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

    /**
     * bind clickevent to holder
     * @param holder
     * @param position
     */
    private void bindBackHolder(BackHolder holder, int position) {
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳会到父节点
                presenter.stepBack(parentNode);
            }
        });
    }

    /**
     * bind data to holder
     * @param holder
     * @param position
     */
    private void bindBookmarkHolder(final BookmarkHolder holder, int position) {
        if (currentPageBookmark.size() == 0)
            return;
        final Bookmark bookmark = currentPageBookmark.get(position-currentPageNode.size()-(parentNode==null?0:1));
        final String url = bookmark.url;
        String title = bookmark.title;
        String imageUrl = bookmark.shortCutUrl;

        holder.bookmarkUrl.setText(url);
        holder.bookmarkTitle.setText(title);

        holder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showChoseDailog(parentNode, bookmark);
            }
        });

        holder.linearlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.changeMenu(false);
                return true;
            }
        });


        //设置小图标
    }

    /**
     * bind data to holder
     * @param holder
     * @param position
     */
    private void bindNodeHolder(final NodeHolder holder, final int position) {
        if (currentPageNode.size() == 0)
            return;
        Node node = currentPageNode.get(position-(parentNode==null?0:1));
        String nodeName = node.nodeName;
        String nodeDes = node.nodeDes;
        String bookmarkCount = getBookmarkCount(node)+"";

        holder.nodeName.setText(nodeName);
        holder.nodeDes.setText(nodeDes);
        holder.bookmarkCount.setText(bookmarkCount);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到下一个节点
                presenter.stepIn(currentPageNode.get(position - (parentNode == null ? 0 : 1)));
            }
        });
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.changeMenu(false);
                return true;
            }
        });

    }

    /**
     * 获取当前节点下有多少书签
     * @param node
     * @return
     */
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

    /**
     * get ItemView type by position
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position){
        if (position == 0 && parentNode != null)
            return BACK;
        if (position<(currentPageNode.size()+(parentNode==null?0:1)))
            return NODE;
        return BOOKMARK;
    }


    /**
     * two different Holder
     */
    public class NodeHolder extends RecyclerView.ViewHolder{
        TextView nodeName;
        TextView nodeDes;
        TextView bookmarkCount;
        RelativeLayout relativeLayout;

        public NodeHolder(View itemView) {
            super(itemView);
            nodeName = (TextView) itemView.findViewById(R.id.node_nodeName);
            nodeDes = (TextView) itemView.findViewById(R.id.node_nodeDes);
            bookmarkCount = (TextView) itemView.findViewById(R.id.node_bookmarkCount);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.node_relativelayout);
        }
    }

    public class BookmarkHolder extends RecyclerView.ViewHolder{
        ImageView bookmarkImage;
        TextView bookmarkTitle;
        TextView bookmarkUrl;
        LinearLayout linearlayout;
        public BookmarkHolder(View itemView) {
            super(itemView);
            bookmarkImage = (ImageView) itemView.findViewById(R.id.bookmark_image);
            bookmarkTitle = (TextView) itemView.findViewById(R.id.bookmark_title);
            bookmarkUrl = (TextView) itemView.findViewById(R.id.bookmark_url);
            linearlayout = (LinearLayout) itemView.findViewById(R.id.bookmark_linearlayout);
        }
    }

    public class BackHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        public BackHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.back_relativelayout);
        }
    }
}
