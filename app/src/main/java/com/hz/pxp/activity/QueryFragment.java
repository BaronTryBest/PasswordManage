package com.hz.pxp.activity;

import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hz.pxp.R;
import com.hz.pxp.database.dao.DAOFactory;
import com.hz.pxp.database.dao.impl.PasswordDAOImpl;
import com.hz.pxp.model.PassItem;

public class QueryFragment extends BaseFragment {

    private PasswordDAOImpl passwordDAO = DAOFactory.getInstance().getPasswordDAO();
    private EditText mQueryEditText;
    private ImageView mQueryImg;
    private PassItem mQueryItem;
    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_check, container, false);

        mQueryEditText = (EditText)rootView.findViewById(R.id.edit_query);
        mQueryImg = (ImageView)rootView.findViewById(R.id.img_query);
        mQueryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mQueryEditText.getText().toString())){
                    Toast.makeText(getContext(),"请输入关键字",Toast.LENGTH_SHORT).show();
                }else {
                    if (passwordDAO.isKeyNameExist(mQueryEditText.getText().toString())){
                        mQueryItem = passwordDAO.queryName(mQueryEditText.getText().toString());
                        System.out.println("mQueryItem.name = "+mQueryItem.name);
                    }else {
                        Toast.makeText(getContext(),"没有此关键字信息",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("====>>> passwordDAO.queryTotal() = "+passwordDAO.queryTotal());
    }
}
