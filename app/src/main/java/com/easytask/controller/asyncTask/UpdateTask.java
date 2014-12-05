package com.easytask.controller.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.easytask.controller.ControllerNewListTaskFragmet;
import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.dao.InterfacesDAO.ITaskDao;
import com.easytask.dao.factory.gestorFactoriesDAO.GestorFactoryDAO;
import com.easytask.dataBase.CustomCRUD.TaskDataBase;
import com.easytask.modelo.ListTasks;

/**
 * Created by danny on 3/12/14.
 */
public class UpdateTask extends AsyncTask {
    private TaskDataBase taskDataBase;
    private IListTaskDao iListTaskDao;
    private ITaskDao iTaskDao;
    private ListTasks listTasks;
    private ControllerNewListTaskFragmet controllerNewListTaskFragmet;


    public UpdateTask(ControllerNewListTaskFragmet controllerNewListTaskFragmet, Context context, ListTasks listTasks) {
        this.controllerNewListTaskFragmet = controllerNewListTaskFragmet;
        this.taskDataBase = new TaskDataBase(context);
        this.listTasks = listTasks;

        try {
            iListTaskDao = GestorFactoryDAO.getInstance().getFactory().getIListTaskDao();
            iTaskDao = GestorFactoryDAO.getInstance().getFactory().getITaskDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        boolean save = false;
        try {
            iListTaskDao.update(this.listTasks);
//            iTaskDao.updateAllForList(this.listTasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if (save) {
        return this.listTasks;
        // } else {
        //     return null;
        // }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o != null) {
            ListTasks listTasks = (ListTasks) o;
            controllerNewListTaskFragmet.updateListTask(listTasks);
        }
    }
}
