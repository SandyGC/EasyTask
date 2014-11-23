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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.easytask.R;
import com.easytask.dataBase.CustomCRUD.UserDataBase;
import com.easytask.modelo.User;

public class ControllerVistaInicio extends Activity {

    private Button botonSigIn;
    private Intent intent;
    private Bundle bundleUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_vista_inicio);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.fondoinicioapp));
        botonSigIn = (Button) findViewById(R.id.button_registrar);

        existUser();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.controller_vista_inicio, menu);
        return false;
    }


    public void startActivitySingIn(View v) {
        intent = new Intent(v.getContext(), ControllerSingIn.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Metodo que comprueba que ya haya datos de un usuario en la dtabase del movil
     * para poder darle la opcion de registrarse o directamente salirle las tareas
     */

    public void existUser() {

        UserDataBase c = new UserDataBase(getApplicationContext());

        if (c.existPassword() != null) {
            User user = c.existPassword();
            bundleUsuario = new Bundle();
            bundleUsuario.putParcelable("usuario", user);
            intent = new Intent(this.getApplicationContext(), ControllerListListTask.class);
            intent.putExtras(bundleUsuario);
            startActivity(intent);
            this.finish();

        }
    }
}
