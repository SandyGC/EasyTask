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
import android.util.Log;

import com.easytask.dataBase.CRUD;
import com.easytask.dataBase.CreateDataBase;
import com.easytask.modelo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandy on 13/10/2014.
 */
public class UserDataBase implements CRUD<User> {


    private Context context;
    //obtengo la instancia de mi base de datos
    private CreateDataBase db;
    //obteniendo la base de datos con permiso de escritura y la
    //guardo en una variable de tipo sqlitedatabase
    private SQLiteDatabase sqdb;

    public UserDataBase(Context context) {
        this.context = context;
        db = CreateDataBase.getInstance(context);
        sqdb = db.getWritableDatabase();
    }


    public User selectUser(String nickU) {
        User user = null;
        if (sqdb != null) {

            /**
             * "CREATE TABLE IF NOT EXISTS  USERS "
             + "(id_User INTEGER PRIMARY KEY AUTOINCREMENT " +
             "NOT NULL,name TEXT,nick TEXT,email TEXT,password TEXT)";
             */
            Cursor cursor = sqdb.rawQuery("select * from USERS where nick='" + nickU + "'", null);
            if (cursor.moveToFirst()) {

                user = new User(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

            }

        }

        return user;
    }

    /**
     * Metodo que comprueba que ya me he registrado en la base de datos y me devuelve el usuario
     * local.
     *
     * @return
     */
    public User existPassword() {
        User user = null;
        if (sqdb != null) {

            /**
             * "CREATE TABLE IF NOT EXISTS  USERS "
             + "(id_User INTEGER PRIMARY KEY AUTOINCREMENT " +
             "NOT NULL,name TEXT,nick TEXT,email TEXT,password TEXT)";
             */
            Cursor cursor = sqdb.rawQuery("select * from USERS  where password not like ''", null);
            if (cursor.moveToFirst()) {

                user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3));

            }

        }

        return user;
    }

    /**
     * Metodo que me insertara un reregistro en la DB.
     *
     * @param object Objeto a insertar.
     * @return Objeto insertado en la DB. Se diferencia del objeto que ha entrado al metodo en que
     * este tiene un ID.
     * @throws Exception
     */
    @Override
    public User insert(User object) throws Exception {

        User user = object;

        int idUser = 0;


        if (sqdb != null) {
            ContentValues valores = new ContentValues();
            valores.putNull("id_User");
            valores.put("name", user.getNameUser());
            valores.put("nick", user.getNickNameUser());
            valores.put("email", user.getEmailUser());
            valores.put("password", user.getPasswordUser());
            valores.put("idUserGCM", user.getIdUserGCM());
            idUser = (int) sqdb.insert("Users", null, valores);

        }

        user.setIdUser(idUser);

        return user;
    }

    /**
     * Metodo que m e devolvera un usuario de la DB busvando por ID.
     *
     * @param id Identificador a eliminar de la DB.
     * @return Usuario encontrado, null en caso de no existir,
     * @throws Exception
     */
    @Override
    public User read(int id) throws Exception {
        User user = null;
        if (sqdb != null) {

            Cursor cursor = sqdb.rawQuery("select * from USERS  where id_User = " + id, null);
            if (cursor.moveToFirst()) {
                user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            }
        }
        return user;
    }

    /**
     * Metodo que actualizara un registro en la DB.
     *
     * @param object objeto a actualizar
     * @return Devuelve el nmero de columnas afectadas, 0 e n caso de error
     * @throws Exception
     */
    @Override
    public boolean update(User object) throws Exception {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", object.getNameUser());
        contentValues.put("nick", object.getNickNameUser());
        contentValues.put("email", object.getEmailUser());
        contentValues.put("idUserGCM", object.getIdUserGCM());
        int numColums = sqdb.update("USERS", contentValues, "id_User = " + object.getIdUser(), null);
        if (numColums == 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Metodo que me eliminara un usuario de la DB.
     *
     * @param object Objeto a eliminar de la DB.
     * @return Nmero de columans afectadas, 0 en caso de error.
     * @throws Exception
     */
    @Override
    public boolean delete(User object) throws Exception {
        int numColums = sqdb.delete("USERS", "id_User = " + object.getIdUser(), null);
        if (numColums == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Metodo que me devol vera toda la lista de usurios de la DB
     *
     * @return List<Users>
     */
    @Override
    public List<User> getAll() {
        List<User> listUser = new ArrayList<User>();

        Cursor cursor = sqdb.rawQuery("SELECT * FROM USERS", null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3));
                listUser.add(user);
            } while (cursor.moveToNext());
        }
        return listUser;
    }
}
