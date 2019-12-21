package com.theindianappguy.letschat.helpingClasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    //Defining sharedpreference
    SharedPreferences sharedPreferences;
    //creating editor
    SharedPreferences.Editor editor;
    //context
    Context context;
    //private mode
    int PRIVATE_MODE = 0;
    //defining name for preference
    private static final String PREF_NAME = "LetsChatPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_USERKEY = "userKey";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USERPHONENUMBER = "phonenumber";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERSTATUS = "userstatus";
    private static final String KEY_USERProfileUrl = "profileurl";

    //Constructor
    public SessionManagement(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void CreateLoginSession(String userKey, String userPhonenumber, String userEmail, String userstatus, String profileUrl) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERKEY, userKey);
        editor.putString(KEY_USERPHONENUMBER, userPhonenumber);
        editor.putString(KEY_EMAIL, userEmail);
        editor.putString(KEY_USERSTATUS, userstatus);
        editor.putString(KEY_USERProfileUrl, profileUrl);
        editor.commit();
    }

    public String getKeyUserkey() {
        return sharedPreferences.getString(KEY_USERKEY, "");
    }

    public String getKeyUserphonenumber() {
        return sharedPreferences.getString(KEY_USERPHONENUMBER, "");
    }

    public String getKeyEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getKeyUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public boolean IsLoggedin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
