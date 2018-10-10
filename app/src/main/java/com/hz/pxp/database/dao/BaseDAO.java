package com.hz.pxp.database.dao;

import java.util.ArrayList;

public interface BaseDAO <T>{
    public long save(T t);
    public int delete(T t);
    public int update(T t);
    public ArrayList<T> query(T t);
}
