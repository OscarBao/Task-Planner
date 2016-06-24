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

    public static void updateLevelProgress(int progressChange) {
        SharedPreferences.Editor editor = sp.edit();
        updateLevelProgress(progressChange, editor);
        editor.apply();
    }
    public static int getCurrentProgress() {
        return sp.getInt(SharedPreferenceConstants.PROGRESS_BAR_PROGRESS, 0);
    }
    public static int getCurrentLevel() {
        return sp.getInt(SharedPreferenceConstants.PROGRESS_LEVEL, 0);
    }

    /*
    --------------->Private methods
     */
    private static void updateLevelProgress(int progressChange, SharedPreferences.Editor editor) {
        int newProgress = 0;
        int levelsChange = 0;
        levelsChange = (getCurrentProgress() + progressChange)/100;
        if(getCurrentProgress() + progressChange >= 100) {
            newProgress = (getCurrentProgress() + progressChange)%100;
        }
        else if(getCurrentProgress() + progressChange <= -100) {
            newProgress = 100 - (getCurrentProgress() + progressChange)%100;
        }
        else {
            newProgress = getCurrentProgress() + progressChange;
        }
        editor.putInt(SharedPreferenceConstants.PROGRESS_LEVEL, (getCurrentLevel() + levelsChange > 0)? (getCurrentLevel() + levelsChange) : 0);
        editor.putInt(SharedPreferenceConstants.PROGRESS_BAR_PROGRESS, newProgress);
    }
}
