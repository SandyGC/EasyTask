package com.easytask.controller.asyncTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.easytask.controller.interfaceFragment.CheckConection;
import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.dao.factory.gestorFactoriesDAO.GestorFactoryDAO;
import com.easytask.dataBase.CustomCRUD.ListTaskDataBase;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.Task;
import com.easytask.modelo.emun.StatusList;

import java.util.List;

/**
 * Created by danny on 1/12/14.
 */
public class MyService extends Service {

    private ListTaskDataBase listTaskDataBase;
    private IListTaskDao iListTaskDao;
    private List<ListTasks> listListTask, listListTaskComple;

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
        Toast.makeText(this.getApplicationContext(), "servicio detenido", Toast.LENGTH_SHORT).show();
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

        try {
            listListTaskComple = listTaskDataBase.getAll();
            for (int i = 0; i < listListTaskComple.size(); i++) {
                if (isComplet(listListTaskComple.get(i).getTasks())) {
                    listListTaskComple.get(i).setStatusList(StatusList.Terminada);
                    try {
                        listTaskDataBase.update(listListTaskComple.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CheckConection.verificaConexion(this.getApplicationContext()) & CheckConection.executeCammand()) {
            if (listListTask.size() == 0) {
                this.onDestroy();
            } else {
                for (int i = 0; i < listListTask.size(); i++) {

                    UpData upData = new UpData(this.getApplicationContext(), listListTask.get(i));
                    upData.execute();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        } else {
            this.onDestroy();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean isComplet(List<Task> taks) {
        boolean complet = true;
        //int numTareas = taks.size();
        for (int i = 0; i < taks.size(); i++) {
            if (taks.get(i).getTaskDone() == 0) {
                complet = false;
            }
        }
        return complet;
    }
}
