package com.interview.healthcare.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.interview.healthcare.models.User;

public class AppPreferenceManager {

    private static SharedPreferences mSharedPref;
    public static final String Login_MODEL_KEY = "Login_Model";


    public AppPreferenceManager() {
    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }


    public static void setLoginModel(User loginModel) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(Login_MODEL_KEY, new Gson().toJson(loginModel));
        prefsEditor.apply();
    }

    public static User getLoginModel() {
        String data = mSharedPref.getString(Login_MODEL_KEY, "");
        User model  = new Gson().fromJson(data, User.class);
        return model;
    }


    public static void clearAllPreferences() {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }

}
