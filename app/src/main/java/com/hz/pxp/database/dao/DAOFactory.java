package com.hz.pxp.database.dao;

import android.content.Context;

import com.hz.pxp.database.DBHelper;
import com.hz.pxp.database.dao.impl.PasswordDAOImpl;

public class DAOFactory {

    private static DBHelper dbHelper;
    private static DAOFactory daoFactory = new DAOFactory();

    private DAOFactory() {

    }

    public static void init(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
    }

    public static DAOFactory getInstance() {// 640x1136
        return daoFactory;
    }

    public PasswordDAOImpl getPasswordDAO() {
        return new PasswordDAOImpl(dbHelper);
    }
}
