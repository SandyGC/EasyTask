package com.easytask.controller.asyncTask;

import android.os.AsyncTask;

import com.easytask.dao.InterfacesDAO.IGroupDao;
import com.easytask.dao.factory.gestorFatoriesDAO.GestorFactoryDAO;
import com.easytask.modelo.Group;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.User;

/**
 * Created by danny on 3/12/14.
 */
public class ShareList extends AsyncTask {

    private ListTasks listTasks;
    private User userShare, userAdmin;
    private IGroupDao iGroupDao;
    private Group group;

    public ShareList(Group group, ListTasks listTask) {

        this.group = group;
        this.listTasks = listTask;

        try {
            iGroupDao = GestorFactoryDAO.getInstance().getFactory().getIGroupDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {

        return null;
    }
}
