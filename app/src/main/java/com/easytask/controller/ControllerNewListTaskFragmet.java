package com.easytask.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.easytask.R;
import com.easytask.adaptet.TaskAdapter;
import com.easytask.controller.customListener.OnClickNewTask;
import com.easytask.controller.interfaceFragment.OnFragmentInteractionListener;
import com.easytask.dataBase.CustomCRUD.ListTaskDataBase;
import com.easytask.dataBase.CustomCRUD.TaskDataBase;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.Task;
import com.easytask.modelo.User;
import com.easytask.modelo.emun.StatusList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControllerNewListTaskFragmet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControllerNewListTaskFragmet extends Fragment implements OnFragmentInteractionListener {

    private ListView listTaskView;

    private EditText editText;

    private ListTasks listTasks;

    private User user;

    private TaskAdapter taskAdapter;

    private Button button;

    private ListTaskDataBase listTaskDataBase;

    private TaskDataBase taskDataBase;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ControllerNewListTaskFragmet.
     */

    public static ControllerNewListTaskFragmet newInstance(Bundle arguments) {
        ControllerNewListTaskFragmet fragment = new ControllerNewListTaskFragmet();
        Bundle args;
        if (arguments != null) {
            args = arguments;

            fragment.setArguments(args);
        }

        return fragment;
    }

    public ControllerNewListTaskFragmet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_controller_new_list_task, container, false);

        listTaskView = (ListView) v.findViewById(R.id.listViewNewTask);

        editText = (EditText) v.findViewById(R.id.title_listTask);

        button = (Button) v.findViewById(R.id.buttonListo);

        setHasOptionsMenu(true);

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.listTasks = getArguments().getParcelable("listTask");
        this.user = getArguments().getParcelable("user");
        if (listTasks == null) {
//            (String title, String dateList, int status, StatusList statusList, User user
            String tittleListTask = editText.getText().toString().trim();
            String date = getDateTime();
            int status = 0;
            StatusList statusList = StatusList.Creada;
            listTasks = new ListTasks(tittleListTask, date, status, statusList, this.user);
        }

        listTasks.setUser(user);

        OnClickNewTask clickNewTask = new OnClickNewTask(this);

        taskAdapter = new TaskAdapter(this, listTasks.getTasks());

        listTaskView.setAdapter(taskAdapter);

        button.setOnClickListener(clickNewTask);

        listTaskDataBase = new ListTaskDataBase(this.getActivity().getApplicationContext());

        taskDataBase = new TaskDataBase(this.getActivity().getApplicationContext());

        editText.setText(listTasks.getTitle());

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
     * MEtodo que añadira la tarea que acabamos de crear a la lista de tareas.
     * Primero se comprueba que la lista tenga tareas, si no las tiene, significa que la
     * lista es nueva y no esta alamacenada en la DB. Si la lista no esta creada la almacenaremos
     * y añadiremos las tareas a la lista. Si la lista ya esta creada, solo añadira tareas a
     * dicha lista.
     *
     * @param task
     */
    public void addTask(Task task) {
        if (listTasks.getIdListTask() == 0) {
            saveListTask(listTasks);
        }
        task.setListTasks(listTasks);
        user.addTask(listTasks, task);
        saveTask(task);
        taskAdapter.notifyDataSetChanged();
        listTaskView.setSelection(listTasks.getTasks().size() - 1);

    }


    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * {@link android.app.Activity#onCreateOptionsMenu(android.view.Menu) Activity.onCreateOptionsMenu}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        this.getActivity().getMenuInflater().inflate(R.menu.controller_new_list_task, menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p/>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                onFragmentInteraction(null, 0);
                break;
            case R.id.action_save:
                if  (listTasks.getTasks().size() == 0){
                    saveListTask(listTasks);
                }else{
                    updateListTask(listTasks);
                }
                onFragmentInteraction(null, 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * MEtodo que almacenarçá en la DB la lista con la que estamos trabajando.
     *
     * @param listTasks lista de tareas a almacenar.
     * @return lista de tareas que os devuelve la base de datos
     */
    public ListTasks saveListTask(ListTasks listTasks) {
        try {

            String tittleTask = editText.getText().toString().trim();
            listTasks.setTitle(tittleTask);
            this.listTasks = listTaskDataBase.insert(listTasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.listTasks;
    }

    public void updateListTask(ListTasks listTasks){
        listTasks.setTitle(editText.getText().toString().trim());
        try {
           listTaskDataBase.update(listTasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que alamcenara la tarea en la DB.
     *
     * @param task tarea a alamcenar
     * @return tarea alamacenada.
     */
    public Task saveTask(Task task) {
        Task returnTask = null;
        try {
            returnTask = taskDataBase.insert(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnTask;
    }

    /**
     * MEtodo que devolvera la fecha actual y nos devolvera un Strign de la fecha
     * con el formato con yyyy-MM-dd HH:mm
     *
     * @return
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onFragmentInteraction(Object o, int number) {

        Fragment fragment;
        android.app.FragmentManager fragmentManager = getFragmentManager();
        Bundle bundle = new Bundle();
        switch (number){
            case 0:
                 fragment = new ControllerShareUsersFragment().newInstance();

                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                break;
            case 1:
                bundle.putParcelable("usuario", user);
                 fragment = new ControllerListListTaskFragment().newInstance(bundle);
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                break;

        }
    }


}
