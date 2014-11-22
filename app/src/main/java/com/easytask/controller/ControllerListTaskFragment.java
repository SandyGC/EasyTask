package com.easytask.controller;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.easytask.R;
import com.easytask.adaptet.TaskAdapter;
import com.easytask.controller.interfaceFragment.OnFragmentInteractionListener;
import com.easytask.dataBase.CustomCRUD.TaskDataBase;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.Task;
import com.easytask.modelo.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControllerListTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControllerListTaskFragment extends Fragment implements OnFragmentInteractionListener {

    private final String TAG = "com.controller.ControllerListTaskFragment";

    private ListView listTaskView;

    private TextView textViewUser, textViewTittle;

    private ListTasks listTask;

    private User user;

    private TaskDataBase taskDataBase;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param arguments Bundle.
     * @return A new instance of fragment ControllerListTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControllerListTaskFragment newInstance(Bundle arguments) {
        ControllerListTaskFragment fragment = new ControllerListTaskFragment();
        Bundle args = new Bundle();

        if (arguments != null) {
            args = arguments;
        }
        fragment.setArguments(args);

        return fragment;
    }

    public ControllerListTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        taskDataBase = new TaskDataBase(this.getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_controller_list_task, container, false);

        if (v != null) {
            textViewTittle = (TextView) v.findViewById(R.id.tittle_list);
            textViewUser = (TextView) v.findViewById(R.id.listTask_nombre);
            listTaskView = (ListView) v.findViewById(R.id.listTask);

        }

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

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(android.os.Bundle)},
     * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}, and
     * {@link #onActivityCreated(android.os.Bundle)}.
     * <p/>
     * <p>This corresponds to {@link android.app.Activity#onSaveInstanceState(android.os.Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("listTask", listTask);
        outState.putParcelable("user", user);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            listTask = savedInstanceState.getParcelable("listTask");
            user = savedInstanceState.getParcelable("user");
        } else {
            listTask = getArguments().getParcelable("listTask");
            user = getArguments().getParcelable("user");
        }

        List<Task> taskList = null;
        try {
            taskList = taskDataBase.getAllFromListTask(listTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

        textViewUser.setText(listTask.getTitle());

        listTask.setTasks(taskList);

        textViewUser.setText(user.getNameUser());
        Log.d(TAG, listTask.getTitle());

        TaskAdapter taskAdapter = new TaskAdapter(this, listTask.getTasks());

        listTaskView.setAdapter(taskAdapter);

        textViewTittle.setText(listTask.getTitle());

        listTask.setUser(user);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        this.getActivity().getMenuInflater().inflate(R.menu.controller_list_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.newTask:
                onFragmentInteraction(listTask, 1);
                break;
            case R.id.action_shareListTask:
                onFragmentInteraction(listTask, 2);
                break;
            case R.id.infoListTask:
                onFragmentInteraction(listTask, 3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Object o, int number) {

        ListTasks listTask = (ListTasks) o;

        Fragment fragment = null;

        Bundle bundle = new Bundle();

        switch (number){
            case 1:


                bundle.putParcelable("listTask", listTask);
                bundle.putParcelable("user", user);

                fragment = new ControllerNewListTaskFragmet().newInstance(bundle);

                break;
            case 2:

                bundle.putParcelable("listTask", listTask);
                bundle.putParcelable("user", user);

                fragment = new ControllerShareUsersFragment().newInstance();
                break;
            case 3:

                bundle.putParcelable("listTask",listTask);
                bundle.putParcelable("user", user);
                fragment = new ControllerInfoListTaskFragment().newInstance(bundle);

                break;
        }


        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link android.app.Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */

}
