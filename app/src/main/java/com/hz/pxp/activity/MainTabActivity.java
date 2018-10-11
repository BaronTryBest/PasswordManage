package com.hz.pxp.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TextView;

import com.hz.pxp.R;
import com.hz.pxp.widget.MyFragmentTabHost;

public class MainTabActivity extends BaseActivity {

    private MyFragmentTabHost mTabHost;
    private TabHost.TabSpec mCheckTab;
    private TabHost.TabSpec mAddTab;
    private TabHost.TabSpec mMineTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21) {
            Window window = MainTabActivity.this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.bg_header));
        }
        setContentView(R.layout.activity_main_tab);
        mTabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);

        initTabs();
    }

    /**
     * 初始化Tabs
     */
    private void initTabs() {
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mCheckTab = mTabHost.newTabSpec("msg").setIndicator(newIndicatorView(R.string.check_tab));
        mAddTab = mTabHost.newTabSpec("contact").setIndicator(newIndicatorView(R.string.add_tab));
        mMineTab = mTabHost.newTabSpec("mine").setIndicator(newIndicatorView(R.string.mine_tab));

        mTabHost.addTab(mCheckTab, CheckFragment.class, null);
        mTabHost.addTab(mAddTab, AddFragment.class, null);
        mTabHost.addTab(mMineTab, MineFragment.class, null);

        mTabHost.setCurrentTab(0);
    }

    /**
     * 创建TabIndicator
     *
     * @param labelResId
     *            文本资源ID
//     * @param iconResId
//     *            图片资源ID
     * @return 创建成功的View
     */
    private View newIndicatorView(int labelResId) {
        View indicatorView = getLayoutInflater().inflate(R.layout.tab_indicator, null);
        TextView textView = (TextView) indicatorView.findViewById(R.id.text);
        textView.setText(labelResId);
        indicatorView.setVisibility(View.VISIBLE);
        return indicatorView;
    }
}
