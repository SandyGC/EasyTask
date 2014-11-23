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

package com.easytask.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sandy on 04/10/2014.
 */
public class CreateDataBase extends SQLiteOpenHelper {

    /**
     * Informacion de la Base De Datos
     */

    private static final String nameDB = "GNotesDB";
    private static final int versioDB = 1;

    private String createTableUser = "CREATE TABLE IF NOT EXISTS  USERS "
            + "(id_User INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "name TEXT NOT NULL," +
            "nick TEXT NOT NULL UNIQUE," +
            "email TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "idUserGCM TEXT )";
    private String createTableTask = "create table if not exists TASKS " +
            "(id_Task INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "realized INTEGER NOT NULL, " +
            "tittle TEXT," +
            "idList INTEGER NOT NULL, " +
            "FOREIGN KEY(idList) REFERENCES LISTTASKS(idList))";
    private String createTableGroup = "CREATE TABLE IF NOT EXISTS  GROUPS " +
            "(id_Group INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "nameGroup TEXT," +
            "id_UnicoG TEXT UNIQUE)";

    private String createTableUserGroup = "CREATE TABLE IF NOT EXISTS  USERGROUP" +
            "( adminGroup INTEGER NOT NULL ," +
            "id_User INTEGER NOT NULL," +
            " id_Group INTEGER NOT NULL ," +
            "PRIMARY KEY (id_User,id_Group)," +
            "FOREIGN KEY(id_User) REFERENCES USERS (id_User)," +
            "FOREIGN KEY(id_Group)REFERENCES GROUPS (id_Group))";
    private String createTableListTasks = "CREATE TABLE IF NOT EXISTS LISTTASKS " +
            "(id_List INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "titleList TEXT," +
            "dateList TIMESTAMP NOT NULL DEFAULT current_timestamp," +
            "status_share INTEGER NOT NULL," +
            "status TEXT NOT NULL," +
            "id_UnicoL TEXT UNIQUE," +
            "id_Group INTEGER ," +
            "id_User INTEGER , " +
            "FOREIGN KEY (id_Group) REFERENCES GROUPS (id_Group)," +
            "FOREIGN KEY (id_User) REFERENCES USERS (id_User))";


    private Context miContexto;
    private static CreateDataBase mInstance = null;

    /**
     * @param context
     */
    public CreateDataBase(Context context) {
        super(context, nameDB, null, versioDB);

        context = this.miContexto;
    }

    public static CreateDataBase getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new CreateDataBase(ctx.getApplicationContext());
        }
        return mInstance;
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*aqui ejecuto todas las consultas referentes a la DDBB*/
        db.execSQL(createTableUser);
        db.execSQL(createTableTask);
        db.execSQL(createTableListTasks);
        db.execSQL(createTableGroup);
        db.execSQL(createTableUserGroup);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Elimino todas las tablas y las vuelvo a crear*/
        db.execSQL("drop table if exist USER");
        db.execSQL("drop table if exist TASK");
        db.execSQL("drop table if exist GROUP");
        db.execSQL("drop table if exist USERGROUP");
        db.execSQL("drop table if exist LISTTASKS");
        db.execSQL(createTableUser);
        db.execSQL(createTableTask);
        db.execSQL(createTableGroup);
        db.execSQL(createTableUserGroup);
        db.execSQL(createTableListTasks);
    }


}
