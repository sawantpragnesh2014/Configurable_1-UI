package com.example.configurableui.universal;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedPref {

    static final String mypref = "mysharefpref";
    private static SharedPref sharedPrefs;
    private SharedPreferences sharedPreferences;
    private Map<String, Object> sharedPrefMap;


    public SharedPref(Context context) {
        sharedPrefMap = new HashMap<>();
        sharedPreferences = context.getSharedPreferences(mypref, Context.MODE_PRIVATE);
    }


    public static void initialize(Context context) {

        if (sharedPrefs == null) {
            sharedPrefs = new SharedPref(context);
        }
    }

    public static SharedPref getInstance() {
        if (sharedPrefs == null) {
            throw new RuntimeException("Initialize SharedPrefs");
        }
        return sharedPrefs;
    }

    public void addString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        sharedPrefMap.put(key, value);
    }

    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }


    public void addBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
        sharedPrefMap.put(key, value);
    }

    public void addInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
        sharedPrefMap.put(key, value);
    }

    public String getString(String key, String defaultValue) {
        if (sharedPrefMap.containsKey(key)) {
            return (String) sharedPrefMap.get(key);
        }

        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (sharedPrefMap.containsKey(key)) {
            return (boolean) sharedPrefMap.get(key);
        }

        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        if (sharedPrefMap.containsKey(key)) {
            return (int) sharedPrefMap.get(key);
        }

        return sharedPreferences.getInt(key, defaultValue);
    }


}
