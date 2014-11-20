package com.easytask.controller.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.easytask.controller.ControllerSingIn;
import com.easytask.modelo.User;

/**
 * Created by danny on 31/10/14.
 */
public class CheckData extends AsyncTask {

    private static final String TAG = "com.controller.asyncTask.CheckData";

    private Context context;
    private ControllerSingIn controllerSignIn;
    private ProgressDialog progresDialog;
    private User user;

    public CheckData(Context context, ControllerSingIn controllerSignIn, User user) {
        this.context = context;
        this.controllerSignIn = controllerSignIn;
        this.user = user;
        progresDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
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
            Thread.sleep(5000);
            Log.d(TAG, user.getNameUser());
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
        controllerSignIn.intentPassword(user);
    }
}
