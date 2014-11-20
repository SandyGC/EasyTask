package com.easytask.controller.customListener;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.easytask.R;
import com.easytask.dataBase.CustomCRUD.TaskDataBase;
import com.easytask.modelo.Task;

/**
 * Created by Sandy on 08/11/2014.
 */
public class OnClickCheckBox implements View.OnClickListener {

    private static final int DONE =1;
    private static final int NO_DONE =0;

    public CheckBox checkBoxTask;
    private TextView textView;
    public TaskDataBase taskDataBase;
    private Task task;

    public OnClickCheckBox(View v, Task task) {
        this.checkBoxTask = (CheckBox) v.findViewById(R.id.checkBox_task);
        this.textView = (TextView) v.findViewById(R.id.textview_task);
        this.taskDataBase = new TaskDataBase(v.getContext());
        this.task = task;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (checkBoxTask.isChecked()){
            Log.d("--------------","Echa");
            task.setTaskDone(DONE);
            textView.setTextColor(Color.GRAY);
            try {
                taskDataBase.update(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Log.d("--------------","no echa");
            task.setTaskDone(NO_DONE);
            textView.setTextColor(Color.BLACK);
            try {
                taskDataBase.update(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
