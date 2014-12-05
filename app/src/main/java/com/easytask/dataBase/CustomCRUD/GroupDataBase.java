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
import com.easytask.modelo.User;

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
    private UserGroupDataBase userGroupDataBase;

    public GroupDataBase(Context context) {
        this.context = context;
        this.db = CreateDataBase.getInstance(this.context);
        this.sqdb = db.getWritableDatabase();
        this.userGroupDataBase = new UserGroupDataBase(context);
    }

    @Override
    public Group insert(Group object) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_Group", object.getIdGroup());
        contentValues.put("nameGroup", object.getNameGroup());
        contentValues.put("id_UnicoG", object.getId_UnicoG());
        int id_Group = (int) sqdb.insert("GROUPS", null, contentValues);
        object.setIdGroup(id_Group);

        return object;
    }

    @Override
    public Group read(int id) throws Exception {

        Group group = null;
        List<User> userList = null;
        if (sqdb != null) {

            Cursor cursor = sqdb.rawQuery("SELECT * FROM GROUPS WHERE id_Group = " + id, null);

            if (cursor.moveToFirst()) {
                group = new Group(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                userList = userGroupDataBase.getAllForIdGroup(cursor.getInt(0));
                group.setParticipants(userList);
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
