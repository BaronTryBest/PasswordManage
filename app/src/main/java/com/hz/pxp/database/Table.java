package com.hz.pxp.database;

public final class Table {
    public static final String TABLE_PASS_WORD = "pass_word";

    public static abstract class PasswordTable{
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_PASSWORD = "pass_word";
        public static final String COLUMN_ENCRYPT_TYPE = "encrypt_type";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_THIRD_LOGIN = "third_login";
        public static final String COLUMN_THIRD_NAME = "third_name";
        public static final String COLUMN_THIRD_INFO = "third_info";
    }
}
