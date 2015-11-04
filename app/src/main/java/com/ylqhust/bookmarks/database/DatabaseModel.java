package com.ylqhust.bookmarks.database;

/**
 * Created by apple on 15/10/29.
 */
public class DatabaseModel {
    public static final String DB_NAME = "bookmarks.db";
    public static int DB_VERSION = 1;
    public static final String CREATE_DB_SQL = "CREATE DATABSAE "+DB_NAME;

    public static final String VARCHAR_TYPE = " varcher(255)";
    public static final String BIGINT_TYPE = " bigint";
    public static final String BLOB_TYPE = " blob";
    public static final String INTEGER_TYPE = " int";
    public static final String COMMA_SEP = ",";



    /**
     * 节点表
     */
    public class NODE{
        public static final String TABLE_NAME = "node";
        public static final String CN_ID = "id";
        public static final String CN_NUM = "num";
        public static final String CN_USERID = "userid";
        public static final String CN_NODENAME = "nodename";
        public static final String CN_CREATETIME = "createtime";
        public static final String CN_LASTMODIFYTIME = "lastmodifytime";
        public static final String CN_NODEDES = "nodedes";
        public static final String CN_PRENODENUM = "prenodenum";


        public static final String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                CN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT"+COMMA_SEP+
                CN_NUM+INTEGER_TYPE+COMMA_SEP+
                CN_USERID+VARCHAR_TYPE+COMMA_SEP+
                CN_NODENAME+VARCHAR_TYPE+COMMA_SEP+
                CN_CREATETIME+BIGINT_TYPE+COMMA_SEP+
                CN_LASTMODIFYTIME+BIGINT_TYPE+COMMA_SEP+
                CN_NODEDES+BLOB_TYPE+COMMA_SEP+
                CN_PRENODENUM+INTEGER_TYPE+
                " )";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXIST "+TABLE_NAME;

    }

    /**
     * 书签表
     */

    public class BOOKMARK{
        public static final String TABLE_NAME = "bookmarks";
        public static final String CN_USERID = "userid";
        public static final String CN_ID = "id";
        public static final String CN_URL = "url";
        public static final String CN_TITLE = "title";
        public static final String CN_SHORTCUTURL = "shortcut";
        public static final String CN_BOOKMARKDES = "bookmarkdes";
        public static final String CN_BOOKMARKNUM = "bookmarknum";
        public static final String CN_CREATETIME = "createtime";
        public static final String CN_LASTMODIFYTIME = "lastmodifytime";
        public static final String CN_BELONGNODENUM = "belongnodenum";
        public static final String CN_ISMODIFIED = "ismodified";
        public static final String CN_ISDELETED = "isdeleted";
        public static final String CN_ISBACKUPED = "isbackuped";
        public static final String CN_ISSYNCED = "issynced";
        public static final String CN_ISPRIVATE = "isprivate";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                CN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT"+COMMA_SEP+
                CN_URL+BLOB_TYPE+COMMA_SEP+
                CN_USERID+VARCHAR_TYPE+COMMA_SEP+
                CN_TITLE+VARCHAR_TYPE+COMMA_SEP+
                CN_SHORTCUTURL+BLOB_TYPE+COMMA_SEP+
                CN_BOOKMARKNUM+INTEGER_TYPE+COMMA_SEP+
                CN_CREATETIME+BIGINT_TYPE+COMMA_SEP+
                CN_LASTMODIFYTIME+BIGINT_TYPE+COMMA_SEP+
                CN_BOOKMARKDES+BLOB_TYPE+COMMA_SEP+
                CN_BELONGNODENUM+INTEGER_TYPE+COMMA_SEP+
                CN_ISMODIFIED+INTEGER_TYPE+COMMA_SEP+
                CN_ISDELETED+INTEGER_TYPE+COMMA_SEP+
                CN_ISBACKUPED+INTEGER_TYPE+COMMA_SEP+
                CN_ISSYNCED+INTEGER_TYPE+COMMA_SEP+
                CN_ISPRIVATE+INTEGER_TYPE+
                " )";
        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXIST "+TABLE_NAME;

    }

    /**
     * 用户表
     */
    public class USER{
        public static final String TABLE_NAME = "user";
        public static final String CN_ID = "id";
        public static final String CN_USERID = "userid";
        public static final String CN_PASSWORD = "password";
        public static final String CN_KEY = "key";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                CN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT"+COMMA_SEP+
                CN_USERID+VARCHAR_TYPE+COMMA_SEP+
                CN_PASSWORD+VARCHAR_TYPE+COMMA_SEP+
                CN_KEY+VARCHAR_TYPE+
                " )";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXIST "+TABLE_NAME;
    }

    /**
     *搜索记录表
     */
    public class SEARCHHISTORY{
        public static final String TABLE_NAME = "searchhistory";
        public static final String CN_ID = "id";
        public static final String CN_USERID = "userid";
        public static final String CN_STRING = "string";
        public static final String CN_FIRSTSEARCHTIME = "firsttime";
        public static final String CN_LASTSEARCHTIME = "lasttime";
        public static final String CN_COUNT = "count";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                CN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT"+COMMA_SEP+
                CN_USERID+VARCHAR_TYPE+COMMA_SEP+
                CN_STRING+VARCHAR_TYPE+COMMA_SEP+
                CN_FIRSTSEARCHTIME+BIGINT_TYPE+COMMA_SEP+
                CN_LASTSEARCHTIME+BIGINT_TYPE+COMMA_SEP+
                CN_COUNT+INTEGER_TYPE+
                " )";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXIST "+TABLE_NAME;
    }

}
