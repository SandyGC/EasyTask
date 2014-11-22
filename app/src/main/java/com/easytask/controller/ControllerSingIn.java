package com.easytask.controller;

import android.app.Activity;
import android.content.Intent;
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

    public User validateFields(){

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

        User user = validateFields();

        if (user != null) {

            /**
             * Instancia de la tarea asincronica
             */

            CheckData cheakData = new CheckData(v.getContext(), this, user);
            cheakData.execute();
        }
    }


    public void intentPassword(User user){
        intentUserPassword = new Intent(this,ControllerSingInPassword.class);
        Bundle b = new Bundle();
        b.putParcelable("usuario", user);
        intentUserPassword.putExtras(b);
        startActivity(intentUserPassword);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(this,ControllerVistaInicio.class);
        startActivity(i);
        this.finish();
    }
}
