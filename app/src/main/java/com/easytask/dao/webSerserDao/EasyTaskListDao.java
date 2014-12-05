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

import java.net.URLEncoder;
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

        String url = URLEncoder.encode(object.getTitle(), "UTF-8");
        String url1 = URLEncoder.encode(object.getDateList(), "UTF-8");
        String url2 = URLEncoder.encode(String.valueOf(object.getStatus_share()), "UTF-8");
        String url3 = URLEncoder.encode(object.getStatusList().toString(), "UTF-8");
        String url4 = URLEncoder.encode(object.getId_UnicoL(), "UTF-8");
        String url5 = URLEncoder.encode(String.valueOf(id_group), "UTF-8");
        String url6 = URLEncoder.encode(object.getUser().getNickNameUser(), "UTF-8");

        HttpPost httpPost = new HttpPost(URL + "listtask/create/" + url + "/" + url1 + "/" + url2 + "/" + url3 +
                "/" + url4 + "/" + url5 + "/" + url6);

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
        String url = URLEncoder.encode(object.getId_UnicoL());

        HttpPost httpPost = new HttpPost(URL + "listtask/update/" + url);

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


    @Override
    public Object updateID_Group(ListTasks listTasks) throws Exception {
        String url = URLEncoder.encode(listTasks.getId_UnicoL(), "UTF-8");
        String url1 = URLEncoder.encode(String.valueOf(listTasks.getGroup().getIdGroup()), "UTF-8");

        HttpPost httpPost = new HttpPost(URL + "listtask/updatelisttaskgroup/" + url + "/" + url1);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            return listTasks;
        } else {
            return null;
        }
    }
}
