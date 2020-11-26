package com.cegep.dailyplanner;

import android.content.Context;
import android.content.SharedPreferences;

public class UserStorage {

    public static final String SHARED_PREFS = "USER_PREFS";
    public static final String EMAIL = "USER_PREFS";

    public static void insertUserName(Context context, String email) {
        SharedPreferences sharedPreferences =context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(EMAIL, email);
        editor.apply();
    }

    public static String getUserEmail(Context context) {
        SharedPreferences sharedPreferences =context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, null);
    }

    public static void clearUserName(Context context) {
        SharedPreferences sharedPreferences =context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }
}
