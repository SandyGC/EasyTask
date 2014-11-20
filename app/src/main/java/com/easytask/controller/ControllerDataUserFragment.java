package com.easytask.controller;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easytask.R;
import com.easytask.controller.interfaceFragment.OnFragmentInteractionListener;
import com.easytask.dataBase.CustomCRUD.UserDataBase;
import com.easytask.modelo.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControllerDataUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ControllerDataUserFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private TextView editTextNombre, editTextNick, editTextEmail;
    private UserDataBase userDataBase;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ControllerDataUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControllerDataUserFragment newInstance() {
        ControllerDataUserFragment fragment = new ControllerDataUserFragment();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
        return fragment;
    }
    public ControllerDataUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_controller_data_user, container, false);

        editTextNombre = (TextView) v.findViewById(R.id.dataUserEditTextSingInNombre);
        editTextNick = (TextView) v.findViewById(R.id.dataUserEditTextSingInNick);
        editTextEmail = (TextView) v.findViewById(R.id.dataUserEditTextSingInEmail);

        userDataBase = new UserDataBase(getActivity().getApplicationContext());
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        User user = userDataBase.existPassword();

        editTextNombre.setText(user.getNameUser());
        editTextNick.setText(user.getNickNameUser());
        editTextEmail.setText(user.getEmailUser());


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
