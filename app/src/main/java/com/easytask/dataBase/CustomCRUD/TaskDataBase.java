package com.easytask.dataBase.CustomCRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.easytask.dataBase.CRUD;
import com.easytask.dataBase.CreateDataBase;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 3/11/14.
 */
public class TaskDataBase implements CRUD<Task> {

    private Context context;
    //obtengo la instancia de mi base de datos
    private CreateDataBase db;
    //obteniendo la base de datos con permiso de escritura y la
    //guardo en una variable de tipo sqlitedatabase
    private SQLiteDatabase sqdb;

    public TaskDataBase(Context context) {
        this.context = context;
        db = CreateDataBase.getInstance(context);
        sqdb = db.getWritableDatabase();
    }

    @Override
    public Task insert(Task object) throws Exception {

        ContentValues contentValues = new ContentValues();
        contentValues.putNull("id_Task");
        contentValues.put("realized",object.getTaskDone());
        contentValues.put("tittle",object.getTittle());
        contentValues.put("idList", object.getListTasks().getIdListTask());
        int id_Task = (int) sqdb.insert("TASKS", null, contentValues);
        object.setIdTask(id_Task);
        return object;
    }

    @Override
    public Task read(int id) throws Exception {
        return null;
    }

    @Override
    public boolean update(Task object) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("realized", object.getTaskDone());
        int numCols = sqdb.update("TASKS",contentValues,"id_Task = "+object.getIdTask(), null);
        if (numCols == 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean delete(Task object) throws Exception {
        return false;
    }

    @Override
    public List<Task> getAll() {
        return null;
    }

    /**
     * Metodo que recuperara todas la tareas a partir de un id de lista de tareas.
     * @param listTasks objeto del que ebtendra el id.
     * @return List con todos los capos TASK dentro.
     * @throws Exception
     */
    public List<Task> getAllFromListTask(ListTasks listTasks) throws Exception {

        List<Task> listTask = new ArrayList<Task>();

        Cursor cursor = sqdb.rawQuery("SELECT * FROM TASKS WHERE idList = " + listTasks.getIdListTask(), null);

        if (cursor.moveToFirst()){

            do {
                int idTask = cursor.getInt(0);
                int taskDone = cursor.getInt(1);
                String tittle = cursor.getString(2);
                Task task = new Task(idTask,taskDone,listTasks,tittle);

                listTask.add(task);

            }while (cursor.moveToNext());
        }

        return listTask;
    }
}
