package com.easytask.controller;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.easytask.R;
import com.easytask.adaptet.UserAdapter;
import com.easytask.controller.interfaceFragment.OnFragmentInteractionListener;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControllerInfoListTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ControllerInfoListTaskFragment extends Fragment {

    private static final String AUTOR ="Autor: ";

    private static final String TITULO ="Titulo: ";

    private TextView tittlePropietario, tittleListTask, dateTask, statusTask;

    private ListView listView;

    private ListTasks listTasks;

    private User user;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ControllerInfoListTaskFragment.
     */
    public static ControllerInfoListTaskFragment newInstance(Bundle bundle) {
        ControllerInfoListTaskFragment fragment = new ControllerInfoListTaskFragment();
        Bundle args = new Bundle();
        if (bundle != null){
            args.putParcelable("listTask",bundle.getParcelable("listTask"));
            args.putParcelable("user",bundle.getParcelable("user"));
        }
        fragment.setArguments(args);
        return fragment;
    }
    public ControllerInfoListTaskFragment() {
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

        View v = inflater.inflate(R.layout.fragment_controller_info_list_task, container, false);

        if (v!= null){
            tittlePropietario = (TextView) v.findViewById(R.id.infoUserListTask);
            tittleListTask = (TextView) v.findViewById(R.id.info_tittle_ListTask);
            listView = (ListView) v.findViewById(R.id.InfoListViewListTask);
            dateTask = (TextView) v.findViewById(R.id.info_date_ListTask);
            statusTask = (TextView) v.findViewById(R.id.info_statusList);
        }

        listTasks = getArguments().getParcelable("listTask");

        user = getArguments().getParcelable("user");

        tittlePropietario.setText(AUTOR+listTasks.getUser().getNickNameUser());

        tittleListTask.setText(TITULO+listTasks.getTitle());

        dateTask.setText(listTasks.getDateList());

        statusTask.setText(listTasks.getStatusList().toString());

        List<User> userList = new ArrayList<User>();

        userList.add(user);

        UserAdapter userAdapter = new UserAdapter(this, userList);

        listView.setAdapter(userAdapter);

        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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


}
