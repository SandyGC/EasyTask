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

package com.easytask.controller;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.easytask.R;
import com.easytask.controller.interfaceFragment.NavigationDrawerCallbacks;
import com.easytask.controller.interfaceFragment.OnFragmentInteractionListener;
import com.easytask.modelo.User;


public class ControllerListListTask extends Activity
        implements NavigationDrawerCallbacks, OnFragmentInteractionListener {

    private final String TAG = "com.easytask.controller.ControllerListListTask";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Bundle extras = getIntent().getExtras();

        user = extras.getParcelable("usuario");

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        onSectionAttached(position);
    }

    public void onSectionAttached(int number) {

        Fragment fragment = null;

        switch (number) {
            case 1:
                mTitle = getString(R.string.tittle_controller_data_user);
                fragment = new ControllerDataUserFragment().newInstance();

                break;
            case 2:
                Bundle bundle = new Bundle();
                bundle.putParcelable("usuario", user);
                mTitle = getString(R.string.app_name);
                fragment = new ControllerListListTaskFragment().newInstance(bundle);

                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            default:

                break;
        }
        if (fragment != null) {

            android.app.FragmentManager fragmentManager = getFragmentManager();

            if (fragment instanceof ControllerListListTaskFragment) {
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            } else {
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }


        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc3aac21")));
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.controller_list_list_task, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.newListTask:
                onFragmentInteraction(null, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Object o, int number) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", this.user);

        Fragment fragment = new ControllerNewListTaskFragmet().newInstance(bundle);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
