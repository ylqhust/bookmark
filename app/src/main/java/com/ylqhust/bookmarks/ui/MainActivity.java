package com.ylqhust.bookmarks.ui;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ylqhust.bookmarks.R;
import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.database.DatabaseModel;
import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;
import com.ylqhust.bookmarks.model.dataModel.SearchHistory;
import com.ylqhust.bookmarks.presenter.Interface.MainPresenter;
import com.ylqhust.bookmarks.presenter.Impl.MainPresenterImpl;
import com.ylqhust.bookmarks.ui.Fragment.HomeFragment;
import com.ylqhust.bookmarks.ui.Fragment.PeopleFragment;
import com.ylqhust.bookmarks.ui.Fragment.PublicFragment;
import com.ylqhust.bookmarks.ui.adapter.SearchHistoryAdapter;
import com.ylqhust.bookmarks.ui.controller.TabController;
import com.ylqhust.bookmarks.ui.widget.WidgetUtils;
import com.ylqhust.bookmarks.view.MainView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        MainView ,
        HomeFragment.HMBridge,
        SearchHistoryAdapter.SHAMBridge{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TabController tabController;
    private List<Node> headNode;
    private List<Bookmark> headBookmark;
    private static DatabaseHelper dbh;
    private static MainPresenter presenter;
    private ProgressDialog progressDialog;

    private HomeFragment home;
    private PublicFragment publicF;
    private PeopleFragment people;

    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        inflater = LayoutInflater.from(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawerLayout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.app_name,R.string.app_name);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.activity_main_navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView userHead = (ImageView) navigationView.findViewById(R.id.a_m_n_h_imageview_userhead);
        userHead.setClickable(true);
        userHead.setOnClickListener(this);

        initTabView();
        progressDialog = new ProgressDialog(this);

        if (presenter == null)
            presenter = new MainPresenterImpl(this);
        if (dbh == null)
            dbh = new DatabaseHelper(this, DatabaseModel.DB_VERSION);
        //TestData.addData(dbh);
        //初始化数据到内存中
        presenter.LoadDatabase(dbh);

            //init action
            View init = new View(this);
            init.setId(R.id.a_m_tab_1_relative);
            onClick(init);
    }


    private void initTabView() {
        RelativeLayout[] relativeLayouts = new RelativeLayout[3];
        relativeLayouts[0] = (RelativeLayout) findViewById(R.id.a_m_tab_1_relative);
        relativeLayouts[1] = (RelativeLayout) findViewById(R.id.a_m_tab_2_relative);
        relativeLayouts[2] = (RelativeLayout) findViewById(R.id.a_m_tab_3_relative);
        relativeLayouts[0].setOnClickListener(this);
        relativeLayouts[1].setOnClickListener(this);
        relativeLayouts[2].setOnClickListener(this);

        ImageView[] imageViews = new ImageView[3];
        imageViews[0] = (ImageView) findViewById(R.id.a_m_tab_1_image);
        imageViews[1] = (ImageView) findViewById(R.id.a_m_tab_2_image);
        imageViews[2] = (ImageView) findViewById(R.id.a_m_tab_3_image);


        tabController = new TabController(relativeLayouts,imageViews,this);
        tabController.setSelectColor(Color.argb(0xff, 0x00, 0x91, 0xea));
        tabController.setUnselectColor(Color.argb(0xff, 0xff, 0xff, 0xff));

        int selectImageIds[] = new int[3];
        selectImageIds[0] = R.drawable.ic_home_white_24dp;
        selectImageIds[1] = R.drawable.ic_public_white_24dp;
        selectImageIds[2] = R.drawable.ic_people_white_24dp;
        int unSelectImageIds[] = new int[3];
        unSelectImageIds[0] = R.drawable.ic_home_black_24dp;
        unSelectImageIds[1] = R.drawable.ic_public_black_24dp;
        unSelectImageIds[2] = R.drawable.ic_people_black_24dp;

        tabController.setSelectImage(selectImageIds);
        tabController.setUnSelectImage(unSelectImageIds);
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        final int id = menuItem.getItemId();
        System.out.println("NAVIGATION");
        switch(id)
        {
            case R.id.a_m_n_menu_backup:
                break;
            case R.id.a_m_n_menu_sync:
                break;
            case R.id.a_m_n_menu_userCenter:
                break;
            case R.id.a_m_n_menu_privateSpace:
                break;
            case R.id.a_m_n_menu_settings:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch(id)
        {
            case R.id.a_m_tab_1_relative:
                presenter.HomeTab();
                break;
            case R.id.a_m_tab_2_relative:
                presenter.PublicTab();
                break;
            case R.id.a_m_tab_3_relative:
                presenter.PeopleTab();
                break;
            case R.id.a_m_n_h_imageview_userhead:
                break;
        }
    }

    @Override
    public void selectTab(int index) {
        tabController.select(index);
    }

    @Override
    public void onHomeTabFinished() {
        if (home == null){
            home = new HomeFragment();
            home.setData(headNode,headBookmark);
            home.setBridge(this);
        }
        removeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_container, home).commit();
    }

    private void removeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_container);
        if (fragment != null){
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void onPublicTabFinished() {
        if (publicF == null){
            publicF = new PublicFragment();
        }
        removeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_container, publicF).commit();
    }

    @Override
    public void onPeopleTabFinished() {
        if (people == null){
            people = new PeopleFragment();
        }
        removeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_container, people).commit();
    }

    /**
     * 加载数据库文件完成
     * @param headNode
     * @param headBookmark
     */
    @Override
    public void LoadFinished(List<Node> headNode, List<Bookmark> headBookmark) {
       // progressDialog.dismiss();
        this.headNode = headNode;
        this.headBookmark = headBookmark;
    }

    /**
     * 正在加载数据库
     */
    @Override
    public void loading() {
        progressDialog.setMessage("正在加载数据");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
    }

    /**
     * 显示节点输入的Dialog
     */
    private View addNodeView;
    private AlertDialog addNodeAD;
    private EditText nodeName;
    private EditText nodeDes;
    private TextView node_cancle;
    private TextView node_ok;
    @Override
    public void showNodeInputDialog() {
        if (addNodeView == null){
            addNodeView = inflater.inflate(R.layout.addnode, null);
            nodeName = (EditText) addNodeView.findViewById(R.id.addnode_et_nodename);
            nodeDes = (EditText) addNodeView.findViewById(R.id.addnode_et_nodedes);
            node_cancle = (TextView) addNodeView.findViewById(R.id.addnode_tv_cancle);
            node_ok = (TextView) addNodeView.findViewById(R.id.addnode_tv_ok);

            initNDViewAction();
        }
        if (addNodeAD == null){
            addNodeAD = new AlertDialog.Builder(this).setTitle(R.string.newNode) .
                    setIcon(R.drawable.ic_note_add_black_24dp).
                    setView(addNodeView).
                    setCancelable(false)
                    .create();
            WidgetUtils.ADKL(addNodeAD);
        }
        nodeName.setError(null);
        addNodeAD.show();
    }

    private void initNDViewAction() {
        node_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nn = nodeName.getText().toString().trim();
                String des = nodeDes.getText().toString().trim();
                home.addNode(nn,des,dbh);
            }
        });
        node_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nodeName.setText("");
                nodeDes.setText("");
                addNodeAD.dismiss();
            }
        });
        WidgetUtils.ClearEditText(nodeName);
        WidgetUtils.ClearEditText(nodeDes);
    }

    /**
     * 显示书签输入的Dialog
     */
    private View addBookmarkView;
    private AlertDialog addBookmarkAD;
    private EditText bookmarkUrl;
    private EditText bookmarkTitle;
    private TextView addbookmark_ok;
    private TextView addbookmark_cancle;
    @Override
    public void showBookmarkInputDialog(String guessUrl,String guessTitle,String shortcuturl) {
        if (addBookmarkView == null){
            addBookmarkView = inflater.inflate(R.layout.addbookmark, null);
            bookmarkTitle = (EditText) addBookmarkView.findViewById(R.id.addbookmark_et_title);
            bookmarkUrl = (EditText) addBookmarkView.findViewById(R.id.addbookmark_et_url);
            addbookmark_ok = (TextView) addBookmarkView.findViewById(R.id.addbookmark_tv_ok);
            addbookmark_cancle = (TextView) addBookmarkView.findViewById(R.id.addbookmark_tv_cancle);

            initBDViewAction();
        }

        if (addBookmarkAD == null){
            addBookmarkAD = new AlertDialog.Builder(this).
                    setTitle(R.string.newBookmark).
                    setCancelable(false).
                    setIcon(R.drawable.ic_add_circle_outline_black_24dp).
                    setView(addBookmarkView).
                    create();
            WidgetUtils.ADKL(addBookmarkAD);
        }
        bookmarkUrl.setText(guessUrl);
        bookmarkTitle.setText(guessTitle);
        bookmarkUrl.setError(null);
        addBookmarkAD.show();
    }

    private void initBDViewAction() {
        addbookmark_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = bookmarkUrl.getText().toString().trim();
                String title = bookmarkTitle.getText().toString().trim();
                home.addBookmark(url,title,"",dbh);
            }
        });

        addbookmark_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarkTitle.setText("");
                bookmarkUrl.setText("");
                addBookmarkAD.dismiss();
            }
        });
        WidgetUtils.ClearEditText(bookmarkTitle);
        WidgetUtils.ClearEditText(bookmarkUrl);
    }

    /**
     * 根据从网络获取的信息，结合用户的输入信息，将信息补全
     * @param title
     * @param shortcuturl
     */
    @Override
    public void choseAutoCompleteBookmark(String title, String shortcuturl) {
        String input = bookmarkTitle==null?bookmarkTitle.getText().toString():"";
        if (input.equals("") && bookmarkTitle!=null && addBookmarkAD.isShowing()){
            bookmarkTitle.setText(title);
        }
        return;
    }



    private AlertDialog searchDA;
    private View searchView;
    private EditText searchEt;
    private TextView searchTvB;
    private ListView searchListView;
    private ImageView searchBack;
    private TextView searchClearall;
    /**
     * 根据从数据库获取的历史搜索结果显示搜索框
     * @param searchHistory
     */
    @Override
    public void showSearchView(List<String> searchHistory) {
        if (searchView == null){
            searchView = inflater.inflate(R.layout.search, null);
            searchEt = (EditText) searchView.findViewById(R.id.search_et);
            searchTvB = (TextView) searchView.findViewById(R.id.search_tv_search);
            searchListView = (ListView) searchView.findViewById(R.id.search_listview);
            searchBack = (ImageView) searchView.findViewById(R.id.search_img_back);
            searchClearall = (TextView) searchView.findViewById(R.id.search_tv_clearall);
            initSearchViewAction();
        }
        if (searchDA == null){
            searchDA = new AlertDialog.Builder(this).
                    setView(searchView).
                    setCancelable(false).create();
            searchDA.getWindow().setGravity(Gravity.TOP);
            WidgetUtils.ADKL(searchDA);
        }
        searchEt.setText("");
        searchEt.setError(null);
        SearchHistoryAdapter adapter = new SearchHistoryAdapter(inflater,searchHistory);
        adapter.setShamBridge(this);
        searchListView.setAdapter(adapter);
        searchDA.show();
    }



    /**
     * 初始化事件监听
     */
    private void initSearchViewAction() {
        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDA.dismiss();
            }
        });
        searchTvB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行搜索，并显示
                String string = searchEt.getText().toString().trim();
                SearchHistory sh = new SearchHistory("STRANGER", string, System.currentTimeMillis());
                dbh.getSearchHisHelper().updateEntity(sh);
                Toast toast = Toast.makeText(MainActivity.this, "搜索完成", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        searchClearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clearAllHistory(dbh);
            }
        });

        WidgetUtils.ClearEditText(searchEt);
    }

    private MenuItem item_search;
    private MenuItem item_addNode;
    private MenuItem item_addBK;
    private MenuItem item_selectAll;
    private MenuItem item_cancle;
    private MenuItem item_null;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main_menu_home, menu);
        initMenuItem(menu);
        return true;
    }

    private void initMenuItem(Menu menu) {
        this.item_addBK = menu.findItem(R.id.a_m_menu_home_addBookmark);
        this.item_addNode = menu.findItem(R.id.a_m_menu_home_addNode);
        this.item_search = menu.findItem(R.id.a_m_menu_home_search);
        this.item_selectAll = menu.findItem(R.id.a_m_menu_home_selectAll);
        this.item_cancle = menu.findItem(R.id.a_m_menu_home_cancle);
        this.item_null = menu.findItem(R.id.a_m_menu_home_null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final int id = item.getItemId();
        switch(id)
        {
            case R.id.a_m_menu_home_search:
                presenter.showSearchView(dbh);
                break;
            case R.id.a_m_menu_home_addNode:
                this.showNodeInputDialog();
                break;
            case R.id.a_m_menu_home_addBookmark:
                presenter.addBookmark(this);
                break;
        }
        return true;
    }

    /**
     * HMBridge
     * 节点名错误
     */
    @Override
    public void onNodeNameError(String errorMessage) {
        nodeName.setError(errorMessage);
        nodeName.requestFocus();
    }

    /**
     * 节点添加完成
     */
    @Override
    public void onAddNodeFinished() {
        addNodeAD.dismiss();
        Toast.makeText(this,"节点添加完成",Toast.LENGTH_SHORT).show();
    }

    /**
     * 书签添加完成
     */
    @Override
    public void onAddBookmarkFinished() {
        addBookmarkAD.dismiss();
        Toast.makeText(this, "书签添加完成", Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求使用dbh,被HomeFragment调用
     * @return
     */
    @Override
    public DatabaseHelper requestDbh() {
        return dbh;
    }

    /**
     * HMBridge
     * 书签URL错误
     * @param errorMessage
     */
    @Override
    public void onBookmarkUrlError(String errorMessage) {
        bookmarkUrl.setError(errorMessage);
        bookmarkUrl.requestFocus();
    }

    /**
     * HMBridge
     * 存在相同书签
     * @param sameUrlMessage
     * @param onClickListener
     */
    @Override
    public void onBookmarkSameUrl(String sameUrlMessage, final View.OnClickListener onClickListener) {
        new AlertDialog.Builder(this).setTitle("提示").
                setCancelable(false).
                setMessage("检测到存在相同URL的书签在\n\n" + sameUrlMessage + "\n\n你确定继续添加吗?").
                setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onClickListener.onClick(new View(MainActivity.this));
                    }
                }).
                setNegativeButton(R.string.cancle, null).create().show();
    }

    /**
     * 清除一条历史记录后调用此方法改变UI
     * @param position
     * @param strings,已经将记录清除过的集合
     */
    @Override
    public void onClearOneHistoryFinished(int position, List<String> strings) {
        SearchHistoryAdapter adapter = new SearchHistoryAdapter(inflater,strings);
        adapter.setShamBridge(this);
        searchListView.setAdapter(adapter);
        searchListView.setSelection((position == 0 ? 0 : position - 1));

    }

    /**
     * 清除所有历史纪录后调用此方法改变UI
     */
    @Override
    public void onClearAllFinished() {
        SearchHistoryAdapter adapter = new SearchHistoryAdapter(inflater,new ArrayList<String>());
        adapter.setShamBridge(this);
        searchListView.setAdapter(adapter);
        Toast toast = Toast.makeText(this, "清除完成", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 改变菜单
     */
    @Override
    public void changeMenu(boolean flag) {
        item_addBK.setVisible(flag);
        item_addNode.setVisible(flag);
        item_search.setVisible(flag);
        item_selectAll.setVisible(!flag);
        item_cancle.setVisible(!flag);
        item_null.setVisible(!flag);
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void choseHistory(String beChosedString) {
        searchEt.setText(beChosedString);
    }

    @Override
    public void clearHistory(int position, List<String> strings) {
        presenter.clearOneHistory(position,strings,dbh);
    }
}
