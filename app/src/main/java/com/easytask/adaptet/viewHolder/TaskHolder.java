package com.easytask.adaptet.viewHolder;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.easytask.R;
import com.easytask.controller.customListener.OnClickCheckBox;
import com.easytask.modelo.Task;

/**
 * Created by danny on 31/10/14.
 */
public class TaskHolder extends ViewBaseHolder {

    private CheckBox checkBoxTask;
    private TextView textView;
    private OnClickCheckBox onClickCheckBox;
  //  private Task task;

    public TaskHolder(View v, Task task) {
        getContentView(v);
        onClickCheckBox = new OnClickCheckBox(v, task);
    }

    @Override
    public void getContentView(View v) {
        super.getContentView(v);
        checkBoxTask = (CheckBox) v.findViewById(R.id.checkBox_task);
        textView = (TextView) v.findViewById(R.id.textview_task);

    }

    @Override
    public void setContentView(Object o) {
        super.setContentView(o);
        Task task = (Task) o;
        if (task.getTaskDone() == 0){
            checkBoxTask.setChecked(false);
            textView.setTextColor(Color.BLACK);
        }else{
            checkBoxTask.setChecked(true);
            textView.setTextColor(Color.GRAY);
        }
        textView.setText(task.getTittle());
        //Modificamos el listener del CheckBox para poder alamcenar cualquier cambio en la database
        checkBoxTask.setOnClickListener(onClickCheckBox);
    }
}
