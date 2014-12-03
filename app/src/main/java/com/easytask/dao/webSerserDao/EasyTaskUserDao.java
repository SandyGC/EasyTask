package com.easytask.dao.webSerserDao;

import com.easytask.dao.InterfacesDAO.IUserDao;
import com.easytask.modelo.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;

/**
 * Created by danny on 18/11/14.
 */
public class EasyTaskUserDao implements IUserDao {

    private static final String URL = "http://easyt.esy.es/index.php/";

    private HttpClient httpClient = new DefaultHttpClient();

    @Override
    public User insert(User object) throws Exception {
        HttpPost httpPostInserUser = new HttpPost(URL + "users/create/" + object.getNameUser() + "/"
                + object.getNickNameUser() + "/" + object.getEmailUser() + "/" + object.getPasswordUser() + "/"
                + object.getIdUserGCM());
        HttpResponse httpResponse = httpClient.execute(httpPostInserUser);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            return object;
        } else {
            return null;
        }

    }

    @Override
    public User read(User object) throws Exception {
        //Construimos la user a la que vamos a llamar
        HttpPost httpPostReadUser = new HttpPost(URL + "users/read/" + object.getNickNameUser());
        //Preparamos la respuesta del server
        HttpResponse response = httpClient.execute(httpPostReadUser);
        //Recuperamos el codigo de la respuesta
        int statusCode = response.getStatusLine().getStatusCode();
        //Si el codigo es 200, es uaurio existe en el servidor
        if (statusCode == 200) {
            return null;
        } else {
            return object;
        }

    }


    @Override
    public boolean update(User object) throws Exception {
        return false;
    }

    @Override
    public boolean delete(User object) throws Exception {
        return false;
    }

    @Override
    public List<User> getAll() throws Exception {
        return null;
    }
}
