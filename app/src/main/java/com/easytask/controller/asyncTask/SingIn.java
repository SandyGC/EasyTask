package com.easytask.controller.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.easytask.controller.ControllerSingInPassword;
import com.easytask.dataBase.CustomCRUD.ListTaskDataBase;
import com.easytask.dataBase.CustomCRUD.UserDataBase;
import com.easytask.modelo.User;

/**
 * Created by danny on 31/10/14.
 */
public class SingIn extends AsyncTask {

    private static final String TAG = "com.controller.asyncTask.SingIn";

    private Context context;
    private ControllerSingInPassword controllerSingInPassword;
    private String password;
    private ProgressDialog progresDialog;
    private User user;
    //
    private UserDataBase insertUser;
    private ListTaskDataBase listTaskDataBase;

    public SingIn(Context context, ControllerSingInPassword controllerSingInPassword, User user, String password) {
        this.context = context;
        this.controllerSingInPassword = controllerSingInPassword;
        this.password = password;
        this.user = user;
        progresDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
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
        try {
            Thread.sleep(5000);
            user.setPasswordUser(password);
            Log.d(TAG, user.getNameUser());
            Log.d(TAG, user.getPasswordUser());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveUserLocal(user);
        return user;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progresDialog.dismiss();

        User user = (User) o;

        if (user != null) {
            controllerSingInPassword.startApp(user);
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
            insertUser.insert(user);
//            listTaskDataBase.datosEjemlo();
            listTaskDataBase.insertAllList(user.getListListTask());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
