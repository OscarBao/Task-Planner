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
    Fragment[] fragments;

    public FragmentManagerFacade(FragmentManager fragmentManager, Fragment[] fragments) {
        this.fragmentManager = fragmentManager;
        this.fragments = fragments;
    }


    public void removeFragmentFromLayout(int containerResId) {
        removeFragmentFromLayout(containerResId, null);
    }

    public void addFragmentsToLayout(int containerResId) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for(Fragment frag : fragments) {
            ft.add(containerResId, frag);
        }
        ft.commit();
    }

    public void hideAllFragments() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for(Fragment frag : fragments) {
            ft.hide(frag);
        }
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
