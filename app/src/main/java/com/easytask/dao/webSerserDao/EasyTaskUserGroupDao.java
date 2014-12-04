package com.easytask.dao.webSerserDao;

import com.easytask.dao.InterfacesDAO.IUserGroupDao;
import com.easytask.modelo.UserGroup;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by danny on 18/11/14.
 */
public class EasyTaskUserGroupDao implements IUserGroupDao {

    private static final String URL = "http://easyt.esy.es/index.php/";

    private HttpClient httpClient = new DefaultHttpClient();

    @Override
    public UserGroup insert(UserGroup object) throws Exception {

        String url = URLEncoder.encode(String.valueOf(object.getAdmin()), "UTF-8");
        String url1 = URLEncoder.encode(object.getUser().getNickNameUser(), "UTF-8");
        String url2 = URLEncoder.encode(String.valueOf(object.getGroup().getIdGroup()), "UTF-8");

        HttpPost httpPost = new HttpPost(URL + "group/insertusergroup/" + url + "/" + url1 + "/" + url2);
        //Preparamos la respuesta del server
        HttpResponse response = httpClient.execute(httpPost);
        //Recuperamos el codigo de la respuesta
        int statusCode = response.getStatusLine().getStatusCode();
        //Si el codigo es 200, es uaurio existe en el servidor
        if (statusCode == 200) {

            return object;
        } else {
            return null;
        }
    }

    @Override
    public UserGroup read(UserGroup object) throws Exception {
        return null;
    }


    @Override
    public boolean update(UserGroup object) throws Exception {
        return false;
    }

    @Override
    public boolean delete(UserGroup object) throws Exception {
        return false;
    }

    @Override
    public List<UserGroup> getAll() throws Exception {
        return null;
    }
}
