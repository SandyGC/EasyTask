
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
package com.easytask.adaptet.viewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.easytask.R;
import com.easytask.modelo.ListTasks;

/**
 * Created by sandy on 4/11/14.
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
