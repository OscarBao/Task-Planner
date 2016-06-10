package com.android.habit;

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

    private boolean fragmentIsInLayout(int containerResId, Fragment fragment) {
        Fragment fragmentInContainer = fragmentManager.findFragmentById(containerResId);
        return ((fragmentInContainer != null)  && (fragmentInContainer.isInLayout()) && (fragmentInContainer.equals(fragment)));
    }

    public void addFragmentToLayout(int containerResId, Fragment fragment) {
        addFragmentToLayout(containerResId, fragment, null);
    }

    public void removeFragmentFromLayout(int containerResId) {
        removeFragmentFromLayout(containerResId, null);
    }

    public void addFragmentToLayout(int containerResId, Fragment fragment, String backStackFlag) {
        removeFragmentFromLayout(containerResId, "inside_call_to_remove_fragment_before_adding");
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerResId, fragment);
        ft.addToBackStack(backStackFlag);
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
