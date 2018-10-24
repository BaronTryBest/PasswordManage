package com.hz.pxp.database.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;

import com.hz.pxp.common.Const;
import com.hz.pxp.common.PreferenceHelper;
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
        System.out.println("===>> newId = "+newId);
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

    /**
     * 通过关键字查询单个记录
     * @param name
     * @return PassItem
     */
    public PassItem queryName(String name){
        PassItem passItem;
        String selection = Table.PasswordTable.COLUMN_NAME + "='" + name + "' and " + Table.PasswordTable.COLUMN_OWNER + "='" + PreferenceHelper.getString(Const.PM_USER_NAME) +"'";
        Cursor cursor = dbHelper.getReadableDatabase().query(Table.TABLE_PASS_WORD, null, selection, null, null, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    if (cursor2PassItem(cursor).name.equals(name)){
                        passItem = new PassItem();
                        passItem = cursor2PassItem(cursor);
                        return passItem;
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * 查询所有user name下的账号
     * @return ArrayList
     */
    public ArrayList<PassItem> queryPassItems() {
        ArrayList<PassItem> items = new ArrayList<>();
        String selection = Table.PasswordTable.COLUMN_OWNER + "='" + PreferenceHelper.getString(Const.PM_USER_NAME) +"'";
        Cursor cursor = dbHelper.getReadableDatabase().query(Table.TABLE_PASS_WORD, null, selection, null, null, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    PassItem passItem = cursor2PassItem(cursor);
                    items.add(passItem);
                }
            } finally {
                cursor.close();
            }
        }
        return items;
    }

    /**
     * 查询的时候使用， 关键判断contains
     * 判断库中是否有此关键字的记录，有的话记录下来，可能有多条
     * @param name
     * @return ArrayList
     */
    public ArrayList<PassItem> queryIsKeyNameExist(String name){
        ArrayList<PassItem> items = new ArrayList<>();
        String selection = Table.PasswordTable.COLUMN_OWNER + "='" + PreferenceHelper.getString(Const.PM_USER_NAME) +"'";
        Cursor cursor = dbHelper.getReadableDatabase().query(Table.TABLE_PASS_WORD, null, selection, null, null, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    if (cursor2PassItem(cursor).name.contains(name)){
                        PassItem passItem = cursor2PassItem(cursor);
                        items.add(passItem);
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return items;
    }

    /**
     * 添加的时候使用， 关键判断equal
     * 判断库中是否有此关键字的记录，有的话记录下来，可能有多条
     * @param name
     * @return ArrayList
     */
    public boolean addIsKeyNameExist(String name){
        String selection = Table.PasswordTable.COLUMN_NAME + "='" + name + "' and " + Table.PasswordTable.COLUMN_OWNER + "='" + PreferenceHelper.getString(Const.PM_USER_NAME) +"'";
        Cursor cursor = dbHelper.getReadableDatabase().query(Table.TABLE_PASS_WORD, null, selection, null, null, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    if (cursor2PassItem(cursor).name.equals(name)){
                        return true;
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return false;
    }


    public int queryTotal(){
        int i = 0;
        String selection = Table.PasswordTable.COLUMN_OWNER + "='" + PreferenceHelper.getString(Const.PM_USER_NAME) +"'";
        Cursor cursor = dbHelper.getReadableDatabase().query(Table.TABLE_PASS_WORD, null, selection, null, null, null, null, null);
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
        passItem.owner = cursor.getString(cursor.getColumnIndex(Table.PasswordTable.COLUMN_OWNER));
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
        values.put(Table.PasswordTable.COLUMN_OWNER,passItem.owner);
        return values;
    }
}
