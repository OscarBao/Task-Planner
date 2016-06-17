package com.android.habit.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.habit.Interfaces.DBConstants;
import com.android.habit.Objects.Task;
import com.android.habit.StaticObjects.TasksList;

import java.util.ArrayList;

/**
 * Created by Oscar_Local on 6/15/2016.
 */
public class TasksDB extends SQLiteOpenHelper {

    private static TasksDB instance;

    public static synchronized TasksDB getInstance(Context context) {
        if(instance == null) {
            instance = new TasksDB(context.getApplicationContext());
        }
        return instance;
    }

    public TasksDB(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstants.QUERY_CREATE_TASK_TABLE);
    }

    public void removeTaskFromDatabase(Task task) {
        SQLiteDatabase wdb = this.getWritableDatabase();
        removeTaskFromDatabase(task, DBConstants.TASKS_TABLE_NAME, wdb);
    }

    public void addTaskToDatabase(Task task) {
        SQLiteDatabase wdb = this.getWritableDatabase();
        //TODO: Do a find check to see if already in
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.TASKS_COLUMN_NAME, task.getName());
        cv.put(DBConstants.TASKS_COLUMN_DESCRIPTION, task.getDescription());
        cv.put(DBConstants.TASKS_COLUMN_POINTS, task.getTaskPoints());
        wdb.insert(DBConstants.TASKS_TABLE_NAME, null, cv);
    }

    public void updateTaskList() {
        updateTaskList(DBConstants.TASKS_TABLE_NAME, this.getReadableDatabase());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldNum, int newNum) {
    }

    /*
    --------------->Private methods
     */
    private void removeTaskFromDatabase(Task task, String table, SQLiteDatabase db) {
        if(!taskIsInDatabase(task, table, db)) return;
        db.execSQL("DELETE FROM " + table + " WHERE " + DBConstants.TASKS_COLUMN_ID + " = " + task.getId() + ";");
    }

    private void updateTaskList(String table, SQLiteDatabase db) {
        TasksList.getList().clear();

        Cursor c = db.rawQuery("SELECT * FROM " + table + ";", null);
        if(c.getCount() == 0) {
            return;
        }
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Task newTask = new Task();
            newTask.setId(c.getInt(c.getColumnIndex(DBConstants.TASKS_COLUMN_ID)));
            newTask.setName(c.getString(c.getColumnIndex(DBConstants.TASKS_COLUMN_NAME)));
            newTask.setDescription(c.getString(c.getColumnIndex(DBConstants.TASKS_COLUMN_DESCRIPTION)));
            newTask.setTaskPoints(c.getInt(c.getColumnIndex(DBConstants.TASKS_COLUMN_POINTS)));

            TasksList.addTask(newTask);
        }

        for(Task task : TasksList.getList()) {
            Log.i("TasksDB PrintList", "Next task is: " + task.getName() + " with id " + task.getId());
        }
    }

    private int findTaskId(Task task, String table, SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM " + table + " WHERE " + DBConstants.TASKS_COLUMN_NAME + " = " + task.getName() + ";", null);
        if(c.getCount() == 0) return 0;
        c.moveToFirst();
        return c.getInt(c.getColumnIndex(DBConstants.TASKS_COLUMN_ID));
    }

    private boolean taskIsInDatabase(Task task, String table, SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM " + table + " WHERE " + DBConstants.TASKS_COLUMN_ID + " = " + task.getId() + ";", null);
        return c.getCount() > 0;
    }
}
