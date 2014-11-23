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
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.easytask.R;
import com.easytask.adaptet.ListTaskAdapter;
import com.easytask.controller.interfaceFragment.OnFragmentInteractionListener;
import com.easytask.dataBase.CustomCRUD.ListTaskDataBase;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.Task;
import com.easytask.modelo.User;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class ControllerListListTaskFragment extends Fragment implements OnFragmentInteractionListener {

    private ListTaskDataBase listTaskDataBase;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private ListView listListTaskView;
    private ArrayList<Task> listTaks, listTaks2;
    private List<ListTasks> listListTasks;
    private ListTaskAdapter listTaskAdapter;

    private User user;


    private Bundle bundle;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ControllerListListTaskFragment newInstance(Bundle bundle) {

        ControllerListListTaskFragment fragment = new ControllerListListTaskFragment();
        Bundle args = new Bundle();
        args.putParcelable("usuario", bundle.getParcelable("usuario"));
        fragment.setArguments(args);

        return fragment;
    }

    public ControllerListListTaskFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_controller_list_list_task, container, false);
        listListTaskView = (ListView) v.findViewById(R.id.listListTask);

        user = getActivity().getIntent().getParcelableExtra("usuario");

        listTaskDataBase = new ListTaskDataBase(getActivity().getApplicationContext());


        try {
            listListTasks = listTaskDataBase.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        listTaskAdapter = new ListTaskAdapter(this, listListTasks);

        listListTaskView.setAdapter(listTaskAdapter);


        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((ControllerListListTask) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listListTaskView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onFragmentInteraction(listListTasks.get(position), 0);
            }
        });

    }

    @Override
    public void onFragmentInteraction(Object o, int number) {

        ListTasks listTask = (ListTasks) o;

        Bundle bundle = new Bundle();
        bundle.putParcelable("listTask", listTask);
        bundle.putParcelable("user", this.user);

        Fragment fragment = new ControllerListTaskFragment().newInstance(bundle);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }
}