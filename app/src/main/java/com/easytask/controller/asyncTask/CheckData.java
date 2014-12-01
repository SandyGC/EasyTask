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
import android.widget.Toast;

import com.easytask.controller.ControllerSingIn;
import com.easytask.dao.InterfacesDAO.IUserDao;
import com.easytask.dao.factory.gestorFatoriesDAO.GestorFactoryDAO;
import com.easytask.modelo.User;

/**
 * Created by sandy on 31/10/14.
 */
public class CheckData extends AsyncTask {

    private static final String TAG = "com.controller.asyncTask.CheckData";

    private Context context;
    private ControllerSingIn controllerSignIn;
    private ProgressDialog progresDialog;
    private User user;
    private IUserDao easyTaskUserDao;

    public CheckData(Context context, ControllerSingIn controllerSignIn, User user) {
        this.context = context;
        this.controllerSignIn = controllerSignIn;
        this.user = user;
        progresDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        try {
            easyTaskUserDao = GestorFactoryDAO.getInstance().getFactory().getIUserDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progresDialog.setTitle("Comprobando datos");
        progresDialog.setMessage("espera...");
        progresDialog.setCancelable(false);
        progresDialog.setIndeterminate(true);
        progresDialog.show();
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */


    @Override
    protected Object doInBackground(Object[] params) {

        try {
            this.user = easyTaskUserDao.read(this.user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.user;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param o The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progresDialog.dismiss();
        if (o == null) {
            Toast.makeText(context, "Debes elegir otro nick", Toast.LENGTH_SHORT).show();
        } else {
            controllerSignIn.intentPassword(user);
        }
    }
}
