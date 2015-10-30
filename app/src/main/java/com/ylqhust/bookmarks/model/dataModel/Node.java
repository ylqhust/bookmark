package com.ylqhust.bookmarks.model.dataModel;

import java.util.List;

/**
 * Created by apple on 15/10/29.
 */
public class Node {
    public String userID;//用户ID
    public String nodeName;//节点名
    public int nodeNum;//节点编号
    public long createTime;//创建时间
    public long lastModifyTime;//最后修改时间
    public String nodeDes;//节点描述
    public int preNodeNum;//所属上级节点编号

    public Node preNode;//所属上级节点
    public List<Node> containNode;//包含的节点
    public List<Bookmark> containBM;//包含的书签

    public Node(String userID,
                String nodeName,
                int nodeNum,
                long createTime,
                long lastModifyTime,
                String nodeDes,
                int preNodeNum){
        this.userID = userID;
        this.nodeName = nodeName;
        this.nodeNum = nodeNum;
        this.createTime = createTime;
        this.lastModifyTime = lastModifyTime;
        this.nodeDes = nodeDes;
        this.preNodeNum = preNodeNum;
    }

    public void update(){}

    public static Node testBuilder(int nodeNum){
        return new Node("STRANGER",nodeNum+"name",nodeNum,System.currentTimeMillis(),
                System.currentTimeMillis(),"null",nodeNum-1);
    }
}
