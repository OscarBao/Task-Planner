package com.android.habit;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Oscar_Local on 6/10/2016.
 */
public class SecondHelloWorldFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_second_hello_world, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
