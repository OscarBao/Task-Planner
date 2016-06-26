package com.android.habit.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.habit.Fragments.HabitFragment;
import com.android.habit.Objects.Task;
import com.android.habit.R;
import com.android.habit.StaticObjects.DaysManager;
import com.android.habit.StaticObjects.TasksList;

import java.util.ArrayList;

/**
 * Created by Oscar_Local on 6/14/2016.
 */
public class TasksAdapter extends ArrayAdapter<Task> {
    HabitFragment parentFragment;
    public TasksAdapter(Context context, ArrayList<Task> tasks, HabitFragment parentFragment)  {
        super(context, 0, tasks);
        this.parentFragment = parentFragment;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        int viewType = getItemViewType(position);


        int layoutResId = (viewType == 1)? R.layout.listitem_task : R.layout.listitem_section_day;
        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(layoutResId, parent, false);
        }

        if(viewType == 0) {
            ListView listView = (ListView) view.findViewById(R.id.fragment_habit_listview);
            TextView date = (TextView) view.findViewById(R.id.listitem_section_day_dayname);
            date.setText(nextItemDate(position));
        }
        else if(viewType == 1) {
            //Fill in task data
            final int pos = position;
            Task task = getItem(position);
            TextView taskName = (TextView) view.findViewById(R.id.listitem_task_textview_name);
            TextView taskDescription = (TextView) view.findViewById(R.id.listitem_task_textview_description);
            TextView taskPoints = (TextView) view.findViewById(R.id.listitem_task_textview_points);
            Button taskComplete = (Button) view.findViewById(R.id.listitem_task_button_complete);

            taskName.setText(task.getName());
            taskDescription.setText(task.getDescription());
            taskPoints.setText(task.getTaskPointsAsString());
            taskComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFragment.completeTask(getItem(pos), pos);
                }
            });
        }
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(TasksList.getList().get(position).getId() == -1) return 0;
        else return 1;
    }

    /*
    --------------->Private methods
     */
    private String nextItemDate(int currentPos) {
        if(TasksList.getList().size() - 1 > currentPos) {
            return DaysManager.getFriendlyDateString(getItem(currentPos).getDateNum());
        }
        else return "";
    }
}
