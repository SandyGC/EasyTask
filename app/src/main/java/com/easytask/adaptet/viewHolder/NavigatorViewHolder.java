package com.easytask.adaptet.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easytask.R;
import com.easytask.adaptet.navigationObjects.Navigation_Object;

/**
 * Created by danny on 30/10/14.
 */
public class NavigatorViewHolder extends ViewBaseHolder {
    private TextView titulo_itm;
    private ImageView icono;

    public NavigatorViewHolder(View v) {
        getContentView(v);
    }

    @Override
    public void getContentView(View v) {
        this.titulo_itm = (TextView) v.findViewById(R.id.title_item);
        this.icono = (ImageView) v.findViewById(R.id.icon);
    }

    @Override
    public void setContentView(Object o) {
        Navigation_Object item_object = (Navigation_Object) o;

        this.titulo_itm.setText(item_object.getTitutlo());
        this.icono.setImageResource(item_object.getIcono());
    }
}
