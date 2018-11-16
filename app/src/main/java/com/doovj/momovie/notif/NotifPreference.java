package com.doovj.momovie.notif;

import android.content.Context;
import android.content.SharedPreferences;

public class NotifPreference {
    public final String PREF_NAME = "NotifPreference";
    public final String KEY_DAILY_REMINDER = "dailyReminder";
    public final String KEY_RELEASE_TODAY = "releaseToday";
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public NotifPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setDailyReminder(boolean checked) {
        editor.putBoolean(KEY_DAILY_REMINDER, checked);
        editor.commit();
    }

    public boolean getDailyReminder() {
        return sharedPreferences.getBoolean(KEY_DAILY_REMINDER, false);
    }

    public void setReleaseToday(boolean c) {
        editor.putBoolean(KEY_RELEASE_TODAY, c);
        editor.commit();
    }

    public boolean getReleaseToday() {
        return sharedPreferences.getBoolean(KEY_RELEASE_TODAY, false);
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }
}
