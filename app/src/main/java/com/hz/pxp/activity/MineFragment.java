package com.hz.pxp.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hz.pxp.R;

public class MineFragment extends BaseFragment {
    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
    }
}
