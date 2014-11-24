package com.easytask.controller.customListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import com.easytask.controller.ControllerListListTaskFragment;

/**
 * Created by danny on 24/11/14.
 */
public class OnItemLongClickListenerListView implements AdapterView.OnItemLongClickListener {

    private ControllerListListTaskFragment controllerListListTaskFragment;


    public OnItemLongClickListenerListView(ControllerListListTaskFragment controllerListListTaskFragment) {
        this.controllerListListTaskFragment = controllerListListTaskFragment;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        removeItemFromList(position);
        return true;

    }

    private void removeItemFromList(final int position) {


        AlertDialog.Builder alert = new AlertDialog.Builder(
                controllerListListTaskFragment.getActivity());

        alert.setTitle("Eliminar Lista");
        alert.setMessage("Quieres eliminar esta lista?");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                controllerListListTaskFragment.delteItem(position);


            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();


    }
}
