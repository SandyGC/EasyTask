package com.easytask.controller.asyncTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.dao.factory.gestorFatoriesDAO.GestorFactoryDAO;
import com.easytask.dataBase.CustomCRUD.ListTaskDataBase;
import com.easytask.modelo.ListTasks;

import java.util.List;

/**
 * Created by danny on 1/12/14.
 */
public class MyService extends Service {

    private ListTaskDataBase listTaskDataBase;
    private IListTaskDao iListTaskDao;
    private List<ListTasks> listListTask;

    private static MyService instance = null;

    public static boolean isRunning() {
        return instance != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        listTaskDataBase = new ListTaskDataBase(this.getApplicationContext());
        try {
            iListTaskDao = GestorFactoryDAO.getInstance().getFactory().getIListTaskDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(this.getApplicationContext(), "servicio iniciado", Toast.LENGTH_SHORT).show();
        try {
            listListTask = listTaskDataBase.getAllWhereStatusLocal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listListTask.size() == 0) {
            this.onDestroy();
        } else {
            for (int i = 0; i < listListTask.size(); i++) {
                try {
                    ListTasks listTask = iListTaskDao.insert(listListTask.get(i));
                    if (listTask != null) {
                        listTaskDataBase.update(listTask);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
