package com.easytask.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sandy on 12/10/2014.
 */
public class UserGroup implements Parcelable {

    private User user;
    private Group group;
    private int admin;

    public UserGroup( User user, Group group, int admin) {
        this.user=user;
        this.group=group;
        this.admin = admin;
    }

    public UserGroup() {
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

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
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
        out.writeParcelable(this.user, flags);
        out.writeParcelable(this.group, flags);
        out.writeInt(this.admin);
    }

    public UserGroup(Parcel in) {
        this.user = (User) in.readValue(User.class.getClassLoader());
        this.group = (Group) in.readValue(Group.class.getClassLoader());
        this.admin = in.readInt();
    }

    public static final Parcelable.Creator<UserGroup> CREATOR = new Creator<UserGroup>() {

        public UserGroup createFromParcel(Parcel source) {
            return new UserGroup(source);
        }
        public UserGroup[] newArray(int size) {
            return new UserGroup[size];
        }
    };
}
