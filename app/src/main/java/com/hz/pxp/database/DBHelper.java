package com.hz.pxp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

public class DBHelper extends SQLiteOpenHelper {
    private static final String name = "pm_user.db";
    private static final int version = 1;

    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        creatAllTables(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void creatAllTables(SQLiteDatabase db){
        creatPassWordTable(db);
    }

    /**
     * 创建密码表
     *
     * @param db
     */
    private void creatPassWordTable(SQLiteDatabase db){
        StringBuilder sqlSb = new StringBuilder("create table if not exists " + Table.TABLE_PASS_WORD + "(");
        sqlSb.append(SyncStateContract.Columns._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlSb.append(Table.PasswordTable.COLUMN_NAME).append(" TEXT,");
        sqlSb.append(Table.PasswordTable.COLUMN_USER_NAME).append(" TEXT,");
        sqlSb.append(Table.PasswordTable.COLUMN_PASSWORD).append(" TEXT,");
        sqlSb.append(Table.PasswordTable.COLUMN_ENCRYPT_TYPE).append(" TEXT,");
        sqlSb.append(Table.PasswordTable.COLUMN_EMAIL).append(" TEXT,");
        sqlSb.append(Table.PasswordTable.COLUMN_PHONE).append(" TEXT,");
        sqlSb.append(Table.PasswordTable.COLUMN_THIRD_LOGIN).append(" TEXT,");
        sqlSb.append(Table.PasswordTable.COLUMN_THIRD_NAME).append(" TEXT,");
        sqlSb.append(Table.PasswordTable.COLUMN_THIRD_INFO).append(" TEXT,");
        sqlSb.append(Table.PasswordTable.COLUMN_OWNER).append(" TEXT,");
        sqlSb.append("UNIQUE (" + Table.PasswordTable.COLUMN_NAME+"," + Table.PasswordTable.COLUMN_OWNER +") ON CONFLICT REPLACE);");
        db.execSQL(sqlSb.toString());
    }
}
