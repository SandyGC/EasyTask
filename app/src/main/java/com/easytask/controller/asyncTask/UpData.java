package com.easytask.controller.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.dao.factory.gestorFatoriesDAO.GestorFactoryDAO;
import com.easytask.dataBase.CustomCRUD.ListTaskDataBase;
import com.easytask.modelo.ListTasks;

/**
 * Created by danny on 2/12/14.
 */
public class UpData extends AsyncTask {

    public IListTaskDao iListTaskDao;
    public ListTasks listTasks;
    public ListTaskDataBase listTaskDataBase;

    public UpData(Context context, ListTasks listTasks) {
        listTaskDataBase = new ListTaskDataBase(context);
        this.listTasks = listTasks;
        try {
            this.iListTaskDao = GestorFactoryDAO.getInstance().getFactory().getIListTaskDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {

        ListTasks listTasks = null;

        try {
            listTasks = iListTaskDao.insert(this.listTasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTasks;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o != null) {
            ListTasks listTasks = (ListTasks) o;
            try {
                listTaskDataBase.update(listTasks);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
