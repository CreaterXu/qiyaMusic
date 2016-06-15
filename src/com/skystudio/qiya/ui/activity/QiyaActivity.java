package com.skystudio.qiya.ui.activity;



import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.design.widget.NavigationView;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.skystudio.qiya.Config.Contans;
import com.skystudio.qiya.R;
import com.skystudio.qiya.adpter.ViewPagerAdpter;

import it.neokree.materialtabs.MaterialTab;

import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class QiyaActivity extends AppCompatActivity implements MaterialTabListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private Toolbar.OnMenuItemClickListener menuItemClickListener;
    private TextView mTextView;
    private MaterialTabHost mMaterialTabHost;
    private ViewPager mViewPager;
    private ViewPagerAdpter mViewpagerAdpter;
    private Resources res;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.activity_base);
        init();
    }

    /**
     * 初始化控件
     *
     * @param
     */
    protected void init() {
        res = this.getResources();
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.id_nv_menu);
        mToolbar = (Toolbar) this.findViewById(R.id.id_toolbar);
        setSupportActionBar(mToolbar);

        setupDrawerContent(mNavigationView);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.hello_world, R.string.app_name);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mMaterialTabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        mViewPager = (ViewPager) this.findViewById(R.id.pager);
        mViewpagerAdpter = new ViewPagerAdpter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewpagerAdpter);
        mViewPager.setAdapter(mViewpagerAdpter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                mMaterialTabHost.setSelectedNavigationItem(position);
            }
        });
        // insert all tabs from pagerAdapter data
        for (int i = 0; i < mViewpagerAdpter.getCount(); i++) {
            mMaterialTabHost.addTab(
                    mMaterialTabHost.newTab()
                            .setIcon(getIcon(i)).setText(getCharsquens(i))
                            .setTabListener(this)
            );
        }
    }


    /*
    * It doesn't matter the color of the icons, but they must have solid colors
    */

    private Drawable getIcon(int position) {
        Drawable drawable = null;
        switch (position) {
            case Contans.ZONE_FRAGMENT:
                drawable = res.getDrawable(R.drawable.ic_trades_24dp);
                break;
            case Contans.RECONMMEND_FRAGMENT:
                drawable = res.getDrawable(R.drawable.ic_recommend_white_24dp);
                break;
            case Contans.CONTACTERS_FRAGMENT:
                drawable = res.getDrawable(R.drawable.ic_group_white_24dp);
                break;
        }
        return drawable;
    }

    private String getCharsquens(int position) {
        String s = null;
        switch (position) {
            case Contans.ZONE_FRAGMENT:
                s = "动态";
                break;
            case Contans.RECONMMEND_FRAGMENT:
                s = "推荐";
                break;
            case Contans.CONTACTERS_FRAGMENT:
                s = "通讯录";
                break;
        }
        return s;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mTextView.setText(menuItem.toString() + "Fragment");
                        return true;
                    }
                });
    }

    /**
     * 指定布局文件
     */
    protected void setRootView(int viewId) {
        setContentView(viewId);
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        /* ShareActionProvider配置 */
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu
                .findItem(R.id.action_share));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        //mShareActionProvider.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.action_notifications:
                msg += "Click notifications";
                break;
            case R.id.action_share:
                msg += "Click share";
                break;
            case R.id.action_search:
                msg += "Click search";
                break;
        }

        if (!msg.equals("")) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
