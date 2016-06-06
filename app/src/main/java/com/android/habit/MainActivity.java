package com.android.habit;

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
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt;
    ImageButton fab;
    Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (ImageButton) findViewById(R.id.fab);
        nextPage = (Button) findViewById(R.id.toAct2);


        fab.setOnClickListener(this);
        nextPage.setOnClickListener(this);

        txt = (TextView) findViewById(R.id.helloWorldText);
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
            case R.id.fab:
                if(txt.getText().equals(getResources().getString(R.string.buttonclicked_textview))) {
                    txt.setText(R.string.buttonunclicked_textview);
                    fab.setImageResource(R.drawable.green_button);
                }
                else {
                    txt.setText(R.string.buttonclicked_textview);
                    fab.setImageResource(R.drawable.gray_button);
                }
                break;
            case R.id.toAct2:
                Intent toAct2Intent = new Intent(this, ListTestActivity.class);
                startActivity(toAct2Intent);
                break;
            default:
                break;
        }
    }
}
