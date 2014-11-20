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
