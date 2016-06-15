package com.android.habit.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.habit.Facades.FragmentManagerFacade;
import com.android.habit.Fragments.FirstHelloWorldFragment;
import com.android.habit.Fragments.HabitFragment;
import com.android.habit.Fragments.SecondHelloWorldFragment;
import com.android.habit.Fragments.ThirdHelloWorldFragment;
import com.android.habit.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt;
    FrameLayout fragContainer;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;


    ArrayList<Fragment> fragments;

    //Bottom buttons
    Button nextPage;

    FragmentManager fm;
    FragmentManagerFacade fragmentManagerFacade;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.navigation_bar_toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.navigation_bar_layout);

        nvDrawer = (NavigationView) findViewById(R.id.navigation_bar_layout_nvview);

        setupDrawerContent(nvDrawer);

        //Create fragments list
        fragments = new ArrayList<>();
        fragments.add(new FirstHelloWorldFragment());
        fragments.add(new SecondHelloWorldFragment());
        fragments.add(new ThirdHelloWorldFragment());
        fragments.add(new HabitFragment());

        //Get fragment management tools
        fm = getFragmentManager();
        Fragment[] fragsArray = new Fragment[fragments.size()];
        fragsArray = fragments.toArray(fragsArray);
        fragmentManagerFacade = new FragmentManagerFacade(fm, fragsArray);
        fragmentManagerFacade.addFragmentsToLayout(R.id.fragment_container);
        fragmentManagerFacade.hideAllFragments();

        //Views
        fragContainer = (FrameLayout) findViewById(R.id.fragment_container);

        //Bottom buttons
        nextPage = (Button) findViewById(R.id.toAct2);

        //Set listeners
        nextPage.setOnClickListener(this);


        //Set default fragment to the FirstHelloWorldFragment
        currentFragment = fragments.get(fragments.size()-1);
        updateFragment();
    }

    private void updateFragment() {
        fragmentManagerFacade.hideAllFragments();
        fragmentManagerFacade.showFragment(currentFragment);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragment = fragments.get(0);
                break;
            case R.id.nav_second_fragment:
                fragment = fragments.get(1);
                break;
            case R.id.nav_third_fragment:
                fragment = fragments.get(2);
                break;
            case R.id.nav_habit_fragment:
                fragment = fragments.get(3);
                break;
            default:
                break;
        }


        fragmentManagerFacade.hideAllFragments();
        //fragmentManagerFacade.hideFragment(fm.findFragmentById(R.id.fragment_container));
        fragmentManagerFacade.showFragment(fragment);

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        // Close the navigation drawer
        mDrawer.closeDrawers();



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.toAct2:

                break;
            default:
                break;
        }
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}
