package com.hz.pxp.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hz.pxp.R;

public class AddFragment extends BaseFragment {

    private Button mAddButton;
    private LinearLayout mAddLayout;

    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        mAddButton = (Button) rootView.findViewById(R.id.add_pw);

    }

}
