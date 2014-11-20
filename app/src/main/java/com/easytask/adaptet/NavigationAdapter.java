package com.easytask.adaptet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.easytask.R;
import com.easytask.adaptet.navigationObjects.Navigation_Object;
import com.easytask.adaptet.viewHolder.NavigatorViewHolder;

import java.util.ArrayList;

/**
 * Created by danny on 30/10/14.
 */
public class NavigationAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Navigation_Object> arrayItemNavigator;
    private LayoutInflater inflator;

    public NavigationAdapter(Activity activity, ArrayList<Navigation_Object> listarray) {
        super();
        this.activity = activity;
        this.arrayItemNavigator = listarray;
        inflator = activity.getLayoutInflater();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */


    @Override
    public int getCount() {
        return arrayItemNavigator.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */

    //retorna objeto ITEM_objct del array list
    @Override
    public Object getItem(int position) {
        return arrayItemNavigator.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
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

        Navigation_Object temNavigator = (Navigation_Object) getItem(position);

        NavigatorViewHolder navigatorViewHolder = null;

        if (v == null) {


            v = inflator.inflate(R.layout.item_navigator, null);
            navigatorViewHolder = new NavigatorViewHolder(v);


            v.setTag(navigatorViewHolder);

        } else {

            navigatorViewHolder = (NavigatorViewHolder) v.getTag();

        }


        navigatorViewHolder.setContentView(temNavigator);

        return v;
    }

}
