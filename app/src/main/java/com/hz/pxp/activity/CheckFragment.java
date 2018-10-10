package com.hz.pxp.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hz.pxp.R;
import com.hz.pxp.database.dao.DAOFactory;
import com.hz.pxp.database.dao.impl.PasswordDAOImpl;

public class CheckFragment extends BaseFragment {

    private PasswordDAOImpl passwordDAO = DAOFactory.getInstance().getPasswordDAO();
    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_check, container, false);

        System.out.println("====>>> passwordDAO.queryTotal() = "+passwordDAO.queryTotal());
    }
}
