package com.easytask.dao.webServerDao;

import android.util.Log;

import com.easytask.dao.InterfacesDAO.ITaskDao;
import com.easytask.modelo.ListTasks;
import com.easytask.modelo.Task;

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
public class EasyTaskTaskDao implements ITaskDao {

    private static final String TAG = "com.easyTask.dao.webServerDao.EasyTaskTaskDAO";

    private static final String URL = "http://easyt.esy.es/index.php/";

    private HttpClient httpClient = new DefaultHttpClient();

    @Override
    public Task insert(Task object) throws Exception {
        return null;
    }

    @Override
    public Task read(Task object) throws Exception {
        return null;
    }


    @Override
    public boolean update(Task object) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Task object) throws Exception {
        return false;
    }

    @Override
    public List<Task> getAll() throws Exception {
        return null;
    }

    @Override
    public boolean updateAllForList(ListTasks listTask) throws Exception {

        String url = URLEncoder.encode(listTask.getId_UnicoL(), "UTF-8");

        HttpPost httpPost = new HttpPost(URL + "listtask/updatetasks/" + url);

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < listTask.getTasks().size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tittle", listTask.getTasks().get(i).getTittle());
            jsonObject.put("realized", listTask.getTasks().get(i).getTaskDone());
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
}
