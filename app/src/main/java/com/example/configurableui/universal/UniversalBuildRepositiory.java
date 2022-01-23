package com.example.configurableui.universal;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class UniversalBuildRepositiory {

   private final static String JSON_FILE_ANDROID_WEAR = "universalData.json";

    private static final String TAG = UniversalBuildRepositiory.class.getSimpleName();

    private static UniversalBuildRepositiory repositoryInstance;

    public static UniversalBuildRepositiory getInstance() {
        if (repositoryInstance == null) {
            repositoryInstance = new UniversalBuildRepositiory();
        }
        return repositoryInstance;
    }


    public static Groups getUniversauildData(Context context) {

        String jsonString = getAssetsJSON(JSON_FILE_ANDROID_WEAR,context);
        Gson gson = new Gson();
        Groups groups = gson.fromJson(jsonString, Groups.class);

        return groups;
    }

    private static String getAssetsJSON(String filename, Context context) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    public int  getProgress(){
        return SharedPref.getInstance().getInt( "ProgressValue",0 );
    }

    public boolean getProxyBoolean(){
        return SharedPref.getInstance().getBoolean("enableProxyStatus", false);
    }

    public boolean getRootBoolean(){
        return SharedPref.getInstance().getBoolean("enableRootStatus", false);
    }

    public boolean getCacheBoolean(){
        return SharedPref.getInstance().getBoolean("enableCacheStatus", false);
    }

    public int getRadioButtonId(){
        return SharedPref.getInstance().getInt("CheckRadioButtonId", 0);
    }


}
