package com.easytask.dao.webSerserDao;

import android.util.Log;

import com.easytask.dao.InterfacesDAO.IUserDao;
import com.easytask.modelo.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    @Override
    public User readUserForShare(User u) throws Exception {
        //Construimos la user a la que vamos a llamar
        HttpPost httpPostReadUser = new HttpPost(URL + "users/read/" + u.getNickNameUser());
        //Preparamos la respuesta del server
        HttpResponse response = httpClient.execute(httpPostReadUser);
        //Recuperamos el codigo de la respuesta
        int statusCode = response.getStatusLine().getStatusCode();
        //Si el codigo es 200, es uaurio existe en el servidor
        if (statusCode == 200) {

            //Nos conectamos para recibir los datos de respuesta
            HttpEntity entity = response.getEntity();
            //Creamos el InputStream
            InputStream is = entity.getContent();
            //Comvertimos el inputtStream en un string
            String temp = StreamToString(is);
            //Creamos el JSON con la cadena del inputStream
            Log.d("Cadena JSON", temp.toString());
            JSONObject jsonObject = new JSONObject(temp);
            Log.d("--------------", jsonObject.toString());
            User user = new User(jsonObject.getString("name"), jsonObject.getString("nick"), jsonObject.getString("email"));

            return user;
        } else {
            return null;
        }
    }

    /**
     * @param is
     * @return
     */
    public String StreamToString(InputStream is) {
        //Creamos el Buffer
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            //Bucle para leer todas las lineas
            //En este ejemplo al ser solo 1 la respuesta
            //Pues no hara falta
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //retornamos el codigo limpio
        return sb.toString();
    }
}
