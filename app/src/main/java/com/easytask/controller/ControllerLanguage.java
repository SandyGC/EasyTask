package com.easytask.controller;

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

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.easytask.R;
import com.easytask.controller.customListener.OnItemClickListenerListView;
import com.easytask.controller.interfaceFragment.OnFragmentInteractionListener;
import com.easytask.modelo.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControllerLanguage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControllerLanguage extends Fragment implements OnFragmentInteractionListener {

    private static final String LANGUAGE = "language";

    private OnItemClickListenerListView onItemClickListenerListView;

    private String[] idiomas = new String[]{"System language", "Español", "English"};

    private ListView listView;

    private User user;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ControllerLanguage.
     */
    public static ControllerLanguage newInstance(Bundle arguments) {
        ControllerLanguage fragment = new ControllerLanguage();
        Bundle args = new Bundle();
        if (arguments != null) {
            args = arguments;
        }
        fragment.setArguments(args);
        return fragment;
    }

    public ControllerLanguage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_controller_language, container, false);

        if (v != null) {
            listView = (ListView) v.findViewById(R.id.listview_language);
        }
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onItemClickListenerListView = new OnItemClickListenerListView(this);

        if (savedInstanceState != null) {
            this.user = getArguments().getParcelable("user");
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.simple_item_listview, idiomas);

        listView.setAdapter(adaptador);

        listView.setEnabled(true);

        listView.setOnItemClickListener(onItemClickListenerListView);

        //Modifico las opciones de menu para que se infle otro layout
        setHasOptionsMenu(true);
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

    /**
     * @param Languaje
     */
    public void setLanguage(String Languaje) {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(
                ControllerLanguage.class.getSimpleName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE, Languaje);
        editor.commit();
    }


    /**
     * Metodo qye sera llamao para que se recarge la app con el idioma elegido
     */
    public void rebootApp() {
        onFragmentInteraction(user, 0);
    }

    @Override
    public void onFragmentInteraction(Object o, int number) {

        User user = (User) o;

        Fragment fragment;
        android.app.FragmentManager fragmentManager = getFragmentManager();
        Bundle bundle = new Bundle();

        bundle.putParcelable("usuario", user);
        fragment = new ControllerListListTaskFragment().newInstance(bundle);
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();


    }
}
