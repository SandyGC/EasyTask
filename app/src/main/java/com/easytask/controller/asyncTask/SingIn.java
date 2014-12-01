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

package com.easytask.controller.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.easytask.controller.ControllerSingInPassword;
import com.easytask.dao.InterfacesDAO.IUserDao;
import com.easytask.dao.factory.gestorFatoriesDAO.GestorFactoryDAO;
import com.easytask.dataBase.CustomCRUD.ListTaskDataBase;
import com.easytask.dataBase.CustomCRUD.UserDataBase;
import com.easytask.modelo.User;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by sandy on 31/10/14.
 */
public class SingIn extends AsyncTask {

    private static final String TAG = "com.controller.asyncTask.SingIn";

    private Context context;
    private ControllerSingInPassword controllerSingInPassword;
    private String password;
    private ProgressDialog progresDialog;
    private User user;
    private GoogleCloudMessaging gcm;
    private String regid;
    String SENDER_ID = "643243620549";
    private UserDataBase insertUser;
    private ListTaskDataBase listTaskDataBase;
    private IUserDao daoUser;

    public SingIn(Context context, ControllerSingInPassword controllerSingInPassword, User user, String password) {
        this.context = context;
        this.controllerSingInPassword = controllerSingInPassword;
        this.password = password;
        this.user = user;
        progresDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        try {
            daoUser = GestorFactoryDAO.getInstance().getFactory().getIUserDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progresDialog.setTitle("REGISTRANDOTE");
        progresDialog.setMessage("ESPERA...");
        progresDialog.setCancelable(false);
        progresDialog.setIndeterminate(true);
        progresDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        user.setPasswordUser(password);


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        User usr = null;
        try {
            usr = idGCM();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usr;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progresDialog.dismiss();

        User user = (User) o;

        if (user != null) {
            controllerSingInPassword.startApp(user);
        } else {
            Toast.makeText(context, "Error al registrar tu usuario, revisa tus datos", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }

    }

    /**
     * Metodo que inserta un usuario en la base de datos despues de haberlo registrado
     */
    public void saveUserLocal(User user) {

        insertUser = new UserDataBase(context);
        listTaskDataBase = new ListTaskDataBase(context);
        //inserto el usuario en la base de datos
        try {
            User u = insertUser.insert(user);
//            listTaskDataBase.datosEjemlo();
            Log.d("nombre usuario", u.getNameUser());
            Log.d("id gcm local: ", u.getIdUserGCM());
            if (user.getListListTask().size() > 0) {
                listTaskDataBase.insertAllList(user.getListListTask());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public User idGCM() throws IOException {


        gcm = GoogleCloudMessaging.getInstance(context);

        //obtenemos el registration id guardado
        regid = gcm.register(SENDER_ID);
        Log.d(TAG, "Registrado en GCM: registration_id=" + regid);
        user.setIdUserGCM(regid);
        //registro en el servidor

        User userInsertado = null;

        try {
            userInsertado = daoUser.insert(user);
            Log.d("Usuario registrado en el servidor :", user.getNameUser());
            //lo meto en local
            if (userInsertado != null) {
                saveUserLocal(userInsertado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInsertado;
    }


}
