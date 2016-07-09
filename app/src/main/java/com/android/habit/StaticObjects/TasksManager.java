package com.android.habit.StaticObjects;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.android.habit.Databases.TasksDB;
import com.android.habit.Objects.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar_Local on 6/22/2016.
 */
public class TasksManager {
    static TasksDB db;
    static List<ArrayAdapter<Task>> adapters;

    public TasksManager(Context context) {
        db = new TasksDB(context);
        adapters = new ArrayList<>();
        db.updateTaskList();
    }

    public static void addNewAdapter(ArrayAdapter<Task> adapter) {
        adapters.add(adapter);
    }

    public static void addNewTask(Task task) {
        db.addTaskToDatabase(task);
        updateAdaptersData();
    }

    public static void removeTask(Task task) {
        db.removeTaskFromDatabase(task);
        updateAdaptersData();
    }

    public static void clearTodaysTasks() {
        db.clearDatabase();
        updateAdaptersData();
    }

    public static void moveTodaysTasksToNextDay() {
        long todayNum = DaysManager.getTodayAsLong();
        db.moveTasksToNextDay(todayNum, DaysManager.getTomorrowAsLong(todayNum));
        updateAdaptersData();
    }

    public static void moveAllTodaysTasksToNextDay() {
        db.moveAllTodaysTasksToNextDay();
        updateAdaptersData();
    }
    /*=========================================================================
                            Private helper methods
     ========================================================================*/

    private static void updateAdaptersData() {
        db.updateTaskList();
        for (ArrayAdapter adapter : adapters) {
            adapter.notifyDataSetChanged();
        }
    }
}
