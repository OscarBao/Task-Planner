package com.android.habit.Interfaces;

/**
 * Created by Oscar_Local on 6/15/2016.
 */
public interface DBConstants {

    //App big data
    String DATABASE_DOT_DB_NAME = "Tasks.db";
    int DATABASE_VERSION = 4;

    //Table names
    String TASKS_TABLE_NAME = "TasksTable";

    /*
    /////////////////////////////////////////////
    ///////////////TASKS TABLE///////////////////
    /////////////////////////////////////////////
     */
    //Columns
    String TASKS_COLUMN_ID = "TasksTableColumnID";
    String TASKS_COLUMN_NAME = "TasksTableColumnName";
    String TASKS_COLUMN_DESCRIPTION = "TasksTableColumnDescription";
    String TASKS_COLUMN_POINTS = "TasksTableColumnPoints";
    String TASKS_COLUMN_DATE = "TasksTableColumnDateInt";

    //Queries
    String QUERY_CREATE_TASK_TABLE = "CREATE TABLE "
            + TASKS_TABLE_NAME + "("
            + TASKS_COLUMN_ID + " INTEGER PRIMARY KEY, "
            + TASKS_COLUMN_NAME + " TEXT, "
            + TASKS_COLUMN_DESCRIPTION + " TEXT, "
            + TASKS_COLUMN_POINTS + " INTEGER, "
            + TASKS_COLUMN_DATE + " LONG"
            + ");";


}
