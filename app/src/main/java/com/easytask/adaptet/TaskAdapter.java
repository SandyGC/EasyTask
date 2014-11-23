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

package com.easytask.adaptet;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.easytask.R;
import com.easytask.adaptet.viewHolder.TaskHolder;
import com.easytask.modelo.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandy on 31/10/14.
 */
public class TaskAdapter extends BaseAdapter {

    private Fragment fragment;
    private List<Task> listTask;
    private LayoutInflater inflater;

    public TaskAdapter(Fragment fragment, List<Task> listTask) {
        this.fragment = fragment;
        this.listTask = listTask;
        inflater = LayoutInflater.from(fragment.getActivity().getApplicationContext());
    }

    @Override
    public int getCount() {
        return listTask.size();
    }

    @Override
    public Object getItem(int position) {
        return listTask.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listTask.get(position).getIdTask();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        TaskHolder taskHolder = null;

        Task task = listTask.get(position);

        if (v == null) {

            v = inflater.inflate(R.layout.item_task, null);
            taskHolder = new TaskHolder(v, task);
            v.setTag(taskHolder);

        } else {

            taskHolder = (TaskHolder) v.getTag();
        }

        taskHolder.setContentView(task);

        return v;
    }
}
