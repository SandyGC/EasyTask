package com.easytask.dao.webSerserDao;

import com.easytask.dao.InterfacesDAO.IUserDao;
import com.easytask.modelo.User;

import java.util.List;

/**
 * Created by danny on 18/11/14.
 */
public class EasyTaskUserDao implements IUserDao {
    @Override
    public User insert(User object) throws Exception {
        return null;
    }

    @Override
    public User read(int id) throws Exception {
        return null;
    }

    @Override
    public boolean update(User object) throws Exception {
        return false;
    }

    @Override
    public boolean delete(User object) throws Exception {
        return false;
    }

    @Override
    public List<User> getAll() throws Exception {
        return null;
    }
}
