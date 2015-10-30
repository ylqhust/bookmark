package com.ylqhust.bookmarks.ui;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ylqhust.bookmarks.R;
import com.ylqhust.bookmarks.database.DatabaseHelper;
import com.ylqhust.bookmarks.database.DatabaseModel;
import com.ylqhust.bookmarks.model.dataModel.Bookmark;
import com.ylqhust.bookmarks.model.dataModel.Node;
import com.ylqhust.bookmarks.presenter.Interface.MainPresenter;
import com.ylqhust.bookmarks.presenter.Impl.MainPresenterImpl;
import com.ylqhust.bookmarks.ui.Fragment.HomeFragment;
import com.ylqhust.bookmarks.ui.controller.TabController;
import com.ylqhust.bookmarks.view.MainView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,MainView {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TabController tabController;
    private List<Node> headNode;
    private List<Bookmark> headBookmark;
    private static DatabaseHelper dbh;
    private static MainPresenter presenter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

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
        tabController.setUnselectColor(Color.argb(0xff,0xff,0xff,0xff));

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
                presenter.PeopleTab();
                break;
        }
    }

    @Override
    public void selectTab(int index) {
        tabController.select(index);
    }

    @Override
    public void onFinished() {
        Toast.makeText(this, "完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHomeTabFinished() {
        Fragment home = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_container,home).commit();
    }

    /**
     * 加载数据库文件完成
     * @param headNode
     * @param headBookmark
     */
    @Override
    public void LoadFinished(List<Node> headNode, List<Bookmark> headBookmark) {
        progressDialog.dismiss();
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
        progressDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main_menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final int id = item.getItemId();
        switch(id)
        {
            case R.id.a_m_menu_home_search:
                break;
            case R.id.a_m_menu_home_addNode:
                break;
            case R.id.a_m_menu_home_addBookmark:
                break;
        }
        return true;
    }

}
