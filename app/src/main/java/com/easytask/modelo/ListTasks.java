/**
 * Copyright [2014] [Sandy Guerrero Cajas]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.easytask.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.easytask.modelo.emun.StatusList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandy on 12/10/2014.
 */
public class ListTasks implements Parcelable {

    private int idListTask;
    private String title;
    private String dateList;
    private int status_share;
    private StatusList statusList;
    private String id_UnicoL;
    private int status_server;

    private User user;
    private Group group;
    private List<Task> Tasks;

    public ListTasks(int idListTask, String title, String dateList, int status_share, StatusList statusList, String id_UnicoL, User user, int status_server) {
        this.idListTask = idListTask;
        this.title = title;
        this.dateList = dateList;
        this.status_share = status_share;
        this.statusList = statusList;
        this.id_UnicoL = id_UnicoL;
        this.status_server = status_server;
        this.user = user;
        Tasks = new ArrayList<Task>();

    }


    public ListTasks(String title, String dateList, int status_share, StatusList statusList, User user, int status_server) {

        this.title = title;
        this.dateList = dateList;
        this.status_share = status_share;
        this.statusList = statusList;
        this.status_server = status_server;
        this.user = user;
        Tasks = new ArrayList<Task>();
    }


    public ListTasks() {
        Tasks = new ArrayList<Task>();
    }

    public int getIdListTask() {
        return idListTask;
    }

    public void setIdListTask(int idListTask) {
        this.idListTask = idListTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateList() {
        return dateList;
    }

    public void setDateList(String dateList) {
        this.dateList = dateList;
    }

    public int getStatus_share() {
        return status_share;
    }

    public void setStatus_share(int status_share) {
        this.status_share = status_share;
    }

    public StatusList getStatusList() {
        return statusList;
    }

    public StatusList getValuesStatusList(int position) {

        StatusList valuesStatusList = null;

        switch (position) {
            case 0:
                valuesStatusList = StatusList.Creada;
                break;
            case 1:
                valuesStatusList = StatusList.Realizando;
                break;
            case 2:
                valuesStatusList = StatusList.Terminada;
                break;
        }

        return valuesStatusList;
    }

    public void setStatusList(StatusList statusList) {
        this.statusList = statusList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Task> getTasks() {
        return Tasks;
    }

    public void setTasks(List<Task> tasks) {
        Tasks = tasks;
    }

    public String getId_UnicoL() {
        return id_UnicoL;
    }

    public void setId_UnicoL(String id_UnicoL) {
        this.id_UnicoL = id_UnicoL;
    }

    public int getStatus_server() {
        return status_server;
    }

    public void setStatus_server(int status_server) {
        this.status_server = status_server;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param out   The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeInt(this.idListTask);
        out.writeString(this.title);
        out.writeString(this.dateList);
        out.writeInt(this.status_share);
        out.writeInt(this.status_server);
        out.writeParcelable(this.user, flags);
        //   out.writeTypedList(this.Tasks);


    }

    public ListTasks(Parcel in) {
        this.idListTask = in.readInt();
        this.title = in.readString();
        this.dateList = in.readString();
        this.status_share = in.readInt();
        this.status_server = in.readInt();
        this.user = in.readParcelable(User.class.getClassLoader());
        //   in.readTypedList(this.Tasks, Task.CREATOR);
    }

    public static final Parcelable.Creator<ListTasks> CREATOR = new Creator<ListTasks>() {

        public ListTasks createFromParcel(Parcel source) {
            return new ListTasks(source);
        }

        public ListTasks[] newArray(int size) {
            return new ListTasks[size];
        }
    };
}
