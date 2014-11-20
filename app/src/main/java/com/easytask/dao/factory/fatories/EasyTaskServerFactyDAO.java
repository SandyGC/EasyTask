package com.easytask.dao.factory.fatories;

import com.easytask.dao.InterfacesDAO.IGroupDao;
import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.dao.InterfacesDAO.ITaskDao;
import com.easytask.dao.InterfacesDAO.IUserDao;
import com.easytask.dao.InterfacesDAO.IUserGroupDao;
import com.easytask.dao.factory.FactoryDAO;
import com.easytask.dao.webSerserDao.EasyTaskGroupDao;
import com.easytask.dao.webSerserDao.EasyTaskListDao;
import com.easytask.dao.webSerserDao.EasyTaskTaskDao;
import com.easytask.dao.webSerserDao.EasyTaskUserDao;
import com.easytask.dao.webSerserDao.EasyTaskUserGroupDao;

/**
 * Created by danny on 18/11/14.
 */
public class EasyTaskServerFactyDAO implements FactoryDAO {
    @Override
    public IGroupDao getIGroupDao() throws Exception {
        return new EasyTaskGroupDao();
    }

    @Override
    public IListTaskDao getIListTaskDao() throws Exception {
        return new EasyTaskListDao();
    }

    @Override
    public ITaskDao getITaskDao() throws Exception {
        return new EasyTaskTaskDao();
    }

    @Override
    public IUserDao getIUserDao() throws Exception {
        return new EasyTaskUserDao();
    }

    @Override
    public IUserGroupDao getIUserGroupDao() throws Exception {
        return new EasyTaskUserGroupDao();
    }
}
