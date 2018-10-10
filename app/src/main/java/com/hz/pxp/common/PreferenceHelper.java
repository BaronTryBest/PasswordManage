package com.hz.pxp.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {
    private static SharedPreferences prefs;

    public static void init(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void remove(String key){
        synchronized (prefs){
            prefs.edit().remove(key).commit();
        }
    }

    public static void putString(String key, String value){
        synchronized (prefs){
            prefs.edit().putString(key,value).commit();
        }
    }

    public static void putLong(String key, long value) {
        synchronized (prefs) {prefs.edit().putLong(key, value).commit();}
    }

    public static void putBoolean(String key, boolean value) {
        synchronized (prefs) {prefs.edit().putBoolean(key, value).commit();}
    }

    public static void putInt(String key, int value) {
        synchronized (prefs) {prefs.edit().putInt(key, value).commit();}
    }

    public static void putFloat(String key,float value) {
        synchronized (prefs) {prefs.edit().putFloat(key,value).commit();}
    }
    /**
     * 获取字符串
     *
     * @param key
     * @return 如果存在则返回相应的值，否则返回NULL
     */
    public static String getString(String key) {
        synchronized (prefs) {return prefs.getString(key, "");}
    }

    /**
     * 获取Boolean
     *
     * @param key
     * @return 默认返回false
     */
    public static boolean getBoolean(String key, boolean defaultVal) {
        synchronized (prefs) {return prefs.getBoolean(key, defaultVal);}
    }

    /**
     * 获取Int
     *
     * @param key
     * @return 默认返回-1
     */
    public static int getInt(String key) {
        synchronized (prefs) {return prefs.getInt(key, -1);}
    }

    public static long getLong(String key) {
        synchronized (prefs) {return prefs.getLong(key, 0);}
    }

    public static float getFloat(String key) {
        float v = 0f;
        synchronized (prefs) {
            do {
                try {
                    v = prefs.getFloat(key, 0f);
                    break;
                } catch (Exception e) {
                }
                try {
                    int iv = prefs.getInt(key, 0);
                    v = iv;
                    break;
                } catch (Exception e) {
                }
            } while (false);
        }

        return v;
    }

    public static void clear() {
        synchronized (prefs) {prefs.edit().clear();}
    }
}
