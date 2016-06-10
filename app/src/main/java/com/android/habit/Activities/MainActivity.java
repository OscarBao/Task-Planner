package com.android.habit.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.TextView;

import com.android.habit.Facades.FragmentManagerFacade;
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
    int currentFragmentIndex = 0;

    //Bottom buttons
    Button nextPage;
    Button addFragButton;
    Button removeFragButton;


    FragmentManager fm;
    FragmentManagerFacade fragmentManagerFacade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        setupDrawerContent(nvDrawer);

        //Create fragments list
        fragments = new ArrayList<>();
        fragments.add(new SecondHelloWorldFragment());
        fragments.add(new ThirdHelloWorldFragment());

        //Get fragment management tools
        fm = getFragmentManager();
        fragmentManagerFacade = new FragmentManagerFacade(fm);

        //Views
        fragContainer = (FrameLayout) findViewById(R.id.fragment_container);

        //Bottom buttons
        nextPage = (Button) findViewById(R.id.toAct2);
        addFragButton = (Button) findViewById(R.id.add_fragment);
        removeFragButton = (Button) findViewById(R.id.remove_fragment);




        removeFragButton.setOnClickListener(this);
        addFragButton.setOnClickListener(this);
        nextPage.setOnClickListener(this);


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
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = SecondHelloWorldFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = SecondHelloWorldFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = ThirdHelloWorldFragment.class;
                break;
            default:
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManagerFacade.addFragmentToLayout(R.id.fragment_container, fragment);

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.toAct2:

                Intent toAct2Intent = new Intent(this, ListTestActivity.class);
                startActivity(toAct2Intent);
                break;

            case R.id.add_fragment:

                fragmentManagerFacade.addFragmentToLayout(R.id.fragment_container, fragments.get(currentFragmentIndex));

                currentFragmentIndex++;
                if(currentFragmentIndex >= fragments.size()) currentFragmentIndex = 0;
                break;

            case R.id.remove_fragment:

                fragmentManagerFacade.removeFragmentFromLayout(R.id.fragment_container);
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
