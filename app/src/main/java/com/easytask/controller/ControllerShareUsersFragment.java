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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.easytask.R;
import com.easytask.adaptet.UserAdapter;
import com.easytask.controller.asyncTask.ShareList;
import com.easytask.controller.asyncTask.ShareUser;
import com.easytask.controller.interfaceFragment.OnFragmentInteractionListener;
import com.easytask.dataBase.CustomCRUD.UserDataBase;
import com.easytask.modelo.Group;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControllerShareUsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControllerShareUsersFragment extends Fragment implements OnFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;

    private EditText editText;
    private ListView listView;
    private Button searchButton;
    private ShareUser shareUser;
    private ControllerShareUsersFragment controllerShareUsersFragment;
    private UserAdapter userAdapter;
    private List<User> userList;
    //
    private ListTasks listTasks;
    private User user;
    //
    private UserDataBase userDataBase;
    private View v;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ControllerShareUsersFragment.
     */
    public static ControllerShareUsersFragment newInstance(Bundle arguments) {
        ControllerShareUsersFragment fragment = new ControllerShareUsersFragment();
        Bundle args = new Bundle();
        if (arguments != null) {
            args = arguments;
        }
        fragment.setArguments(args);
        return fragment;
    }

    public ControllerShareUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controllerShareUsersFragment = this;

        userDataBase = new UserDataBase(this.getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (savedInstanceState != null) {
            this.listTasks = savedInstanceState.getParcelable("listTask");
            this.user = savedInstanceState.getParcelable("user");
        } else {
            this.listTasks = getArguments().getParcelable("listTask");
            this.user = getArguments().getParcelable("user");
        }

        v = inflater.inflate(R.layout.fragment_controller_share_users, container, false);


        if (v != null) {
            editText = (EditText) v.findViewById(R.id.searchName);
            listView = (ListView) v.findViewById(R.id.listView_searchUsers);
            searchButton = (Button) v.findViewById(R.id.searchButton);
        }

        userList = userDataBase.getAllWhereI();

        userAdapter = new UserAdapter(this, userList);

        listView.setAdapter(userAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nikNameUser = editText.getText().toString().trim();
                if (nikNameUser.length() != 0) {

                    userList.removeAll(userList);
                    userAdapter.notifyDataSetChanged();

                    User user = new User(nikNameUser);
                    shareUser = new ShareUser(v.getContext(), user, controllerShareUsersFragment);
                    shareUser.execute();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("voy acompartir", "-------------------->");

                if (listTasks.getGroup() == null) {
                    Group group = new Group("Sin nombre");
                    group.getParticipants().add(user);
                    group.getParticipants().add(userList.get(position));
                    ShareList shareList = new ShareList(controllerShareUsersFragment, v.getContext(), group, listTasks);
                    shareList.execute(0);
                } else {
                    Group group = listTasks.getGroup();
                    group.getParticipants().add(userList.get(position));
                    ShareList shareList = new ShareList(controllerShareUsersFragment, v.getContext(), group, listTasks);
                    shareList.execute(1);
                }

            }
        });

        //Modifico las opciones de menu para que se infle otro layout
        setHasOptionsMenu(true);

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        this.getActivity().getMenuInflater().inflate(R.menu.global, menu);
    }

    public void addUser(User user) {
        userList.add(user);
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentInteraction(Object o, int number) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("listTask", listTasks);
        bundle.putParcelable("user", user);
        Fragment fragment = new ControllerListTaskFragment().newInstance(bundle);
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }
}
