package com.android.habit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_test);

        String[] myStringArray = {"String1", "String2", "String3"};

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myStringArray
        );

        ListView listView = (ListView) findViewById(R.id.secondPageList);
        listView.setAdapter(myAdapter);
    }
}
