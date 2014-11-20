package com.easytask.dataBase.CustomCRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.easytask.dataBase.CRUD;
import com.easytask.dataBase.CreateDataBase;
import com.easytask.modelo.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandy on 08/11/2014.
 */
public class GroupDataBase implements CRUD<Group> {

    private Context context;
    //obtengo la instancia de mi base de datos
    private CreateDataBase db;
    //obteniendo la base de datos con permiso de escritura y la
    //guardo en una variable de tipo sqlitedatabase
    private SQLiteDatabase sqdb;

    public GroupDataBase(Context context) {
        this.context = context;
        this.db = CreateDataBase.getInstance(this.context);
        this.sqdb = db.getWritableDatabase();
    }

    @Override
    public Group insert(Group object) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameGroup", object.getNameGroup());
        contentValues.put("id_UnicoG", object.getId_UnicoG());
        int id_Group = (int) sqdb.insert("GROUPS", null, contentValues);
        object.setIdGroup(id_Group);

        return object;
    }

    @Override
    public Group read(int id) throws Exception {

        Group group = null;
        if (sqdb != null) {

            Cursor cursor = sqdb.rawQuery("SELECT * FROM GROUPS WHERE id_Group = " + id, null);

            if (cursor.moveToFirst()) {
                group = new Group(cursor.getInt(0), cursor.getString(1), cursor.getString(1));
            }

        }
        return group;
    }

    @Override
    public boolean update(Group object) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameGroup", object.getNameGroup());
        contentValues.put("id_UnicoG", object.getId_UnicoG());
        int numColums = sqdb.update("GROUPS", contentValues, "id_Group = " + object.getIdGroup(), null);
        if (numColums == 0) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean delete(Group object) throws Exception {
        int numColums = sqdb.delete("GROUPS", "id_Group = " + object.getIdGroup(), null);
        if (numColums == 0) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public List<Group> getAll() throws Exception {

        List<Group> listGroup = new ArrayList<Group>();
        if (sqdb != null) {

            Cursor cursor = sqdb.rawQuery("SELECT * FROM GROUPS ", null);

            if (cursor.moveToFirst()) {
                do {
                    Group group = new Group(cursor.getInt(0), cursor.getString(1), cursor.getString(1));
                    listGroup.add(group);
                } while (cursor.moveToNext());
            }

        }
        return listGroup;
    }
}
