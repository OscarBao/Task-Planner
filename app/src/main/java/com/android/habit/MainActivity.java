package com.android.habit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt;
    FrameLayout fragContainer;

    ArrayList<Fragment> fragments;
    int currentFragmentIndex = 0;

    //Bottom buttons
    Button nextPage;
    Button addFragButton;
    Button removeFragButton;


    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fragments = new ArrayList<>();
        fragments.add(new SecondHelloWorldFragment());
        fragments.add(new ThirdHelloWorldFragment());
        fm = getFragmentManager();



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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.toAct2:
                Intent toAct2Intent = new Intent(this, ListTestActivity.class);
                startActivity(toAct2Intent);
                break;
            case R.id.add_fragment:

                FragmentTransaction ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.add(R.id.fragment_container, fragments.get(currentFragmentIndex));
                ft.addToBackStack(null);
                ft.commit();

                currentFragmentIndex++;
                if(currentFragmentIndex >= fragments.size()) currentFragmentIndex = 0;
                break;
            case R.id.remove_fragment:
                ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.remove(fm.findFragmentById(R.id.fragment_container));
                ft.addToBackStack(null);
                ft.commit();
                break;
            default:
                break;
        }
    }
}
