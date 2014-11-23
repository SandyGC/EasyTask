

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
import com.easytask.modelo.User;

/**
 * Created by Sandy on 10/11/2014.
 */
public class ShareUserHolder extends ViewBaseHolder {

    private ImageView imageView;
    private TextView textView;

    public ShareUserHolder(View v) {
        getContentView(v);
    }

    @Override
    public void getContentView(View v) {
        super.getContentView(v);
        imageView = (ImageView) v.findViewById(R.id.imageView_shareUser);
        textView = (TextView) v.findViewById(R.id.textView_shareuser);
    }

    @Override
    public void setContentView(Object o) {
        super.setContentView(o);
        User user = (User) o;
        textView.setText(user.getNickNameUser());
    }
}
