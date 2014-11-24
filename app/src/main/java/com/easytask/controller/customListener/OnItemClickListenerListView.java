package com.easytask.controller.customListener;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.easytask.controller.ControllerLanguage;

import java.util.Locale;

/**
 * Created by danny on 24/11/14.
 */
public class OnItemClickListenerListView implements AdapterView.OnItemClickListener {

    private static final String ESPANOL = "es";
    private static final String ENGLISH = "en";

    private ControllerLanguage controllerLanguage;
    private Context context;

    public OnItemClickListenerListView(ControllerLanguage controllerLanguage) {
        this.controllerLanguage = controllerLanguage;
        context = controllerLanguage.getActivity().getApplicationContext();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                setLocal(Locale.getDefault().toString());
                break;
            case 1:
                setLocal(ESPANOL);
                break;
            case 2:
                setLocal(ENGLISH);
                break;
        }
    }

    /**
     * @param lang
     */
    private void setLocal(String lang) {
        Log.d("LANG", lang);
        Locale myLocale = new Locale(lang);
        Resources res = controllerLanguage.getActivity().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        controllerLanguage.rebootApp();
        controllerLanguage.setLanguage(lang);

    }


}
