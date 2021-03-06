/**
 * Copyright [2014] [Sandy Guerrero Cajas]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.easytask.dataBase.CustomCRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.easytask.dataBase.CRUD;
import com.easytask.dataBase.CreateDataBase;
import com.easytask.modelo.Group;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.Task;
import com.easytask.modelo.User;
import com.easytask.modelo.emun.StatusList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by sandy on 1/11/14.
 */
public class ListTaskDataBase implements CRUD<ListTasks> {

    private Context context;
    //obtengo la instancia de mi base de datos
    private CreateDataBase db;
    //obteniendo la base de datos con permiso de escritura y la
    //guardo en una variable de tipo sqlitedatabase
    private SQLiteDatabase sqdb;

    private UserDataBase userDataBase;
    private GroupDataBase groupDataBase;
    private TaskDataBase taskDataBase;

    public ListTaskDataBase(Context context) {
        this.context = context;
        this.db = CreateDataBase.getInstance(context);
        sqdb = db.getWritableDatabase();
        userDataBase = new UserDataBase(context);
        groupDataBase = new GroupDataBase(context);
        taskDataBase = new TaskDataBase(context);
    }

//    public void datosEjemlo() throws Exception {
///*
//        //id, Echo, idlista, titulo
//        Task tarea1 = new Task(0, 0, 0, "tarea1-Ejemplo");
//
//
//        ArrayList<Task> listTask = new ArrayList<Task>();
//        listTask.add(tarea1);
//
//*/
//        //id, Titulo, fecha, estado de compartida, estado de echa, id del grupo, id del usuario, lista de tareas
//        ListTasks lista1 = new ListTasks(0, "lista1-Ejemplo", getDateTime(), 0, StatusList.Realizando,userDataBase.read(1));
//        Log.d("Fecha obtenida por java ===>", getDateTime());
//
//
//
///*    private String createTableListTasks = "CREATE TABLE IF NOT EXISTS LISTTASKS " +
//            "(id_List INTEGER, " +
//            "titleList TEXT," +
//            "dateList DATE," +
//            "status INTEGER," +
//            "status_share TEXT"+
//            "id_Group INTEGER," +
//            "id_User INTEGER, " +
//            "statusList Text,"+
//            "FOREIGN KEY (id_Group) REFERENCES GROUPS (id_Group)," +
//            "FOREIGN KEY (id_User) REFERENCES USERS (id_User))";*//*
//
//*/
//        ContentValues valoresListTask = new ContentValues();
//        valoresListTask.put("id_List", lista1.getIdListTask());
//        valoresListTask.put("titleList", lista1.getTitle());
//        valoresListTask.put("dateList", lista1.getDateList());
//        valoresListTask.put("status_share", lista1.getStatusList().toString());
//        valoresListTask.put("status", lista1.getStatus());
//        valoresListTask.putNull("id_Group");
//        valoresListTask.put("id_User", lista1.getUser().getIdUser());
//        Long l = sqdb.insert("LISTTASKS", null, valoresListTask);
//        Log.d("insertando lista", l.toString());
//
//
//
//
//
///*
//        private String createTableTask = "create table if not exists TASKS " +
//                "(id_Task INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
//                "realized INTEGER, " +
//                "tittle TEXT," +
//                "idList INTEGER, " +
//                "FOREIGN KEY(idList) REFERENCES LISTTASKS(idList))";*/
///*
//        ContentValues valoresTask = new ContentValues();
//        valoresTask.put("id_Task", tarea1.getIdTask());
//        valoresTask.put("realized", tarea1.getTaskDone());
//        valoresTask.put("tittle", tarea1.getTittle());
//        valoresTask.put("idList", tarea1.getIdList());
//        sqdb.insert("TASKS", null, valoresTask);*/
//
//
//
//    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void insertAllList(List<ListTasks> listListTask) throws Exception {
        for (ListTasks listTasks : listListTask) {
            insert(listTasks);
        }

    }

    /**
     * @param object
     * @return
     * @throws Exception
     */
    @Override
    public ListTasks insert(ListTasks object) throws Exception {

        boolean isUnicol = false;

        ContentValues valuesListTask = new ContentValues();
        valuesListTask.putNull("id_List");
        valuesListTask.put("titleList", object.getTitle());
        valuesListTask.put("dateList", object.getDateList());
        valuesListTask.put("status_share", object.getStatus_share());
        valuesListTask.put("status", object.getStatusList().toString());

        if (object.getGroup() != null) {
            valuesListTask.put("id_Group", object.getGroup().getIdGroup());
        } else {
            valuesListTask.putNull("id_Group");
        }
        valuesListTask.put("status_server", object.getStatus_server());
        valuesListTask.put("id_User", object.getUser().getIdUser());


        if (object.getId_UnicoL() != null) {
            valuesListTask.put("id_UnicoL", object.getId_UnicoL());
            isUnicol = true;
        }

        int idListTask = (int) sqdb.insert("LISTTASKS", null, valuesListTask);

        //Modifico el id de la lista
        object.setIdListTask(idListTask);

        if (!isUnicol) {

            //Modifico la clave unia de la lista
            ListTasks listTasks = insertIdUnique(object);
            return listTasks;
        } else {
            return object;
        }


    }

    @Override
    public ListTasks read(int id) throws Exception {
        return null;
    }

    /**
     * @param object
     * @return
     * @throws Exception
     */
    @Override
    public boolean update(ListTasks object) throws Exception {

        ContentValues contentValues = new ContentValues();
        contentValues.put("titleList", object.getTitle());
        contentValues.put("dateList", object.getDateList());
        contentValues.put("status_share", object.getStatus_share());
        contentValues.put("status", object.getStatusList().toString());
        contentValues.put("id_UnicoL", object.getId_UnicoL());

        if (object.getGroup() != null) {
            contentValues.put("id_Group", object.getGroup().getIdGroup());
        } else {
            contentValues.putNull("id_Group");
        }

        contentValues.put("id_User", object.getUser().getIdUser());
        contentValues.put("status_server", object.getStatus_server());
        int numColums = sqdb.update("LISTTASKS", contentValues, "id_List = " + object.getIdListTask(), null);

        if (numColums == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param object
     * @return
     * @throws Exception
     */
    @Override
    public boolean delete(ListTasks object) throws Exception {
        int numColums = sqdb.delete("LISTTASKS", "id_List = " + object.getIdListTask(), null);
        if (numColums == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<ListTasks> getAll() throws Exception {

        List listListTask = new ArrayList();

        Cursor cursor = sqdb.rawQuery("SELECT * FROM LISTTASKS", null);

        TaskDataBase taskDataBase = new TaskDataBase(context);

        if (cursor.moveToFirst()) {
            do {


                int idListTask = cursor.getInt(0);
                String tittle = cursor.getString(1);
                String dateList = cursor.getString(2);
                int status = cursor.getInt(3);


                String id_unicoG = cursor.getString(5);
                int idGroup = cursor.getInt(6);
                Group group = null;
                if (idGroup != 0) {
                    group = groupDataBase.read(idGroup);

                }
                int idUser = cursor.getInt(7);
                int statusServer = cursor.getInt(8);
                User user = userDataBase.read(idUser);

                //Se coruye el objet ListTask y se le modifica el grupo sea null o no
                ListTasks listTasks = new ListTasks(idListTask, tittle, dateList, status, StatusList.Creada, id_unicoG, user, statusServer);
                listTasks.setGroup(group);

                List<Task> tasks = taskDataBase.getAllFromListTask(listTasks);
                listTasks.setTasks(tasks);

                StatusList statusList = listTasks.getValuesStatusList(cursor.getInt(4));

                listTasks.setStatusList(statusList);

                listListTask.add(listTasks);

            } while (cursor.moveToNext());
        }

        return listListTask;
    }

    public List<ListTasks> getAllWhereStatusLocal() throws Exception {

        List listListTask = new ArrayList();

        Cursor cursor = sqdb.rawQuery("SELECT * FROM LISTTASKS WHERE status_server = 0", null);

        TaskDataBase taskDataBase = new TaskDataBase(context);


        if (cursor.moveToFirst()) {
            do {


                int idListTask = cursor.getInt(0);
                String tittle = cursor.getString(1);
                String dateList = cursor.getString(2);
                int status = cursor.getInt(3);


                String id_unicoG = cursor.getString(5);
                int idGroup = cursor.getInt(6);
                Group group = null;
                if (idGroup != 0) {
                    group = groupDataBase.read(idGroup);

                }
                int idUser = cursor.getInt(7);
                int statusServer = cursor.getInt(8);
                User user = userDataBase.read(idUser);

                //Se coruye el objet ListTask y se le modifica el grupo sea null o no
                ListTasks listTasks = new ListTasks(idListTask, tittle, dateList, status, StatusList.Creada, id_unicoG, user, statusServer);
                listTasks.setGroup(group);

                List<Task> tasks = taskDataBase.getAllFromListTask(listTasks);
                listTasks.setTasks(tasks);

                StatusList statusList = listTasks.getValuesStatusList(cursor.getInt(4));

                listTasks.setStatusList(statusList);

                listListTask.add(listTasks);

            } while (cursor.moveToNext());
        }

        return listListTask;
    }

    /**
     * Metodo que modificara la clace unica de la lista.
     * Al llamar a este meodo se modificara la clave unica de la lista,en la DB y se modificar el objeto.
     *
     * @param object Objeto ListTask
     * @return ListTask con idUnico añadido
     */
    public ListTasks insertIdUnique(Object object) {

        ListTasks listTasks = (ListTasks) object;

        String id_unicoL = String.valueOf(listTasks.getUser().getNickNameUser() +
                listTasks.getIdListTask() +
                listTasks.getTitle());
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_UnicoL", id_unicoL);
        int numColums = sqdb.update("LISTTASKS", contentValues, "id_List = " + listTasks.getIdListTask(), null);
        listTasks.setId_UnicoL(id_unicoL);
        return listTasks;
    }

    public List<ListTasks> getAllWhereStatusShare() throws Exception {

        List listListTask = new ArrayList();

        Cursor cursor = sqdb.rawQuery("SELECT * FROM LISTTASKS WHERE status_share = 1", null);

        TaskDataBase taskDataBase = new TaskDataBase(context);


        if (cursor.moveToFirst()) {
            do {


                int idListTask = cursor.getInt(0);
                String tittle = cursor.getString(1);
                String dateList = cursor.getString(2);
                int status = cursor.getInt(3);


                String id_unicoG = cursor.getString(5);
                int idGroup = cursor.getInt(6);
                Group group = null;
                if (idGroup != 0) {
                    group = groupDataBase.read(idGroup);

                }
                int idUser = cursor.getInt(7);
                int statusServer = cursor.getInt(8);
                User user = userDataBase.read(idUser);

                //Se coruye el objet ListTask y se le modifica el grupo sea null o no
                ListTasks listTasks = new ListTasks(idListTask, tittle, dateList, status, StatusList.Creada, id_unicoG, user, statusServer);
                listTasks.setGroup(group);

                List<Task> tasks = taskDataBase.getAllFromListTask(listTasks);
                listTasks.setTasks(tasks);

                StatusList statusList = listTasks.getValuesStatusList(cursor.getInt(4));

                listTasks.setStatusList(statusList);

                listListTask.add(listTasks);

            } while (cursor.moveToNext());
        }

        return listListTask;
    }

    /**
     * Devuelve las tareas que son mias y no estan compartidas
     *
     * @return
     * @throws Exception
     */
    public List<ListTasks> getAllWhereMines() throws Exception {

        List listListTask = new ArrayList();

        Cursor cursor = sqdb.rawQuery("SELECT * FROM LISTTASKS WHERE status_share = 0", null);

        TaskDataBase taskDataBase = new TaskDataBase(context);


        if (cursor.moveToFirst()) {
            do {


                int idListTask = cursor.getInt(0);
                String tittle = cursor.getString(1);
                String dateList = cursor.getString(2);
                int status = cursor.getInt(3);


                String id_unicoG = cursor.getString(5);
                int idGroup = cursor.getInt(6);
                Group group = null;
                if (idGroup != 0) {
                    group = groupDataBase.read(idGroup);

                }
                int idUser = cursor.getInt(7);
                int statusServer = cursor.getInt(8);
                User user = userDataBase.read(idUser);

                //Se coruye el objet ListTask y se le modifica el grupo sea null o no
                ListTasks listTasks = new ListTasks(idListTask, tittle, dateList, status, StatusList.Creada, id_unicoG, user, statusServer);
                listTasks.setGroup(group);

                List<Task> tasks = taskDataBase.getAllFromListTask(listTasks);
                listTasks.setTasks(tasks);

                StatusList statusList = listTasks.getValuesStatusList(cursor.getInt(4));

                listTasks.setStatusList(statusList);

                listListTask.add(listTasks);

            } while (cursor.moveToNext());
        }

        return listListTask;
    }
    public ListTasks readForIDUnico(String id_UnicoL) throws Exception {
        ListTasks listTasks = null;
        if (sqdb != null) {
            String[] args = new String[]{id_UnicoL};
            Cursor cursor = sqdb.rawQuery("select * from LISTTASKS  WHERE id_UnicoL = ?", args);
            if (cursor.moveToFirst()) {

                int idListTask = cursor.getInt(0);
                String tittle = cursor.getString(1);
                String dateList = cursor.getString(2);
                int status = cursor.getInt(3);


                String id_unicoG = cursor.getString(5);
                int idGroup = cursor.getInt(6);
                Group group = null;
                if (idGroup != 0) {
                    group = groupDataBase.read(idGroup);

                }
                int idUser = cursor.getInt(7);
                int statusServer = cursor.getInt(8);
                User user = userDataBase.read(idUser);

                //Se coruye el objet ListTask y se le modifica el grupo sea null o no
                listTasks = new ListTasks(idListTask, tittle, dateList, status, StatusList.Creada, id_unicoG, user, statusServer);
                listTasks.setGroup(group);

                List<Task> tasks = taskDataBase.getAllFromListTask(listTasks);
                listTasks.setTasks(tasks);

                StatusList statusList = listTasks.getValuesStatusList(cursor.getInt(4));

                listTasks.setStatusList(statusList);
            }
        }
        return listTasks;
    }
}