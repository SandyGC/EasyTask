package com.easytask.adaptet.viewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.easytask.R;
import com.easytask.modelo.ListTasks;

/**
 * Created by danny on 4/11/14.
 */
public class ListTaskHolder extends ViewBaseHolder {

    private TextView tittle, date, statusList;
    private ImageButton imageButton;

    public ListTaskHolder(View v) {
        getContentView(v);
    }

    @Override
    public void getContentView(View v) {
        super.getContentView(v);
        tittle = (TextView) v.findViewById(R.id.tittle_tasks);
        date = (TextView) v.findViewById(R.id.date_ListTask);
        statusList = (TextView) v.findViewById(R.id.statusList);
      //  imageButton = (ImageButton) v.findViewById(R.id.shareListTask);
    }

    @Override
    public void setContentView(Object o) {
        super.setContentView(o);
        ListTasks listTasks = (ListTasks) o;

        tittle.setText(listTasks.getTitle());
        date.setText(listTasks.getDateList());
        statusList.setText(listTasks.getStatusList().toString());
        //Imagen button de compartir
    }
}
