package com.easytask.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandy on 12/10/2014.
 */
public class Group implements Parcelable {

    private int idGroup;
    private String nameGroup;
    private String id_UnicoG;
    private List<User> participants;


    public Group(int idGroup, String nameGroup, String id_UnicoG) {
        this.idGroup = idGroup;
        this.nameGroup = nameGroup;
        this.id_UnicoG = id_UnicoG;
        this.participants = new ArrayList<User>();
    }

    public Group(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public Group() {
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getId_UnicoG() {
        return id_UnicoG;
    }

    public void setId_UnicoG(String id_UnicoG) {
        this.id_UnicoG = id_UnicoG;
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
        out.writeInt(this.idGroup);
        out.writeString(this.nameGroup);
        out.writeString(this.id_UnicoG);
        out.writeTypedList(this.participants);
    }

    public Group(Parcel in) {
        this.idGroup = in.readInt();
        this.nameGroup = in.readString();
        this.id_UnicoG = in.readString();
        participants = new ArrayList<User>();
        in.readTypedList(this.participants, User.CREATOR);
    }


    public static final Parcelable.Creator<Group> CREATOR = new Creator<Group>() {

        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
