package com.android.habit.Facades;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by Oscar_Local on 6/10/2016.
 */
public class FragmentManagerFacade {
    FragmentManager fragmentManager;

    public FragmentManagerFacade(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void removeFragmentFromLayout(int containerResId) {
        removeFragmentFromLayout(containerResId, null);
    }

    public void replaceFragment(int containerResId, Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerResId, fragment);
        ft.commit();
    }


    public void hideFragment(Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(fragment);
        ft.commit();
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    public void removeFragmentFromLayout(int containerResId, String backStackFlag) {
        Fragment fragmentInContainer = (Fragment) fragmentManager.findFragmentById(containerResId);
        if(fragmentInContainer != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.remove(fragmentInContainer);
            ft.addToBackStack(backStackFlag);
            ft.commit();
        }
    }
}
