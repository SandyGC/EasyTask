package com.easytask.controller.asyncTask;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;

import com.easytask.controller.ControllerListListTask;
import com.easytask.dataBase.CustomCRUD.ListTaskDataBase;
import com.easytask.dataBase.CustomCRUD.TaskDataBase;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.Task;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by danny on 5/12/14.
 */
public class GCMIntentService extends IntentService {

    private static final int NOTIF_ALERTA_ID = 1;

    private ListTaskDataBase listTaskDataBase;
    private TaskDataBase taskDataBase;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public GCMIntentService() {
        super("GCMIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        listTaskDataBase = new ListTaskDataBase(getApplicationContext());
        taskDataBase = new TaskDataBase(getApplicationContext());

        //Se obtiene una instancia del GCM
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        //Se obtiene los extras del intent.
        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();

        String taskRecive = extras.getString("task");
        String addTaskToList = extras.getString("add_tasks");
        String ListTaskRecive = extras.getString("listTask");

        if (taskRecive != null) {

            String tasks = extras.getString("tasks");
            try {
                JSONObject jsonObject = new JSONObject(tasks);
                String id_UnicoL = jsonObject.getString("id_UnicoL");
                //Recuperamos la lista alamcenada en local
                ListTasks lisTasksLocal = ListTaskForIdUnico(id_UnicoL);
                //Obtenemos las tareas recibidas
                JSONArray jsonArrayTasks = jsonObject.getJSONArray("tasks");
                //Iteramos sobre las tareas
                for (int i = 0; i < jsonArrayTasks.length(); i++) {
                    //Obtenemos un objeto json por cada tarea
                    JSONObject jsonObjectTask = jsonArrayTasks.getJSONObject(i);
                    //Construimos un objeto task con los datos obtenidos
                    Task task = new Task(jsonObjectTask.getInt("realized"), jsonObjectTask.getString("tittle"));
                    //Iteramos sobre las tareas alamcenadas en local, y buscamos las que tienen el mismo titulo y
                    //las modificamos
                    for (int j = 0; j < lisTasksLocal.getTasks().size(); j++) {
                        if (lisTasksLocal.getTasks().get(i).getTittle().equals(task.getTittle())) {
                            task.setIdTask(lisTasksLocal.getTasks().get(i).getIdTask());
                            try {
                                taskDataBase.update(task);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (addTaskToList != null) {
            try {
                JSONObject jsonObject = new JSONObject(addTaskToList);
                String id_unicoL = jsonObject.getString("id_UnicoL");
                ListTasks listTasks = listTaskDataBase.readForIDUnico(id_unicoL);
                JSONObject jsonObjectTask = jsonObject.getJSONObject("task");
                Task task = new Task(jsonObjectTask.getInt("realized"), jsonObjectTask.getString("tittle"));
                task.setListTasks(listTasks);

                taskDataBase.insert(task);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ListTaskRecive != null) {
            mostrarNotification();
        }

    }

    private void mostrarNotification() {


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        Notification.Builder n =
                new Notification.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_menu_edit)
                        .setContentTitle("EasyTask")
                        .setContentText("Nueva lista compartida")
                        .setAutoCancel(true)
                        .setWhen(new Date().getTime())
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(this, ControllerListListTask.class);


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contIntent = PendingIntent.getActivity(
                this, 0, intent, 0);


        n.setContentIntent(contIntent);


        mNotificationManager.notify(NOTIF_ALERTA_ID, n.getNotification());

    }

    public ListTasks ListTaskForIdUnico(String id_UnicoL) {

        ListTasks listTasks = null;

        try {
            listTasks = listTaskDataBase.readForIDUnico(id_UnicoL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTasks;
    }
}
