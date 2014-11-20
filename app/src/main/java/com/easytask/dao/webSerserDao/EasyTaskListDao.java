package com.easytask.dao.webSerserDao;

import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.modelo.ListTasks;

import java.util.List;

/**
 * Created by danny on 18/11/14.
 */
public class EasyTaskListDao implements IListTaskDao {
    @Override
    public ListTasks insert(ListTasks object) throws Exception {
        return null;
    }

    @Override
    public ListTasks read(int id) throws Exception {
        return null;
    }

    @Override
    public boolean update(ListTasks object) throws Exception {
        return false;
    }

    @Override
    public boolean delete(ListTasks object) throws Exception {
        return false;
    }

    @Override
    public List<ListTasks> getAll() throws Exception {
        return null;
    }
}
