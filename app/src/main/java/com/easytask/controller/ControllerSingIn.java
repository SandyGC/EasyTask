
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
package com.easytask.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easytask.R;
import com.easytask.controller.asyncTask.CheckData;
import com.easytask.dataBase.CustomCRUD.UserDataBase;
import com.easytask.modelo.User;
import com.google.android.gms.fitness.data.Value;

import java.io.IOException;

public class ControllerSingIn extends Activity {

    private EditText textViewname, textViewnick, textViewemail;
    private User user;

    //Instancia de la clase
    private UserDataBase insertUser;
    //intent para poder pasar abrir otra actividad
    private Intent intentUserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_sing_in);

        textViewname = (EditText) findViewById(R.id.editTextSingInNombre);
        textViewnick = (EditText) findViewById(R.id.editTextSingInNick);
        textViewemail = (EditText) findViewById(R.id.editTextSingInEmail);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.controller_sing_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();
        // if (id == R.id.action_settings) {
        //     return true;
        // }
        return super.onOptionsItemSelected(item);
    }

    public User validateFields() {

        String name = textViewname.getText().toString().trim();
        String email = textViewemail.getText().toString().trim();
        String nick = textViewnick.getText().toString().trim();


        if (name.equals("")) {
            Toast toastName = Toast.makeText(this, "El nombre no puede ser vacío", Toast.LENGTH_LONG);
            toastName.show();
            // txtNombre.setBackgroundColor(Color.RED);
            return null;
        } else if (nick.equals("")) {
            Toast toastNick = Toast.makeText(this, "El Nick no puede ser vacío", Toast.LENGTH_LONG);
            toastNick.show();
            //  txtNick.setBackgroundColor(Color.RED);
            return null;

        } else if (email.equals("")) {
            Toast toastEmail = Toast.makeText(this, "Email no puede estar vacio", Toast.LENGTH_LONG);
            toastEmail.show();
            //  txtEmail.setBackgroundColor(Color.RED);
            return null;


        } else if (checkValidEmail(email) == false) {
            Toast toastEmail = Toast.makeText(this, "El email es incorrecto", Toast.LENGTH_LONG);
            toastEmail.show();
            //  txtEmail.setBackgroundColor(Color.RED);
            return null;
        } else {
            //construyo el usuario con los datos introducidos sin contraseña
            user = new User(name, nick, email);

            return user;
        }

    }

    /**
     * Metodo que comprueba el formato del email
     *
     * @param email
     * @return
     */
    public static boolean checkValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern) && email.length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que
     *
     * @param v
     */
    public void next(View v) {
        //comprobar conexion internet
        if (verificaConexion(v.getContext()) && executeCammand()) {
            User user = validateFields();

            if (user != null) {

                /**
                 * Instancia de la tarea asincronica
                 */

                CheckData cheakData = new CheckData(v.getContext(), this, user);
                cheakData.execute();
            }

        } else {

            Toast conection = Toast.makeText(this, "No hay internet", Toast.LENGTH_LONG);
            conection.show();
        }

    }

    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No solo wifi, tambien GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle deberia no ser tan nyapa
        for (int i = 0; i < 2; i++) {
            // Tenemos conexion? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    private boolean executeCammand() {
        System.out.println(" executeCammand");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue " + mExitValue);
            if (mExitValue == 0) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
            System.out.println(" Exception:" + ignore);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
        }
        return false;
    }

    public void intentPassword(User user) {
        intentUserPassword = new Intent(this, ControllerSingInPassword.class);
        Bundle b = new Bundle();
        b.putParcelable("usuario", user);
        intentUserPassword.putExtras(b);
        startActivity(intentUserPassword);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, ControllerVistaInicio.class);
        startActivity(i);
        this.finish();
    }
}
