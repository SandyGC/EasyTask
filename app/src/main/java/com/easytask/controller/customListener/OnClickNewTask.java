package com.easytask.controller.customListener;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.easytask.R;
import com.easytask.controller.ControllerNewListTaskFragmet;
import com.easytask.modelo.Task;

/**
 * Created by danny on 6/11/14.
 */
public class OnClickNewTask implements View.OnClickListener {

    private EditText editText;
    private CheckBox checkBox;
    private ControllerNewListTaskFragmet controllerNewListTaskFragmet;

    public OnClickNewTask(ControllerNewListTaskFragmet controllerNewListTaskFragmet) {
        editText = (EditText) controllerNewListTaskFragmet.getView().findViewById(R.id.editTextNewTask);
        checkBox = (CheckBox) controllerNewListTaskFragmet.getView().findViewById(R.id.checkBoxNewTask);
        this.controllerNewListTaskFragmet = controllerNewListTaskFragmet;
        editText.requestFocus();

    }

    @Override
    public void onClick(View v) {
        String newTask = editText.getText().toString().trim();
        if (newTask.length() > 0){
            int done = checkBox.isChecked()?1:0;
            Task task = new Task(done, newTask);
            this.controllerNewListTaskFragmet.addTask(task);
        }
        editText.setText("");
        checkBox.setChecked(false);
    }

}
