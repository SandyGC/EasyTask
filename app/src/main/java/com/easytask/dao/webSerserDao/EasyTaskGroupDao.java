package com.easytask.dao.webSerserDao;

import com.easytask.dao.InterfacesDAO.IGroupDao;
import com.easytask.modelo.Group;

import org.apache.http.client.methods.HttpPost;

import java.util.List;

/**
 * Created by danny on 18/11/14.
 */
public class EasyTaskGroupDao implements IGroupDao {

    private static final String URL = "http://easyt.site40.net/index.php/";

    @Override
    public Group insert(Group object) throws Exception {

        int lastPasticipan = object.getParticipants().size() - 1;

        HttpPost httpPost = new HttpPost(URL + "group/share/" + object.getParticipants().get(lastPasticipan) + "/" +
                object.getParticipants().get(0) + "/");
        return null;
    }

    @Override
    public Group read(Group object) throws Exception {
        return null;
    }


    @Override
    public boolean update(Group object) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Group object) throws Exception {
        return false;
    }

    @Override
    public List<Group> getAll() throws Exception {
        return null;
    }
}
