package com.easytask.dao.webSerserDao;

import android.util.Log;

import com.easytask.dao.InterfacesDAO.IListTaskDao;
import com.easytask.modelo.ListTasks;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 18/11/14.
 */
public class EasyTaskListDao implements IListTaskDao {

    private static final String TAG = "com.easyTask.dao.webServerDao.EasyTaskListDao";

    private static final String URL = "http://easyt.esy.es/index.php/";

    private HttpClient httpClient = new DefaultHttpClient();


    @Override
    public ListTasks insert(ListTasks object) throws Exception {
        int id_group = 0;
        if (object.getGroup() != null) {
            id_group = object.getGroup().getIdGroup();
        }

        HttpPost httpPost = new HttpPost(URL + "listtask/create/" + object.getTitle() + "/" + object.getDateList() + "/" +
                object.getStatus_share() + "/" + object.getStatusList().toString() + "/" + object.getId_UnicoL() + "/" +
                id_group + "/" + object.getUser().getNickNameUser());

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < object.getTasks().size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tittle", object.getTasks().get(i).getTittle());
            jsonObject.put("realized", object.getTasks().get(i).getTaskDone());
            jsonArray.put(jsonObject);
        }

        List parametros = new ArrayList();

        parametros.add(new BasicNameValuePair("json", jsonArray.toString()));

        Log.d(TAG, jsonArray.toString());

        httpPost.setEntity(new UrlEncodedFormEntity(parametros));


        HttpResponse httpResponse = httpClient.execute(httpPost);
        int response = httpResponse.getStatusLine().getStatusCode();
        if (response == 200) {
            object.setStatus_server(1);
            return object;
        } else {
            return null;
        }
    }

    @Override
    public ListTasks read(ListTasks object) throws Exception {

        return null;
    }


    @Override
    public boolean update(ListTasks object) throws Exception {


        HttpPost httpPost = new HttpPost(URL + "listtask/update/" + object.getId_UnicoL());

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < object.getTasks().size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tittle", object.getTasks().get(i).getTittle());
            jsonObject.put("realized", object.getTasks().get(i).getTaskDone());
            jsonArray.put(jsonObject);
        }

        List parametros = new ArrayList();

        parametros.add(new BasicNameValuePair("json", jsonArray.toString()));

        Log.d(TAG, jsonArray.toString());

        httpPost.setEntity(new UrlEncodedFormEntity(parametros));


        HttpResponse httpResponse = httpClient.execute(httpPost);
        int response = httpResponse.getStatusLine().getStatusCode();

        if (response == 200) {

            return true;
        } else {
            return false;
        }

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
