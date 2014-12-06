package com.easytask.controller.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.easytask.controller.ControllerShareUsersFragment;
import com.easytask.dao.InterfacesDAO.IGroupDao;
import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.dao.InterfacesDAO.IUserGroupDao;
import com.easytask.dao.factory.gestorFactoriesDAO.GestorFactoryDAO;
import com.easytask.dataBase.CustomCRUD.GroupDataBase;
import com.easytask.dataBase.CustomCRUD.ListTaskDataBase;
import com.easytask.dataBase.CustomCRUD.UserGroupDataBase;
import com.easytask.modelo.Group;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.User;
import com.easytask.modelo.UserGroup;

/**
 * Created by danny on 3/12/14.
 */
public class ShareList extends AsyncTask {

    private ListTasks listTasks;
    private User userShare, userAdmin;
    private IGroupDao iGroupDao;
    private IListTaskDao iListTaskDao;
    private IUserGroupDao iUserGroupDao;
    private Group group;
    //
    private ListTaskDataBase listTaskDataBase;
    private UserGroupDataBase userGroupDataBase;
    private GroupDataBase groupDataBase;
    //
    private ProgressDialog progressDialog;
    //
    private ControllerShareUsersFragment controllerShareUsersFragment;

    public ShareList(ControllerShareUsersFragment controllerShareUsersFragment, Context context,
                     Group group, ListTasks listTasks) {
        this.controllerShareUsersFragment = controllerShareUsersFragment;
        this.listTasks = listTasks;
        this.group = group;
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        this.listTaskDataBase = new ListTaskDataBase(context);
        this.userGroupDataBase = new UserGroupDataBase(context);
        this.groupDataBase = new GroupDataBase(context);
        try {
            iGroupDao = GestorFactoryDAO.getInstance().getFactory().getIGroupDao();
            iListTaskDao = GestorFactoryDAO.getInstance().getFactory().getIListTaskDao();
            iUserGroupDao = GestorFactoryDAO.getInstance().getFactory().getIUserGroupDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Compartiendo");
        progressDialog.setMessage("...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        int option = Integer.parseInt(params[0].toString());
        Group saveGroup = null;
        UserGroup userGroup = null;
        UserGroup saveUserGroup = null;

        switch (option) {
            case 0:
                try {
                    saveGroup = iGroupDao.insert(group);
                    if (saveGroup != null) {
                        groupDataBase.insert(saveGroup);
                        saveGroup.setParticipants(group.getParticipants());
                        listTasks.setGroup(saveGroup);
                        ListTasks listTasksSave = iListTaskDao.updateID_Group(listTasks);
                        listTasksSave.setStatus_share(1);
                        listTaskDataBase.update(listTasksSave);
                        //
                        User userAdmin = group.getParticipants().get(0);
                        //
                        for (int i = 0; i < listTasks.getGroup().getParticipants().size(); i++) {

                            if (listTasks.getGroup().getParticipants().get(i).getNickNameUser()
                                    .equals(userAdmin.getNickNameUser())) {
                                userGroup = new UserGroup(listTasks.getUser(), saveGroup, 1);
                            } else {
                                userGroup = new UserGroup(listTasks.getGroup().getParticipants().get(i), saveGroup, 0);
                            }
                            //Inserto en el servidor
                            saveUserGroup = iUserGroupDao.insert(userGroup);
                            if (saveUserGroup != null) {
                                userGroupDataBase.insert(saveUserGroup);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                int userAdd = listTasks.getGroup().getParticipants().size() - 1;
                UserGroup userGroupAdd = new UserGroup(listTasks.getGroup().getParticipants().get(userAdd),
                        group, 0);
                try {
                    saveUserGroup = iUserGroupDao.insert(userGroupAdd);
                    if (saveUserGroup != null) {
                        userGroupDataBase.insert(userGroupAdd);
                    }
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

        return saveUserGroup;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
        // if (o != null){
        controllerShareUsersFragment.onFragmentInteraction(null, 0);
        // }
    }
}
