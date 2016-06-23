package com.android.habit.Fragments;

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
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.habit.Adapters.TasksAdapter;
import com.android.habit.Databases.TasksDB;
import com.android.habit.Objects.Task;
import com.android.habit.StaticObjects.ProgressManager;
import com.android.habit.StaticObjects.DaysManager;
import com.android.habit.StaticObjects.TasksList;
import com.android.habit.R;
import com.android.habit.StaticObjects.TasksManager;

import java.util.ArrayList;

/**
 * Created by Oscar_Local on 6/14/2016.
 */
public class HabitFragment extends Fragment {

    //List variables
    ArrayAdapter<Task> tasksAdapter;
    ListView listView;
    AdapterView.OnItemClickListener itemClickListener;
    View.OnClickListener onClickListener;

    TasksDB db;
    TasksManager tasksManager;

    //Controls
    Button addTaskButton;
    Button overdueButton;
    ProgressBar levelBar;
    AddTaskDialog addTaskDialog;

    //Static variables
    static String newTaskName;
    static String newTaskDescription;
    static int newTaskPoints;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        tasksManager = new TasksManager(this.getActivity());
        tasksAdapter = new TasksAdapter(this.getActivity(), (ArrayList<Task>)(TasksList.getList()));
        TasksManager.addNewAdapter(tasksAdapter);

        db = new TasksDB(this.getActivity());

        //Register listeners
        itemClickListener = new HabitsItemClickListener();
        onClickListener = new HabitsClickListener();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_habit, container, false);

        //Setting up dialog
        addTaskDialog = new AddTaskDialog();

        //Setting up listview
        listView = (ListView) v.findViewById(R.id.fragment_habit_listview);
        listView.setOnItemClickListener(itemClickListener);
        listView.setAdapter((ArrayAdapter) tasksAdapter);
        tasksAdapter.notifyDataSetChanged();

        //Controls
        addTaskButton = (Button) v.findViewById(R.id.fragment_habit_button_add_task);
        overdueButton = (Button) v.findViewById(R.id.fragment_habit_button_overdue);
        addTaskButton.setOnClickListener(onClickListener);
        overdueButton.setOnClickListener(onClickListener);
        levelBar = (ProgressBar) v.findViewById(R.id.fragment_habit_level_bar);
        levelBar.setProgress(ProgressManager.getCurrentProgress());


        //Update tasks
        if(DaysManager.isNextDay()) {
            clearTodayTasks();
        }
        DaysManager.markTodayVisited();



        return v;
    }



    private void showNewTaskDialog() {
        addTaskDialog.getNewDialog().show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    /*=========================================================================
                            Private helper methods
    =========================================================================*/
    private void completeTask(Task task) {updateProgressFromTask(task, true);}

    private void updateProgressFromTask(Task task, boolean success) {
        if(success) {
            levelBar.setProgress(levelBar.getProgress() + task.getTaskPoints());
            ProgressManager.addProgress(task.getTaskPoints());
        }
        else {
            levelBar.setProgress(levelBar.getProgress() - task.getTaskPoints());
            ProgressManager.subtractProgress(task.getTaskPoints());
        }
    }

    private void addNewTask() {
        TasksManager.addNewTask(new Task(newTaskName, newTaskDescription, newTaskPoints, DaysManager.getTodayAsLong()));
        wipeStaticDialogData();
    }

    private void removeTask(int pos) {TasksManager.removeTask(TasksList.getList().get(pos));}

    private void wipeStaticDialogData() {
        newTaskName = "";
        newTaskDescription = "";
        newTaskPoints = 0;
    }

    private void clearTodayTasks() {
        for(Task task : db.getThisDaysTasks(DaysManager.getTodayAsLong())) {
            updateProgressFromTask(task, false);
        }
        TasksManager.moveAllTodaysTasksToNextDay();
    }

    /*=========================================================================
                                Inner classes
     ========================================================================*/
    protected class HabitsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.fragment_habit_button_add_task:
                    showNewTaskDialog();
                    break;
                case R.id.fragment_habit_button_overdue:
                    clearTodayTasks();
                    break;
            }
        }
    }
    protected class HabitsItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            completeTask(TasksList.getList().get(position));
            db.getThisDaysTasks(DaysManager.getTodayAsLong());
            removeTask(position);
        }
    }
    protected class AddTaskDialog {
        Dialog dialog;
        public AddTaskDialog() {
        }

        public Dialog getNewDialog() {
            dialog = new Dialog(getActivity());
            dialog.setTitle("Add New Task");
            dialog.setContentView(R.layout.fragment_habit_dialog);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });


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

            return dialog;
        }
    }

}
