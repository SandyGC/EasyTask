package com.easytask.adaptet;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.easytask.R;
import com.easytask.adaptet.viewHolder.ShareUserHolder;
import com.easytask.modelo.User;

import java.util.List;


public class UserAdapter extends BaseAdapter {

    private Fragment fragment;
    private List<User> userList;
    private LayoutInflater inflater;

    public UserAdapter(Fragment fragment, List<User> userList) {
        this.fragment = fragment;
        this.userList = userList;
        this.inflater = LayoutInflater.from(fragment.getActivity().getApplicationContext());
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return userList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return userList.get(position).getIdUser();
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        ShareUserHolder shareUserHolder = null;

        User user = userList.get(position);

        if (v == null){
            v = inflater.inflate(R.layout.item_share_user, null);
            shareUserHolder = new ShareUserHolder(v);
            v.setTag(shareUserHolder);
        }else{
            shareUserHolder = (ShareUserHolder) v.getTag();
        }

        shareUserHolder.setContentView(user);

        return v;
    }
}
