package com.easytask.controller.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.easytask.dao.InterfacesDAO.ITaskDao;
import com.easytask.dao.factory.gestorFactoriesDAO.GestorFactoryDAO;
import com.easytask.modelo.ListTasks;

/**
 * Created by danny on 3/12/14.
 */
public class UpdateTaskDone extends AsyncTask {

    private ListTasks listTasks;

    private ITaskDao iTaskDao;

    public UpdateTaskDone(Context context, ListTasks listTasks) {
        this.listTasks = listTasks;

        try {
            iTaskDao = GestorFactoryDAO.getInstance().getFactory().getITaskDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            iTaskDao.updateAllForList(this.listTasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.listTasks;
    }
}
