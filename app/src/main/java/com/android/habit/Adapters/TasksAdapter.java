package com.android.habit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.habit.Objects.Task;
import com.android.habit.R;

import java.util.ArrayList;

/**
 * Created by Oscar_Local on 6/14/2016.
 */
public class TasksAdapter extends ArrayAdapter<Task> {
    public TasksAdapter(Context context, ArrayList<Task> tasks)  {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //Get the data item for this position
        Task task = getItem(position);

        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listitem_task, parent, false);
        }

        TextView taskName = (TextView) view.findViewById(R.id.listitem_task_textview_name);
        TextView taskDescription = (TextView) view.findViewById(R.id.listitem_task_textview_description);
        TextView taskPoints = (TextView) view.findViewById(R.id.listitem_task_textview_points);

        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
        taskPoints.setText(task.getTaskPointsAsString());

        return view;
    }
}
