package com.easytask.dao.webSerserDao;

import android.util.Log;

import com.easytask.dao.InterfacesDAO.IGroupDao;
import com.easytask.modelo.Group;

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
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by danny on 18/11/14.
 */
public class EasyTaskGroupDao implements IGroupDao {

    private static final String URL = "http://easyt.esy.es/index.php/";

    private HttpClient httpClient = new DefaultHttpClient();

    @Override
    public Group insert(Group object) throws Exception {

        int lastPasticipan = object.getParticipants().size() - 1;

        String url = URLEncoder.encode(object.getParticipants().get(0).getNickNameUser(), "UTF-8");

        HttpPost httpPost = new HttpPost(URL + "group/create/" + url);

        //Preparamos la respuesta del server
        HttpResponse response = httpClient.execute(httpPost);
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
            Group group = new Group(jsonObject.getInt("id_Group"), jsonObject.getString("nameGroup"), jsonObject.getString("id_UnicoG"));

            return group;
        } else {
            return null;
        }
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
