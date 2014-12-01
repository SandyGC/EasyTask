package com.easytask.dao.webSerserDao;

import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.modelo.ListTasks;

import org.apache.http.client.methods.HttpPost;

import java.util.List;

/**
 * Created by danny on 18/11/14.
 */
public class EasyTaskListDao implements IListTaskDao {

    private static final String URL = "http://easyt.site40.net/index.php/";

    @Override
    public ListTasks insert(ListTasks object) throws Exception {
        HttpPost httpPost = new HttpPost(URL + "/listtask/create/" + object.getTitle() + "/" + object.getDateList() + "/" +
                object.getStatus_share() + "/" + object.getStatusList().toString());
        return null;
    }

    @Override
    public ListTasks read(ListTasks object) throws Exception {

        return null;
    }


    @Override
    public boolean update(ListTasks object) throws Exception {
        return false;
    }

    @Override
    public boolean delete(ListTasks object) throws Exception {
        return false;
    }

    @Override
    public List<ListTasks> getAll() throws Exception {
        return null;
    }
}
