package com.hz.pxp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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
