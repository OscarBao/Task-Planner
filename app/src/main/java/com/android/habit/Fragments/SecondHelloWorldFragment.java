package com.android.habit.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.habit.Databases.TasksDB;
import com.android.habit.R;
import com.android.habit.StaticObjects.DaysManager;
import com.android.habit.StaticObjects.TasksManager;

/**
 * Created by Oscar_Local on 6/10/2016.
 */
public class SecondHelloWorldFragment extends Fragment implements AdapterView.OnItemClickListener{

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second_hello_world, container, false);
        String[] myStringArray = {"String1", "String2", "String3"};

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                myStringArray
        );

        listView = (ListView) v.findViewById(R.id.secondPageList);
        listView.setOnItemClickListener(this);
        listView.setAdapter(myAdapter);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(view.getContext(), ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
    }
}
