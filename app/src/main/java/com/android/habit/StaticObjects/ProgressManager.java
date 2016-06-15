package com.android.habit.StaticObjects;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.habit.Interfaces.SharedPreferenceConstants;

/**
 * Created by Oscar_Local on 6/15/2016.
 */
public class ProgressManager {
    Context context;
    static SharedPreferences sp;

    public ProgressManager(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(SharedPreferenceConstants.PROGRESSDATA, Context.MODE_PRIVATE);
    }

    public static void addProgress(int progress) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(SharedPreferenceConstants.PROGRESS_BAR_PROGRESS, getCurrentProgress() + progress);
        editor.apply();
    }
    public static void subtractProgress(int progress) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(SharedPreferenceConstants.PROGRESS_BAR_PROGRESS, getCurrentProgress() - progress);
        editor.apply();
    }
    public static int getCurrentProgress() {
        return sp.getInt(SharedPreferenceConstants.PROGRESS_BAR_PROGRESS, 0);
    }

    /*
    --------------->Private methods
     */
}
