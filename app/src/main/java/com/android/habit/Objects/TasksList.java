package com.android.habit.Objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Oscar_Local on 6/14/2016.
 */
public class TasksList implements Iterable<Task> {
    static List<Task> tasks;

    /**
     * Default constructor
     */
    public TasksList() {}

    public static void startList() {tasks = new ArrayList<>();}

    public static void addTask(Task task) {
        tasks.add(task);
    }

    public Iterator<Task> iterator() {
        return tasks.iterator();
    }

    public static List<Task> getList() {
        return tasks;
    }
}
