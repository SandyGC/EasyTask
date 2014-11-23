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

import java.util.Date;

/**
 * Created by Sandy on 12/10/2014.
 */
public class Task implements Parcelable {

    private int idTask;
    private int taskDone;
    private ListTasks listTasks;
    private String tittle;

    public Task() {
    }

    public Task(int idTask, int taskDone, ListTasks listTasks, String tittle) {
        this.idTask = idTask;
        this.taskDone = taskDone;
        this.listTasks = listTasks;
        this.tittle = tittle;
    }

    public Task(int taskDone, String tittle) {
        this.taskDone = taskDone;
        this.tittle = tittle;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public int getTaskDone() {
        return taskDone;
    }

    public void setTaskDone(int taskDone) {
        this.taskDone = taskDone;
    }

    public ListTasks getListTasks() {
        return listTasks;
    }

    public void setListTasks(ListTasks listTasks) {
        this.listTasks = listTasks;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
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
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idTask);
        dest.writeInt(this.taskDone);
        dest.writeParcelable(this.listTasks, flags);
        dest.writeString(this.tittle);
    }

    public Task(Parcel dest) {
        this.idTask = dest.readInt();
        this.taskDone = dest.readInt();
        this.listTasks = dest.readParcelable(ListTasks.class.getClassLoader());
        this.tittle = dest.readString();

    }

    public static final Parcelable.Creator<Task> CREATOR = new Creator<Task>() {

        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
