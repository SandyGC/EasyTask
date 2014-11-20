package com.easytask.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easytask.R;
import com.easytask.controller.asyncTask.CheckData;
import com.easytask.controller.asyncTask.SingIn;
import com.easytask.modelo.User;

public class ControllerSingInPassword extends Activity {

    private static  final String TAG ="com.controller.ControllerSingInPassword";

    private static final int MAX_LENTH_PASS = 2;

    private EditText editTextPass1, editTextPass2;
    private Bundle bundle;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_sing_in_password);

        editTextPass1 = (EditText) findViewById(R.id.singInPassword1);
        editTextPass2 = (EditText) findViewById(R.id.singInPassword2);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("usuario");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.controller_sing_in_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public void checkPass(View v) {
        String paswor1 = editTextPass1.getText().toString();
        String paswor2 = editTextPass2.getText().toString();

        if (paswor1.length() >= MAX_LENTH_PASS) {

            if (paswor1.equals(paswor2)) {

                SingIn singIn = new SingIn(v.getContext(),this,user,paswor1);
                singIn.execute();

            } else {
                Toast.makeText(this, "La contraseña no coinciden", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "La contraseña tiene que tener 8 caracters minimos", Toast.LENGTH_SHORT).show();

        }

    }

    public void startApp(User user) {
        Intent controllerListListTask = new Intent(this,ControllerListListTask.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("usuario",user);
        Log.d(TAG,user.getPasswordUser());
        controllerListListTask.putExtras(bundle);
        startActivity(controllerListListTask);
        this.finish();
    }
}
