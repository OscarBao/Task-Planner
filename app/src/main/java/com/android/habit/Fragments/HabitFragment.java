package com.android.habit.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.habit.Adapters.TasksAdapter;
import com.android.habit.Objects.Task;
import com.android.habit.Objects.TasksList;
import com.android.habit.R;

import java.util.ArrayList;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Oscar_Local on 6/14/2016.
 */
public class HabitFragment extends Fragment {

    //List variables
    ArrayAdapter tasksAdapter;
    ListView listView;
    AdapterView.OnItemClickListener itemClickListener;
    View.OnClickListener onClickListener;

    //Controls
    Button addTaskButton;

    //Static variables
    static String newTaskName;
    static String newTaskDescription;
    static int newTaskPoints;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        TasksList.startList();

        //Register listeners
        itemClickListener = new HabitsItemClickListener();
        onClickListener = new HabitsClickListener();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_habit, container, false);

        tasksAdapter = new TasksAdapter(v.getContext(), (ArrayList<Task>)(TasksList.getList()));

        addTaskButton = (Button) v.findViewById(R.id.fragment_habit_button_add_task);
        addTaskButton.setOnClickListener(onClickListener);

        listView = (ListView) v.findViewById(R.id.fragment_habit_listview);
        listView.setOnItemClickListener(itemClickListener);
        listView.setAdapter((ListAdapter) tasksAdapter);

        return v;
    }

    protected void addNewTask() {
        TasksList.addTask(new Task(newTaskName, newTaskDescription, newTaskPoints));
        tasksAdapter.notifyDataSetChanged();

        wipeStaticDialogData();

    }

    private void wipeStaticDialogData() {
        newTaskName = "";
        newTaskDescription = "";
        newTaskPoints = 0;
    }

    private void showNewTaskDialog() {
        final Dialog dialog = new Dialog(getView().getContext());
        dialog.setTitle("Add New Task");
        dialog.setContentView(R.layout.fragment_habit_dialog);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        dialog.show();


        final EditText nameEditor = (EditText) dialog.findViewById(R.id.fragment_habit_dialog_edittext_name);
        final EditText descriptionEditor = (EditText) dialog.findViewById(R.id.fragment_habit_dialog_edittext_description);
        final EditText pointsEditor = (EditText) dialog.findViewById(R.id.fragment_habit_dialog_edittext_points);
        Button okButton = (Button) dialog.findViewById(R.id.fragment_habit_dialog_button_ok);
        Button cancelButton = (Button) dialog.findViewById(R.id.fragment_habit_dialog_button_cancel);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTaskName = nameEditor.getText().toString();
                newTaskDescription = descriptionEditor.getText().toString();
                if(pointsEditor.getText().toString().equals("")) {
                    newTaskPoints = 0;
                }
                else {
                    newTaskPoints = Integer.parseInt(pointsEditor.getText().toString());
                }

                if(!newTaskName.equals("")) {
                    addNewTask();
                }
                dialog.cancel();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    /*
    --------------->Inner listener classes
     */
    protected class HabitsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.fragment_habit_button_add_task:
                    showNewTaskDialog();
            }
        }
    }
    protected class HabitsItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO: make something happen later
        }
    }
}
