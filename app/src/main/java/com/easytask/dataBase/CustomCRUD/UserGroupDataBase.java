package com.easytask.dataBase.CustomCRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.easytask.dataBase.CRUD;
import com.easytask.dataBase.CreateDataBase;
import com.easytask.modelo.User;
import com.easytask.modelo.UserGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 5/12/14.
 */
public class UserGroupDataBase implements CRUD<UserGroup> {

    private Context context;
    //obtengo la instancia de mi base de datos
    private CreateDataBase db;
    //obteniendo la base de datos con permiso de escritura y la
    //guardo en una variable de tipo sqlitedatabase
    private SQLiteDatabase sqdb;
    private UserDataBase userDataBase;

    public UserGroupDataBase(Context context) {
        this.context = context;
        this.db = CreateDataBase.getInstance(this.context);
        this.sqdb = db.getWritableDatabase();
        this.userDataBase = new UserDataBase(context);
    }

    @Override
    public UserGroup insert(UserGroup object) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminGroup", object.getAdmin());
        contentValues.put("id_User", object.getUser().getIdUser());
        contentValues.put("id_Group", object.getGroup().getIdGroup());
        sqdb.insert("USERGROUP", null, contentValues);


        return object;
    }

    @Override
    public UserGroup read(int id) throws Exception {
        return null;
    }

    @Override
    public boolean update(UserGroup object) throws Exception {
        return false;
    }

    @Override
    public boolean delete(UserGroup object) throws Exception {
        return false;
    }

    @Override
    public List<UserGroup> getAll() throws Exception {
        return null;
    }

    public List<User> getAllForIdGroup(int idGroup) {

        List<User> userList = new ArrayList<User>();

        Cursor cursor = sqdb.rawQuery("SELECT id_User FROM USERGROUP WHERE id_Group = " + idGroup, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    User user = userDataBase.read(cursor.getInt(0));
                    userList.add(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        return userList;
    }
}
