package com.easytask.dao;

import java.util.List;

/**
 * Created by danny on 18/11/14.
 */
public interface DataAccesObject<T> {

    public T insert(T object) throws Exception;
    public T read(int id) throws Exception;
    public boolean update(T object) throws Exception;
    public boolean delete (T object) throws Exception;

    public List<T> getAll() throws Exception;
}
