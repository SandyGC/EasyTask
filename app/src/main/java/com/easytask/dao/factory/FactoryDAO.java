package com.easytask.dao.factory;

import com.easytask.dao.InterfacesDAO.IGroupDao;
import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.dao.InterfacesDAO.ITaskDao;
import com.easytask.dao.InterfacesDAO.IUserDao;
import com.easytask.dao.InterfacesDAO.IUserGroupDao;

/**
 * Created by danny on 18/11/14.
 */
public interface FactoryDAO {
    public IGroupDao getIGroupDao() throws Exception;
    public IListTaskDao getIListTaskDao() throws Exception;
    public ITaskDao getITaskDao() throws Exception;
    public IUserDao getIUserDao() throws Exception;
    public IUserGroupDao getIUserGroupDao() throws Exception;
}
