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

package com.easytask.adaptet.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easytask.R;
import com.easytask.adaptet.navigationObjects.Navigation_Object;

/**
 * Created by sandy on 30/10/14.
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
