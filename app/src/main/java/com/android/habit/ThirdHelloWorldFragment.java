package com.android.habit;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Oscar_Local on 6/10/2016.
 */
public class ThirdHelloWorldFragment extends Fragment implements View.OnClickListener {

    ImageButton fab;
    TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_third_hello_world, container, false);

        fab = (ImageButton) v.findViewById(R.id.fab);
        txt = (TextView) v.findViewById(R.id.helloWorldText);
        fab.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fab:
                if (txt.getText().equals(getResources().getString(R.string.buttonclicked_textview))) {
                    txt.setText(R.string.buttonunclicked_textview);
                    fab.setImageResource(R.drawable.green_button);
                } else {
                    txt.setText(R.string.buttonclicked_textview);
                    fab.setImageResource(R.drawable.gray_button);
                }


                break;
            default:
                break;
        }
    }
}
