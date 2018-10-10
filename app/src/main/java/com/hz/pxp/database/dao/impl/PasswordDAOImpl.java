package com.hz.pxp.database.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.hz.pxp.database.DBHelper;
import com.hz.pxp.database.Table;
import com.hz.pxp.database.dao.PasswordDao;
import com.hz.pxp.model.PassItem;

import java.util.ArrayList;

public class PasswordDAOImpl implements PasswordDao{

    private DBHelper dbHelper;

    public PasswordDAOImpl(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public long save(PassItem passItem) {
        ContentValues values = passItem2Values(passItem);
        long newId = dbHelper.getWritableDatabase().insert(Table.TABLE_PASS_WORD, null, values);
        return newId;
    }

    @Override
    public int delete(PassItem passItem) {
        return 0;
    }

    @Override
    public int update(PassItem passItem) {
        return 0;
    }

    @Override
    public ArrayList<PassItem> query(PassItem passItem) {
        return null;
    }

    public int queryTotal(){
        int i = 0;
        Cursor cursor = dbHelper.getReadableDatabase().query(Table.TABLE_PASS_WORD, null, null, null, null, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    i++;
                }
            } finally {
                cursor.close();
            }
        }
        return i;
    }

    private PassItem cursor2PassItem(Cursor cursor){
        PassItem passItem = new PassItem();
        passItem.name = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_NAME));
        passItem.userName = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_USER_NAME));
        passItem.passWord = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_PASSWORD));
        passItem.passType = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_ENCRYPT_TYPE));
        passItem.email = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_EMAIL));
        passItem.phone = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_PHONE));
        passItem.isThird = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_THIRD_LOGIN));
        passItem.thirdName = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_THIRD_NAME));
        passItem.thirdInfo = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_THIRD_INFO));
        return passItem;
    }

    private ContentValues passItem2Values(PassItem passItem){
        ContentValues values = new ContentValues();
        values.put(Table.PasswordTable.COLUMN_NAME,passItem.name);
        values.put(Table.PasswordTable.COLUMN_USER_NAME,passItem.userName);
        values.put(Table.PasswordTable.COLUMN_PASSWORD,passItem.passWord);
        values.put(Table.PasswordTable.COLUMN_ENCRYPT_TYPE,passItem.passType);
        values.put(Table.PasswordTable.COLUMN_EMAIL,passItem.email);
        values.put(Table.PasswordTable.COLUMN_PHONE,passItem.phone);
        values.put(Table.PasswordTable.COLUMN_THIRD_LOGIN,passItem.isThird);
        values.put(Table.PasswordTable.COLUMN_THIRD_NAME,passItem.thirdName);
        values.put(Table.PasswordTable.COLUMN_THIRD_INFO,passItem.thirdInfo);
        return values;
    }
}
