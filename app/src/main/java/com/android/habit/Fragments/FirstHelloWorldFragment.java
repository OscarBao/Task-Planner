package com.android.habit.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.habit.R;

/**
 * Created by Oscar_Local on 6/10/2016.
 */
public class FirstHelloWorldFragment extends Fragment implements View.OnClickListener {
    ProgressBar bar;
    Button decreaseHealthBy10;
    Button decreaseHealthBy50;
    Button increaseHealthBy20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_hello_world, container, false);

        bar = (ProgressBar) v.findViewById(R.id.bar);
        decreaseHealthBy10 = (Button) v.findViewById(R.id.decrease_health_by_10);
        decreaseHealthBy50 = (Button) v.findViewById(R.id.decrease_health_by_50);
        increaseHealthBy20 = (Button) v.findViewById(R.id.increase_health_by_20);

        decreaseHealthBy10.setOnClickListener(this);
        decreaseHealthBy50.setOnClickListener(this);
        increaseHealthBy20.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == (R.id.decrease_health_by_10)) {
            bar.setProgress(bar.getProgress()-bar.getMax()/10);
        }
        else if(v.getId() == R.id.decrease_health_by_50) {
            bar.setProgress(bar.getProgress()-bar.getMax()/2);
        }
        else if(v.getId() == R.id.increase_health_by_20) {
            bar.setProgress(bar.getProgress()+bar.getMax()/5);
        }
    }
}
