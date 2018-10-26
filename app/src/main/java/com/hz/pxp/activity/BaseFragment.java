package com.hz.pxp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hz.pxp.R;

public abstract class BaseFragment extends Fragment{

    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            initViews(inflater,container);// 控件初始化
        }
        return rootView;
    }

    protected View findViewById(int id) {
        return rootView.findViewById(id);
    }

    protected void setHeaderTitle(String text) {
        TextView title = (TextView) findViewById(R.id.headerTitle);
        title.setText(text);
    }

    protected abstract void initViews(LayoutInflater inflater, ViewGroup container);
}
