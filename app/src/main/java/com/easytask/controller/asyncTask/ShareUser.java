package com.easytask.controller.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.easytask.controller.ControllerShareUsersFragment;
import com.easytask.dao.InterfacesDAO.IUserDao;
import com.easytask.dao.factory.gestorFactoriesDAO.GestorFactoryDAO;
import com.easytask.dataBase.CustomCRUD.UserDataBase;
import com.easytask.modelo.User;

/**
 * Created by danny on 3/12/14.
 */
public class ShareUser extends AsyncTask {

    private IUserDao iUserDao;
    private UserDataBase userDataBase;
    private ProgressDialog progressDialog;
    private User user;

    private ControllerShareUsersFragment controllerShareUsersFragment;

    public ShareUser(Context context, User user, ControllerShareUsersFragment controllerShareUsersFragment) {
        this.user = user;
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        userDataBase = new UserDataBase(context);
        try {
            iUserDao = GestorFactoryDAO.getInstance().getFactory().getIUserDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.controllerShareUsersFragment = controllerShareUsersFragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Buscando usuario");
        progressDialog.setMessage("... :D :) :P");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {

        User userRecovery = null;

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            userRecovery = iUserDao.readUserForShare(this.user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userRecovery;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
        if (o != null) {
            User user = (User) o;
            controllerShareUsersFragment.addUser(user);
            try {
                userDataBase.insert(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
