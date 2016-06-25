package com.android.habit.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.habit.Interfaces.DBConstants;
import com.android.habit.Objects.Task;
import com.android.habit.StaticObjects.DaysManager;
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
        super(context, DBConstants.DATABASE_DOT_DB_NAME, null, DBConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstants.QUERY_CREATE_TASK_TABLE);
    }

    public void removeTaskFromDatabase(Task task) {
        SQLiteDatabase wdb = this.getWritableDatabase();
        removeTaskFromDatabase(task, DBConstants.TASKS_TABLE_NAME, wdb);
    }

    public void clearDatabase() {
        SQLiteDatabase wdb = this.getWritableDatabase();
        wdb.execSQL("DELETE FROM " + DBConstants.TASKS_TABLE_NAME + ";");
    }

    public void addTaskToDatabase(Task task) {
        SQLiteDatabase wdb = this.getWritableDatabase();
        //TODO: Do a find check to see if already in
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.TASKS_COLUMN_NAME, task.getName());
        cv.put(DBConstants.TASKS_COLUMN_DESCRIPTION, task.getDescription());
        cv.put(DBConstants.TASKS_COLUMN_POINTS, task.getTaskPoints());
        cv.put(DBConstants.TASKS_COLUMN_DATE, task.getDateNum());
        wdb.insert(DBConstants.TASKS_TABLE_NAME, null, cv);
        wdb.close();
    }

    public void moveTasksToNextDay(long oldDateNum, long newDateNum) {
        SQLiteDatabase wdb = this.getWritableDatabase();
        wdb.execSQL("UPDATE " + DBConstants.TASKS_TABLE_NAME + " SET " + DBConstants.TASKS_COLUMN_DATE + " = " + newDateNum
                    + " WHERE " + DBConstants.TASKS_COLUMN_DATE + " = " + oldDateNum + ";");
        wdb.close();
    }

    public void moveAllTodaysTasksToNextDay() {
        SQLiteDatabase wdb = this.getWritableDatabase();
        String updateTasksToTodayQuery =
                (
                        "UPDATE " + DBConstants.TASKS_TABLE_NAME + " SET " + DBConstants.TASKS_COLUMN_DATE
                        + "=" + DaysManager.getTodayAsLong() + " WHERE " + DBConstants.TASKS_COLUMN_DATE
                        + " < " + DaysManager.getTodayAsLong() + ";"
                        );
        wdb.execSQL(updateTasksToTodayQuery);
        wdb.close();
    }

    public ArrayList<Task> getThisDaysOverdueTasks(long dateNum) {
        SQLiteDatabase rdb = this.getReadableDatabase();
        Cursor c = rdb.rawQuery("SELECT * FROM " + DBConstants.TASKS_TABLE_NAME + " WHERE " + DBConstants.TASKS_COLUMN_DATE + " < " + dateNum + ";", null);

        System.out.println("-------------Cursor size is " + c.getCount());

        ArrayList<Task> spit = new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            spit.add(buildTaskFromCursor(c));
        }
        rdb.close();
        return spit;

    }

    public void updateTaskList() {
        updateTaskList(DBConstants.TASKS_TABLE_NAME, this.getReadableDatabase());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldNum, int newNum) {
        switch(oldNum) {
            case 1:
                db.execSQL("ALTER TABLE " + DBConstants.TASKS_TABLE_NAME + " ADD COLUMN " + DBConstants.TASKS_COLUMN_DATE + " INTEGER;");
                break;
            case 2:
                db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TASKS_TABLE_NAME);
                onCreate(db);
                break;
            case 3:
                db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TASKS_TABLE_NAME);
                onCreate(db);
                break;
            case 4:
                db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TASKS_TABLE_NAME);
                onCreate(db);
                break;
        }
    }

    /*
    --------------->Private methods
     */
    private void removeTaskFromDatabase(Task task, String table, SQLiteDatabase db) {
        if(!taskIsInDatabase(task, table, db)) return;
        db.execSQL("DELETE FROM " + table + " WHERE " + DBConstants.TASKS_COLUMN_ID + " LIKE " + task.getId() + ";");
    }

    private void updateTaskList(String table, SQLiteDatabase db) {
        TasksList.getList().clear();

        Cursor c = db.rawQuery("SELECT * FROM " + table + " ORDER BY " + DBConstants.TASKS_COLUMN_DATE + " ASC;", null);
        if(c.getCount() == 0) {
            return;
        }
        long prevLatestDate = 0;
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            long latestDate = c.getLong(c.getColumnIndex(DBConstants.TASKS_COLUMN_DATE));
            if(!(latestDate == prevLatestDate)) {
                Task newTaskToAdd = new Task();
                newTaskToAdd.setDateNum(latestDate);
                TasksList.addTask(newTaskToAdd);
            }
            Task newTask = buildTaskFromCursor(c);
            TasksList.addTask(newTask);

            prevLatestDate = latestDate;
        }

        for(Task task : TasksList.getList()) {
            System.out.println("");
            Log.i("TasksDB PrintList", "Next task is: " + task.getName() + " with id " + task.getId() + " and date " + task.getDateNum());
        }
    }

    private Task buildTaskFromCursor(Cursor c) {
        Task newTask = new Task();
        newTask.setId(c.getInt(c.getColumnIndex(DBConstants.TASKS_COLUMN_ID)));
        newTask.setName(c.getString(c.getColumnIndex(DBConstants.TASKS_COLUMN_NAME)));
        newTask.setDescription(c.getString(c.getColumnIndex(DBConstants.TASKS_COLUMN_DESCRIPTION)));
        newTask.setTaskPoints(c.getInt(c.getColumnIndex(DBConstants.TASKS_COLUMN_POINTS)));
        newTask.setDateNum(c.getLong(c.getColumnIndex(DBConstants.TASKS_COLUMN_DATE)));
        return newTask;
    }

    private int findTaskId(Task task, String table, SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM " + table + " WHERE " + DBConstants.TASKS_COLUMN_NAME + " = " + task.getName() + ";", null);
        if(c.getCount() == 0) {
            c.close();
            return 0;
        }
        c.moveToFirst();
        int id = c.getInt(c.getColumnIndex(DBConstants.TASKS_COLUMN_ID));
        c.close();
        return id;
    }

    private boolean taskIsInDatabase(Task task, String table, SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM " + table + " WHERE " + DBConstants.TASKS_COLUMN_ID + " = " + task.getId() + ";", null);
        int count = c.getCount();
        c.close();
        return count > 0;
    }
}
