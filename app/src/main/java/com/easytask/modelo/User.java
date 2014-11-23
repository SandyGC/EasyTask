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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandy on 12/10/2014.
 */
public class User implements Parcelable {

    private int idUser;
    private String nameUser;
    private String nickNameUser;
    private String emailUser;
    private String passwordUser;
    private String idUserGCM;
    //el usuario se crea con una lista de de listas de tareas, de manera que cuando
    //creo una nueva lista de tareas la añado a mi array donde guardo todas mis listas.
    private List<ListTasks> listListTask;
    //El usuario se crea con una lista de grupos, ya que puede o no pertenecer
    //a ninguno o muchos grupos. Cuando yo comparta una lista se creara un grupo, en el
    //aparecere yo, otro usuario con el que haya compartido la lista y a esa lista se le
    //asigna el grupo.
    private List<Group> listGroup;


    public User(int idUser, String nameUser, String nickNameUser, String emailUser) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.nickNameUser = nickNameUser;
        this.emailUser = emailUser;
        //cuando creo el usuario se crea por defecto con una lista de listas de tareas.
        this.listListTask = new ArrayList<ListTasks>();
        this.listGroup = new ArrayList<Group>();


    }

    public User(String nameUser, String nickNameUser, String emailUser) {
        this.nameUser = nameUser;
        this.nickNameUser = nickNameUser;
        this.emailUser = emailUser;
        //cuando creo el usuario se crea por defecto con una lista de listas de tareas.
        this.listListTask = new ArrayList<ListTasks>();
        listGroup = new ArrayList<Group>();


    }

    public User() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNickNameUser() {
        return nickNameUser;
    }

    public void setNickNameUser(String nickNameUser) {
        this.nickNameUser = nickNameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }


    public List<ListTasks> getListListTask() {
        return listListTask;
    }

    public void setListListTask(List<ListTasks> listListTask) {
        this.listListTask = listListTask;
    }

    public List<Group> getListGroup() {
        return listGroup;
    }

    public void setListGroup(List<Group> listGroup) {
        this.listGroup = listGroup;
    }

    public String getIdUserGCM() {
        return idUserGCM;
    }

    public void setIdUserGCM(String idUserGCM) {
        this.idUserGCM = idUserGCM;
    }

    /**
     * MEtodo que añade una lista de tareas a la lista de tareas que tiene el usuario
     *
     * @param listTasks lista de tareas a añadir
     * @return el objeto  lista que sse acaba de añadir
     */
    public ListTasks addList(ListTasks listTasks) {
        this.listListTask.add(listTasks);
        //devuelv la lista q acabo de crear
        return listTasks;
    }

    /**
     * MEtodo que añade tareas a la lista de tareas.
     *
     * @param listTasks lista donde añadir la tarea.
     * @param task      tarea que se va a añadir en la lista
     * @return devuelve el objeto tarea que se ha añadido.
     */
    public Task addTask(ListTasks listTasks, Task task) {
        listTasks.getTasks().add(task);
        return task;
    }

    /**
     * Metodo que añade un participante a un grupo
     *
     * @param group grupo al que añadir el participante.
     * @param user  usuario que se va a añadir al grupo.
     */
    private void addParticipant(Group group, User user) {
        group.getParticipants().add(user);
    }

    /**
     * Metodo que comprueba que una lista ya esta compartida con un usuario,
     * de esta manera no se puede volver a compartir la mimsa lista con el mismo
     * usuario. El metodo recibe la lista que se quiere compartir y el usuario
     * con el que se quiere compartir la lista. Busco el grupo en el que esta esa lista,
     * y de ese grupo obtengo los participantes, compruebo que si ese usuario ya esta en
     * participantes significara que esa lista ya ha sido compartida con ese usuario.
     *
     * @param listTasksShare lista para compartir
     * @param user           usuario con el que se quiere compartir
     * @return devuelve true si ya esta compartida y no false.
     */

    public boolean comprobeShare(ListTasks listTasksShare, User user) {
        boolean shareOK = false;
        Group group = listTasksShare.getGroup();
        for (int i = 0; i < group.getParticipants().size(); i++) {
            if (group.getParticipants().get(i).getIdUser() == user.getIdUser()) {
                shareOK = true;
            }
        }

        return shareOK;
    }

    /**
     * Metodo que comparte una lista
     *
     * @param listTasks
     */
    public void shareOk(ListTasks listTasks) {
        listTasks.setStatus_share(1);
    }

    /**
     * Metodo que va a compartir una lista con un usuario.
     * primero comprueba si esa lista ya esta compartida con
     * ese mismo usuario. Si no esta compartida, le cambio el
     * estado a la lista que pasara a esatr compartida.
     * Creo un grupo, añado ese grupo a mi lista de grupos
     * ese grupo lo añado a lista de grupos del otro usuario.
     * yo y el otro usuario nos añadimos como participantes del
     * grupo. Cambio en la lista, el grupo que tiene esa lista.
     *
     * @param listTasks
     * @param user
     */
    public boolean shareListTask(ListTasks listTasks, User user) {

        if (!comprobeShare(listTasks, user)) {
            shareOk(listTasks);
            Group group = new Group("Sin nombre");
            listGroup.add(group);
            user.getListGroup().add(group);
            addParticipant(group, this);
            addParticipant(group, user);
            listTasks.setGroup(group);
            return true;
        } else {
            return false;
        }

    }

    /////////////////////////////////////
    /////////////PARCELABLE//////////////
    ////////////////////////////////////

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
        dest.writeInt(this.idUser);
        //en orden como este declarado en la clase los atributos
        dest.writeString(this.nameUser);
        dest.writeString(this.nickNameUser);
        dest.writeString(this.emailUser);
        dest.writeString(this.passwordUser);
        dest.writeString(this.idUserGCM);
        dest.writeTypedList(this.listListTask);
        dest.writeTypedList(this.listGroup);


/**
 *     private int idUser;
 private String nameUser;
 private String nickNameUser;
 private String emailUser;
 private String passwordUser;
 private String idUserGCM;
 //el usuario se crea con una lista de de listas de tareas, de manera que cuando
 //creo una nueva lista de tareas la añado a mi array donde guardo todas mis listas.
 private List<ListTasks> listListTask;
 //El usuario se crea con una lista de grupos, ya que puede o no pertenecer
 //a ninguno o muchos grupos. Cuando yo comparta una lista se creara un grupo, en el
 //aparecere yo, otro usuario con el que haya compartido la lista y a esa lista se le
 //asigna el grupo.
 private List<Group> listGroup;

 */
    }

    public User(Parcel dest) {
        this.idUser = dest.readInt();
        this.nameUser = dest.readString();
        this.nickNameUser = dest.readString();
        this.emailUser = dest.readString();
        this.passwordUser = dest.readString();
        this.idUserGCM = dest.readString();
        this.listListTask = new ArrayList<ListTasks>();
        dest.readTypedList(this.listListTask, ListTasks.CREATOR);
        this.listGroup = new ArrayList<Group>();
        dest.readTypedList(this.listGroup, Group.CREATOR);

    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
