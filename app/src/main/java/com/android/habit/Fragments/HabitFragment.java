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

/**
 * Created by Oscar_Local on 6/14/2016.
 */
public class HabitFragment extends Fragment {
    AdapterView.OnItemClickListener itemClickListener;
    View.OnClickListener onClickListener;

    ListView listView;
    TasksList tasks;
    Button addTaskButton;

    ArrayAdapter tasksAdapter;

    static String newTaskName;
    static String newTaskDescription;
    static int newTaskPoints;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //Register listeners
        itemClickListener = new HabitsItemClickListener();
        onClickListener = new HabitsClickListener();



        tasks = new TasksList();
        tasks.addTask(new Task("Yoga", "Do some yoga", 3));
        tasks.addTask(new Task("Homework", "Finish CSE 110 homework", 5));
        /*
        tasks.addTask(new Task("Habits", "Work on the Habits program. Why is static thing warning?", 6));
        tasks.addTask(new Task("Test habits", "Testing whether the listView scrolls or not", 2));
        tasks.addTask(new Task("Add tasks", "Add some more tasks to find out whether it does or not", 2));
        tasks.addTask(new Task("Rest", "Get some rest since I'm sick", 3));
        tasks.addTask(new Task("Eat medicine", "Eat medicine soon! In four hours", 5));
        */
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_habit, container, false);

        String[] myStringArray = {"Chore 1", "Job 2"};

        //TODO: bring outside of method later
        tasksAdapter = new TasksAdapter(v.getContext(), (ArrayList<Task>)(tasks.getList()));

        addTaskButton = (Button) v.findViewById(R.id.fragment_habit_button_add_task);


        addTaskButton.setOnClickListener(onClickListener);

        listView = (ListView) v.findViewById(R.id.fragment_habit_listview);
        listView.setOnItemClickListener(itemClickListener);
        listView.setAdapter((ListAdapter) tasksAdapter);


        return v;
    }

    protected void addNewTask() {
        tasks.addTask(new Task(newTaskName, newTaskDescription, newTaskPoints));
        tasksAdapter.notifyDataSetChanged();
    }

    private void showNewTaskDialog() {
        final Dialog dialog = new Dialog(getView().getContext());
        dialog.setTitle("Add New Task");
        dialog.setContentView(R.layout.fragment_habit_dialog);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                addNewTask();
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
                newTaskPoints = Integer.parseInt(pointsEditor.getText().toString());

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
